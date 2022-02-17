package com.cdold.old_web_app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 配置文件工具类
 */
public class ConfUtil {

    public static String loadConfig() {
        /**
         * 检查并写入配置
         */
        // 缓冲流，用于读取和写入配置文件
        BufferedReader reader = null;
        BufferedWriter writer = null;
        // 保存 url 配置值
        String url = null;
        // 配置文件项
        File confFile = new File(ParamConstant.confAbsPath);
        try {
            // 检查是否存在配置
            if (!confFile.exists()) {
                File confFileDir = new File(ParamConstant.confAbsDir);
                if (!confFileDir.exists()) {
                    confFileDir.mkdir();
                }
                // 创建配置文件
                confFile.createNewFile();
                writer = new BufferedWriter(new FileWriter(confFile));
                // 写入默认配置
                writer.write(ParamConstant.urlParamKey + " " + ParamConstant.defaultUrl);
            }
        } catch (Exception e) {
            LogUtil.writeLog("配置文件创建异常：" + e);
            System.exit(1);
        } finally {
            // 资源关闭
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 读取配置
         */
        try {
            reader = new BufferedReader(new FileReader(confFile));
            String text;
            while ((text = reader.readLine()) != null) {
                String trimText = text.trim();
                String[] params = trimText.split(" ");
                if (ParamConstant.urlParamKey.equals(params[ParamConstant.paramKeyIndex])) {
                    if (!"".equals(params[ParamConstant.paramValIndex]) && params[ParamConstant.paramValIndex] != null) {
                        url = params[ParamConstant.paramValIndex];
                        break;
                    }
                }
            }
            // 配置文件内容为空，仍然写入默认配置
            if (url == null) {
                url = ParamConstant.defaultUrl;
                writer = new BufferedWriter(new FileWriter(confFile));
                writer.write(ParamConstant.urlParamKey + " " + ParamConstant.defaultUrl);
            }
        } catch (Exception e) {
            LogUtil.writeLog("配置文件读取异常：" + e);
            System.exit(1);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return url;
    }
}
