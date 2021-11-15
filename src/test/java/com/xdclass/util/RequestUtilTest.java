package com.xdclass.util; 

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/** 
* RequestUtil Tester. 
* @author: Fanyong Kong
* @since : 11/13/2021
* @version 1.0 
*/ 
public class RequestUtilTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: doGet(String url, String headers, String body)
    *
    */
    @Test
    public void testDoGet() throws Exception {

        String url = "https://api.xdclass.net/user/api/v1/order/find_orders";
        String headers = "{\"token\":\"xdclasseyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZGNsYXNzIiwicm9sZXMiOiIxIiwiaW1nIjoiaHR0cHM6Ly94ZC12aWRlby1wYy1pbWcub3NzLWNuLWJlaWppbmcuYWxpeXVuY3MuY29tL3hkY2xhc3NfcHJvL2RlZmF1bHQvaGVhZF9pbWcvMjEuanBlZyIsImlkIjo2NzUwMjIsIm5hbWUiOiJkYXNkYWRhc2QxMTEiLCJpYXQiOjE2MzY3NzYyODksImV4cCI6MTYzNzM4MTA4OX0.2kjBNQ3VtX7k5ivTxiSZ1n5MG3ZqVuZfI_-P_f6Tbw0\"}"; //
    //    String headers = "";
        String body = "{}";

    //    String url = "https://api.xdclass.net/pub/api/v1/web/video_detail";
    //    String headers = "";
    //    String body = "{\"video_id\":53}";

        CloseableHttpResponse httpResponse = RequestUtil.doGet(url, headers, body);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(httpResponse.getStatusLine().getStatusCode());
        // assert by response code
        assertEquals(statusCode, 200);

        // get the entity object
        HttpEntity entity = httpResponse.getEntity();
        // convert the entity to string
        String entityStr = EntityUtils.toString(entity, "UTF-8");
        if(entityStr!=null && !entityStr.isEmpty()){
            JSONObject jsonObject = JSONObject.parseObject(entityStr);
            if(jsonObject!=null && !jsonObject.isEmpty() && jsonObject.containsKey("data")){
                JSONObject firstLevelDataObject = jsonObject.getJSONObject("data");  // the data object
                if(firstLevelDataObject!=null && !firstLevelDataObject.isEmpty() && firstLevelDataObject.containsKey("data")){
                    assertTrue(true);
                }else{
                    Assert.fail("the second level data is not available");
                }
            }else{
                Assert.fail("the first level data is not available");
            }
        }else{
            Assert.fail("response body is empty");
        }
    }

    /**
    *
    * Method: doPost(String url, String headers, String body)
    *
    */
    @Test
    public void testDoPost() throws Exception {

        String url = "https://api.xdclass.net/pub/api/v1/web/web_login";
        String headers = "{\"Content-Type\": \"application/x-www-form-urlencoded\"}";
        String body = "{\"phone\": \"13113777555\", \"pwd\": \"1234567890\"}";

    //    String headers = "";
    //    String body = "";

        CloseableHttpResponse httpResponse = RequestUtil.doPost(url, headers, body);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(httpResponse.getStatusLine().getStatusCode());
        // assert by response code
        assertEquals(statusCode, 200);

        // get the entity object
        HttpEntity entity = httpResponse.getEntity();
        // convert the entity to string
        String entityStr = EntityUtils.toString(entity, "UTF-8");
        System.out.println(entityStr);

        if(entityStr!=null && !entityStr.isEmpty()){
            JSONObject jsonObject = JSONObject.parseObject(entityStr); // the whole json object
            if(jsonObject!=null && !jsonObject.isEmpty() && jsonObject.containsKey("data")){
                JSONObject dataObject = jsonObject.getJSONObject("data");  // the data object
                if(dataObject!=null && !dataObject.isEmpty() && dataObject.containsKey("token")){
                    assertTrue(true);
                }else{
                    Assert.fail("the data field in post response does not contain token field!");
                }
            }else{
                Assert.fail("post response does not contain data field!");
            }
        }else{
            Assert.fail("post response body is empty!");
        }


    }


} 
