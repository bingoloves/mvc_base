package com.learn.mvc.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.learn.base.fragment.BaseFragment;
import com.learn.base.utils.ExecutorUtil;
import com.learn.base.utils.StringUtil;
import com.learn.gallery.GalleryActivity;
import com.learn.gallery.GalleryHelper;
import com.learn.multistate.MultistateLayout;
import com.learn.mvc.R;
import com.learn.photo.ImagePicker;
import com.learn.qiniu.PLVideoListActivity;
import com.learn.qiniu.QiNiuMainActivity;
import com.learn.wechat.ChatHelper;
import com.learn.wechat.callback.LoginCallback;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class Fragment1 extends BaseFragment{
    String[] videoPath = new String []{
            "https://cmgw-hz.lechange.com:8890/LCO/4L03F00PAZF1379/0/1/20191216T055001/dev_4L03F00PAZF1379_20191216T055001.m3u8",
            "https://cmgw-hz.lechange.com:8890/LCO/4L03F00PAZF1379/1/1/20191216T055008/dev_4L03F00PAZF1379_20191216T055008.m3u8",
            "https://cmgw-hz.lechange.com:8890/LCO/4L03F00PAZF1379/7/1/20191216T055052/dev_4L03F00PAZF1379_20191216T055052.m3u8"
    };
    String[] imageUrls = new String[]{
            "https://www.kymjs.com/qiniu/image/logo_s.jpg",
            "https://static.oschina.net/uploads/zb/2015/0905/214628_00Ui_1164691.gif",
            "https://www.jiuwa.net/tuku/20170823/F45v1uhz.gif",
            "https://www.kymjs.com/qiniu/image/logo_grey.png",
            "https://www.kymjs.com/qiniu/image/kymjs.png",
            "https://www.kymjs.com/qiniu/image/logo_s.png",
            "https://www.kymjs.com/qiniu/image/logo_grey.png",
            "https://www.kymjs.com/qiniu/image/kymjs.png"
    };
    @BindView(R.id.multistate_layout)
    MultistateLayout multistateLayout;
    @OnClick({R.id.moveQiNiu,R.id.succeed_tv,R.id.net_tv,R.id.loading_tv,R.id.data_tv,
            R.id.crash_btn,R.id.register_btn
    })
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.moveQiNiu:
                //startActivity(new Intent(getActivity(),QiNiuMainActivity.class));
                Intent intent = new Intent(getActivity(),PLVideoListActivity.class);
                intent.putExtra("videoPath",StringUtil.join(",",videoPath));
                startActivity(intent);
                break;
            case R.id.succeed_tv:
                multistateLayout.setStatus(MultistateLayout.SUCCESS);
                break;
            case R.id.net_tv:
                multistateLayout.setStatus(MultistateLayout.NO_NETWORK);
                break;
            case R.id.loading_tv:
                multistateLayout.setStatus(MultistateLayout.LOADING);
                break;
            case R.id.data_tv:
                multistateLayout.setStatus(MultistateLayout.EMPTY);
                break;
            case R.id.crash_btn:
                //伪造一个异常
                Integer.parseInt("goldze");
                break;
            case R.id.register_btn:

                break;
        }
    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_layout1;
    }

    @Override
    protected void initView(View root) {
        multistateLayout.setStatus(MultistateLayout.SUCCESS);
    }

    @Override
    protected void initListener() {
            multistateLayout.setOnReloadListener(new MultistateLayout.OnReloadListener() {
                @Override
                public void onReload(View v, int status) {
                    toast("status = "+status);
                }
            });
    }

    @Override
    protected void lazyLoad() {

    }


    /**
     * 设置预览
     */
    private void preview(){
        GalleryActivity.preview(getActivity(),0,imageUrls);
    }
}
