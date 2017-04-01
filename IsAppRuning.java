package com.leshu.leshuvoice.__widgets;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.util.List;

/**
 * Created by liupanpan on 2017/3/28.
 */

public class IsAppRuning {
    private static final String TAG = "IsAppRuning";

    /**
     * 方法描述：判断某一应用是否正在运行
     * @param context     上下文
     * @param packageName 应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo info : list) {
            if (info.importance == 100 && info.processName.equals(packageName)) {
                return true;
            }else {
                return false;
            }
        }
        return false;
    }


    //获取已安装应用的 uid，-1 表示未安装此应用或程序异常
    public static int getPackageUid(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                Log.d(TAG, "getPackageUid: " + applicationInfo.uid);
                return applicationInfo.uid;
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    /**
     * 判断某一 uid 的程序是否有正在运行的进程，即是否存活
     * Created by cafeting on 2017/2/4.
     *
     * @param context 上下文
     * @param uid     已安装应用的 uid
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isProcessRunning(Context context, int uid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() > 0) {
            for (ActivityManager.RunningServiceInfo appProcess : runningServiceInfos) {
                if (uid == appProcess.uid) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean IsAppRuning(Context context, String packageName) {
        int uid = getPackageUid(context, packageName);
        if (uid > 0) {
            boolean rstA = isAppRunning(context, packageName);
            boolean rstB = isProcessRunning(context, uid);
            if (rstA || rstB) {
                return true;
            } else {
                //指定包名的程序未在运行中
                return false;
            }
        } else {
            //应用未安装
            return false;
        }

    }


}
