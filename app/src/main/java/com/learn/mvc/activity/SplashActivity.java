package com.learn.mvc.activity;

import android.os.Bundle;

import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EasyUtils;
import com.learn.base.activity.BaseActivity;
import com.learn.base.utils.ExecutorUtil;
import com.learn.mvc.MainActivity;
import com.learn.mvc.R;
import com.learn.wechat.ChatHelper;

public class SplashActivity extends BaseActivity {

    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setFullScreen();
//        ExecutorUtil.get().sheduleDelay(1000, new Runnable() {
//            @Override
//            public void run() {
//                startActivity(LoginActivity.class,true);
//            }
//        });
        ChatHelper.getChatHelper().initHandler(getMainLooper());
    }

    @Override
    protected void onStart() {
        super.onStart();
        ExecutorUtil.get().execute(new Runnable() {
            @Override
            public void run() {
                if (ChatHelper.getChatHelper().isLoggedIn()) {
                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                    long start = System.currentTimeMillis();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    long costTime = System.currentTimeMillis() - start;
                    //wait
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String topActivityName = EasyUtils.getTopActivityName(EMClient.getInstance().getContext());
                    //if (topActivityName != null && (topActivityName.equals(VideoCallActivity.class.getName()) || topActivityName.equals(VoiceCallActivity.class.getName()) || topActivityName.equals(ConferenceActivity.class.getName()))) {
                        // avoid main screen overlap Calling Activity
                    //} else {
                        //enter main screen
                        startActivity(MainActivity.class,true);
                    //}
                }else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    startActivity(LoginActivity.class,true);
                }
            }
        });
    }
}
