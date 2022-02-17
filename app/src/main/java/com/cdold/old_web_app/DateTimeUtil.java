package com.cdold.old_web_app;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

    public static String formatDate(Date date) {
        return DateTimeUtil.format.format(date);
    }
}
