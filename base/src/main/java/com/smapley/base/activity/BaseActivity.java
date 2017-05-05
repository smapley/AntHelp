package com.smapley.base.activity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.smapley.base.application.BaseApplication;
import com.smapley.base.utils.ActivityStack;

import org.xutils.x;

/**
 * Created by eric on 2017/3/13.
 * 基础Activity
 */

public abstract class BaseActivity extends MyActionBarActivity {

    private SharedPreferences userShared;
    private SharedPreferences setShared;
    protected boolean isExit = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(this);
        x.view().inject(this);
        initState();
        initView();
        userShared = BaseApplication.getInstance().getUserShared();
        setShared = BaseApplication.getInstance().getSetShared();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initArgument();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        //Activity堆栈管理
        ActivityStack.getInstance().removeActivity(this);
        super.onDestroy();

    }

    protected void setUserSherd(String name , String value){
        SharedPreferences.Editor editor = userShared.edit();
        editor.putString(name,value);
        editor.commit();
    }
    protected void setUserSherd(String name , Boolean value){
        SharedPreferences.Editor editor = userShared.edit();
        editor.putBoolean(name,value);
        editor.commit();
    }
    protected boolean getUserSherdBoolean(String name) {
        return userShared.getBoolean(name,false);
    }
    protected String getUserSherdString(String name){
        return userShared.getString(name,"");
    }

    public abstract void initArgument();

    public abstract void initView();

    protected  void showToast(int msg){
        showToast(getString(msg));
    }

    protected void showToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    protected void showDialog(String msg,DialogListener dialogListener){
        showDialog("提示",msg,dialogListener);
    }

    protected void showDialog(String title, String msg, final DialogListener dialogListener){

        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("取消",null);
        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onNetural(dialog,which);
            }
        });
        builder.show();
    }
    public abstract class DialogListener{
        protected abstract void onNetural(DialogInterface dialog, int which);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isExit){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                showDialog("确定要退出吗？", new DialogListener() {
                    @Override
                    protected void onNetural(DialogInterface dialog, int which) {
                        ActivityStack.getInstance().finishAllActivity();
                    }
                });
            }
            return false;
        }else {
            super.onKeyDown(keyCode,event);
            return true;
        }
    }
}
