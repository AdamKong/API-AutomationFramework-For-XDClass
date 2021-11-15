package com.xdclass.base; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.assertTrue;

/** 
* Testbase Tester. 
* @author: Fanyong Kong
* @since : 11/13/2021
* @version 1.0 
*/ 
public class TestbaseTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getProperty(String key) 
* 
*/ 
@Test
public void testGetProperty() throws Exception { 

    String myHost = Testbase.getProperty("webAPIHost");
    String dbHost = Testbase.getProperty("dbHost");
    String dbUsername = Testbase.getProperty("dbUsername");
    String dbPassword = Testbase.getProperty("dbPassword");
    String dbTable = Testbase.getProperty("dbTable");

    System.out.println(myHost);
    System.out.println(dbHost);
    System.out.println(dbUsername);
    System.out.println(dbPassword);
    System.out.println(dbTable);

    assertTrue(myHost!=null);
} 


} 
