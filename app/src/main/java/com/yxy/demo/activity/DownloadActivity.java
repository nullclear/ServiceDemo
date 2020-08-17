package com.yxy.demo.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        intent = new Intent(this, DownloadService.class);
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
    protected void onDestroy() {
        super.onDestroy();
        //判断服务是否已经绑定
        if (isBind) {
            unbindService(connection);
            isBind = false;
        }
    }
}
