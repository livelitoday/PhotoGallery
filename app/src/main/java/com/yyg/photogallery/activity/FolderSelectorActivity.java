package com.yyg.photogallery.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import dr.android.fileselector.FileSelectActivity;

/**
 * Email 1418749937@qq.com
 * Created by zhiyuan on 2017/6/29.
 * Version 1.0
 * Description: 继承自module依赖库中的FileSelectActivity，实现文件夹选择的模块
 */
public class FolderSelectorActivity extends FileSelectActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initParams() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClickOkBtn() {
        super.onClickOkBtn();
    }
}
