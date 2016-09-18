package com.ragentek.imclient;

import android.app.Application;

import com.ragentek.imclient.core.QQConnection;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : RGK
 * Author     : qi.guan
 * Date       : 2016/9/18 15:40
 */
public class MyApplication extends Application {
    private QQConnection conn;
    private String account;

    public QQConnection getConn(){
        return conn;
    }

    public void setConn(QQConnection conn){
        this.conn = conn;
    }

    public String getAccount(){
        return account;
    }

    public void setAccount(String account){
        this.account = account;
    }
}
