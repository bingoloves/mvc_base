package com.learn.mvc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.learn.base.activity.BaseActivity;
import com.learn.base.statusbar.StatusBarUtil;
import com.learn.base.utils.ExecutorUtil;
import com.learn.base.utils.LogUtils;
import com.learn.base.utils.SPUtils;
import com.learn.mvc.App;
import com.learn.mvc.Constants;
import com.learn.mvc.MainActivity;
import com.learn.mvc.R;
import com.learn.wechat.ChatHelper;
import com.learn.wechat.callback.LoginCallback;
import com.learn.wechat.db.DemoDBManager;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.name_et)
    EditText nameEditText;
    @BindView(R.id.password_et)
    EditText passwordEditText;
    @OnClick({R.id.register_btn,R.id.login_btn})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.register_btn://注册
                register();
                break;
            case R.id.login_btn://登录
                login();
                break;
        }
    }

    private boolean autoLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ChatHelper.getChatHelper().isLoggedIn()) {
            autoLogin = true;
            startActivity(MainActivity.class);
            return;
        }
        setContentView(R.layout.activity_login);
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimaryDark));
        if (ChatHelper.getChatHelper().getCurrentUsernName() != null) {
            nameEditText.setText(ChatHelper.getChatHelper().getCurrentUsernName());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }
    private void register() {
        String username = nameEditText.getText().toString().trim();
        String pwd = passwordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(username)|| TextUtils.isEmpty(pwd)){
            toast("用户名密码不可为空");
            return;
        }
        // call method in SDK
        ExecutorUtil.get().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(username, pwd);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // save current user
                            ChatHelper.getChatHelper().setCurrentUserName(username);
                            toast(getResources().getString(R.string.Registered_successfully));
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode=e.getErrorCode();
                            if(errorCode==EMError.NETWORK_ERROR){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_ALREADY_EXIST){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.EXCEED_SERVICE_LIMIT){
                                toast(getResources().getString(R.string.register_exceed_service_limit));
                            }else{
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void login(){
        String name = nameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(name)|| TextUtils.isEmpty(password)){
            toast("用户名密码不可为空");
            return;
        }
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        ChatHelper.getChatHelper().setCurrentUserName(name);

        final long start = System.currentTimeMillis();
        // call login method
        LogUtils.getInstance().e("EMClient.getInstance().login");
        EMClient.getInstance().login(name, password, new EMCallBack() {

            @Override
            public void onSuccess() {
                LogUtils.getInstance().e("login: onSuccess");
                SPUtils.put(LoginActivity.this,Constants.USER_NAME,name);
                SPUtils.put(LoginActivity.this,Constants.USER_PASSWORD,password);
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        App.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

//                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
//                    //pd.dismiss();
//                }

                // get user's info (this should be get from App's server or 3rd party service)
                ChatHelper.getChatHelper().getUserProfileManager().asyncGetCurrentUserInfo();

                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);//右进左出效果
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                LogUtils.getInstance().e("login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                LogUtils.getInstance().e("login: onError: " + code);
                runOnUiThread(new Runnable() {
                    public void run() {
                        toast(getString(R.string.Login_failed) + message);
                    }
                });
            }
        });
    }
}
