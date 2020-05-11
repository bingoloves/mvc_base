package com.learn.wechat.callback;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;

public class ChatConnectionListener implements EMConnectionListener {

    @Override
    public void onConnected() {
    }
    @Override
    public void onDisconnected(final int error) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if(error == EMError.USER_REMOVED){
//                    // 显示帐号已经被移除
//                }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//                    // 显示帐号在其他设备登录
//                } else {
//                    if (NetUtils.hasNetwork(MainActivity.this)){//连接不到聊天服务器
//
//                    } else{//当前网络不可用，请检查网络设置
//
//                    }
//                }
//            }
//        });
    }
}
