package com.smapley.anthelp.activity;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.smapley.anthelp.R;
import com.smapley.anthelp.http.MainResponse;
import com.smapley.anthelp.utils.Constant;
import com.smapley.base.activity.BaseActivity;
import com.smapley.base.http.BaseCallback;
import com.smapley.base.utils.ShakeManager;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.main_gold)
    private TextView gold;
    @ViewInject(R.id.main_hbgold)
    private TextView hbGold;

    private ShakeManager shakeManager;

    @Override
    public void initArgument() {
        isExit = true;
        getData();
    }


    @Override
    public void initView() {
        showCenterImg(R.mipmap.main_logo);
        showRightImg(R.mipmap.icon_mine);
    }

    @Override
    protected void onStart() {
        super.onStart();
        shakeManager = new ShakeManager(this);
    }

    @Override
    protected void onPause() {
        shakeManager.close();
        super.onPause();
    }


    @Override
    protected void onRightImg(View v) {
        super.onRightImg(v);
        startActivity(new Intent(MainActivity.this,Test.class));
    }

    public void getData() {
        RequestParams params = new RequestParams(Constant.MAIN);
        params.addBodyParameter("ukey", getUserSherdString("ukey"));
        x.http().post(params, new BaseCallback<MainResponse>() {
            @Override
            public void success(MainResponse result) {
                gold.setText(result.getSygold());
                hbGold.setText(result.getHbgold());
            }
        });

    }


}
