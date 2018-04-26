package com.wujie.pictureselector.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.wujie.pictureselector.R;
import com.wujie.pictureselector.bean.Picture;

import java.util.List;

/**
 * Created by wujie on 2018/4/17/017.
 */

public class PsPriviewAdapter extends PagerAdapter {
    private Context mContext;

    private List<Picture> pictures;

    public PsPriviewAdapter(Context mContext, List<Picture> pictures) {
        this.mContext = mContext;
        this.pictures = pictures;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.ps_priview_item,
                container, false);
        PhotoView photoView = (PhotoView) convertView.findViewById(R.id.photo_view);
        Glide.with(mContext).load(pictures.get(position).getPath()).into(photoView);
        (container).addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        int count = 0;
        if (pictures != null) {
            count = pictures.size();
        }
        return count;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
