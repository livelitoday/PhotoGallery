package com.yyg.photogallery;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/6/29.
 * Version 1.0
 * Description: RecyclerView没有直接提供给列表设置分割线的方法，所以这里继承官方提供的RecyclerView.ItemDecoration类，自定义RecyclerView的分割线
 * 设置分割线思路：(1)留出分割线位置 (2)按给出的位置绘制分割线
 * 处理细节：（1）最右边和最右边不能留分割线
 * （2）最下面不能分割线
 * （3）最后一张图片是相机图片
 */
public class GridLayoutIntemDecoration extends RecyclerView.ItemDecoration {
    //Paint mPaint;
    Drawable mDivider;//用于绘制分割线的Drawable资源

    /**
     * @param context
     * @param drawableId
     */
    public GridLayoutIntemDecoration(Context context, int drawableId) {
        //获取Drawable
        mDivider = ContextCompat.getDrawable(context, drawableId);
    }

    /**
     * 设置给定item的偏移量，指定itemview的paddingLeft，paddingTop， paddingRight， paddingBottom
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //拿到每一个子View,也就是ItemView留出边距进行绘制
        int position = parent.getChildAdapterPosition(view);//获取子视图在Adapter中的位置
        //因为只有相邻的两个Item之间才设置分割线，所以只用留出每一个itemView的右边即可
        //同时需要去掉最右边和最下边的间隔
        int bottom = mDivider.getIntrinsicHeight();
        int right = mDivider.getIntrinsicWidth();
        if (isLastCol(view, parent)) {//如果是最后一列
            outRect.right = 0;
            Log.d("getItemOffsets", "getItemOffsets: 最后一列" + position);
        } else {
            outRect.right = right;
            Log.d("getItemOffsets", "不是最后一列，outRect.right-->: " + outRect.right);
        }
        if (isLastRow(view, parent)) {//如果是最后一行
            Log.d("getItemOffsets", "getItemOffsets: 最后一行" + position);
            outRect.bottom = 0;
        } else {
            outRect.bottom = bottom;
        }
//        if ((position + 1) % 3 != 0) {
//            outRect.right = mDivider.getIntrinsicWidth();//获取Drawable的固定宽度
//        }

        //outRect.right = right;
        //如果下一行还有ItemView就需要给下一行留下绘制分割线的位置

    }

    /**
     * 根据位置判断是否位于最后一列
     *
     * @param view
     * @param parent
     * @return
     */
    private boolean isLastCol(View view, RecyclerView parent) {
        //获取当前item的位置
        int CurPos = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //获取当前的列数
        int spanCount = getSpanCount(parent);
        Log.d("isLastCol", "isLastCol: " + spanCount);
        return (CurPos + 1) % spanCount == 0 ? true : false;
    }

    /**
     * 根据parent获取当前的列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            return gridLayoutManager.getSpanCount();
        }
        return 1;
    }


    /**
     * 根据位置判断是否位于最后一行,和判断是否是最后一行类似，用当前位置对当前行数求余
     *
     * @param view
     * @param parent
     * @return
     */
    private boolean isLastRow(View view, RecyclerView parent) {
        //当前位置 + 1> （行数 - 1）*列数
        int CurPos = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        //判断是否为整行，不是整行就是要+1
        int rowCount = parent.getAdapter().getItemCount() % spanCount == 0 ? (parent.getAdapter().getItemCount() / spanCount) : (parent.getAdapter().getItemCount() / spanCount + 1);
//        if (CurPos > (rowCount - 1) * spanCount) {
//            return true;
//        }
        return (CurPos + 1) > (rowCount - 1) * spanCount;
    }

    /**
     * 在itemView之前绘制需要的分割线
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //super.onDraw(c, parent, state);
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }


    /**
     * 绘水平方向的分割线
     *
     * @param c
     * @param parent
     */
    @TargetApi(17)
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getRight() - layoutParams.getMarginStart();
            int right = left + mDivider.getIntrinsicWidth();
            int top = childView.getTop();
            int bottom = mDivider.getIntrinsicHeight() + childView.getBottom();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    /**
     * 绘制垂直方向的分割线
     *
     * @param c
     * @param parent
     */
    @TargetApi(17)
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            //getMarginStart()方法需要做版本兼容
            int left = childView.getLeft() - layoutParams.getMarginStart();
            int right = childView.getRight() + mDivider.getIntrinsicWidth();
            int top = childView.getBottom();
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

}