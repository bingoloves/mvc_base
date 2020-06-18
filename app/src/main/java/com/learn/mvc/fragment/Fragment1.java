package com.learn.mvc.fragment;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import com.learn.base.fragment.BaseFragment;
import com.learn.base.utils.DateUtils;
import com.learn.base.utils.LogUtils;
import com.learn.base.utils.StringUtil;
import com.learn.dialog.IDialog;
import com.learn.dialog.SuperDialog;
import com.learn.multistate.MultistateLayout;
import com.learn.mvc.R;
import com.learn.mvc.glide.GlideHelper;
import com.learn.qiniu.PLVideoListActivity;

import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;

public class Fragment1 extends BaseFragment{
    String[] videoPath = new String []{
            "https://cmgw-hz.lechange.com:8890/LCO/4L03F00PAZF1379/0/1/20191216T055001/dev_4L03F00PAZF1379_20191216T055001.m3u8",
            "https://cmgw-hz.lechange.com:8890/LCO/4L03F00PAZF1379/1/1/20191216T055008/dev_4L03F00PAZF1379_20191216T055008.m3u8",
            "https://cmgw-hz.lechange.com:8890/LCO/4L03F00PAZF1379/7/1/20191216T055052/dev_4L03F00PAZF1379_20191216T055052.m3u8"
    };
    private String imageTest = "http://192.168.1.112:9100/fast/viewFile?token=1b5bc2624a31db8d957291e06c3e1637&stamp=1&path=group1/M01/00/0A/wKgB5l6-LHyAd-XPAAAe92plnEc850.jpg";
    //private String iamgeTest = "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3252521864,872614242&fm=26&gp=0.jpg";
    @BindView(R.id.test_iv)
    ImageView testIv;
    @BindView(R.id.multistate_layout)
    MultistateLayout multistateLayout;
    @OnClick({R.id.moveQiNiu,R.id.succeed_tv,R.id.net_tv,R.id.loading_tv,R.id.data_tv, R.id.crash_btn,
            R.id.image1_tv,R.id.image2_tv,R.id.image3_tv,R.id.image4_tv,R.id.dialog_tv
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
            case R.id.image1_tv:
                new GlideHelper.Builder().crossFade().build().load(getContext(),imageTest,testIv);
                break;
            case R.id.image2_tv:
                new GlideHelper.Builder()
                        .crossFade()
                        .setType(GlideHelper.CIRCLE)
                        .build()
                        .load(getContext(),imageTest,testIv);
                break;
            case R.id.image3_tv:
                new GlideHelper.Builder()
                        .crossFade()
                        .setType(GlideHelper.ROUND)
                        .setRoundingRadius(16)
                        .build()
                        .load(getContext(),imageTest,testIv);
//                new GlideHelper.Builder()
//                        .crossFade()
//                        .fitCenter()
//                        .setRoundingRadius(16)
//                        .build()
//                        .load(getContext(),R.drawable.em_splash,testIv);
                break;
            case R.id.image4_tv:
                new GlideHelper.Builder()
                        .fitCenter()
                        .crossFade()
                        .build()
                        .load(getContext(),imageTest,testIv);
                break;
            case R.id.dialog_tv:
                showDialog();
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
        new GlideHelper.Builder().build().load(getContext(),imageTest,testIv);
        multistateLayout.setOnReloadListener(new MultistateLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                toast("status = "+status);
            }
        });
    }
    @Override
    protected void refreshLoad() {
        super.refreshLoad();
        toast("页面切换到1");
    }
    /**
     * 显示dialog
     */
    private void showDialog(){
        new SuperDialog.Builder(getContext())
                .setTitle("我是标题")
//                .setDialogView(R.layout.layout_custom_dialog)
//                .setFullScreenWidth()
                .setAnimStyle(R.style.dialog_modal)
                .setContent("您好,我们将在30分钟处理，稍后通知您订单结果！")
                .setPositiveButton(new IDialog.OnClickListener() {
                    @Override
                    public void onClick(IDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    @Override
    protected void lazyLoad() {

    }
}
