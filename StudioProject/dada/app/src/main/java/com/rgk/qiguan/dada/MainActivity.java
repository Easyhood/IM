package com.rgk.qiguan.dada;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rgk.qiguan.dada.bean.QQMessage;
import com.rgk.qiguan.dada.bean.QQMessageType;
import com.rgk.qiguan.dada.utils.XMLUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Socket socket;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connect(View view){
        //创建socket通过服务器的ip连接到端口
        new Thread(){
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.15.82", 5225);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void send(View view){

        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF("你好");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toXML(View view){
        QQMessage msg = new QQMessage();
        msg.content = "你好";
        msg.from = "1000";
        msg.type = QQMessageType.MSG_TYPE_CHAT_P2P;
        result = XMLUtils.toXML(msg);
        System.out.println(result);
    }

    public void toObj(View view){
        QQMessage msg = XMLUtils.fromXML(result, QQMessage.class);
        System.out.println(msg.content);
    }
}
