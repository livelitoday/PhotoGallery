package com.yyg.photogallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yyg.photogallery.R;
import com.yyg.photogallery.interfaces.OnItemCallbackListener;
import com.yyg.photogallery.interfaces.OnItemClickListener;
import com.yyg.photogallery.utils.ScreenUtils;
import com.yyg.photogallery.view.CustomImageView;

import java.util.Collections;
import java.util.List;

/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/6/29.
 * Version 1.0
 * Description:显示图片的RecyclerView适配器，实现OnItemCallbackListener接口并重写其拖动方法
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements OnItemCallbackListener {
    private Context mContext;
    private List<String> mList;
    private OnItemClickListener listener;
    private int mSmallPhotoSize;
    private boolean isShowDelete = false;
    /**
     * 标记RecyclerView是否处于滑动状态
     */

    /**
     * 从构造函数中传入数据源和context
     *
     * @param context
     * @param data
     * @param spanCount
     */
    public RecyclerViewAdapter(Context context, List<String> data, int spanCount) {
        this.mContext = context;
        this.mList = data;
        this.mSmallPhotoSize = (ScreenUtils.getScreentWidth(mContext)-3) / spanCount;
    }

    public void setItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //加载略缩图，对原图进行压缩,避免OutOfMemory错误,
        //使用Glide进行图片加载，优化滑动加载图片的效果
        Glide.with(mContext).load(mList.get(position)).override(mSmallPhotoSize-3, mSmallPhotoSize-3).into(holder.img_content);
        //根据标记判断是否加载左上角的可删除图标
        if (isShowDelete) {
            holder.img_content.setPadding(5, 5, 5, 5);
            holder.img_delete.setVisibility(View.VISIBLE);
        } else {
            holder.img_content.setPadding(0, 0, 0, 0);
            holder.img_delete.setVisibility(View.GONE);
        }

        //获取当前 Returns the position of the ViewHolder in terms of the latest layout pass.
        final int curPosition = holder.getLayoutPosition();
        if (null != listener) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //此处进行调用，但需要实现OnItemClickListener的类中具体实现点击事件方法
                    listener.onClick(holder.itemView, curPosition);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    //每一张略缩图左上角显示可以删除的可点击图标
                    //showDeleteIcon(holder);
                    listener.onLongClick(holder.itemView, curPosition);
                    return true;
                }

            });
            //此处重写onTouch方法必须判断触摸事件类型，否则onTouch方法会至少被调用两个
            holder.img_delete.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //只对按下事件进行接口方法回调
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (holder.img_delete.isEnabled()) {
                            listener.onDeleteItemClick(holder.itemView, curPosition);
                            return true;//返回true表示img_delete消费了当前的触摸点击时间，不会再将事件回传给img_content
                        }
                    }
                    return false;
                }

            });

        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 移动item的位置
     *
     * @param fromPosition 起始位置
     * @param toPosition   移动的位置
     */
    @Override
    public void onMove(int fromPosition, int toPosition) {
        //对数据源进行移动
        if (mList != null) {
            Collections.swap(mList, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    /**
     * 拖动删除
     *
     * @param position
     */
    @Override
    public void onSwipe(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 继承自RecyclerView提供的ViewHolde提高View复用
     * 初始化和ItemView相关的控件
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_content;
        public ImageView img_delete;

        private ViewHolder(View itemView) {
            super(itemView);
            img_content = (CustomImageView) itemView.findViewById(R.id.iv_photo);
            img_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            //根据屏幕大小设置略缩图的宽高
            itemView.setLayoutParams(new RelativeLayout.LayoutParams(mSmallPhotoSize, mSmallPhotoSize));
        }


    }

    /**
     * 设置左上角删除图标是否可见
     *
     * @param isShowDelete
     */
    public void setIsShowDelete(boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
        notifyDataSetChanged();
    }
}
