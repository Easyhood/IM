package com.ragentek.imclient.core;

import com.ragentek.imclient.bean.QQMessage;
import com.ragentek.imclient.utils.XMLUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private boolean flag;
    private ArrayList<QQMessageListener> listeners = new ArrayList<>();



    public QQConnection(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    //连接到服务端
    public void connect(){
        try {
            //建立跟服务端的socket连接
            socket = new Socket(ip, port);
            //获取到流
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            flag = true;
            //监听服务端发来的消息
            super.start();
        } catch (UnknownHostException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    //断开服务端的连接
    public void disConnect(){
        try {
            flag = false;
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发送消息
    public void sendMessage(QQMessage msg){
        if (outputStream != null){
            String message = XMLUtils.toXML(msg);
            try {
                outputStream.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //接收消息
    @Override
    public void run() {
        while (true){
            try {
                String result = inputStream.readUTF();
                QQMessage msg = XMLUtils.fromXML(result, QQMessage.class);
                if (listeners != null && listeners.size()>0){
                    for (QQMessageListener listener: listeners){
                        System.out.println("有消息");
                        listener.onMessageReceive(msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addListener(QQMessageListener listener){
        System.out.println("listeners"+ listeners.size());
        this.listeners.add(listener);
    }

    public void removeListener(QQMessageListener listener){
        this.listeners.remove(listener);
    }
}
