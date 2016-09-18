package com.ragentek.imclient.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText et_account;
    private EditText et_pwd;
    private String account;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_account = ( EditText ) findViewById ( R.id.et_account );
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        new Thread(){
            @Override
            public void run() {
                super.run();
                //创建QQConnection对象

            }
        }.start();
    }

    public void login(View view){
        account = et_account.getText().toString();
        pwd = et_pwd.getText().toString();

        if (account != null && account.trim().length() > 0 && pwd != null && pwd.trim().length() > 0) {

        }
    }
}
