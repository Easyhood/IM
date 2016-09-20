package com.ragentek.imclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ragentek.imclient.Service.MessageService;
import com.ragentek.imclient.bean.QQMessage;
import com.ragentek.imclient.bean.QQMessageType;
import com.ragentek.imclient.core.QQConnection;
import com.ragentek.imclient.core.QQMessageListener;

public class LoginActivity extends BaseActivity implements QQMessageListener{
    private EditText et_account;

    private EditText et_pwd;
    private String account;
    private String pwd;
    private QQConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_account = ( EditText ) findViewById(R.id.et_account);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        new Thread(){
            public void run() {
                //创建QQConnection对象
                conn = new QQConnection("192.168.15.82",5225);
                //添加监听器
                conn.addListener(LoginActivity.this);
                //连接到服务端
                conn.connect();
            } ;
        }.start();
    }

    public void login(View view){
        account = et_account.getText().toString();
        pwd = et_pwd.getText().toString();

        if (account != null && account.trim().length() > 0 && pwd != null && pwd.trim().length() > 0) {
            QQMessage msg = new QQMessage();
            msg.type = QQMessageType.MSG_TYPE_LOGIN;
            msg.from = account;
            msg.content = account+"#"+pwd;
            conn.sendMessage(msg);
        }
    }

    @Override
    public void onMessageReceive(QQMessage msg) {
         //在登录的界面只处理返回好友的列表信息
        if (msg !=null && msg.type.equals(QQMessageType.MSG_TYPE_BUDDY_LIST)){
           //如果成功的获取到好友列表说明登录成功
            //通过application缓存connection
             application.setConn(conn);
            //通过application登录的账号
            application.setAccount(account);
            startService(new Intent(getApplicationContext(), MessageService.class));
            String result = msg.content;
            System.out.println(result);
            //打开好友列表activity
            Intent intent = new Intent(getApplicationContext(), BuddylistActivity.class);
            intent.putExtra("message",msg);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onDestroy() {
        conn.removeListener(this);
        super.onDestroy();
    }
}
