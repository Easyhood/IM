package cn.itcast.server.listener;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import cn.itcast.server.bean.QQMessage;
import cn.itcast.server.core.QQConnection;
import cn.itcast.server.core.QQConnectionManager;

public class MessageSender {
	public void toClient(QQMessage msg, QQConnection conn) throws IOException {
		System.out.println("单发当前客户端to Client \n" + msg.toXml());
		if (conn != null) {
			conn.writer.writeUTF(msg.toXml());
		}
	}

	public void toEveryClient(QQMessage msg) throws IOException {
		System.out.println("群发所有客户端  to toEveryClient Client \n" + msg.toXml());
		// conn.writer.writeUTF(toClient.toXml());
		Set<Map.Entry<Long, QQConnection>> allOnLines = QQConnectionManager.conns.entrySet();
		for (Map.Entry<Long, QQConnection> entry : allOnLines) {
			entry.getValue().writer.writeUTF(msg.toXml());
		}
	}

	public void toOtherClient(QQMessage msg) throws IOException {
		System.out.println("群发所有其他客户端  to toEveryClient Client \n" + msg.toXml());
		// conn.writer.writeUTF(toClient.toXml());
		Set<Map.Entry<Long, QQConnection>> allOnLines = QQConnectionManager.conns.entrySet();
		for (Map.Entry<Long, QQConnection> entry : allOnLines) {
			if (entry.getValue().who.account!=msg.from) {
				entry.getValue().writer.writeUTF(msg.toXml());
			}
		}
	}
}
