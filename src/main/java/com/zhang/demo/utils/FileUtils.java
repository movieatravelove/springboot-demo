package com.zhang.demo.utils;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * 文件处理工具类
 */
public class FileUtils {


    /**
     * 根据系统获取缓存目录
     * @return
     */
    public static String getPath() {
        String path = null;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("linux")) {
            path = "/usr/local/temp/";
        } else {
            try {
                String serverpath = ResourceUtils.getURL("classpath:static/images/temp/").getPath()
                        .replace("%20", " ").replace('/', '\\');
                path = serverpath.substring(1);//从路径字符串中取出工程路径
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return path;
    }
}
