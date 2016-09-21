package cn.itcast.server.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import cn.itcast.server.core.QQConnection;
import cn.itcast.server.listener.ChatP2PListener;
import cn.itcast.server.listener.ChatRoomListener;
import cn.itcast.server.listener.LoginMsgListener;
import cn.itcast.server.listener.LoginOutListener;

public class QQImServer {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// ①　创建一个线程 等其他客户端的连接
			final ServerSocket server = new ServerSocket(5225);
			System.out.println("---服务器启动---" + new Date().toString());
			new Thread() {//
				public void run() {
					while (true) {
						QQConnection conn = null;
						try {
							Socket client = server.accept();
							System.out.println("---有客户端接入---" + client);
							// ②　如果客户端连接成功分配置一个线程
							conn = new QQConnection(client);
							conn.addOnRecevieMsgListener(new LoginMsgListener(conn));
							conn.addOnRecevieMsgListener(new ChatP2PListener());
							conn.addOnRecevieMsgListener(new ChatRoomListener());
							conn.addOnRecevieMsgListener(new LoginOutListener());
							// ③　该线程内等待用户数据
							conn.connect();
							// ④　分配一个线程给客户端
						} catch (IOException e) {
							e.printStackTrace();
							conn.disconnect();
						}
					}
				};
			}.start();

		} catch (Exception e) {//
			e.printStackTrace();
		}
	}

}
