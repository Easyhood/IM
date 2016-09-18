package com.ragentek.imclient.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : RGK
 * Author     : qi.guan
 * Date       : 2016/9/18 14:17
 */
public class MyTime {

    //date --->long

    /**
     * current
     */
    public static String getTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formate =  new SimpleDateFormat("MM-dd HH:mm:ss");
        return formate.format(date);
    }

    /**
     * long -->String
     */
    public static String getTime(Long time){
        Date date = new Date(time);
        SimpleDateFormat formate = new SimpleDateFormat("MM-dd HH:mm:ss");
        return formate.format(date);
    }

    /**
     * String -->long
     */
    public static Long getTime(String dateString)throws ParseException{
        SimpleDateFormat formate = new SimpleDateFormat("MM-dd HH:mm:ss");
        return formate.parse(dateString).getTime();
    }

}
