package com.learn.base.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.base.R;

public class Tabbar extends FrameLayout {

    private ImageView mTabImageView;
    private TextView mTitleView;

    private String mTitle;
    private Drawable mNormalDrawable;
    private Drawable mSelectedDrawable;
    private int mTargetColor;

    // 标题和轮廓图默认的着色
    private static final int DEFAULT_TAB_COLOR = 0xff000000;

    // 标题和轮廓图最终的着色
    private static final int DEFAULT_TAB_TARGET_COLOR = 0x11E53;

    public Tabbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        inflate(context, R.layout.layout_common_tab, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabView);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TabView_tabColor) {// 获取标题和轮廓最终的着色
                mTargetColor = a.getColor(attr, DEFAULT_TAB_TARGET_COLOR);
            } else if (attr == R.styleable.TabView_tabImage) {// 获取轮廓图
                mNormalDrawable = a.getDrawable(attr);
            } else if (attr == R.styleable.TabView_tabSelectedImage) {// 获取选中图
                mSelectedDrawable = a.getDrawable(attr);
            } else if (attr == R.styleable.TabView_tabTitle) {// 获取标题
                mTitle = a.getString(attr);
            }
        }
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitleView = findViewById(R.id.tab_title);
        mTitleView.setTextColor(DEFAULT_TAB_COLOR);
        mTitleView.setText(mTitle);
        mTabImageView = findViewById(R.id.tab_image);
        mTabImageView.setImageDrawable(mNormalDrawable);
    }

    public void setSelected(boolean selected) {
        if (mTitleView!=null){
            mTitleView.setTextColor(selected?mTargetColor:DEFAULT_TAB_COLOR);
        }
        if (mTabImageView!=null){
            mTabImageView.setImageDrawable(selected?mSelectedDrawable:mNormalDrawable);
        }
    }
}
