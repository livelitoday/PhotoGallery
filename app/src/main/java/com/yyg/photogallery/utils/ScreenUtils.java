package com.yyg.photogallery.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/6/29.
 * Version 1.0
 * Description:通用屏幕相关信息获取工具类
 */

public class ScreenUtils {
    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreentWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
}
