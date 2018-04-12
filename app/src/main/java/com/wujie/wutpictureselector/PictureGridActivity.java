package com.wujie.wutpictureselector;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wujie.pictureselector.bean.Picture;
import com.wujie.wutpictureselector.adapter.PictureAdapter;

import java.util.ArrayList;
import java.util.List;

public class PictureGridActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<Picture> mAllPicturList;
    private List<String> mDirList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_grid);
        initView();
    }

    private void initView() {
        getPicrures();
        mRecyclerView = (RecyclerView) findViewById(R.id.picture_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(new PictureAdapter(this, mAllPicturList));
    }

    private void getPicrures() {
        String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED};
        String orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,null, null, orderBy);
        mAllPicturList = new ArrayList<>();
        mDirList = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Picture picture = new Picture();
                    picture.setPath(cursor.getString(0));
                    if(picture.getPath() == null) {
                        continue;
                    }
                    mAllPicturList.add(picture);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }
}
