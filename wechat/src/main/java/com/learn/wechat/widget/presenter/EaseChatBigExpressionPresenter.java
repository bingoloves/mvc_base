package com.learn.wechat.widget.presenter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.learn.wechat.widget.chatrow.EaseChatRow;
import com.learn.wechat.widget.chatrow.EaseChatRowBigExpression;

/**
 * Created by zhangsong on 17-10-12.
 */

public class EaseChatBigExpressionPresenter extends EaseChatTextPresenter {
    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new EaseChatRowBigExpression(cxt, message, position, adapter);
    }

    @Override
    protected void handleReceiveMessage(EMMessage message) {
    }
}
