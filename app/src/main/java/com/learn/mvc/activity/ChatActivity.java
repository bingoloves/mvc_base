package com.learn.mvc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hyphenate.util.EasyUtils;
import com.learn.base.activity.BaseActivity;
import com.learn.base.statusbar.StatusBarUtil;
import com.learn.mvc.MainActivity;
import com.learn.mvc.fragment.ChatFragment;
import com.learn.wechat.R;
import com.learn.wechat.fragment.EaseChatFragment;

/**
 * Created by bingo on 2020/5/11.
 */

public class ChatActivity extends BaseActivity{
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    public String toChatUsername;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimaryDark));
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public String getToChatUsername(){
        return toChatUsername;
    }
}
