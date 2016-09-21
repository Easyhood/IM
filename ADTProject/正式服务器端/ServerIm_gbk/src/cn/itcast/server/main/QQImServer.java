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
			// �١�����һ���߳� �������ͻ��˵�����
			final ServerSocket server = new ServerSocket(5225);
			System.out.println("---����������---" + new Date().toString());
			new Thread() {//
				public void run() {
					while (true) {
						QQConnection conn = null;
						try {
							Socket client = server.accept();
							System.out.println("---�пͻ��˽���---" + client);
							// �ڡ�����ͻ������ӳɹ�������һ���߳�
							conn = new QQConnection(client);
							conn.addOnRecevieMsgListener(new LoginMsgListener(conn));
							conn.addOnRecevieMsgListener(new ChatP2PListener());
							conn.addOnRecevieMsgListener(new ChatRoomListener());
							conn.addOnRecevieMsgListener(new LoginOutListener());
							// �ۡ����߳��ڵȴ��û�����
							conn.connect();
							// �ܡ�����һ���̸߳��ͻ���
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
