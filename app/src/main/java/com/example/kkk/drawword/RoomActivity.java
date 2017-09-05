package com.example.kkk.drawword;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by KKK on 2017-08-17.
 */

public class RoomActivity extends Activity {
    private Handler mHandler;
    ListView listView;
    ArrayList<ChatData> item= new ArrayList();
    Room_Adapter room_adapter;
    EditText text,port_num;
    Button submit,refresh;
    ChatData chatData;
    boolean socket_condition = true;
    String read1 = "";
    String read2 = "";
    String user_name,ment;

    Socket socket;
    BufferedWriter bufferedWriter = null;
    BufferedReader bufferedReader = null;
    //Read_thread read_thread;
    Tcp_chat tcp_chat;
    Tcp_Connect tcp_connect;

    String ip = "13.124.229.116";
    int port = 8000;

    Handler handler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_layout);
        layout();
        listView.setAdapter(room_adapter);

        //read_thread = new Read_thread();
        tcp_connect = new Tcp_Connect();
        String num = "8000";
        item.add(new ChatData("asd","hi~"));
        tcp_connect.execute(num);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("test","thread1");
        checkUpdate.start();
        Log.d("test","thread2");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String port = port_num.getText().toString();
                String chat_content = text.getText().toString();
                tcp_chat = new Tcp_chat();
                tcp_chat.execute("asdf",chat_content);
                /*ClientThread clientThread1;
                clientThread1 = new ClientThread(client, handler2);
                clientThread1.start();
                */
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(RoomActivity.this, "refresh", Toast.LENGTH_SHORT).show();
                Log.d("stream","123");
                Tcp_refresh tcp_refresh = new Tcp_refresh();
                Log.d("stream","345");
                tcp_refresh.execute();
                Log.d("stream","567");*/
                Toast.makeText(RoomActivity.this, "시작", Toast.LENGTH_SHORT).show();
//                checkUpdate.start();
            }
        });


        handler2 = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String asdf =bundle.getString("msg");
                item.add(new ChatData("나나난",asdf));
            }
        };
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        read_thread.interrupt();
        Toast.makeText(this, "뒤로가기", Toast.LENGTH_SHORT).show();
    }


    private Thread checkUpdate = new Thread() {

        public void run() {

                String line = null;
                Log.w("ChattingStart", "Start Thread");
                while (true) {
                    Log.w("Chatting is running", "4");
                    try {
                        line = bufferedReader.readLine();
                        ment = line;
                        Log.d("Chatting is running", "2");
                        handler.sendEmptyMessage(0);
                        Log.d("Chatting is running", "3");
                    } catch (Exception e) {
                        Log.d("Chatting is running","fail");
                    }



                }

        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("test","chat123");
            Toast.makeText(RoomActivity.this, ment, Toast.LENGTH_SHORT).show();
            Log.d("test","chat456");
            item.add(new ChatData("asd","hi~"));
        }
    };

    private Runnable showUpdate = new Runnable() {

        public void run() {
            Log.d("test","chat123");
            Toast.makeText(RoomActivity.this, "12123123123", Toast.LENGTH_SHORT).show();
            Log.d("test","chat456");
            item.add(new ChatData("asd","hi~"));

        }
    };

    public class Tcp_refresh extends AsyncTask<String ,String ,String> {
        String user,ment;
        @Override
        protected String doInBackground(String... params) {
            Log.d("stream","asdd11123123");
            try {
                read1 = bufferedReader.readLine();
                read2 = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("fail","refresh");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            item.add(new ChatData(read1,read2));
            room_adapter.notifyDataSetChanged();
            Log.d("stream","refresh");
            Toast.makeText(RoomActivity.this, "새로고침", Toast.LENGTH_SHORT).show();

        }
    }

    public class Tcp_chat extends AsyncTask<String ,String ,String> {
        String user,ment,ment1;
        @Override
        protected String doInBackground(String... params) {
            Log.d("stream","asdd11123123");
            user = params[0];
            ment = params[1];

            /*PrintWriter user_writer = new PrintWriter(bufferedWriter, true);
            user_writer.println(user);*/
            PrintWriter ment_writer = new PrintWriter(bufferedWriter, true);
            ment_writer.println(ment);
            try {
                Log.d("input",ment);
                ment1 = bufferedReader.readLine();
                Log.d("output",ment);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("send",ment);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("stream","asdd");
            Toast.makeText(RoomActivity.this, "전송됨", Toast.LENGTH_SHORT).show();


        }
    }

    public class Tcp_Connect extends AsyncTask<String ,String,String >{
        String port_st;
        boolean status;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(RoomActivity.this, "소켓 연결중..", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            
            if (status == true) {

                Toast.makeText(RoomActivity.this, "연결됨", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(RoomActivity.this, "실패", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            port_st = params[0];
            int port = Integer.parseInt(port_st);
            try {
                Log.d("stream","asdd123");
                socket = new Socket("13.124.229.116",port);
                Log.d("second",String.valueOf(port));
//                "13.124.60.238",8007
                boolean result = socket.isConnected();
                if(result) {
                    Log.d("stream","서버에 연결됨");
                    status = true;
                }
                else {
                    Log.d("stream","서버에 연결안됨");
                    status = false;
                }
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d("stream","fin");
                


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("fail",e.toString());
            }
            return null;
        }
    }

    void layout(){
        listView = (ListView) findViewById(R.id.ment_view);
        text = (EditText) findViewById(R.id.text_ment);
        port_num = (EditText) findViewById(R.id.port);
        submit = (Button) findViewById(R.id.submit_ment);
        refresh = (Button) findViewById(R.id.refresh);
        room_adapter = new Room_Adapter(getLayoutInflater(),item);
    }
}
