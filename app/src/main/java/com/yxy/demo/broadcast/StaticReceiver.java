package com.yxy.demo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StaticReceiver extends BroadcastReceiver {
    public static final String FLAG = "com.yxy.demo.broadcast.StaticReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "页面跳转", Toast.LENGTH_SHORT).show();
    }
}
