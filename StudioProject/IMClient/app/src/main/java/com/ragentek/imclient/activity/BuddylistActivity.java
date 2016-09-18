package com.ragentek.imclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ragentek.imclient.bean.BuddyInfo;
import com.ragentek.imclient.bean.QQMessage;
import com.ragentek.imclient.bean.QQMessageType;
import com.ragentek.imclient.core.QQConnection;
import com.ragentek.imclient.core.QQMessageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuddylistActivity extends BaseActivity implements QQMessageListener{

    private ListView lv_list;
    private ArrayList<BuddyInfo> buddyInfos = new ArrayList<>();
    private Handler handler = new Handler();
    private MyAdapter adapter;
    private QQConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conn = application.getConn();
        setContentView(R.layout.activity_buddylist);
        QQMessage msg = (QQMessage) getIntent().getSerializableExtra("message");
        lv_list = (ListView) findViewById(R.id.lv_list);
        adapter = new MyAdapter();
        String buddys = msg.content;
        parseJson(buddys);

        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                BuddyInfo info = buddyInfos.get(position);
                Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
                intent.putExtra("toAccount",info.account);
                intent.putExtra("nickName", info.nick);
                startActivity(intent);
            }
        });
        conn.addListener(this);
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return buddyInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return buddyInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View view;
            if (convertView == null){
                view = View.inflate(getApplicationContext(), R.layout.item_buddylist, null);
                holder = new ViewHolder();
                holder.tv_state =  (TextView) view.findViewById(R.id.tv_state);
                holder.tv_nick = (TextView) view.findViewById(R.id.tv_account);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_nick.setTag(buddyInfos.get(position).nick);
            holder.tv_state.setText(buddyInfos.get(position).account+"@outlook.com");
            return view;
        }
        class ViewHolder{
            TextView tv_nick;
            TextView tv_state;
        }
    }

    @Override
    public void onMessageReceive(final QQMessage msg) {
         System.out.println("BuddyListActivity====onMessageReceive");
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (msg != null && msg.type.equals(QQMessageType.MSG_TYPE_BUDDY_LIST)){
                    parseJson(msg.content);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void parseJson(String buddys){
        buddyInfos.clear();
        try {
            JSONObject jObject = new JSONObject(buddys);
            JSONArray array = jObject.getJSONArray("buddyList");
            for (int i = 0;i < array.length();i++){
                BuddyInfo info = new BuddyInfo();
                JSONObject jObj = array.getJSONObject(i);
                info.account = jObj.getString("account");
                info.nick = jObj.getString("nick");
                info.avatar = jObj.getInt("avatar");
                if (!info.account.equals(application.getAccount())){
                    buddyInfos.add(info);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
