package com.ragentek.imclient.activity;

import android.os.Bundle;

import com.ragentek.imclient.bean.QQMessage;
import com.ragentek.imclient.core.QQMessageListener;

public class ChatListActivity extends BaseActivity implements QQMessageListener {

    private String toAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        toAccount = getIntent().getStringExtra("toAccount");
        String nickName = getIntent().getStringExtra("nickName");

    }

    @Override
    public void onMessageReceive(QQMessage msg) {

    }
}
