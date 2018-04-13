package com.wujie.pictureselector.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wujie.pictureselector.R;
import com.wujie.pictureselector.bean.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujie on 2018/4/12/012.
 */

public class PictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Picture> pictureList;

    private int pWidth = 0;

    public PictureAdapter(Context mContext, List<Picture> pictureList) {
        this.mContext = mContext;
        this.pictureList = pictureList;
        pWidth = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getWidth() /3 ;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.ps_grid_item,
                parent,false);
        return new PictureHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PictureHolder) {
            PictureHolder pictureHolder = (PictureHolder) holder;
            Glide.with(mContext).load(pictureList.get(position).getPath()).into(pictureHolder
                    .imageView);
        }
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public void Refreash(List<Picture> datas) {
        if(datas == null || datas.size() == 0) {
            this.pictureList = new ArrayList<>();
        } else {
            this.pictureList = datas;
        }
        notifyDataSetChanged();
    }

    class PictureHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        public PictureHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_pic);
            itemView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, pWidth)); //让图片是个正方形
        }
    }
}
