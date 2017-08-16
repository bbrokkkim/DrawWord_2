package com.example.kkk.drawword;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText id,pwd;
    Button login,join;
    boolean where = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        layout();
        join.setText("로그인으로");
//        Presenter_login presenter_login = new Presenter_login(user_id,user_pwd);
        final String check_login_info = "uncorrect";
        final Model_login model_login = new Model_login(check_login_info);
/*
        Login_fragment login_fragment = new Login_fragment();
        Fragment fr = new Login_fragment();*/


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchfragment();
                Log.d("con",String.valueOf(where));
            }
        });

        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Boolean check = model_login.Connect_http(user_id,user_pwd);

                model_login.connect_login1.execute(user_id,user_pwd);
                if (check_login_info.equals("correct")){
                    Intent intent = new Intent();
                    Toast.makeText(MainActivity.this, "정보일치", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "정보가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_fragment exampleFragment = new Login_fragment();
             //   Login_fragment.onCreateView();
            }
        });*/
    }


    public void switchfragment(){
        Fragment fr,hide_fr;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (where == true){
            fr = new Login_fragment();
            join.setText("로그인으로");
            where = false;
        }
        else {
            fr = new Join_fragment();
            join.setText("회원가입하러");
            where = true;
        }

        fragmentTransaction.add(R.id.fragmentloginorjoin,fr);
        fragmentTransaction.replace(R.id.fragmentloginorjoin,fr);
        fragmentTransaction.commit();
    }

    void layout(){
        id = (EditText)findViewById(R.id.id);
        pwd = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        join = (Button) findViewById(R.id.other_frag);

    }

    public static class Login_fragment extends Fragment {
        public Login_fragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.login_fragment, container, false);
        }
    }
    public static class Join_fragment extends Fragment {
        public Join_fragment(){

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.join_fragment, container, false);
        }
    }

}