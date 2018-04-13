package com.wujie.pictureselector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wujie.pictureselector.R;
import com.wujie.pictureselector.bean.PictureFolder;

import java.util.List;

/**
 * Created by wujie on 2018/4/13/013.
 */

public class FolderAdapter extends BaseAdapter {

    private Context mContext;
    private List<PictureFolder> folders;

    public FolderAdapter(Context mContext, List<PictureFolder> folders) {
        this.mContext = mContext;
        this.folders = folders;
    }

    @Override
    public int getCount() {
        return folders.size();
    }

    @Override
    public Object getItem(int position) {
        return folders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FolderHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ps_folder_item,null);
            holder = new FolderHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (FolderHolder) convertView.getTag();
        PictureFolder pictureFolder = folders.get(position);
        Glide.with(mContext).load(pictureFolder.picture.getPath()).into(holder.imageView);
        holder.nameTv.setText(pictureFolder.name);
        holder.countTv.setText(mContext.getResources().getString(R.string.folder_size,
                pictureFolder.pictures.size()));

        return convertView;
    }

    class FolderHolder  {
        ImageView imageView;
        TextView nameTv;
        TextView countTv;
        ImageView checkIv;

        FolderHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.iv_fist_image);
            nameTv = (TextView) convertView.findViewById(R.id.tv_folder_name);
            countTv = (TextView) convertView.findViewById(R.id.tv_image_count);
            checkIv = (ImageView) convertView.findViewById(R.id.iv_folder_check);
        }
    }

}
