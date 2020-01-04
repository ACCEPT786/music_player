package com.example.music_player;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class find_psw_back extends AppCompatActivity {
    private TextView tv_main_title;//标题
    private TextView tv_back;
    private Button btn_find_back_pwd;
    private String userName, spPsw, init_pwd;
    private EditText et_user_name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_psw_back);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("找回密码");
        tv_main_title.setTextColor(this.getResources().getColor(R.color.colorBlack));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(view ->
                find_psw_back.this.finish());
        btn_find_back_pwd = findViewById(R.id.btn_find_back_pwd);
        btn_find_back_pwd.setOnClickListener(view ->
                onClick());
    }
    public void onClick(){
        et_user_name = findViewById(R.id.et_user_name);
        userName = et_user_name.getText().toString().trim();
        spPsw = readPsw(userName);
        init_pwd = MD5Utils.Decryption(spPsw);
        Toast.makeText(find_psw_back.this, "初始密码为：" + init_pwd, Toast.LENGTH_SHORT).show();
    }

    private String readPsw(String userName){
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(userName , "");
    }
}
