package com.learn.photo.bean;

import android.net.Uri;

import java.io.Serializable;

/**
 *图片实体类
 */
public class Image implements Serializable {

    private String path;
    private long time;
    private String name;
    private String mimeType;
    private Uri uri;

    public Image(String path, long time, String name, String mimeType, Uri uri) {
        this.path = path;
        this.time = time;
        this.name = name;
        this.mimeType = mimeType;
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isGif(){
        return "image/gif".equals(mimeType);
    }
}
