package com.wujie.pictureselector.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wujie.pictureselector.R;

/**
 * Created by wujie on 2018/4/13/013.
 */

public class FolderPopupWindow extends PopupWindow implements View.OnClickListener{

    private ListView listView;
    private final View view;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private ItemClickListener itemClickListener;



    public FolderPopupWindow(Context context, BaseAdapter adapter) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.ps_folder_popup, null);
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(itemClickListener != null) {
                    itemClickListener.onnItemClick(parent, view, position, id);
                }
            }
        });


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
                int maxHeight = view.getHeight() * 3 / 4;
                int realHeight = listView.getHeight();
                ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                layoutParams.height = realHeight > maxHeight ? maxHeight : realHeight;
                listView.setLayoutParams(layoutParams);
                enterAnimal();
            }
        });
    }


    private void enterAnimal() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0,1);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(listView, "translationY", listView
                .getHeight(), 0);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(400);
        set.playTogether(alpha, translationY);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    private void exitAnimator() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1 , 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(listView, "translationY", 0,
                listView.getHeight());
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.playTogether(alpha, translationY);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                FolderPopupWindow.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void dismiss() {
        exitAnimator();
    }

    public interface ItemClickListener {
       void onnItemClick(AdapterView<?> parent, View view, int position, long id);
    }
}
