package com.wujie.pictureselector.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wujie on 2018/4/13/013.
 * 图片文件夹
 */

public class PictureFolder implements Serializable{
    public String name; //当前文件夹的名字
    public String path; //当前文件夹的路径
    public Picture picture;
    public List<Picture> pictures;

    @Override
    public boolean equals(Object obj) {
        try {
            PictureFolder other = (PictureFolder) obj;
            return this.path.equalsIgnoreCase(other.path) && this.name.equalsIgnoreCase(other.name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.equals(obj);
    }
}
