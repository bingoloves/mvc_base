package com.learn.base.activity;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.learn.base.R;
import com.learn.base.statusbar.StatusBarUtil;
import com.learn.base.toast.ToastUtil;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 设置黑白屏效果
     */
    protected void setBlackWhiteScreen(){
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE,paint);
    }


    @Override
    public void setContentView(int layoutResID) {
        //setBlackWhiteScreen();
        super.setContentView(layoutResID);
        initStatusBar();
        unbinder = ButterKnife.bind(this);
    }

    private void initStatusBar() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //setStatusBarDark();
    }

    /**
     * 是否全屏显示
     */
    protected void setFullScreen(){
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
    }

    protected void setStatusBarDark(){
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
    }

    /**
     * 简单统一调用自定义 Toast
     */
    public void toast(String msg){
        ToastUtil.showAtCenter(this,msg);
    }



    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);//右进左出效果
        //overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);//下进上出效果
    }
    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz,false);
    }
    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     * @param isFinish 是否关闭当前页
     */
    public void startActivity(Class<?> clz,boolean isFinish) {
        startActivity(new Intent(this, clz));
        if (isFinish)finish();
    }
    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        startActivity(clz,bundle,false);
    }
    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     * @param isFinish 是否关闭当前页
     */
    public void startActivity(Class<?> clz, Bundle bundle,boolean isFinish) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isFinish)finish();
    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
        //overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
