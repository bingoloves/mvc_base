package com.learn.photo;

import android.widget.ImageView;

/**
 * Created by bingo on 2020/5/17.
 */
public interface ImageLoader {
    /**
     * 加载普通图片
     * @param imageUrl
     * @param imageView
     */
    void displayImage(String imageUrl, ImageView imageView);

    /**
     * 加载gif图片
     * @param imageUrl
     * @param imageView
     */
    void displayGif(String imageUrl, ImageView imageView);
}
