package com.learn.base.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;

import com.learn.base.adapter.recyclerview.base.ItemViewDelegate;
import com.learn.base.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

    public void clear(){
        if (mDatas!=null){
            mDatas.clear();
        }
        this.notifyDataSetChanged();
    }

    public void update(List<T> list){
        if (list!=null && !list.isEmpty()){
            if (mDatas!=null){
                mDatas.clear();
            }
            mDatas.addAll(list);
            this.notifyDataSetChanged();
        }
    }
}
