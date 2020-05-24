package com.learn.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bingo on 2020/5/24.
 * 原生的dialog 封装
 */

public class NativeDialog {

    /**
     * 普通样式
     * @param activity
     */
    private void showNormalDialog(Activity activity){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(activity);
        //normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    /**
     * 三个按钮
     * setNeutralButton 设置中间的按钮  若只需一个按钮，仅设置 setPositiveButton 即可
     *
     * @param activity
     */
    private void showMultiBtnDialog(Activity activity){
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(activity);
        //normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("我是一个普通Dialog").setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("按钮1",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNeutralButton("按钮2",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNegativeButton("按钮3", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ...To-do
            }
        });
        // 创建实例并显示
        normalDialog.show();
    }

    /**
     * 列表Dialog
     * @param activity
     */
    private void showListDialog(Activity activity) {
        final String[] items = { "我是1","我是2","我是3","我是4" };
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(activity);
        listDialog.setTitle("我是一个列表Dialog");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
            }
        });
        listDialog.show();
    }

    /**
     * 单选Dialog
     * @param activity
     */
    private void showSingleChoiceDialog(Activity activity,int selectPosition){
        final String[] items = { "我是1","我是2","我是3","我是4" };
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(activity);
        singleChoiceDialog.setTitle("我是一个单选Dialog");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, selectPosition,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //whick 选中的位置
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
        singleChoiceDialog.show();
    }

    /**
     * 多选Dialog
     * @param activity
     */
    private void showMultiChoiceDialog(Activity activity,String[] items,String[] selectItems) {
        if (items == null)return;
        ArrayList<Boolean> choiceSets = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            if (selectItems!=null){
                Set<String> set = new HashSet<>(Arrays.asList(selectItems));
                boolean contains = set.contains(item);
                choiceSets.set(i,contains);
            } else {
                choiceSets.set(i,false);
            }
        }
        boolean[] choiceSetsArr = {false,false,false,false};
        AlertDialog.Builder multiChoiceDialog = new AlertDialog.Builder(activity);
        multiChoiceDialog.setTitle("我是一个多选Dialog");
        multiChoiceDialog.setMultiChoiceItems(items, choiceSetsArr, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //choiceSets.set(which,isChecked);
                    }
                });
        multiChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        multiChoiceDialog.show();
    }

    /**
     * 等待Dialog
     * @param activity
     */
    private void showWaitingDialog(Activity activity) {
        ProgressDialog waitingDialog = new ProgressDialog(activity);
        waitingDialog.setTitle("我是一个等待Dialog");
        waitingDialog.setMessage("等待中...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }

    /**
     * 进度条Dialog
     * @param activity
     */
    private void showProgressDialog(Activity activity) {
        final int MAX_PROGRESS = 100;
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgress(0);//设置初始进度
        progressDialog.setTitle("我是一个进度条Dialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置样式（水平进度条）
        progressDialog.setMax(MAX_PROGRESS);//设置进度最大值
        progressDialog.show();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int progress= 0;
//                while (progress < MAX_PROGRESS){
//                    try {
//                        Thread.sleep(100);
//                        progress++;
//                        progressDialog.setProgress(progress);
//                    } catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                }
//                // 进度达到最大值后，窗口消失
//                progressDialog.cancel();
//            }
//        }).start();
    }

    /**
     * 编辑Dialog
     * @param activity
     */
    private void showInputDialog(Activity activity) {
        final EditText editText = new EditText(activity);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(activity);
        inputDialog.setTitle("我是一个输入Dialog").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    /**
     * 自定义Dialog
     */
    private void showCustomizeDialog(Activity activity,View view) {
        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(activity);
        customizeDialog.setTitle("我是一个自定义Dialog");
        customizeDialog.setView(view);
        customizeDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        customizeDialog.show();
    }
}
