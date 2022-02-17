package com.cdold.old_web_app;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 日志工具类
 */
public class LogUtil {
    private static File logFile = null;
    private static FileOutputStream out = null;

    /**
     * 写入日志到文件
     *
     * @param logVal 日志内容
     */
    public static void writeLog(String logVal) {
        logFile = new File(ParamConstant.logAbsPath);
        File logDirFile = new File(ParamConstant.logAbsDir);
        try {
            if (!logFile.exists()) {
                if (!logDirFile.exists()) {
                    logDirFile.mkdir();
                }
                logFile.createNewFile();
                out.write((DateTimeUtil.formatDate(new Date()) + "\t" + logVal).getBytes(StandardCharsets.UTF_8));
                return;
            }
            // 日志大于 10M，自动删除
            if (logFile.length() >= 1024 * 1024 * 10) {
                logFile.delete();
                logDirFile.mkdir();
                logFile.createNewFile();
            }
            if (out == null) {
                out = new FileOutputStream(logFile, true);
            }
            out.write((DateTimeUtil.formatDate(new Date()) + "\t" + logVal + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();// 写出
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}