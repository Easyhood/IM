package com.ragentek.imclient.core;

import java.net.Socket;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : Ragentek
 * Author     : qi.guan
 * Date       : 2016/9/17 23:18
 */
public class QQConnection extends Thread {
     //服务端的ip地址
    private String ip;
    //服务端口号
    private int port;
    private Socket socket;

    public QQConnection(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    //连接到服务端
    public void connect(){

        //建立跟服务端的socket连接
        socket = new Socket(ip, port);
    }
}
