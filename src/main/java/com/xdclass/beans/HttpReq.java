package com.xdclass.beans;

import java.sql.Timestamp;

/**
 * @program : xdclassAPITest
 * @ Author      ：Fanyong Kong
 * @ Date        ：Created in 15:59 2021/11/13 2021
 * @ Description ：This is the request bean
 * @Version : 1.0$
 */
public class HttpReq {
    private int id=0;
    private String app="";
    private String module="";
    private String title="";
    private String method="";
    private String url="";
    private String run="";
    private String headers="";
    private int pre_case_id=0;
    private String pre_fields="";
    private String request_body="";
    private String expect_result="";
    private String assert_type="";
    private String pass= "";
    private String msg="";
    private Timestamp update_time=null;
    private String response="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public int getPre_case_id() {
        return pre_case_id;
    }

    public void setPre_case_id(int pre_case_id) {
        this.pre_case_id = pre_case_id;
    }

    public String getPre_fields() {
        return pre_fields;
    }

    public void setPre_fields(String pre_fields) {
        this.pre_fields = pre_fields;
    }

    public String getRequest_body() {
        return request_body;
    }

    public void setRequest_body(String request_body) {
        this.request_body = request_body;
    }

    public String getExpect_result() {
        return expect_result;
    }

    public void setExpect_result(String expect_result) {
        this.expect_result = expect_result;
    }

    public String getAssert_type() {
        return assert_type;
    }

    public void setAssert_type(String assert_type) {
        this.assert_type = assert_type;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
