package com.wujie.wutpictureselector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                               @Override
                               public void
                               onSubscribe(Disposable d) {

                               }

                               @Override
                               public void
                               onNext(Boolean aBoolean) {
                                   Toast.makeText(MainActivity.this, "申请权限" + aBoolean, Toast
                                           .LENGTH_SHORT).show();
                               }

                               @Override
                               public void
                               onError(Throwable e) {

                               }

                               @Override
                               public void
                               onComplete
                                       () {

                               }
                           }
                );


        imageView = findViewById(R.id.image_view);

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

        Glide.with(this).load("http://s15.sinaimg.cn/mw690/0062ywFUgy6Y2pBSx1sde&690").apply
                (options)
                .into(imageView);

    }
}
