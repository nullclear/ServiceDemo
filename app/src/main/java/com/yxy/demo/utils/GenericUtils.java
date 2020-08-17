package com.yxy.demo.utils;

import android.app.ActivityManager;
import android.content.Context;
import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Nuclear on 2020/8/17
 */
public class GenericUtils {
    //判断服务是否在运行
    public static boolean isServiceRunning(@NonNull Context context, @NonNull Class<?> clazz) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = manager.getRunningServices(30);
        for (ActivityManager.RunningServiceInfo info : infos) {
            if (clazz.getName().equals(info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
