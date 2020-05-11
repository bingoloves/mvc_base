package com.learn.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.learn.base.utils.ActivityStackManager;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Stack;

/**
 * App的配置信息
 */
public class AppConfig implements Application.ActivityLifecycleCallbacks {
    private static AppConfig config;
    private Context context;
    private Application application;
    private float fontScale = 1.0f;//字体缩放倍数
    private float width = 375;//设计图的宽高（dp值）414/736
    private float height = 789;
    private float sNoncompatDesity;//屏幕适配分辨率
    private float sNoncompatScaledDesity;
    private WeakReference<Activity> currentActivity;//当前Activity的弱引用

    private AppConfig(){}

    public static AppConfig getConfig(){
        if (config == null){
            synchronized (AppConfig.class){
                if (config == null){
                    config = new AppConfig();
                }
            }
        }
        return  config;
    }
    public void init(Application application){
        this.application = application;
        context = application.getApplicationContext();
        this.application.registerActivityLifecycleCallbacks(this);
    }
    /**
     * 获取当前页面的Activity对象
     * @return Activity对象弱引用
     */
    public WeakReference<Activity> getCurrentActivity() {
        return currentActivity;
    }

    /**
     * 提供一个全局的Context
     * @return
     */
    public Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }
    public float getFontScale() {
        return fontScale;
    }

    public void setFontScale(float fontScale) {
        this.fontScale = fontScale;
    }

    /**
     * 设置全部已有页面页面Activity字体大小
     * @param fontScale
     */
    public void setAppFontSize(float fontScale) {
        Stack<Activity> activityStack = ActivityStackManager.getActivityStack();
        if (activityStack!=null){
            this.fontScale = fontScale;
            for (Activity activity : activityStack) {
                Resources resources = activity.getResources();
                if (resources != null) {
                    android.content.res.Configuration configuration = resources.getConfiguration();
                    configuration.fontScale = fontScale;
                    resources.updateConfiguration(configuration, resources.getDisplayMetrics());
                    activity.recreate();
                }
            }
        }
    }
    /**
     * 适配
     * @param activity
     * @param application
     * @param isWidth  是否宽度适配
     */
    public void setCustomDesity(@NonNull Activity activity, @NonNull final Application application,boolean isWidth) {
        final DisplayMetrics appdisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDesity == 0) {
            sNoncompatDesity = appdisplayMetrics.density;
            sNoncompatScaledDesity = appdisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override


                public void onConfigurationChanged(Configuration configuration) {
                    if (configuration != null && configuration.fontScale > 0) {
                        sNoncompatScaledDesity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override

                public void onLowMemory() {

                }

            });
        }
        float targetDesity = isWidth?((float) appdisplayMetrics.widthPixels / width):((float) appdisplayMetrics.heightPixels / height);//设计图的宽度dp
        float targetScaleDesity = targetDesity * (sNoncompatScaledDesity / sNoncompatDesity);
        int targetDesityDpi = (int) (160 * targetDesity);
        appdisplayMetrics.density = targetDesity;
        appdisplayMetrics.scaledDensity = targetScaleDesity;
        appdisplayMetrics.densityDpi = targetDesityDpi;
        appdisplayMetrics.scaledDensity = targetScaleDesity;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDesity;
        activityDisplayMetrics.scaledDensity = targetScaleDesity;
        activityDisplayMetrics.densityDpi = targetDesityDpi;
        setBitmapDefaultDensity(activityDisplayMetrics.densityDpi);
    }

    /**
     * 设置 Bitmap 的默认屏幕密度
     * 由于 Bitmap 的屏幕密度是读取配置的，导致修改未被启用
     * 所有，反射方式强行修改
     *
     * @param defaultDensity 屏幕密度
     */
    private static void setBitmapDefaultDensity(int defaultDensity) {
        Class clazz;
        try {
            clazz = Class.forName("android.graphics.Bitmap");
            Field field = clazz.getDeclaredField("sDefaultDensity");
            field.setAccessible(true);
            field.set(null, defaultDensity);
            field.setAccessible(false);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消当前的适配
     *
     * @param activity
     */
    public void cancelAdaptScreen(Activity activity) {
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        final DisplayMetrics appDm = context.getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        activityDm.density = systemDm.density;
        activityDm.scaledDensity = systemDm.scaledDensity;
        activityDm.densityDpi = systemDm.densityDpi;
        appDm.density = systemDm.density;
        appDm.scaledDensity = systemDm.scaledDensity;
        appDm.densityDpi = systemDm.densityDpi;
        setBitmapDefaultDensity(activityDm.densityDpi);
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        // 禁止字体大小随系统设置变化
        Resources resources = activity.getResources();
        if (resources != null && resources.getConfiguration().fontScale != fontScale) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = fontScale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        setCustomDesity(activity,application,true);
        currentActivity = new WeakReference<>(activity);
        ActivityStackManager.getStackManager().addActivity(activity);

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        ActivityStackManager.getStackManager().removeActivity(activity);
    }

    /**
     * 获取本地软件版本号
     */
    public int getLocalVersion(Context context) {
        int localVersion = 0;
        try {
        PackageInfo packageInfo = context.getApplicationContext()
                .getPackageManager()
                .getPackageInfo(context.getPackageName(), 0);
        localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public String getLocalVersionName(Context context) {
        String localVersionName = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            localVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersionName;
    }
}
