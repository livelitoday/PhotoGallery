package com.yyg.photogallery.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yyg.photogallery.GridLayoutIntemDecoration;
import com.yyg.photogallery.R;
import com.yyg.photogallery.adapter.RecyclerViewAdapter;
import com.yyg.photogallery.interfaces.OnItemCallbackHelper;
import com.yyg.photogallery.interfaces.OnItemClickListener;
import com.yyg.photogallery.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.MemoryHandler;

/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/6/29.
 * Version 1.0
 * Description:图片浏览器的主Activity,使用RecyclerView控件，借助其布局管理器GridLayoutManager,以表格视图的形式显示图片的略缩图片
 * 实现自定义的接口，重写相应的点击item、点击删除图标、长按事件方法
 */
public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecycelrView;
    /*标记是否显示左上角的删除图标*/
    public static boolean isShowedDelete = false;
    ArrayList<String> pathList = null;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        initData();

    }

    /**
     * 初始化界面数据
     */
    private void initData() {
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setContentView(R.layout.activity_main);
        mRecycelrView = (RecyclerView) findViewById(R.id.photo_rl);
        //final String basePath = Environment.getExternalStorageDirectory() + File.separator + "photo";
        final String choosedFolder = getIntent().getStringExtra("path_folder");
        pathList = FileUtils.getAllFileWithFilter(new File(choosedFolder + File.separator), ".jpg");
        pathList.addAll(FileUtils.getAllFileWithFilter(new File(choosedFolder + File.separator), ".png"));
        //创建适配器对象时需要设置spanCount(每一行的列数目)
        final int spanCount = 3;
        if (pathList != null) {
            mAdapter = new RecyclerViewAdapter(this, pathList, spanCount);
        }
        mRecycelrView.setAdapter(mAdapter);
        //使用GridLayoutManager布局管理器实现表格布局
        mRecycelrView.setLayoutManager(new GridLayoutManager(this, spanCount));
        //添加列分割线
        mRecycelrView.addItemDecoration(new GridLayoutIntemDecoration(this, R.drawable.divider_shape));
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        //为RecyclerView添加滑动监听事件处理
        //设置点击和长按事件
        mAdapter.setItemClickListener(this);
        //借助v7包中提供的ItemTouchHelper实现对图片的拖动排序
        ItemTouchHelper.Callback callback = new OnItemCallbackHelper(mAdapter);
        /**
         * 实例化ItemTouchHelper对象,然后添加到RecyclerView
         */
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecycelrView);
    }


    /**
     * 显示指定路径的图片
     *
     * @param showPath
     */
    private void showPhoto(String showPath) {
        Intent intent = new Intent(this, ShowPhotoActivity.class);
        intent.putExtra("photo_path", showPath);
        startActivity(intent);
    }

    /**
     * 处理点击事件
     *
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                //Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
            case R.id.tv_ok:
                //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                mAdapter.setIsShowDelete(false);
                isShowedDelete = false;
                break;
            default:
                break;
        }
    }

    private long ClickCount = 0;

    /**
     * 退出操作的处理 根据用户两次按退出键的间隔判断是否为错误操作
     */
    @Override
    public void onBackPressed() {
        if (isShowedDelete) {
            //hideAllDeleteIcon();
            mAdapter.setIsShowDelete(false);
            isShowedDelete = false;
        }
        super.onBackPressed();

    }

    @Override
    public void onClick(View itemView, int pos) {
        //Toast.makeText(MainActivity.this, "点击" + pos, Toast.LENGTH_SHORT).show();
        //点击放大显示pos位置的图片,根据position从ArrayList中获取具体的文件路径，从而加载进内存，显示原图片
        String showPath = pathList.get(pos);
        if (!isShowedDelete) {//判断当前是否处于编辑状态，如果已经是编辑状态，在长按不再重复调用长按事件方法
            showPhoto(showPath);
        } else {
            //hideAllDeleteIcon();
            mAdapter.setIsShowDelete(false);
            isShowedDelete = false;
        }
    }

    @Override
    public void onLongClick(View itemView, int pos) {
        //长按后显示每一张略缩图都会显示左上角的删除图标
        //Toast.makeText(MainActivity.this, "长按" + pos, Toast.LENGTH_SHORT).show();
        //避免第二次长按又重复调用此方法
        if (!isShowedDelete) {
            mAdapter.setIsShowDelete(true);
            isShowedDelete = true;
        }
    }

    @Override
    public void onDeleteItemClick(View itemView, final int pos) {

        //删除指定位置路径下的图片文件
        Toast.makeText(MainActivity.this, "删除" + pos, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_tip);
        builder.setCancelable(true);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: 删除");
                //更新拖拽删除的图片列表
                //mAdapter.notifyItemRemoved(pos);
                //更新拖拽删除的图片列表
                mAdapter.notifyItemRemoved(pos);
                pathList.remove(pos);
                //删除图片对应的本地文件
                FileUtils.deleteFileByPath(pathList.get(pos));

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
