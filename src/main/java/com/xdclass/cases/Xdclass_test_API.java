package com.xdclass.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdclass.beans.HttpReq;
import com.xdclass.util.DBUtil;
import com.xdclass.util.Log;
import com.xdclass.util.RequestUtil;
import com.xdclass.util.SendEmail;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.xdclass.util.SendEmail.sendEmail;

/**
 * @program : xdclassAPITest
 * @ Author      ：Fanyong Kong
 * @ Date        ：Created in 18:46 2021/11/13 2021
 * @ Description ：This is to run the tests in DB
 * @Version : 1.0
 */
public class Xdclass_test_API {

    private SendEmail SendEail;

    /**
     * @Description: load all of the requests in db by app
     * @params: [app]
     * @return: java.util.List<com.xdclass.beans.HttpAPI>
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static List<HttpReq> loadAllReqsByApp(String app){
        List<HttpReq> list = DBUtil.getReqByApp(app);
        return list;
    }

    /**
     * @Description: load requests in db by id
     * @params: [id]
     * @return: java.util.List<com.xdclass.beans.HttpAPI>
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static List<HttpReq> loadReqById(int id){
        List<HttpReq> list = DBUtil.getReqById(id);
        return list;
    }

    /**
     * @Description: update a request by id
     * @params: [response, pass, message, ts, id]
     * @return: int
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static int updateReqById(String response, String pass, String message, Timestamp ts, int id){
        int updateNumber = DBUtil.updateReqById(response, pass, message, ts, id);
        return updateNumber;
    }


    /**
     * @Description: this is to run all of the requests in db
     * @params: [app]
     * @return: void
     * @Author: Fanyong Kong
     * @Date: 2021/11/14
     */
    public static void runAllCases(String app){
        Log.startTestCase("Run All Cases");
        List<HttpReq> list = loadAllReqsByApp(app);
        for(int i=0; i<list.size(); i++){
            HttpReq req = list.get(i);
            if("yes".equalsIgnoreCase(req.getRun())){
                // run the case
                CloseableHttpResponse httpResponse = runSingleCase(req);
                //assertion
                JSONObject jsonObjectResult = assertResponse(req, httpResponse);
                //update DB
                Date date = new Date();
                Timestamp ts = new Timestamp(date.getTime());
                updateReqById(jsonObjectResult.getString("responseContent"), jsonObjectResult.getString("isPassed"), jsonObjectResult.getString("msg"), ts, req.getId());
            }
        }
        Log.endTestCase("Run All Cases");

        Log.info("Start sending test report!");
        // send test report
        sendTestReport(app);
        Log.info("Test report has been sent, please check your email!");
    }


    /**
     * @Description: This is to run a single request in DB
     * @params: [req]
     * @return: org.apache.http.client.methods.CloseableHttpResponse
     * @Author: Fanyong Kong
     * @Date: 2021/11/14
     */
    public static CloseableHttpResponse runSingleCase(HttpReq req){

        CloseableHttpResponse httpResponse = null;
        int pre_case_id = req.getPre_case_id();

        if(pre_case_id > -1){
            HttpReq preReq = DBUtil.getReqById(pre_case_id).get(0);
            CloseableHttpResponse preReqHttpResponse = runSingleCase(preReq);
            JSONObject jsonObjPreReqResult = assertResponse(preReq, preReqHttpResponse);
            if(jsonObjPreReqResult.getString("isPassed").equalsIgnoreCase("True")){
                JSONArray pre_fields = JSON.parseArray(req.getPre_fields());
                for(int i=0; i<pre_fields.size();i++){
                    JSONObject pre_field = (JSONObject) pre_fields.get(i);
                    if(pre_field.getString("scope").equalsIgnoreCase("header")){
                        String pre_field_name = pre_field.getString("field");
                        JSONObject headersObject = JSON.parseObject(req.getHeaders());
                        Map<String,Object> headersMap = (Map<String,Object>)headersObject;
                        JSONObject responseObj = JSONObject.parseObject(jsonObjPreReqResult.getString("responseContent"));
                        String preFieldInResponse = responseObj.getJSONObject("data").getString(pre_field_name);
                        headersMap.put(pre_field_name, preFieldInResponse);
                        req.setHeaders(JSONObject.toJSONString(headersMap));
                    }else{
                        System.out.println("replace the related filed in body"); // omit the body replacement
                    }
                }
            }else{
                // pre request failed. 改变当前or pre request msg?
//                String errmsg = "reason: pre case failed to execute, pre_case_msg:" + jsonObjPreReqResult.getString("msg");
//                req.setMsg(errmsg);
                return httpResponse;
            }
        }

        httpResponse = RequestUtil.doRequest(req);

        return httpResponse;
    }

