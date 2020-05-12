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
import com.learn.multistate.MultistateLayout;
import com.learn.mvc.R;
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

    @BindView(R.id.multistate_layout)
    MultistateLayout multistateLayout;
    @BindView(R.id.avatar_iv)
    ImageView mAvatarIv;
    @OnClick({R.id.moveQiNiu,R.id.choose_photo,R.id.take_photo,R.id.pre_photo,
            R.id.succeed_tv,R.id.net_tv,R.id.loading_tv,R.id.data_tv,
            R.id.crash_btn,R.id.register_btn
    })
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.moveQiNiu:
                startActivity(new Intent(getActivity(),QiNiuMainActivity.class));
                break;
            case R.id.choose_photo:
                choosePhoto();
                break;
            case R.id.take_photo:
                takePhoto();
                break;
            case R.id.pre_photo:
                preview();
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
    protected void createView(ViewGroup container, Bundle savedInstanceState) {

    }
    private BGAPhotoHelper mPhotoHelper;
    private static final int REQUEST_CODE_PERMISSION_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PERMISSION_TAKE_PHOTO = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_TAKE_PHOTO = 2;
    private static final int REQUEST_CODE_CROP = 3;
    @Override
    protected void initView(View root) {
        multistateLayout.setStatus(MultistateLayout.SUCCESS);
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
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
    private static final int PRC_PHOTO_PICKER = 4;
    private static final int RC_CHOOSE_PHOTO = 4;
    private static final int RC_PHOTO_PREVIEW = 5;
    private ArrayList<String> models = new ArrayList<>();
    private String prePath = "";
    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(getContext())
                    .cameraFileDir(takePhotoDir)// 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(9) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    /**
     * 设置预览
     */
    private void preview(){
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(getActivity())
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                //.maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                //.currentPosition(position) //当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BGAPhotoHelper.onSaveInstanceState(mPhotoHelper, outState);
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        BGAPhotoHelper.onRestoreInstanceState(mPhotoHelper, savedInstanceState);
//    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CHOOSE_PHOTO)
    public void choosePhoto() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            startActivityForResult(mPhotoHelper.getChooseSystemGalleryIntent(), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "请开起存储空间权限，以正常使用 Demo", REQUEST_CODE_PERMISSION_CHOOSE_PHOTO, perms);
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_TAKE_PHOTO)
    public void takePhoto() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            try {
                startActivityForResult(mPhotoHelper.getTakePhotoIntent(), REQUEST_CODE_TAKE_PHOTO);
            } catch (Exception e) {
                BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_take_photo);
            }
        } else {
            EasyPermissions.requestPermissions(this, "请开起存储空间和相机权限，以正常使用 Demo", REQUEST_CODE_PERMISSION_TAKE_PHOTO, perms);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
                try {
                    startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getFilePathFromUri(data.getData()), 200, 200), REQUEST_CODE_CROP);
                } catch (Exception e) {
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                try {
                    startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getCameraFilePath(), 200, 200), REQUEST_CODE_CROP);
                } catch (Exception e) {
                    mPhotoHelper.deleteCameraFile();
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_CROP) {
                prePath = mPhotoHelper.getCropFilePath();
                models.add(prePath);
                BGAImage.display(mAvatarIv, R.mipmap.bga_pp_ic_holder_light, prePath, BGABaseAdapterUtil.dp2px(200));
            }
        } else {
            if (requestCode == REQUEST_CODE_CROP) {
                mPhotoHelper.deleteCameraFile();
                mPhotoHelper.deleteCropFile();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
