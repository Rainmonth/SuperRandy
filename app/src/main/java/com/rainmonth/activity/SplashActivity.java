package com.rainmonth.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rainmonth.R;

/**
 * 启动页主要负责以下工作：
 * 1、默认展示图片，一定时间后，进入主页
 * 2、展示获取的网络图片，
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
