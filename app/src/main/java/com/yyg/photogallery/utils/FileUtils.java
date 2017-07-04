package com.yyg.photogallery.utils;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/7/1.
 * Version 1.0
 * Description:文件操作的工具类
 */

public class FileUtils {

    /**
     * 获取dir目录下后缀为suffix的文件列表(具体路径列表)
     *
     * @param dir 指定获取的文件目录
     * @return 文件列表
     */
    public static ArrayList<String> getAllFileWithFilter(File dir, String suffix) {
        //如果dir为空或者不是目录则直接返回null
        if (null == dir || !dir.isDirectory()) return null;
        ArrayList<String> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                //获取绝对路径
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

    /**
     * 删除文件
     *
     * @param path 需要删除的文件路径
     */
    public static void deleteFileByPath(String path) {
        File file = new File(path);
        //文件存在并且不是目录
        if (file.exists() && !file.isDirectory()) {
            file.delete();
        }
//        if (!file.exists()) {
//            Log.d("delete-->", "deleteFileByPath:删除成功！ ");
//        }
    }

}
