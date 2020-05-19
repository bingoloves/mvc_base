package com.learn.mvc.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.learn.base.fragment.BaseFragment;
import com.learn.base.utils.LogUtils;
import com.learn.gallery.GalleryActivity;
import com.learn.mvc.R;
import com.learn.photo.ImagePicker;
import com.learn.photo.adapter.AddImageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Fragment4 extends BaseFragment {
    private static final int REQUEST_CODE = 1001;
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
    @BindView(R.id.recyclerview)
    RecyclerView contentRv;
    private AddImageAdapter mAdapter;
    @OnClick({R.id.choose_photo,R.id.choose_multi_photo,R.id.crop,R.id.take_photo})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.choose_photo://单图
                ImagePicker.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .start(this, REQUEST_CODE); // 打开相册
                break;
            case R.id.choose_multi_photo://多图 (最多9张)
                ImagePicker.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(this, REQUEST_CODE); // 打开相册
                break;
            case R.id.crop://单选并剪裁
                ImagePicker.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setCrop(true)  // 设置是否使用图片剪切功能。
                        .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .setSingle(true)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .start(this, REQUEST_CODE); // 打开相册
                break;
            case R.id.take_photo://仅拍照，不打开相册
                ImagePicker.builder()
                        .onlyTakePhoto(true)  // 仅拍照，不打开相册
                        .start(this, REQUEST_CODE);

                /*ImagePicker.builder()//拍照并剪裁
                        .setCrop(true) // 设置是否使用图片剪切功能。
                        .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .onlyTakePhoto(true)  // 仅拍照，不打开相册
                        .start(this, REQUEST_CODE);*/
                break;
        }
    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_layout4;
    }

    @Override
    protected void initView(View root) {
        contentRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mAdapter = new AddImageAdapter(getContext(),true);
        mAdapter.setOnItemClickListener(new AddImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(int postion, boolean isAddPhoto) {
                if (isAddPhoto){
                    ImagePicker.builder()
                            .useCamera(true) // 设置是否使用拍照
                            .setSingle(true)  //设置是否单选
                            .canPreview(true) //是否点击放大图片查看,，默认为true
                            .start(Fragment4.this, REQUEST_CODE); // 打开相册
                } else {
                    GalleryActivity.preview(getActivity(),postion,mAdapter.getImages());
                }
            }
        });
        contentRv.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void lazyLoad() {

    }
    private static final int PRC_PHOTO_PICKER = 4;
    private static final int REQUEST_CODE_PERMISSION_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PERMISSION_TAKE_PHOTO = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_TAKE_PHOTO = 2;
    private static final int REQUEST_CODE_CROP = 3;
    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImagePicker.SELECT_RESULT);
            LogUtils.getInstance().e(new Gson().toJson(images));
            boolean isCameraImage = data.getBooleanExtra(ImagePicker.IS_CAMERA_IMAGE, false);
            LogUtils.getInstance().e("是否是拍照图片 :"+ isCameraImage);
            mAdapter.refresh(images);
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CHOOSE_PHOTO)
    public void choosePhoto() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {

        } else {
            EasyPermissions.requestPermissions(this, "请开起存储空间权限，以正常使用 Demo", REQUEST_CODE_PERMISSION_CHOOSE_PHOTO, perms);
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_TAKE_PHOTO)
    public void takePhoto() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            try {

            } catch (Exception e) {

            }
        } else {
            EasyPermissions.requestPermissions(this, "请开起存储空间和相机权限，以正常使用 Demo", REQUEST_CODE_PERMISSION_TAKE_PHOTO, perms);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}