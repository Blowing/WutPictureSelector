package com.wujie.pictureselector.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.wujie.pictureselector.R;
import com.wujie.pictureselector.adapter.PsPriviewAdapter;
import com.wujie.pictureselector.bean.PhotoAlbum;

public class PicturePriviewActivity extends AppCompatActivity {

    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_priview);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        PsPriviewAdapter adapter = new PsPriviewAdapter(this, PhotoAlbum.pictures);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(PhotoAlbum.index, true);
    }
}
