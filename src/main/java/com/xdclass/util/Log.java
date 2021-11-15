package com.xdclass.util;

import org.apache.log4j.Logger;

public class Log {
    //init Logger object
    private static Logger log = Logger.getLogger(Log.class.getName());

    public static void startTestCase(String CaseName){

        log.info("======================================================================");
        log.info("*************     "+CaseName+" Starting"+"     *************");
    }

    public static void endTestCase(String CaseName){
        log.info("*************     "+CaseName+" Ended"+"     *************");
        log.info("======================================================================");
    }

    public static void info(String message){
        log.info(message);
    }
    public static void warn(String message){
        log.warn(message);
    }
    public static void error(String message){
        log.error(message);
    }
    public static void fatal(String message){
        log.fatal(message);
    }
    public static void debug(String message){
        log.debug(message);
    }

}

