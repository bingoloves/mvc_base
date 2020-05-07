package com.learn.mvc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.learn.base.activity.BaseActivity;
import com.learn.base.layout.Tabbar;
import com.learn.base.statusbar.StatusBarUtil;
import com.learn.mvc.fragment.Fragment1;
import com.learn.mvc.fragment.Fragment2;
import com.learn.mvc.fragment.Fragment3;
import com.learn.mvc.fragment.Fragment4;
import com.learn.mvc.fragment.Fragment5;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_nav_item1)
    Tabbar tabView1;
    @BindView(R.id.bottom_nav_item2)
    Tabbar tabView2;
    @BindView(R.id.bottom_nav_item3)
    Tabbar tabView3;
    @BindView(R.id.bottom_nav_item4)
    Tabbar tabView4;
    @BindView(R.id.bottom_nav_item5)
    Tabbar tabView5;

    @OnClick({R.id.bottom_nav_item1,R.id.bottom_nav_item2,R.id.bottom_nav_item3,R.id.bottom_nav_item4,R.id.bottom_nav_item5})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.bottom_nav_item1:
                currentIndex = 0;
                break;
            case R.id.bottom_nav_item2:
                currentIndex = 1;
                break;
            case R.id.bottom_nav_item3:
                currentIndex = 2;
                break;
            case R.id.bottom_nav_item4:
                currentIndex = 3;
                break;
            case R.id.bottom_nav_item5:
                currentIndex = 4;
                break;
        }
        toast(currentIndex+"");
        showFragment();
        updateTabs(currentIndex);
    }
    private static final String CURRENT_FRAGMENT = "current_fragment";
    private int currentIndex = 0;
    private List<Fragment> fragments = new ArrayList<>();
    private List<Tabbar> mTabViews = new ArrayList<>();
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimaryDark));
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            //获取"内存重启"时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0);
            fragments.clear();
            fragments.add(fragmentManager.findFragmentByTag(0 + ""));
            fragments.add(fragmentManager.findFragmentByTag(1 + ""));
            fragments.add(fragmentManager.findFragmentByTag(2 + ""));
            fragments.add(fragmentManager.findFragmentByTag(3 + ""));
            fragments.add(fragmentManager.findFragmentByTag(4 + ""));
        } else {//正常启动时调用
            fragments.add(new Fragment1());
            fragments.add(new Fragment2());
            fragments.add(new Fragment3());
            fragments.add(new Fragment4());
            fragments.add(new Fragment5());
            mTabViews.add(tabView1);
            mTabViews.add(tabView2);
            mTabViews.add(tabView3);
            mTabViews.add(tabView4);
            mTabViews.add(tabView5);
        }
        showFragment();
        updateTabs(currentIndex);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_FRAGMENT, currentIndex);//"内存重启"时保存当前的fragment索引
        super.onSaveInstanceState(outState);
    }
    /**
     * 显示fragment
     */
    private void showFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == currentIndex){
                if (!fragment.isAdded()){
                    transaction.add(R.id.container,fragment, "" + i);
                }
                transaction.show(fragment);
            } else {
                if (!fragment.isAdded()){
                    transaction.add(R.id.container,fragment, "" + i);
                }
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }

    /**
     * 显示tabView
     * @param index
     */
    private void updateTabs(int index) {
        for (int i = 0; i < mTabViews.size(); i++) {
            if (index == i) {
                mTabViews.get(i).setSelected(true);
            } else {
                mTabViews.get(i).setSelected(false);
            }
        }
//        if (currentIndex == 0){
//            StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimaryDark));
//        } else {
//            StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorAccent));
//        }
    }
}
