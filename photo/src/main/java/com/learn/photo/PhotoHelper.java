package com.learn.photo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.learn.photo.activity.PhotoPickerActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by bingo on 2020/5/17.
 */

public class PhotoHelper {
    private static final String EXTRA_CAMERA_FILE_DIR = "EXTRA_CAMERA_FILE_DIR";
    private static final String EXTRA_SELECTED_PHOTOS = "EXTRA_SELECTED_PHOTOS";
    private static final String EXTRA_MAX_CHOOSE_COUNT = "EXTRA_MAX_CHOOSE_COUNT";
    private static final String EXTRA_PAUSE_ON_SCROLL = "EXTRA_PAUSE_ON_SCROLL";

    private static final String STATE_SELECTED_PHOTOS = "STATE_SELECTED_PHOTOS";

    /**
     * 拍照的请求码
     */
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    /**
     * 预览照片的请求码
     */
    private static final int RC_PREVIEW = 2;
    /**
     * Grid 网格的列数
     */
    private static final int SPAN_COUNT = 3;

    public static class IntentBuilder {
        private Intent mIntent;

        public IntentBuilder(Context context) {
            mIntent = new Intent(context, PhotoPickerActivity.class);
        }

        /**
         * 拍照后图片保存的目录。如果传 null 表示没有拍照功能，如果不为 null 则具有拍照功能，
         */
        public IntentBuilder cameraFileDir(@Nullable File cameraFileDir) {
            mIntent.putExtra(EXTRA_CAMERA_FILE_DIR, cameraFileDir);
            return this;
        }

        /**
         * 图片选择张数的最大值
         *
         * @param maxChooseCount
         * @return
         */
        public IntentBuilder maxChooseCount(int maxChooseCount) {
            mIntent.putExtra(EXTRA_MAX_CHOOSE_COUNT, maxChooseCount);
            return this;
        }

        /**
         * 当前已选中的图片路径集合，可以传 null
         */
        public IntentBuilder selectedPhotos(@Nullable ArrayList<String> selectedPhotos) {
            mIntent.putStringArrayListExtra(EXTRA_SELECTED_PHOTOS, selectedPhotos);
            return this;
        }

        /**
         * 滚动列表时是否暂停加载图片，默认为 false
         */
        public IntentBuilder pauseOnScroll(boolean pauseOnScroll) {
            mIntent.putExtra(EXTRA_PAUSE_ON_SCROLL, pauseOnScroll);
            return this;
        }

        public Intent build() {
            return mIntent;
        }
    }

    /**
     * 获取已选择的图片集合
     *
     * @param intent
     * @return
     */
    public static ArrayList<String> getSelectedPhotos(Intent intent) {
        return intent.getStringArrayListExtra(EXTRA_SELECTED_PHOTOS);
    }
    private ImageLoader imageLoader;
    private static PhotoHelper helper = new PhotoHelper();
    private PhotoHelper(){}
    public static PhotoHelper get(){
        return helper;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }
}
