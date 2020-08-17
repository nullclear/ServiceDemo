package com.yxy.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;

public class DownloadService extends Service {
    private final String TAG = "###DownloadService";

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(), String.valueOf(System.currentTimeMillis()), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Timer timer = new Timer();
    private DownloadBinder binder = new DownloadBinder();

    //多次绑定不会多次调用此方法, 只会返回Binder的对象引用
    @Override
    public IBinder onBind(Intent intent) {
        //绑定服务的时候可以通过Intent传参数进来
        Log.d(TAG, "onBind: 执行");
        //定时发送消息 只有[绑定服务]才会触发此定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, 1000);
        return binder;
    }

    @Override
    public void onCreate() {
        //只会执行一次
        Log.d(TAG, "onCreate: 执行");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //多次开启服务会调用这个多次
        Log.d(TAG, "onStartCommand: 执行");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //服务停止的时候才会取消计时任务
        timer.cancel();
        Log.d(TAG, "onDestroy: 执行");
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: 执行");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //返回true, 解绑不销毁
        Log.d(TAG, "onUnbind: 执行");
        return true;
    }

    public class DownloadBinder extends Binder {
        public void startDownload(String url) {
            //很显然 Toast属于系统级别的应用
            Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "startDownload: " + url);
        }
    }
}
