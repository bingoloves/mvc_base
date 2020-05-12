package com.learn.mvc;

/**
 * 常量管理
 */
public class Constants {
    /*缓存的用户信息*/
    public static final String TOKEN = "token";//token令牌
    public static final String USER_NAME = "user_name";//用户姓名
    public static final String USER_PASSWORD = "user_password";//用户密码
    public static final String USER_NICKNAME = "user_nickname";//用户昵称
    public static final String USER_AVATAR = "user_avatar";//用户头像
    public static final String USER_ID = "user_id";//用户id


    public static final int REQUEST_CODE_CHOOSE_PHOTO = 200;//请求照片code
    public static final int REQUEST_CODE_CHECK_PERSON = 201;//选择人员code
    public static final int RESULT_CODE_CHECK_PERSON = 202;//选择人员返回code
    //用于跳转刷新页面
    public static final int REQUEST_CODE_OK = 203;//通用请求code
    public static final int RESULT_CODE_OK = 204;//通用返回code

    /*服务端相关*/
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final int SERVER_CODE_SESSION_TIMEOUT = 400;//回话超时

    /*网络异常*/
    public static final String NO_NETWORK = "网络异常！";
    public static final String NO_DATA = "暂无数据！";
    public static final String NO_SERVER = "暂无服务！";
    public static final String NET_SERVER_ERROR = "连接不上服务器，请检查手机网络！";
    public static final String NO_DATA_ERROR = "获取不到相关信息，请检查下手机网络！";

}
