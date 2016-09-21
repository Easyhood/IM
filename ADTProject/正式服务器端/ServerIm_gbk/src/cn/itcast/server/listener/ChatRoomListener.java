package cn.itcast.server.listener;

import java.io.IOException;

import cn.itcast.server.bean.QQMessageType;
import cn.itcast.server.bean.QQMessage;
import cn.itcast.server.core.QQConnection.OnRecevieMsgListener;

public class ChatRoomListener extends MessageSender implements OnRecevieMsgListener {
	@Override
	public void onReceive(QQMessage fromOneClient) {
		if (QQMessageType.MSG_TYPE_CHAT_ROOM.equals(fromOneClient.type)) {
			try {
				toOtherClient(fromOneClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
