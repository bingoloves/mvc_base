package com.learn.mvc.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.learn.base.fragment.BaseFragment;
import com.learn.mvc.App;
import com.learn.mvc.R;
import com.learn.wechat.ChatHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class Fragment2 extends BaseFragment {
    @BindView(R.id.send_message_et)
    EditText sendMessageEt;

    @OnClick(R.id.send_btn)
    public void clickEvent(View view){
        String msg = sendMessageEt.getText().toString().trim();
        if (TextUtils.isEmpty(msg)){
            toast("请输入内容");
            return;
        }
        String currentUsernName = ChatHelper.getChatHelper().getCurrentUsernName();
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(msg,  "xuebing".equals(currentUsernName)?"xurui":"xuebing");//"xuebing".equals(currentUsernName)?"xurui":"xuebing"
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_layout2;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void lazyLoad() {

    }
}
