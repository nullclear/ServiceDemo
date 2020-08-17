package com.yxy.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class DownloadService extends Service {
    private final String TAG = "###DownloadService";

    private DownloadBinder binder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        //绑定服务的时候可以通过Intent传参数进来
        Log.d(TAG, "onBind: 执行");
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
            Log.d(TAG, "startDownload: " + url);
        }
    }
}
