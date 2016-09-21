package cn.itcast.server.listener;

import cn.itcast.server.bean.Db;
import cn.itcast.server.bean.QQBuddyList;
import cn.itcast.server.bean.QQMessage;
import cn.itcast.server.bean.QQMessageType;
import cn.itcast.server.bean.QQUser;
import cn.itcast.server.core.QQConnection;
import cn.itcast.server.core.QQConnection.OnRecevieMsgListener;
import cn.itcast.server.core.QQConnectionManager;

public class LoginMsgListener extends MessageSender implements OnRecevieMsgListener {
	private QQConnection conn = null;

	public LoginMsgListener(QQConnection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void onReceive(QQMessage fromCient) {
		if (QQMessageType.MSG_TYPE_LOGIN.equals(fromCient.type)) {
			try {
				QQMessage toClient = new QQMessage();
				if (QQMessageType.MSG_TYPE_LOGIN.equals(fromCient.type)) {
					String[] params = fromCient.content.split("#");
					String account = params[0];
					String pwd = params[1];
					QQUser user = Db.getByAccount(Long.parseLong(account));
					if (user == null) {
						// ������
						toClient.type = QQMessageType.MSG_TYPE_FAILURE;
						toClient.content = "������";
						toClient(toClient, conn);
					} else {
						// ����
						if (user.password.equals(pwd)) {
							// ��¼ �ɹ�
							toClient.type = QQMessageType.MSG_TYPE_BUDDY_LIST;
							// ������������
							// ��������ݵ����Ӷ���
							conn.who = user;
							QQConnectionManager.put(user.account, conn);
							QQBuddyList list = QQConnectionManager.list;
							toClient.content = list.toJson();
							toEveryClient(toClient);
						} else {
							toClient.type = QQMessageType.MSG_TYPE_FAILURE;
							toClient.content = "ʧ��";
							toClient(toClient, conn);
						}
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}