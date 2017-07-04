package com.yyg.photogallery.interfaces;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.yyg.photogallery.adapter.RecyclerViewAdapter;

/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/6/30.
 * Version 1.0
 * Description:实现ItemTouchHelper.Callback接口，重写和item拖拽相关的方法
 */

public class OnItemCallbackHelper extends ItemTouchHelper.Callback {
    RecyclerViewAdapter mAdapter;

    /**
     * 传入Adapter对象
     *
     * @param adapter
     */
    public OnItemCallbackHelper(RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
    }

    /**
     * 设置可以滑拖动的方向的flag
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //设置上下左右都可以拖拽和滑动
        int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT;
        //可以水平  方向拖动删除
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    /**
     * 当拖拽某个Item时回调此方法
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 当滑动某个item时被回调
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onSwipe(viewHolder.getAdapterPosition());
    }
}
