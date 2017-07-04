package com.yyg.photogallery.interfaces;

import android.view.View;

/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/6/29.
 * Version 1.0
 * Description:
 */
public interface OnItemClickListener {
    /**
     * 点机击事件方法
     *
     * @param itemView item子视图
     * @param pos      item位置
     */
    void onClick(View itemView, int pos);

    /**
     * 长按事件方法
     *
     * @param itemView item子视图
     * @param pos      item位置
     */
    void onLongClick(View itemView, int pos);

    /**
     * @param itemView item子视图
     * @param pos      itemView位置
     */
    void onDeleteItemClick(View itemView, int pos);
}