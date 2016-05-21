package com.nirays.social.swachhbharat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by kirant400 on 08/05/2016.
 */
public class DBUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    public static String getUTCDateTimeAsString(Date date)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(date);
        return utcTime;
    }

    public static Date getUTCDateTimeStringAsDate(String UTCDate){
        Date utcTime=null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            utcTime = sdf.parse(UTCDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return utcTime;
    }
}
