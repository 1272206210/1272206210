package com.example.music_list2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class RegisterActivity extends AppCompatActivity {
    private String TAG = "RegisterActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//初始化设置
        setContentView(R.layout.activity_register);//设置layout布局
        Log.d(TAG,"onCreate");
        initView();
    }
    private void initView() {
        //初始化控件
        EditText etAccount = findViewById(R.id.etAccount);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btRegister = findViewById(R.id.btRegister);

        btRegister.setOnClickListener(v -> {
            if (etAccount.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "请输入你的信息", Toast.LENGTH_SHORT).show();
            } else {
                Intent it = new Intent(this,LoginActivity.class);
                //将注册账号密码存储到sp对象里面
                SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("account",etAccount.getText().toString());
                editor.putString("password",etPassword.getText().toString());
                editor.commit();

                startActivity(it);
                Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
