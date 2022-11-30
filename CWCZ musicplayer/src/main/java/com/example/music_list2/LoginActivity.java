package com.example.music_list2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private String TAG = "LoginActivity";
    private CheckBox show, hide;
    private String account = "";
    private String password = "";
    private TextView tv_welcome;
    private EditText etPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//初始化checkbox控件，它是button的子类，用于实现多选功能
        setContentView(R.layout.activity_login);//设置一个layout布局
        Log.d(TAG,"onCreate");//Log.d的输出颜色是蓝色的
        initView();
    }
    private void initView() {
        //获取界面控件
        EditText etAccount = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
        Button btLogin = findViewById(R.id.btLogin);
        Button btRegister = findViewById(R.id.btRegister);
        tv_welcome = findViewById(R.id.tv_welcome);
        show = (CheckBox) findViewById(R.id.show);
        hide = (CheckBox) findViewById(R.id.hide);
        show.setOnCheckedChangeListener(this);
        hide.setOnCheckedChangeListener(this);
        show.setChecked(true);
        hide.setChecked(false);

        btLogin.setOnClickListener(v -> {
            if (etAccount.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "请输入你的信息", Toast.LENGTH_SHORT).show();
            } else {
                //获取用户输入账号密码
                account = etAccount.getText().toString();
                password = etPassword.getText().toString();
                //获取注册时保存的账号密码
                SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
                String spAccount=sp.getString("account","");
                String spPassword=sp.getString("password","");

                //密码和账号的比对
                if(account.equals(spAccount) && password.equals(spPassword)){
                    Intent intent = new Intent(this, MainActivity.class);
                    //显式调用
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "账号或密码不对，请重新输入", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btRegister.setOnClickListener(v -> {
                Intent intent = new Intent(this, RegisterActivity.class);
                //显式intent实现 activity之间的跳转
                startActivity(intent);

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult");
        if(requestCode == 11 && resultCode == 11){
            account = data.getStringExtra("account");
            password = data.getStringExtra("password");
            tv_welcome.setText(account + "你好，欢迎注册，请重新登录。");
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            int showPwd = buttonView.getId();
            if(R.id.show != showPwd && show.isChecked()){
                password = etPassword.getText().toString();
                show.setChecked(false);
                hide.setChecked(true);//隐藏被选中
                //隐藏密码
                int len = password.length();
                String content = "";
                for(int i = 0; i < len; i++){
                    content += "*";
                }
                etPassword.setText(content);
            }
            if(R.id.hide != showPwd&& hide.isChecked()){

                hide.setChecked(false);
                show.setChecked(true);//被选中
                //显示密码
                etPassword.setText(password);
            }
        }
    }

}
