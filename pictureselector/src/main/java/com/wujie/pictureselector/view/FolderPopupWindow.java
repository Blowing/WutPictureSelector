package com.wujie.pictureselector.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wujie.pictureselector.R;

/**
 * Created by wujie on 2018/4/13/013.
 */

public class FolderPopupWindow extends PopupWindow implements View.OnClickListener{

    private ListView listView;

    public FolderPopupWindow(Context context, BaseAdapter adapter) {
        super(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.ps_folder_popup, null);
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        view.setOnClickListener(this);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setAnimationStyle(0);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {



            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int maxHeight = view.getHeight() * 7 / 8;
                int realHeight = listView.getHeight();
                ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                layoutParams.height = realHeight > maxHeight ? maxHeight : realHeight;
                listView.setLayoutParams(layoutParams);
            }
        });
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}
