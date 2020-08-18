package com.yxy.demo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

public class DynamicReceiver extends BroadcastReceiver {
    public static final String FLAG = "com.yxy.demo.broadcast.DynamicReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);//屏幕中间
        toast.show();
    }
}
