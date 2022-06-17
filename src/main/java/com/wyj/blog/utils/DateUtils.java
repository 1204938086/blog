package com.wyj.blog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static Date getToDayDate() throws ParseException {
        Date date = new Date();
        //date.setDate(date.getDate()+1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return sdf.parse(sdf.format(date));
    }
}
