package cn.itcast.server.listener;

import cn.itcast.server.bean.QQMessage;
import cn.itcast.server.bean.QQMessageType;
import cn.itcast.server.core.QQConnection;
import cn.itcast.server.core.QQConnectionManager;
import cn.itcast.server.core.QQConnection.OnRecevieMsgListener;

public class LoginOutListener extends MessageSender implements OnRecevieMsgListener {
	public LoginOutListener() {
		super();
	}
	@Override
	public void onReceive(QQMessage fromCient) {
		if (QQMessageType.MSG_TYPE_LOGIN_OUT.equals(fromCient.type)) {
			try {
				QQMessage toClient = new QQMessage();
				toClient.type = QQMessageType.MSG_TYPE_SUCCESS;
				toClient.content = "ÍË³ö³É¹¦";
				QQConnection conn = QQConnectionManager.get(fromCient.from );
				QQConnectionManager.remove(fromCient.from );
				toClient(toClient, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}