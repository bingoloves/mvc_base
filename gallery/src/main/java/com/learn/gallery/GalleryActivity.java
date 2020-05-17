/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.learn.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * 图片预览界面
 */
public class GalleryActivity extends FragmentActivity {

    public static String URL_KEY = "gallery_url_key";
    public static String URL_INDEX = "gallery_url_index";
    private TextView textView;
    private String[] imageUrls;
    private int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getIntentData();
        initView();
    }

    private void getIntentData() {
        Intent from = getIntent();
        imageUrls = from.getStringArrayExtra(URL_KEY);
        index = from.getIntExtra(URL_INDEX, 0);
    }

    private void initView() {
        textView = findViewById(R.id.page_title);
        if (imageUrls.length < 2) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(String.format("%d/%d", index + 1, imageUrls.length));
        }
        GalleryViewPager mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new GalleryPagerAdapter(this, imageUrls));
        mViewPager.setCurrentItem(index);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                textView.setText(String.format("%d/%d", position + 1, imageUrls.length));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 预览
     * @param cxt
     * @param index
     * @param urls
     */
    public static void preview(Context cxt, int index, String... urls) {
        if (!StringUtils.isEmpty(urls)) {
            Intent intent = new Intent();
            intent.putExtra(GalleryActivity.URL_INDEX, index);
            intent.putExtra(GalleryActivity.URL_KEY, urls);
            intent.setClass(cxt, GalleryActivity.class);
            cxt.startActivity(intent);
        }
    }
    public static void preview(Context cxt, String... urls) {
        preview(cxt, 0, urls);
    }
    public static void preview(Context cxt,int index, List<String> urls) {
        preview(cxt, index, urls.toArray(new String[urls.size()]));
    }
}
