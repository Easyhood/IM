package cn.itcast.server.core;

import java.util.HashMap;

import cn.itcast.server.bean.Db;
import cn.itcast.server.bean.QQBuddy;
import cn.itcast.server.bean.QQBuddyList;
import cn.itcast.server.bean.QQUser;

public class QQConnectionManager {
	public static HashMap<Long, QQConnection> conns = new HashMap<Long, QQConnection>();
	public static QQBuddyList list = new QQBuddyList();

	public static void put(long account, QQConnection conn) {
		System.out.println("====�˺�" + account + "������");
		remove(account);
		conns.put(account, conn);
		QQUser u = Db.getByAccount(account);
		QQBuddy item = new QQBuddy();
		item.account = u.account;
		item.avatar = u.avatar;
		item.nick = u.nick;
		list.buddyList.add(item);
	}

	public static void remove(Long account) {
		if (conns.containsKey(account)) {
//			System.out.println("====�˺�" + account + "������");
			conns.remove(account);
			QQBuddy delete = null;
			for (QQBuddy item : list.buddyList) {
				if (account == item.account) {
					delete = item;
					break;
				}
			}
			if (delete != null) {
//				System.out.println("====�������������Ƴ� 0000" + account);
				list.buddyList.remove(delete);

			}
		}
	}

	public static QQConnection get(long account) {
		if (conns.containsKey(account)) {
			return conns.get(account);
		}
		return null;
	}
}
