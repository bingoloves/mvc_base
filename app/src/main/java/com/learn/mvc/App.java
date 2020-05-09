package com.learn.mvc;

import android.app.Application;

import com.learn.multistate.MultistateLayout;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initMultisateLayout();
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
