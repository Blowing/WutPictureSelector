package com.wujie.pictureselector.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wujie.pictureselector.R;
import com.wujie.pictureselector.adapter.FolderAdapter;
import com.wujie.pictureselector.adapter.PictureAdapter;
import com.wujie.pictureselector.base.BaseActivity;
import com.wujie.pictureselector.bean.Picture;
import com.wujie.pictureselector.bean.PictureFolder;
import com.wujie.pictureselector.view.FolderPopupWindow;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class PictureGridActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RelativeLayout mDirLayout;
    private TextView mDirTv;


    private PictureAdapter pictureAdapter;
    private FolderAdapter folderAdapter;

    private List<Picture> mAllPicturList;
    private List<String> mDirList;
    private List<PictureFolder> pictureFolders;
    private LinkedHashMap<String, List<Picture>> mDirMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String ss = intent.getStringExtra("image");
        SharedPreferences s = getSharedPreferences("picture", Context.MODE_PRIVATE);
        Toast.makeText(this, s.getString("hehe", "你是个瓜娃子"), Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_picture_grid);
        initView();
        initDirView();
    }

    private void initView() {
        getPicrures();
        mRecyclerView = (RecyclerView) findViewById(R.id.picture_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        pictureAdapter = new PictureAdapter(this, mAllPicturList);
        mRecyclerView.setAdapter(pictureAdapter);

    }

    private void initDirView() {
        mDirLayout = (RelativeLayout) findViewById(R.id.rl_dir);
        mDirTv = (TextView) findViewById(R.id.tv_dir);

        mDirLayout.setOnClickListener(this);
    }

    private void getPicrures() {
        String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED};
        String orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, orderBy);
        mAllPicturList = new ArrayList<>();
        mDirList = new ArrayList<>();
        mDirMap = new LinkedHashMap<>();
        pictureFolders = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Picture picture = new Picture();
                    picture.setPath(cursor.getString(0));

                    Log.i("wujie", picture.getPath() + "");

                    if (picture.getPath() == null) {
                        continue;
                    }
                    mAllPicturList.add(picture);

                    int last = picture.getPath().lastIndexOf("/");
                    String cateName;
                    if (last == -1) {
                        continue;
                    } else if (last == 0) {
                        cateName = "/";
                    } else {
                        int secondLast = picture.getPath().lastIndexOf("/", last - 1);
                        cateName = picture.getPath().substring(secondLast + 1, last);
                    }
                    if (mDirMap.containsKey(cateName)) {

                        mDirMap.get(cateName).add(picture);
                    } else {
                        List<Picture> pictures = new ArrayList<>();
                        pictures.add(picture);
                        mDirMap.put(cateName, pictures);
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        PictureFolder pictureFolder = new PictureFolder();
        pictureFolder.name = "全部图片";
        pictureFolder.pictures = mAllPicturList;
        pictureFolder.picture = mAllPicturList.get(0);
        pictureFolders.add(pictureFolder);
        for (String key:
             mDirMap.keySet()) {
            PictureFolder folder = new PictureFolder();
            folder.name = key;
            folder.pictures = mDirMap.get(key);
            folder.picture = mDirMap.get(key).get(0);
            pictureFolders.add(folder);
        }
        folderAdapter = new FolderAdapter(this, pictureFolders);

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.rl_dir) {
            showPictureDirPop();

        } else {
        }
    }

    @SuppressLint("ResourceAsColor")
    private void showPictureDirPop() {

        final FolderPopupWindow popupWindow = new FolderPopupWindow(this, folderAdapter);
        popupWindow.setItemClickListener(new FolderPopupWindow.ItemClickListener() {
            @Override
            public void onnItemClick(AdapterView<?> parent, View view, int position, long id) {
                FolderAdapter adapter = (FolderAdapter) parent.getAdapter();
                adapter.setLastSelect(position);
                PictureFolder folder = (PictureFolder) adapter.getItem(position);
                pictureAdapter.Refreash(folder.pictures);
                mDirTv.setText(folder.name);
                popupWindow.dismiss();
            }
        });

        if(popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            //popupWindow.showAsDropDown(mDirLayout);

           popupWindow.showAtLocation(findViewById(R.id.foot_bar), Gravity.BOTTOM, 0, 0);
        }
    }
}
