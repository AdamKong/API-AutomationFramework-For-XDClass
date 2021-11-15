package com.xdclass.util;

import com.xdclass.base.Testbase;
import com.xdclass.beans.HttpReq;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program      : xdclassAPITest
 * @ Author      ：Fanyong Kong
 * @ Date        ：Created in 14:16 2021/11/13 2021
 * @ Description ：This is to encapsulate DB releated actions
 * @Version      : 1.0
 */
public class DBUtil {

    private static String driver = "";
    private static String url = "";
    private static String username = "";
    private static String password = "";

    /**
     * @Description: This is to get a DB connection
     * @params: []
     * @return: java.sql.Connection
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static Connection getConnection(){
        Connection conn = null;

        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://" + Testbase.getProperty("dbHost") + ":"
                              + Testbase.getProperty("dbPort") + "/"
                              + Testbase.getProperty("dbName") + "?serverTimezone=GMT%2B8&useSSL=false";
        username = Testbase.getProperty("dbUsername");
        password = Testbase.getProperty("dbPassword");

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return conn;
    }

    /**
     * @Description: This is to close the rs/state/connection
     * @params: [conn, state, rs]
     * @return: void
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static void freeResrouce(Connection conn, Statement state, ResultSet rs){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(state!=null){
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description: this is to find requests by app
     * @params: [sql]
     * @return: java.util.List<com.xdclass.beans.HttpAPI>
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static List<HttpReq> getReqByApp(String app){
        List<HttpReq> APIList = new ArrayList<HttpReq>();
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";

        try {
            sql="select * from `case` where app=?";
            ps = conn.prepareStatement(sql);
            // ps.setString(1, app);
            ps.setObject(1, app);
            rs = ps.executeQuery();
            while(rs.next()){
                HttpReq api = new HttpReq();
                api.setId(rs.getInt("id"));
                api.setApp(rs.getString("app"));
                api.setModule(rs.getString("module"));
                api.setTitle(rs.getString("title"));
                api.setMethod(rs.getString("method"));
                api.setUrl(rs.getString("url"));
                api.setRun(rs.getString("run"));
                api.setHeaders(rs.getString("headers"));
                api.setPre_case_id(rs.getInt("pre_case_id"));
                api.setPre_fields(rs.getString("pre_fields"));
                api.setRequest_body(rs.getString("request_body"));
                api.setExpect_result(rs.getString("expect_result"));
                api.setAssert_type(rs.getString("assert_type"));
                api.setPass(rs.getString("pass"));
                api.setMsg(rs.getString("msg"));
                api.setUpdate_time(rs.getTimestamp("update_time"));
                api.setResponse(rs.getString("response"));

                APIList.add(api);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            freeResrouce(conn, ps, rs);
        }

        return APIList;
    }


    /**
     * @Description: This is to find case by id
     * @params: [id]
     * @return: java.util.List<com.xdclass.beans.HttpAPI>
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static List<HttpReq> getReqById(int id){
        List<HttpReq> APIList = new ArrayList<HttpReq>();
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";

        try {
            sql="select * from `case` where id=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                HttpReq api = new HttpReq();
                api.setId(rs.getInt("id"));
                api.setApp(rs.getString("app"));
                api.setModule(rs.getString("module"));
                api.setTitle(rs.getString("title"));
                api.setMethod(rs.getString("method"));
                api.setUrl(rs.getString("url"));
                api.setRun(rs.getString("run"));
                api.setHeaders(rs.getString("headers"));
                api.setPre_case_id(rs.getInt("pre_case_id"));
                api.setPre_fields(rs.getString("pre_fields"));
                api.setRequest_body(rs.getString("request_body"));
                api.setExpect_result(rs.getString("expect_result"));
                api.setAssert_type(rs.getString("assert_type"));
                api.setPass(rs.getString("pass"));
                api.setMsg(rs.getString("msg"));
                api.setUpdate_time(rs.getTimestamp("update_time"));
                api.setResponse(rs.getString("response"));

                APIList.add(api);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            freeResrouce(conn, ps, rs);
        }

        return APIList;
    }


    /**
     * @Description: this is to update case by id
     * @params: [response, pass, message, id]
     * @return: int
     * @Author: Fanyong Kong
     * @Date: 2021/11/13
     */
    public static int updateReqById(String response, String pass, String message, Timestamp ts, int id){
        System.out.println(message);
        int number = 0;
        Connection conn = getConnection();
        PreparedStatement ps = null;
        String sql = "";

        try {
            if("True".equalsIgnoreCase(pass)){
                sql = "update `case` set pass=?, msg=?, update_time=? where id=?";
                ps = conn.prepareCall(sql);
                ps.setString(1, pass);
                ps.setString(2, message);
                ps.setTimestamp(3, ts);
                ps.setInt(4, id);
            }else{
                sql = "update `case` set pass=?, msg=?, update_time=?, response=? where id=?";
                ps = conn.prepareCall(sql);
                ps.setString(1, pass);
                ps.setString(2, message);
                ps.setTimestamp(3, ts);
                ps.setString(4, response);
                ps.setInt(5, id);
            }
            number = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            freeResrouce(conn, ps, null);
        }

        return number;
    }

}
