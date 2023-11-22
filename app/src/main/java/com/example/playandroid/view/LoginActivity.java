package com.example.playandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.playandroid.R;
import com.example.playandroid.presenter.Presenter1;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements IView3{
    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private String mUserName;
    private String mPassword;
    Presenter1 presenter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUser=findViewById(R.id.user_name_edittext);
        editTextPassword=findViewById(R.id.password_edit);
        buttonLogin=findViewById(R.id.login_button);
        buttonRegister=findViewById(R.id.register_button);
        presenter1=new Presenter1(this);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserName=editTextUser.getText().toString();
                mPassword=editTextPassword.getText().toString();
                presenter1.fetchGetLoginResult(mUserName,mPassword);
            }
        });
    }

    @Override
    public void showData3(ArrayList<?> list) {
        String result=list.get(0).toString();
        if(result.equals("账号密码不匹配！")){
            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
            //步骤1：创建一个SharedPreferences对象
            SharedPreferences sharedPreferences= getSharedPreferences("data",Context.MODE_PRIVATE);
            //步骤2： 实例化SharedPreferences.Editor对象
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //步骤3：将获取过来的值放入文件
            editor.putString("name", result);
            //步骤4：提交
            editor.commit();
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);//给后面开启的活动传值
            startActivity(intent);
        }
    }
}