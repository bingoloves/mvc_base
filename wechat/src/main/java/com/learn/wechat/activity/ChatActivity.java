package com.learn.wechat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.learn.base.activity.BaseActivity;
import com.learn.base.statusbar.StatusBarUtil;
import com.learn.wechat.R;

/**
 * Created by bingo on 2020/5/11.
 */

public class ChatActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimaryDark));
    }
}
