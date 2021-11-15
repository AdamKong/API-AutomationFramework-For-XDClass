package com.xdclass.cases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserTestCase {


    @BeforeClass
    public void setUp(){
        System.out.println("set up");
    }

    @DataProvider(name= "getData")
    public Object[][] get(){
        String[][] str = new String[2][3];
        int x=0;
        for(int i=0; i<2; i++){
            for(int j=0; j<3; j++){
                str[i][j] = "" + x++;
            }
        }

        return str;
    }

    @Test(dataProvider = "getData")
    public void login(String a, String b, String c){
        System.out.println("executing");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

    @AfterClass
    public void tearDown(){
        System.out.println("tear down");
    }

}
