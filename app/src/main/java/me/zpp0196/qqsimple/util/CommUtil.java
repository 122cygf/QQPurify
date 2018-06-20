package me.zpp0196.qqsimple.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.List;

import static me.zpp0196.qqsimple.BuildConfig.APPLICATION_ID;
import static me.zpp0196.qqsimple.Common.PACKAGE_NAME_QQ;
import static me.zpp0196.qqsimple.Common.PACKAGE_NAME_VXP;

/**
 * Created by zpp0196 on 2018/5/27 0027.
 */

public class CommUtil {

    public static boolean isInVxp() {
        return System.getProperty("vxp") != null;
    }

    static boolean isVxpRunning(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) {
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(PACKAGE_NAME_VXP)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isInstalled(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            packageManager.getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static int getQQVersionCode(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(PACKAGE_NAME_QQ, 0).versionCode;
        } catch (Throwable e) {
            Log.e("CommUtil", "Can not get QQ versionCode!");
            return 0;
        }
    }

    public static String getQQVersionName(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(PACKAGE_NAME_QQ, 0).versionName;
        } catch (Throwable e) {
            Log.e("CommUtil", "Can not get QQ versionName!");
            return "unknown";
        }
    }

    public static File getDataDir(Context context) {
        return new File(context.getApplicationInfo().dataDir);
    }

    public static File getPrefsDir(Context context) {
        return new File(getDataDir(context), "shared_prefs");
    }

    public static File getPrefsFile(Context context) {
        return new File(getPrefsDir(context), APPLICATION_ID + "_preferences.xml");
    }

    public static File getBackupPrefsFile() {
        return new File(getSdDataDir(), APPLICATION_ID + "_preferences.xml");
    }

    private static File getSdDataDir() {
        File dir = new File(Environment.getExternalStorageDirectory(),
                "Android/data/" + APPLICATION_ID);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static String getThrowableMsg(Throwable tr) {
        StackTraceElement[] trace = tr.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(tr.getClass()
                .getName())
                .append("：");
        sb.append(tr.getMessage())
                .append("\n");
        for (StackTraceElement traceElement : trace)
            sb.append("\tat ")
                    .append(traceElement)
                    .append("\n");
        return sb.toString();
    }
}
