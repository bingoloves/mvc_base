package com.learn.gallery;

/**
 * Created by bingo on 2020/5/17.
 */

public class GalleryHelper {
    private static GalleryHelper galleryHelper = new GalleryHelper();
    public static GalleryHelper get(){
        return galleryHelper;
    }
    private ImageLoader imageLoader;
   /* private GalleryHelper(Builder builder){
        this.imageLoader = builder.imageLoader;
    }
    public static class Builder{
        private ImageLoader imageLoader;
        public Builder setImageLoader(ImageLoader imageLoader){
            this.imageLoader = imageLoader;
            return this;
        }

        public GalleryHelper build(){
            return new GalleryHelper(this);
        }
    }*/

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }
}
