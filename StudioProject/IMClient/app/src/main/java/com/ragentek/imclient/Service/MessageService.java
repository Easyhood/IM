package com.ragentek.imclient.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.ragentek.imclient.MyApplication;
import com.ragentek.imclient.activity.R;
import com.ragentek.imclient.bean.QQMessage;
import com.ragentek.imclient.bean.QQMessageType;
import com.ragentek.imclient.core.QQConnection;
import com.ragentek.imclient.core.QQMessageListener;

public class MessageService extends Service  implements QQMessageListener{

    private MyApplication application;
    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = (MyApplication) getApplication();
        QQConnection conn = application.getConn();
        conn.addListener(this);
    }

    @Override
    public void onMessageReceive(final QQMessage msg) {
        if (msg != null && msg.type.equals(QQMessageType.MSG_TYPE_CHAT_P2P)){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),msg.content,Toast.LENGTH_LONG).show();
                    //创建Notification.Builder 通过builder 可以创建Notification的内容
                    Notification.Builder builder = new Notification.Builder(getApplicationContext());
                    //setTicker 设置在状态栏跑的文字
                    builder.setTicker("有"+msg.from+"发来的消息:"+msg.content);
                    //setContentTitle 设置Notification的标题 setContentText设置Notification的内容
                    builder.setContentTitle("聊天消息").setContentText(msg.content);
                    //设置图标
                    builder.setSmallIcon(R.drawable.conversation_bg_logo);
                    //设置点击消失
                    builder.setOngoing(true);
                    //创建notifycation对象
                    Notification notification = builder.build();
                    //通过notifycationManager可以发送一个notificationmanager
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    //notify第一参数可以给notifycation指定一个id 通过id可以区分不同的notifycation
                    manager.notify(0,notification);
                }
            });
        }
    }
}
