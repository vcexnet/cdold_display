package com.cdold.old_web_app;

import android.os.Environment;

/**
 * 配置参数常量类
 */
public class ParamConstant {
    /**
     * webview 默认访问路径
     */
//    public static final String defaultUrl = "https://www.samsph.com/";
    public static final String defaultUrl = "http://192.168.1.112:8080/?patientID=0403&areaId=7&packSize=4&packIndex=1&type=1";

    /**
     * url 参数的 key
     */
    public static final String urlParamKey = "url";

    /**
     * 解析配置数组中参数 key 的 index
     */
    public static final int paramValIndex = 1;

    /**
     * 解析配置数组中参数 value 的 index
     */
    public static final int paramKeyIndex = 0;

    /**
     * 配置文件绝对路径
     */
    public static final String confAbsPath = Environment.getExternalStorageDirectory() + "/cdold_conf/conf.txt";

    /**
     * 配置目录绝对路径
     */
    public static final String confAbsDir = Environment.getExternalStorageDirectory() + "/cdold_conf";

    /**
     * 日志文件绝对路径
     */
    public static final String logAbsPath = Environment.getExternalStorageDirectory() + "/cdold_log/log.txt";

    /**
     * 日志目录绝对路径
     */
    public static final String logAbsDir = Environment.getExternalStorageDirectory() + "/cdold_log";
}
