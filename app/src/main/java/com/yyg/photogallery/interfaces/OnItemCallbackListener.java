package com.yyg.photogallery.interfaces;


/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/6/29.
 * Version 1.0
 * Description:定义拖动item位置的接口，结合ItemTouchHelper实现拖动排序
 */
public interface OnItemCallbackListener {
    /**
     * @param fromPosition 起始位置
     * @param toPosition   移动的位置
     */
    void onMove(int fromPosition, int toPosition);

    /**
     *
     * @param position
     */
    void onSwipe(int position);
}