package com.learn.mvc.glide;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * glide 图片加载帮助类
 */
public class GlideHelper {
    public static final int NONE = 0 ;         //普通类
    public static final int ROUND = 1;         //方形圆角类
    public static final int CIRCLE = 2;        //圆形头像类

    @IntDef({NONE,ROUND,CIRCLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }
    private int palceholder = -1;
    private int error = -1;
    private boolean isDiskCache = true;
    private int type = NONE;
    private int roundingRadius = 8;//圆角时 默认弧度,非圆角类型设置无效;
    private int width,height;//设置宽高
    private boolean fitCenter = false;
    private boolean isGif = false;
    private boolean crossFade = false;//淡入淡出效果

    public GlideHelper(Builder builder) {
        this.palceholder = builder.palceholder;
        this.error = builder.error;
        this.isDiskCache = builder.isDiskCache;
        this.type = builder.type;
        this.roundingRadius = builder.roundingRadius;
        this.width = builder.width;
        this.height = builder.height;
        this.fitCenter = builder.fitCenter;
        this.isGif = builder.isGif;
        this.crossFade = builder.crossFade;
    }

    public static class Builder{
        private int palceholder;
        private int error;
        private boolean isDiskCache;
        private int type;
        private int roundingRadius;
        private int width,height;
        private boolean fitCenter;
        private boolean isGif;
        private boolean crossFade;
        public Builder setPalceholder(int palceholder) {
            this.palceholder = palceholder;
            return this;
        }

        public Builder setError(int error) {
            this.error = error;
            return this;
        }

        public Builder setDiskCache(boolean diskCache) {
            isDiskCache = diskCache;
            return this;
        }

        public Builder setType(@Type int type) {
            this.type = type;
            return this;
        }
        public Builder setRoundingRadius(int roundingRadius) {
            this.roundingRadius = roundingRadius;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder fitCenter() {
            this.fitCenter = true;
            return this;
        }

        public Builder isGif() {
            this.isGif = true;
            return this;
        }
        public Builder crossFade() {
            this.crossFade = true;
            return this;
        }

        public GlideHelper build(){
            return new GlideHelper(this);
        }
    }

    public void load(Context context, String path, ImageView imageView) {
        if (null != context) {
            DrawableTypeRequest<String> load = Glide.with(context).load(getLoadPath(path));
            if (palceholder != -1) load.placeholder(palceholder);
            if (error != -1) load.placeholder(error);
            if (isGif) load.asGif();
            if (isDiskCache) load.diskCacheStrategy(DiskCacheStrategy.SOURCE);
            if (width!=0 && height!=0)load.override(width, height);
            if (fitCenter) load.fitCenter();
            if (crossFade)load.crossFade();
            switch (type){
                case ROUND:
                    load.transform(new GlideRoundTransform(context,roundingRadius));
                    break;
                case CIRCLE:
                    load.transform(new GlideCircleTransform(context));
                    break;
                case NONE:
                default:
                    break;
            }
            load.into(imageView);
        }
    }

    /**
     * 加载本地静态图资源 不存在占位图说法  效果不理想,不推荐使用
     * @param context
     * @param resId
     * @param imageView
     */
    @Deprecated
    public void load(Context context, int resId, ImageView imageView) {
        if (null != context) {
            DrawableTypeRequest<Integer> load = Glide.with(context).load(resId);
            if (isGif) load.asGif();
            if (isDiskCache) load.diskCacheStrategy(DiskCacheStrategy.SOURCE);
            if (width!=0&&height!=0)load.override(width, height);
            if (fitCenter) load.fitCenter();
            if (crossFade)load.crossFade();
            switch (type){
                case ROUND:
                    load.transform(new GlideRoundTransform(context,roundingRadius));
                    break;
                case CIRCLE:
                    load.transform(new GlideCircleTransform(context));
                    break;
                case NONE:
                default:
                    break;
            }
            load.into(imageView);
        }
    }
    /**
     * 对线上资源为空判断
     * @param url
     * @return
     */
    private String getLoadPath(String url){
        return TextUtils.isEmpty(url)?"":url;
    }
}
