package com.rainmonth.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rainmonth.router.RouterConstant;
import com.rainmonth.router.RouterUtils;

public class AppMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);

        findViewById(R.id.app_btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterUtils.getInstance().build(RouterConstant.PATH_APP_HOME).navigation();
            }
        });
    }
}
