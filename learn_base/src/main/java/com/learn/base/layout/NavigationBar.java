package com.learn.base.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learn.base.R;

public class NavigationBar extends RelativeLayout {
    private Context context;
    private int backgroundColor;
    private RelativeLayout rootView;
    private LinearLayout navBarBackLayout,navBarMenuLayout;
    private TextView navBarBackText,navBarTitle,navBarMenuText;
    private ImageView navBarBackIcon,navBarMenuIcon;
    private String backText,title,menuText;
    private Drawable backIcon,menuIcon;
    public NavigationBar(Context context) {
        super(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        this.context = context;
        View.inflate(context, R.layout.layout_navigation_bar, this);//挂载view
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.NavigationBar_nav_bar_bg) {
                backgroundColor = a.getColor(attr, Color.parseColor("#008577"));
            } else if (attr == R.styleable.NavigationBar_nav_bar_left_title) {
                backText = a.getString(attr);
            } else if (attr == R.styleable.NavigationBar_nav_bar_left_image) {
                backIcon = a.getDrawable(attr);
            } else if (attr == R.styleable.NavigationBar_nav_bar_title) {
                title = a.getString(attr);
            } else if (attr == R.styleable.NavigationBar_nav_bar_right_title) {
                menuText = a.getString(attr);
            }else if (attr == R.styleable.NavigationBar_nav_bar_right_image) {
                menuIcon = a.getDrawable(attr);
            }
        }
        //回收资源，这一句必须调用
        a.recycle();
    }

    /**
     * 此方法会在所有的控件都从xml文件中加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rootView = findViewById(R.id.nav_bar_root);

        navBarBackLayout = findViewById(R.id.nav_bar_back);
        navBarMenuLayout = findViewById(R.id.nav_bar_menu);

        navBarBackText= findViewById(R.id.nav_bar_back_text);
        navBarTitle = findViewById(R.id.nav_bar_title);
        navBarMenuText = findViewById(R.id.nav_bar_menu_text);

        navBarBackIcon = findViewById(R.id.nav_bar_back_icon);
        navBarMenuIcon= findViewById(R.id.nav_bar_menu_icon);
        rootView.setBackgroundColor(backgroundColor);
        navBarBackIcon.setImageDrawable(backIcon);
        navBarMenuIcon.setImageDrawable(menuIcon);
        navBarMenuText.setText(backText);
        navBarTitle.setText(title);
        navBarMenuText.setText(menuText);
    }

    public void setThemeColor(int backgroundColor) {
        rootView.setBackgroundColor(backgroundColor);
    }

    public void setLeftText(CharSequence left){
        navBarBackText.setText(left);
    }

    public void setTitle(CharSequence title){
        navBarTitle.setText(title);
    }

    public void setRightText(CharSequence right){
        navBarMenuText.setText(right);
    }
    public void setNavBarBackIcon(@Nullable Drawable drawable){
        navBarBackIcon.setImageDrawable(drawable);
    }
    public void setNavBarBackIcon(@DrawableRes int resId){
        navBarBackIcon.setImageResource(resId);
    }
    public void setNavBarMenuIcon(@Nullable Drawable drawable){
        navBarMenuIcon.setImageDrawable(drawable);
    }
    public void setNavBarMenuIcon(@DrawableRes int resId){
        navBarMenuIcon.setImageResource(resId);
    }
    public void setMenuClickListener(@Nullable OnClickListener listener){
        navBarMenuLayout.setOnClickListener(listener);
    }
    public void setBackClickListener(@Nullable OnClickListener listener){
        navBarBackLayout.setOnClickListener(listener);
    }
    public void setTitleSize(int size){
        navBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
}
