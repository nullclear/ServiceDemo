package com.yxy.demo.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yxy.demo.R;
import com.yxy.demo.broadcast.DynamicReceiver;
import com.yxy.demo.service.DownloadService;
import com.yxy.demo.utils.GenericUtils;

public class DownloadActivity extends AppCompatActivity {
    private final String TAG = "###DownloadActivity";

    @BindView(R.id.bind_service)
    Button bindService;
    @BindView(R.id.unbind_service)
    Button unbindService;
    @BindView(R.id.start_download)
    Button startDownload;
    @BindView(R.id.separate)
    Button separate;
    @BindView(R.id.text)
    TextView text;

    private Intent intent;
    //绑定标识
    private boolean isBind = false;
    //下载服务
    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: 执行 " + name.getClassName());
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: 执行");
        }
    };

    private DynamicReceiver dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: 执行");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        intent = new Intent(this, DownloadService.class);

        dy = new DynamicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DynamicReceiver.FLAG);
        registerReceiver(dy, filter);
    }

    @OnClick({R.id.bind_service, R.id.unbind_service, R.id.start_download, R.id.separate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_service:
                isBind = bindService(intent, connection, Service.BIND_AUTO_CREATE);
                if (isBind) {
                    Toast.makeText(this, "绑定服务成功", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "绑定服务");
                } else {
                    Toast.makeText(this, "绑定服务失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.unbind_service:
                if (GenericUtils.isServiceRunning(this, DownloadService.class)) {
                    if (isBind) {
                        unbindService(connection);
                        isBind = false;
                        downloadBinder = null;
                        Toast.makeText(this, "解绑服务成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "服务并未绑定", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "服务不在运行中", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.start_download:
                if (downloadBinder != null && GenericUtils.isServiceRunning(this, DownloadService.class) && isBind) {
                    downloadBinder.startDownload("www.baidu.com");

                    //这种方法也能启动动态注册的广播, 依然没有用到Action
                    Intent broad = new Intent();
                    broad.setComponent(new ComponentName(this, DynamicReceiver.class));
                    sendBroadcast(broad);

                } else {
                    Toast.makeText(this, "服务未开启", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.separate:
                Log.d(TAG, "----------------------------");
                break;
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: 执行");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: 执行");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: 执行");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: 执行");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: 执行");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: 执行");
        super.onDestroy();
        //判断服务是否已经绑定 多次解绑会报错
        if (isBind && GenericUtils.isServiceRunning(this, DownloadService.class)) {
            unbindService(connection);
            isBind = false;
        }
        //解绑动态广播
        unregisterReceiver(dy);
    }
}
