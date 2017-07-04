package com.yyg.photogallery.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yyg.photogallery.R;

public class ShowPhotoActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
//        String path = getIntent().getStringExtra("photo_path");
//        showPhoto(path);

    }

    private void initView() {
        setContentView(R.layout.activity_show_photo);
        mImageView = (ImageView) findViewById(R.id.iv_show);
        String path = getIntent().getStringExtra("photo_path");
        showPhoto(path);
    }

    private void initEvent() {
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.tv_ok).setVisibility(View.GONE);
    }


    /**
     * 根据文件路径显示原图
     *
     * @param path
     */
    private void showPhoto(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
