package com.learn.mvc;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.learn.base.app.AppConfig;
import com.learn.base.crash.CaocConfig;
import com.learn.base.utils.LogUtils;
import com.learn.multistate.MultistateLayout;
import com.learn.wechat.ChatHelper;

import java.util.Iterator;
import java.util.List;

public class App extends Application {
    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.getInstance().setDebug(BuildConfig.DEBUG);//是否开启打印日志
        initCrash();//初始化全局异常崩溃
        initMultisateLayout();//多状态布局
        AppConfig.getConfig().init(this);//屏幕适配
        initWechat();
    }

    /**
     * 初始化聊天相关配置
     */
    private void initWechat() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
            LogUtils.getInstance().e("enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        ChatHelper.getChatHelper().init(this,BuildConfig.DEBUG);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    /**
     * 异常捕捉
     */
    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(BuildConfig.DEBUG) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
//                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(MainActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }

    /**
     * 初始化多状态布局
     */
    private void initMultisateLayout() {
        MultistateLayout.getBuilder()
                .setLoadingText("加载中...")
                .setLoadingTextSize(12)
                .setLoadingTextColor(R.color.colorPrimary)
                //.setLoadingViewLayoutId(R.layout.custom_loading_view) //如果设置了自定义loading视图,上面三个方法失效
                .setEmptyImgId(R.drawable.ic_empty2)
                .setErrorImgId(R.drawable.ic_error)
                .setNoNetWorkImgId(R.drawable.ic_no_network2)
                .setEmptyImageVisible(true)
                .setErrorImageVisible(true)
                .setNoNetWorkImageVisible(true)
                //.setEmptyText(getString(R.string.custom_empty_text))
                //.setErrorText(getString(R.string.custom_error_text))
                //.setNoNetWorkText(getString(R.string.custom_nonetwork_text))
                .setAllTipTextSize(12)
                .setAllTipTextColor(R.color.text_color_child)
                .setAllPageBackgroundColor(R.color.pageBackground)
                //.setReloadBtnText(getString(R.string.custom_reloadBtn_text))
                .setReloadBtnTextSize(12)
                .setReloadBtnTextColor(R.color.colorPrimary)
                .setReloadBtnBackgroundResource(R.drawable.selector_btn_normal)
                .setReloadBtnVisible(false)
                .setReloadClickArea(MultistateLayout.FULL);
    }
}
