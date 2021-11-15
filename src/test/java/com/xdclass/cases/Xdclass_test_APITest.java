package com.xdclass.cases; 

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdclass.beans.HttpReq;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import static com.xdclass.cases.Xdclass_test_API.*;

/** 
* Xdclass_test_API Tester. 
* @author: Fanyong Kong
* @since : 11/13/2021
* @version 1.0 
*/ 
public class Xdclass_test_APITest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: loadAllReqsByApp(String app)
* 
*/ 
@Test
public void testLoadAllReqsByApp() throws Exception {
    Boolean flag = true;
    List<HttpReq> list = loadAllReqsByApp("小滴课堂");
    int id = -1;
    for(int i=0; i<list.size(); i++){
        id = list.get(i).getId();
        System.out.println(id);
        System.out.println(list.get(i).getUpdate_time());
        if(id < 0){
            flag = false;
        }
    }
    Assert.assertTrue(flag);
} 

/** 
* 
* Method: loadReqById(int id)
* 
*/ 
@Test
public void testLoadReqById() throws Exception {

    Boolean flag = true;
    List<HttpReq> list = loadReqById(1);
    int id = -1;
    for(int i=0; i<list.size(); i++){
        id = list.get(i).getId();
        System.out.println(id);
        System.out.println(list.get(i).getUpdate_time());
        if(id < 0){
            flag = false;
        }
    }
    Assert.assertTrue(flag);
} 

/** 
* 
* Method: updateReqById(String response, Boolean isPass, String message, int id)
* 
*/ 
@Test
public void testUpdateReqById() throws Exception {
    Date date = new Date();
    Timestamp ts = new Timestamp(date.getTime());
    int number = updateReqById("{}", "True", "this is a test msg", ts, 11);
    Assert.assertEquals(number, 1);
} 

/** 
* 
* Method: runAllCases(String app) 
* 
*/ 
@Test
public void testRunAllCases() throws Exception {
    runAllCases("小滴课堂");
    // to simulate the negative case, please change the login username in DB.
} 

/** 
* 
* Method: runSingalCase(HttpReq req)
* 
*/ 
@Test
public void testRunSingalCase() throws Exception {

    HttpReq req = loadReqById(1).get(0);
    CloseableHttpResponse httpResponse = runSingleCase(req);
    HttpEntity entity = httpResponse.getEntity();
    String entityStr = EntityUtils.toString(entity, "UTF-8");
    JSONObject jsonObject = JSON.parseObject(entityStr);
    Assert.assertEquals(jsonObject.getString("code"), 0+"");
    System.out.println(entityStr);

} 

/** 
* 
* Method: assertResponse(String oneCase, String response) 
* 
*/ 
@Test
public void testAssertResponse() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: sendTestReport(String app) 
* 
*/ 
@Test
public void testSendTestReport() throws Exception {
    sendTestReport("小滴课堂");
} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
@Test
public void testMain() throws Exception { 
//TODO: Test goes here... 
} 


} 
