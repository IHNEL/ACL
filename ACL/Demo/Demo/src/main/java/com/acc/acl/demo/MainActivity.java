package com.acc.acl.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.acc.acl.util.log.L;
import com.acc.acl.util.log.LogLevel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.getDefaultSettings().setLogLevel(LogLevel.NONE).setStackTraceCount(3).setShowDivider(true).getShowThreadInfo();
        L.d("Test Log");
    }
}
