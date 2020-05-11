package com.learn.wechat;

/**
 * 同步登录回调
 */
public interface LoginCallback {
    void onNext();
    void onError(String msg);
}
