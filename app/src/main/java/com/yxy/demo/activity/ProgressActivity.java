package com.yxy.demo.activity;

import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yxy.demo.R;

import java.util.Timer;
import java.util.TimerTask;

public class ProgressActivity extends AppCompatActivity {

    @BindView(R.id.progress_horizontal)
    ProgressBar progressHorizontal;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(this);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ProgressActivity.this.runOnUiThread(() -> {
                    int progress = progressHorizontal.getProgress();
                    int max = progressHorizontal.getMax();
                    progressHorizontal.setProgress(progress + 1);
                    //progressHorizontal.setMax(max + 100);
                });
            }
        }, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
