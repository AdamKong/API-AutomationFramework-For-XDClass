package com.xdclass.util; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

/** 
* Log Tester. 
* @author: Fanyong Kong
* @since : 11/15/2021
* @version 1.0 
*/ 
public class LogTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: startTestCase(String CaseName) 
* 
*/ 
@Test
public void testStartTestCase() throws Exception { 
Log.startTestCase("app");
} 

/** 
* 
* Method: endTestCase(String CaseName) 
* 
*/ 
@Test
public void testEndTestCase() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: info(String message) 
* 
*/ 
@Test
public void testInfo() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: warn(String message) 
* 
*/ 
@Test
public void testWarn() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: error(String message) 
* 
*/ 
@Test
public void testError() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: fatal(String message) 
* 
*/ 
@Test
public void testFatal() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: debug(String message) 
* 
*/ 
@Test
public void testDebug() throws Exception { 
//TODO: Test goes here... 
} 


} 
