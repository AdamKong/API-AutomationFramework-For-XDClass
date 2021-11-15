package com.xdclass.util;

import com.alibaba.fastjson.JSONObject;
import com.xdclass.base.Testbase;
import com.xdclass.beans.HttpReq;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RequestUtil {

    /**
     * @Description: this is the doGet method.
     * @params: [url, headers, body]
     * @return: org.apache.http.client.methods.CloseableHttpResponse
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static CloseableHttpResponse doGet(String url, String headers, String body){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = null;
        CloseableHttpResponse httpResponse = null;

        try {

            // read the parameters in body and append them at the end of the url
            URIBuilder uriBuilder = new URIBuilder(url);
            if(body!=null && !body.isEmpty()){
                JSONObject jsonObjectBody = JSONObject.parseObject(body);
                if(jsonObjectBody!=null && !jsonObjectBody.isEmpty()){
                    List<NameValuePair> list = new LinkedList<>();
                    Map<String, Object> bodymap = (Map<String, Object>)jsonObjectBody;
                    for(Map.Entry<String, Object> entry: bodymap.entrySet()){
                        list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                    }
                    uriBuilder.setParameters(list);
                }
            }
            httpget = new HttpGet(uriBuilder.build());


            // parse the header json and add into request header
            if(headers!=null && !headers.isEmpty()){
                JSONObject jsonObject = JSONObject.parseObject(headers);
                if(jsonObject!=null && !jsonObject.isEmpty()){
                    Map<String, Object> headermap = (Map<String, Object>)jsonObject;
                    for(Map.Entry<String, Object> entry: headermap.entrySet()){
                        httpget.addHeader(entry.getKey(), entry.getValue().toString());
                    }
                }
            }

            httpResponse = httpclient.execute(httpget);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return httpResponse;
    }



    /**
     * @Description: this is the doPost method.
     * @params: [url, headers, body]
     * @return: org.apache.http.client.methods.CloseableHttpResponse
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static CloseableHttpResponse doPost(String url, String headers, String body) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = null;
        CloseableHttpResponse httpResponse = null;

        try {
            httpPost = new HttpPost(url);

            // parse the header json and add into request header
            if(headers!=null && !headers.isEmpty()){
                JSONObject jsonObjectHeaders = JSONObject.parseObject(headers);
                if(jsonObjectHeaders!=null && !jsonObjectHeaders.isEmpty()){
                    Map<String, Object> headermap = (Map<String, Object>)jsonObjectHeaders;
                    for(Map.Entry<String, Object> entry: headermap.entrySet()){
                        httpPost.addHeader(entry.getKey(), entry.getValue().toString());
                    }
                }
            }

            // parse the body json and add into request body
            if(body!=null && !body.isEmpty()){
                JSONObject jsonObjectBody = JSONObject.parseObject(body);
                if(jsonObjectBody!=null && !jsonObjectBody.isEmpty()){
                    List<NameValuePair> list = new LinkedList<>();
                    Map<String, Object> bodymap = (Map<String, Object>)jsonObjectBody;
                    for(Map.Entry<String, Object> entry: bodymap.entrySet()){
                        list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                    }
                    UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
                    httpPost.setEntity(entityParam);
                }
            }

            // execute the http request and get the response
            httpResponse = httpclient.execute(httpPost);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpResponse;
    }



    /**
     * @Description: this is to encapsulate the request methods
     * @params: [req]
     * @return: org.apache.http.client.methods.CloseableHttpResponse
     * @Author: Fanyong Kong
     * @Date: 2021/11/14
     */
    public static CloseableHttpResponse doRequest(HttpReq req) {
        String method = req.getMethod();
        String url = Testbase.getProperty("webAPIHost")+req.getUrl();
        String headers = req.getHeaders();
        String body = req.getRequest_body();

        CloseableHttpResponse httpResponse = null;
        if(method!=null && !method.isEmpty()){
            if(method.equalsIgnoreCase("get")){
                httpResponse = doGet(url, headers, body);
            }else if(method.equalsIgnoreCase("post")){
                httpResponse = doPost(url, headers, body);
            }else{
                System.out.println("method is not supported!");
            }
        }

        return httpResponse;
    }

}