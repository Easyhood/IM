package com.rgk.qiguan.dada.bean;

import com.rgk.qiguan.dada.utils.MyTime;

import java.io.Serializable;


public class QQMessage implements Serializable{
	public String fromNick;
	public String type ;
	public String from ;
	public int fromAvatar;
	public String to;
	public String content ;
	public String sendTime = MyTime.geTime();
}
