package com.wujie.wutpictureselector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView =  findViewById(R.id.image_view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PictureGridActivity.class);
                startActivity(intent);
            }
        });

        RequestOptions options = new RequestOptions();
        options.centerInside();
        options.placeholder(R.mipmap.ic_launcher);

        Glide.with(this).load("http://s15.sinaimg.cn/mw690/0062ywFUgy6Y2pBSx1sde&690").apply(options)
                .into(imageView);

    }
}
