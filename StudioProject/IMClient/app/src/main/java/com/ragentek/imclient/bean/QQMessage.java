package com.ragentek.imclient.bean;

import com.ragentek.imclient.utils.MyTime;

import java.io.Serializable;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : RGK
 * Author     : qi.guan
 * Date       : 2016/9/18 14:11
 */
public class QQMessage implements Serializable {
    public String type;//QQMessageType.MSG_TYPE_CHAT_P2P;类型数据 chat login
    public String from;//发送者account
    public String fromNick;//昵称
    public int fromAvatar;//头像
    public String to;//接受者
    public String content;//接收消息内容
    public String sendTime = MyTime.getTime();//发送时间
}