    /**
     * @Description: This is to assert the http response according to the assert type set in DB
     * @params: [req, httpResponse]
     * @return: com.alibaba.fastjson.JSONObject - isPassed, msg
     * @Author: Fanyong Kong
     * @Date: 2021/11/14
     */
    public static JSONObject assertResponse(HttpReq req, CloseableHttpResponse httpResponse){
        JSONObject assertResultObject = new JSONObject();
        String msg="";
        if(httpResponse==null){
            assertResultObject.put("isPassed", "False");
            msg="module:" + req.getModule() + ", title:" + req.getTitle() + ", assert_type:" + req.getAssert_type()
                    + ", case_id:" + req.getId() + ", pre_case_id:" + req.getPre_case_id() + ", reason: pre case is failed";
            assertResultObject.put("msg", msg);
            assertResultObject.put("responseContent", null);

            return assertResultObject;
        }

        Boolean isPassed = false;
        String assertType = req.getAssert_type();
        String expectResult = req.getExpect_result();

        HttpEntity entity = httpResponse.getEntity();
        String entityStr = "";
        JSONObject jsonResponse = null;

        try {
            entityStr = EntityUtils.toString(entity, "UTF-8");
            jsonResponse = JSONObject.parseObject(entityStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(null != jsonResponse){
            if("code".equalsIgnoreCase(assertType)){
                // assert by code
                String codeInJson = jsonResponse.getInteger("code") + "";
                if(codeInJson.equalsIgnoreCase(expectResult)){
                    isPassed = true;
                }else{
                    isPassed = false;
                }
            }else if("data_json".equalsIgnoreCase(assertType)){
                // assert by data_json
                JSONObject firstLevelData = jsonResponse.getJSONObject("data");
                if(null!=firstLevelData && !firstLevelData.isEmpty() && firstLevelData.size()>0){
                    isPassed = true;
                }else{
                    isPassed = false;
                }
            }else if("data_json_array".equalsIgnoreCase(assertType)){
                // assert by data_json_array
                JSONArray firstLevelData = jsonResponse.getJSONArray("data");
                if(null!=firstLevelData && !firstLevelData.isEmpty() && firstLevelData.size()>0){
                    isPassed = true;
                }else{
                    isPassed = false;
                }
            }
        }

        if(jsonResponse.getString("msg") == null){
            msg="module:" + req.getModule() + ", title:" + req.getTitle() + ", assert_type:" + req.getAssert_type();
        }else{
            msg="module:" + req.getModule() + ", title:" + req.getTitle() + ", assert_type:" + req.getAssert_type()
                    + ", response:" + jsonResponse.getString("msg");
        }

        if(isPassed){
            assertResultObject.put("isPassed", "True");
        }else{
            assertResultObject.put("isPassed", "False");
        }
        assertResultObject.put("msg", msg);
//        req.setMsg(msg);
        assertResultObject.put("responseContent", entityStr);

        return assertResultObject;
    }

    /**
     * @Description: This is to send email for test reports.
     * @params: [app]
     * @return: void
     * @Author: Fanyong Kong
     * @Date: 2021/11/14
     */
    public static void sendTestReport(String app){
        String titleTemplate = "API Automation Test Result on ###0###";
        String titleWithDate = "";
        String contentTemplate = "<html><body><h4>API TEST RESULT on ###1###:</h4><table border=\"1\"><tr><th>编号</th><th>模块</th><th>标题</th><th>是否通过</th><th>备注</th><th>响应</th></tr>###2###</table></body></html>";
        String contentWithResult = "";
        String contentWithDate = "";
        List<HttpReq> list = loadAllReqsByApp(app);
        String row = "";
        for(int i=0; i<list.size(); i++){
            HttpReq hr = list.get(i);
            row += "<tr><th>"+hr.getId()+"</th><th>"+hr.getModule()+"</th><th>"+hr.getTitle()+"</th><th>"+hr.getPass()+"</th><th>"+hr.getMsg()+"</th><th>"+hr.getResponse()+"</th></tr>";
        }
        contentWithResult = contentTemplate.replaceAll("###2###",row);

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        contentWithDate = contentWithResult.replaceAll("###1###", ts.toString());
        titleWithDate = titleTemplate.replaceAll("###0###",ts.toString());

        try{
            sendEmail(titleWithDate, contentWithDate);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        // runAllCases("小滴课堂");
    }
}
