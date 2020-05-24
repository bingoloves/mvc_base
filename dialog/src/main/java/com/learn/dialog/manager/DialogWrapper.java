package com.learn.dialog.manager;

import com.learn.dialog.SuperDialog;

/**
 * 管理多个dialog 按照dialog的优先级依次弹出
 */

public class DialogWrapper {

    private SuperDialog.Builder dialog;//统一管理dialog的弹出顺序

    public DialogWrapper(SuperDialog.Builder dialog) {
        this.dialog = dialog;
    }

    public SuperDialog.Builder getDialog() {
        return dialog;
    }

    public void setDialog(SuperDialog.Builder dialog) {
        this.dialog = dialog;
    }

}
