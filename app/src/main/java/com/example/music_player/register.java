package com.example.music_player;

import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.music_player.MD5_encryption.MD5Utils;

public class register extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_back;
    private Button btn_register;
    private EditText et_user_name,et_psw,et_psw_again;
    private String userName,psw,pswAgain;
    private RelativeLayout rl_title_bar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        init();
    }

    private void init() {
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_main_title.setTextColor(this.getResources().getColor(R.color.colorBlack));
        tv_back = findViewById(R.id.tv_back);
        rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        btn_register = findViewById(R.id.btn_register);
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        et_psw_again = findViewById(R.id.et_psw_again);
        tv_back.setOnClickListener(view ->
                register.this.finish());
        btn_register.setOnClickListener(view -> {
            getEditString();
            if(TextUtils.isEmpty(userName)){
                Toast.makeText(register.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            }else if(TextUtils.isEmpty(psw)){
                Toast.makeText(register.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }else if(TextUtils.isEmpty(pswAgain)){
                Toast.makeText(register.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                return;
            }else if(!psw.equals(pswAgain)){
                Toast.makeText(register.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                return;
            }else if(isExistUserName(userName)){
                Toast.makeText(register.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
                return;
            }else{
                Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
                saveRegisterInfo(userName, psw);
                Intent data = new Intent();
                data.putExtra("userName", userName);
                setResult(RESULT_OK, data);
                register.this.finish();
            }
        });
    }
    private void getEditString(){
        userName = et_user_name.getText().toString().trim();
        psw = et_psw.getText().toString().trim();
        pswAgain = et_psw_again.getText().toString().trim();
    }
    private boolean isExistUserName(String userName){
        boolean has_userName = false;
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPsw = sp.getString(userName, "");
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName = true;
        }
        return has_userName;
    }
    private void saveRegisterInfo(String userName,String psw){
        String md5Psw = MD5Utils.Encryption(psw);
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName, md5Psw);
        editor.commit();
    }
}
