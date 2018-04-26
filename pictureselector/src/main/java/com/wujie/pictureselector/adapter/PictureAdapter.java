package com.wujie.pictureselector.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wujie.pictureselector.R;
import com.wujie.pictureselector.bean.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujie on 2018/4/12/012.
 * 展示图片的Adapter
 */

public class PictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Picture> pictureList;

    private List<Picture> selectList;

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private int maxSelectCount = 9;
    private int pWidth = 0;

    public PictureAdapter(Context mContext, List<Picture> pictureList) {
        this.mContext = mContext;
        this.pictureList = pictureList;
        this.selectList = new ArrayList<>();
        pWidth = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getWidth() / 3;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.ps_grid_item,
                parent, false);
        return new PictureHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PictureHolder) {
            final PictureHolder pictureHolder = (PictureHolder) holder;
            final Picture picture = pictureList.get(position);
            boolean isCheck = selectList.contains(picture);
            Glide.with(mContext).load(picture.getPath()).into(pictureHolder
                    .imageView);
            pictureHolder.checkBox.setChecked(isCheck);
            pictureHolder.maskView.setVisibility(isCheck ? View.VISIBLE : View.GONE);
            pictureHolder.checkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean lastCheckSta = pictureHolder.checkBox.isChecked();
                    if (!lastCheckSta && selectList.size() >= maxSelectCount) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string
                                .max_count, maxSelectCount), Toast.LENGTH_SHORT).show();
                    } else if (!lastCheckSta) {
                        selectList.add(picture);
                        pictureHolder.checkBox.setChecked(true);
                        pictureHolder.maskView.setVisibility(View.VISIBLE);
                    } else {
                        selectList.remove(picture);
                        pictureHolder.checkBox.setChecked(false);
                        pictureHolder.maskView.setVisibility(View.GONE);
                    }

                }
            });

            pictureHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null) {
                        itemClickListener.onItemClick(position, pictureList);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public void Refreash(List<Picture> datas) {
        if (datas == null || datas.size() == 0) {
            this.pictureList = new ArrayList<>();
        } else {
            this.pictureList = datas;
        }
        notifyDataSetChanged();
    }

    class PictureHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private View maskView;
        private View checkView;
        private CheckBox checkBox;

        public PictureHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_pic);
            itemView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, pWidth)); //让图片是个正方形
            maskView = (View) itemView.findViewById(R.id.mask_view);
            checkView = (View) itemView.findViewById(R.id.check_view);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_check);


        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, List<Picture> pictures);
    }
}
