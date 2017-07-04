package com.yyg.photogallery.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yyg.photogallery.R;

import java.util.ArrayList;

import dr.android.fileselector.FileSelectConstant;

/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/6/29.
 * Version 1.0
 * Description:选择需要浏览照片的文件目录
 */
public class ChooseFolderActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int FILE_SELECTOR_REQUEST_CODE = 0x0001;
    private EditText mEtChooseFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化视图
        initView();
        //初始化事件
        initEvent();
    }

    private void initEvent() {
        mEtChooseFolder.setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.tv_back).setVisibility(View.GONE);
        findViewById(R.id.tv_ok).setOnClickListener(this);
    }

    private void initView() {
        setContentView(R.layout.activity_choose_folder);
        mEtChooseFolder = (EditText) findViewById(R.id.edit_dir);
    }

    /**
     * 打开系统文件管理器
     */
    private void openSysFileChooser() {
        Intent intent = new Intent(ChooseFolderActivity.this, FolderSelectorActivity.class);
        intent.putExtra(FileSelectConstant.SELECTOR_REQUEST_CODE_KEY, FileSelectConstant.SELECTOR_MODE_FOLDER);
        startActivityForResult(intent, FILE_SELECTOR_REQUEST_CODE);
    }

    /**
     * 重写onActivityResult获取通过调用系统文件管理器的返回值
     *
     * @param requestCode 请求码
     * @param resultCode  返回结果编码
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            String result = data.getStringArrayListExtra(FileSelectConstant.SELECTOR_BUNDLE_PATHS).get(0);
            //Toast.makeText(this, "paths: " + result, Toast.LENGTH_SHORT).show();
            mEtChooseFolder.setText(result);
        }
    }

    /**
     * 实现点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                startShowPhtotoActivity();
                break;
            case R.id.btn_ok:
                startShowPhtotoActivity();
                break;
            case R.id.edit_dir:
                openSysFileChooser();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到显示图片略缩图的界面
     */
    private void startShowPhtotoActivity() {
        //确认后将选中的文件夹路径传入到MainActivity中
        Intent i = new Intent(ChooseFolderActivity.this, MainActivity.class);
        i.putExtra("path_folder", mEtChooseFolder.getText().toString());
        startActivity(i);
    }
}
