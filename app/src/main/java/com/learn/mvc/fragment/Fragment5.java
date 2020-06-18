package com.learn.mvc.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.learn.base.adapter.recyclerview.CommonAdapter;
import com.learn.base.adapter.recyclerview.base.ViewHolder;
import com.learn.base.fragment.BaseFragment;
import com.learn.base.utils.LogUtils;
import com.learn.mvc.R;
import com.learn.refresh.OnLoadMoreListener;
import com.learn.refresh.OnRefreshListener;
import com.learn.refresh.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Fragment5 extends BaseFragment implements OnLoadMoreListener, OnRefreshListener {

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;

    private List<String> list = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_layout5;
    }

    @Override
    protected void initView(View root) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.add("你好！");
        list.add("Hello");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        list.add("特朗普");
        recyclerView.setAdapter(new CommonAdapter<String>(getContext(),R.layout.layout_list_item,list) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.tv_title,o);
            }
        });
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void lazyLoad() {
        LogUtils.getInstance().e("lazyLoad");
    }

    @Override
    protected void refreshLoad() {
        super.refreshLoad();
        toast("页面切换到5");
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
            }
        },1000);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
            }
        },1000);
    }
}