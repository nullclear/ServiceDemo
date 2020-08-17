package com.yxy.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yxy.demo.activity.DownloadActivity;
import com.yxy.demo.service.DownloadService;
import com.yxy.demo.utils.GenericUtils;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {
    private final String TAG = "###MainActivity";

    @BindView(R.id.start_service)
    Button startService;
    @BindView(R.id.stop_service)
    Button stopService;
    @BindView(R.id.go_download_activity)
    Button goDownloadActivity;
    @BindView(R.id.separate)
    Button separate;
    @BindView(R.id.text)
    TextView text;

    Intent serviceIntent;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: 执行");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        serviceIntent = new Intent(this, DownloadService.class);
        //定时改变text的值
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(() -> {
                    text.setText(String.valueOf(System.currentTimeMillis()));
                });
            }
        }, 0, 1000);
    }

    @OnClick({R.id.start_service, R.id.stop_service, R.id.go_download_activity, R.id.separate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                ComponentName service = startService(serviceIntent);
                if (DownloadService.class.getName().equals(service.getClassName())) {
                    Toast.makeText(this, "开启服务成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "开启服务失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.stop_service:
                if (GenericUtils.isServiceRunning(this, DownloadService.class)) {
                    boolean flag = stopService(serviceIntent);
                    if (flag) {
                        Toast.makeText(this, "停止服务成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "停止服务失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "服务不在运行中", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_download_activity:
                Intent intent = new Intent(this, DownloadActivity.class);
                startActivity(intent);
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
        timer.cancel();
        if (GenericUtils.isServiceRunning(this, DownloadService.class)) {
            //多次调用不会报错
            stopService(serviceIntent);
        }
    }
}
