package com.ragentek.imclient.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ragentek.imclient.bean.QQMessage;
import com.ragentek.imclient.bean.QQMessageType;
import com.ragentek.imclient.core.QQConnection;
import com.ragentek.imclient.core.QQMessageListener;

import java.util.ArrayList;

public class ChatListActivity extends BaseActivity implements QQMessageListener {

    private String toAccount;
    private ListView lv_list;
    private MyAdapter adapter;
    private EditText et_chat_content;
    private QQConnection conn;
    private Handler handler = new Handler();
    private ArrayList<QQMessage> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        toAccount = getIntent().getStringExtra("toAccount");
        String nickName = getIntent().getStringExtra("nickName");
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        lv_list = (ListView) findViewById(R.id.lv_list);
        adapter = new MyAdapter();
        lv_list.setAdapter(adapter);
        et_chat_content = (EditText) findViewById(R.id.et_content);
        tv_title.setText("与"+nickName+"聊天中。。。");
        conn = application.getConn();
        conn.addListener(this);
    }

    public void send(View view){
        String chatContent = et_chat_content.getText().toString();
        final QQMessage msg = new QQMessage();
        //获取当前账号放到发送消息msg.from
        msg.type = QQMessageType.MSG_TYPE_CHAT_P2P;
        msg.from = application.getAccount();
        msg.to = toAccount;
        msg.content = chatContent;
        messages.add(msg);
        adapter.notifyDataSetChanged();
        if (messages.size() > 0){
            lv_list.setSelection(messages.size()-1);
            et_chat_content.setText("");
            new Thread(){
                @Override
                public void run() {
                    conn.sendMessage(msg);
                }
            }.start();
        }
    }
    @Override
    public void onMessageReceive(final QQMessage msg) {
        if (msg != null && msg.type.equals(QQMessageType.MSG_TYPE_CHAT_P2P)){
            handler.post(new Runnable() {
                @Override
                public void run() {
                  messages.add(msg);
                  adapter.notifyDataSetChanged();
                    if (messages.size()>0){
                        lv_list.setSelection(messages.size()-1);
                    }
                }
            });
        }
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (messages.get(position).from.equals(application.getAccount())){
                return 0;
            }
            return 1;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (0 == getItemViewType(position)){
                if (convertView == null){
                    view = View.inflate(getApplicationContext(), R.layout.item_chat_send, null);
                    holder = new ViewHolder();
                    holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
                    holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
                    view.setTag(holder);
                }else {
                    view = convertView;
                    holder = (ViewHolder) view.getTag();
                }
            }else {
                if (convertView == null){
                    view = View.inflate(getApplicationContext(),R.layout.item_chat_receive,null);
                    holder = new ViewHolder();
                    holder.tv_time =(TextView) view.findViewById(R.id.tv_time);
                    holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
                    view.setTag(holder);
                }else {
                    view = convertView;
                    holder = (ViewHolder) view.getTag();
                }
            }
            holder.tv_time.setText(messages.get(position).sendTime);
            holder.tv_content.setText(messages.get(position).content);
            return view;

        }


        class ViewHolder{
            TextView tv_time;
            TextView tv_content;
        }
    }
}
