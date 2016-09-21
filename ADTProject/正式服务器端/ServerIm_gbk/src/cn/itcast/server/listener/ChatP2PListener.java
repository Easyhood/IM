package cn.itcast.server.listener;

import java.io.IOException;

import cn.itcast.server.bean.QQMessageType;
import cn.itcast.server.bean.QQMessage;
import cn.itcast.server.core.QQConnection;
import cn.itcast.server.core.QQConnection.OnRecevieMsgListener;
import cn.itcast.server.core.QQConnectionManager;

public class ChatP2PListener extends MessageSender implements OnRecevieMsgListener {
	@Override
	public void onReceive(QQMessage fromOneClient) {
		if (QQMessageType.MSG_TYPE_CHAT_P2P.equals(fromOneClient.type)) {
			QQConnection anotherOne = QQConnectionManager.get(fromOneClient.to);
			try {
				toClient(fromOneClient, anotherOne);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
