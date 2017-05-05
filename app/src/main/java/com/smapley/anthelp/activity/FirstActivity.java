package com.smapley.anthelp.activity;

import android.content.Intent;

import com.smapley.anthelp.R;
import com.smapley.base.activity.BaseActivity;
import com.smapley.base.activity.LoginActivity;

import org.xutils.view.annotation.ContentView;

/**
 * Created by wuzhixiong on 2017/4/29.
 */
@ContentView(R.layout.activity_first)
public class FirstActivity extends BaseActivity {
    @Override
    public void initArgument() {
        if(!getUserSherdBoolean("isLogin")){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    public void initView() {

    }
}
