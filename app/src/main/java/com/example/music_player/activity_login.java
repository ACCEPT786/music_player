package com.example.music_player;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.music_player.MD5_encryption.MD5Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;


public class activity_login extends AppCompatActivity {
    private TextView tv_main_title;
    private Button btn_login, tv_register, tv_find_psw;
    private String userName,psw,spPsw;
    private EditText et_user_name,et_psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getPermissions();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        init();
    }

    private void init() {

        tv_register = findViewById(R.id.tv_register);
        tv_find_psw = findViewById(R.id.tv_find_psw);
        btn_login = findViewById(R.id.btn_login);
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);


        tv_register.setOnClickListener(view ->
            startActivity(new Intent(com.example.music_player.activity_login.this, register.class)));

        tv_find_psw.setOnClickListener(view ->
            startActivity(new Intent(com.example.music_player.activity_login.this, find_psw_back.class)));

        btn_login.setOnClickListener(view ->{
            userName = et_user_name.getText().toString().trim();
            psw = et_psw.getText().toString().trim();
            String md5Psw = MD5Utils.Encryption(psw);
            spPsw = readPsw(userName);
            if(TextUtils.isEmpty(userName)){
                Toast.makeText(activity_login.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            }else if(TextUtils.isEmpty(psw)){
                Toast.makeText(activity_login.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }else if(md5Psw.equals(spPsw)){
                Toast.makeText(activity_login.this, "登录成功", Toast.LENGTH_SHORT).show();
                saveLoginStatus(true, userName);
                Intent data = new Intent();
                data.putExtra("isLogin",true);
                setResult(RESULT_OK,data);
                activity_login.this.finish();
                startActivity(new Intent(activity_login.this, MainActivity.class));
                return;
            }else if((spPsw!=null && !TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw))){
                Toast.makeText(activity_login.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }else{
                Toast.makeText(activity_login.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String readPsw(String userName){
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(userName , "");
    }
    private void saveLoginStatus(boolean status,String userName){
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin", status);
        editor.putString("loginUserName", userName);
        editor.commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String userName = data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                et_user_name.setText(userName);
                et_user_name.setSelection(userName.length());
            }
        }
    }
    private void getPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        Toast.makeText(getApplicationContext(), "已经获取所需权限", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "未能获取所需权限", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
