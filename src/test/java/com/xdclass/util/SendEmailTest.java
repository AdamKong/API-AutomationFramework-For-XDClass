package com.xdclass.util; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* SendEmail Tester. 
* @author: Fanyong Kong
* @since : 11/14/2021
* @version 1.0 
*/ 
public class SendEmailTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: sendEmail() 
* 
*/ 
@Test
public void testSendEmail() throws Exception {
    SendEmail.sendEmail("test email", "hello, this is a test email!");
} 


} 
