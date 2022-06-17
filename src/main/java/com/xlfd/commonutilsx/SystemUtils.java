package com.xlfd.commonutilsx;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SystemUtils {
    public interface OnStartAppFiledListener {
        void onStartAppFiled();
    }

    /**
     * 判断一个程序是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isRunBackground(Context context) {
        return !isRunForeground(context);
    }

    /**
     * 判断一个程序是否在前台运行
     *
     * @param context
     * @return
     */
    public static boolean isRunForeground(Context context) {
        return TextUtils.equals(getForegroundApp(context),context.getPackageName());
    }


    /**
     * 需要添加权限
     * <uses-permission  android:name="android.permission.PACKAGE_USAGE_STATS" />
     * 获取在前台应用的包名
     * @param context
     *
     * @return
     */
    public static String getForegroundApp(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long endt = calendar.getTimeInMillis();//结束时间
        calendar.add(Calendar.DAY_OF_MONTH, -1);//时间间隔为一个月
        long statt = calendar.getTimeInMillis();//开始时间
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        //获取一个月内的信息
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, statt, endt);

        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return null;
        }

        UsageStats recentStats = null;
        for (UsageStats usageStats : queryUsageStats) {

            if (recentStats == null || recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed()) {
                recentStats = usageStats;
            }
        }

        return recentStats.getPackageName();
    }

    /**
     * 开启其他应用
     *
     * @param packageName  包名
     * @param activityPath activity全路径名
     */
    public static void startNormalApp(Context context, String packageName, String activityPath) {
        startNormalApp(context, packageName, activityPath, null, null, null);
    }

    public static void startNormalApp(Context context, String packageName, String activityPath,
                                      OnStartAppFiledListener listener) {
        startNormalApp(context, packageName, activityPath, null, null, listener);
    }

    public static void startNormalApp(Context context, String packageName, String activityPath,
                                      String key, CharSequence value) {
        startNormalApp(context, packageName, activityPath, key, value, null);
    }

    /**
     * 开启其他应用,并设置string Extra
     *
     * @param packageName  包名
     * @param activityPath activity全路径名
     * @param key          Extra的key
     * @param value        Extra的value
     */
    public static void startNormalApp(Context context, String packageName, String activityPath,
                                      String key, CharSequence value, OnStartAppFiledListener listener) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName.trim(), activityPath.trim()));
        intent.setAction(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtils.isEmpty(key)) {
            intent.putExtra(key, value);
        }
        try {
            context.startActivity(intent);
            Log.e("wtf", "start activity success package = " + packageName + ",activity = " + activityPath);
        } catch (Exception e) {
            if (listener != null) listener.onStartAppFiled();
            Log.e("wtf", "start activity Exception" + e.toString());
        }
    }

    /**
     * 开启其他应用,并设置boolean Extra
     *
     * @param packageName  包名
     * @param activityPath activity全路径名
     * @param key          Extra的key
     * @param value        Extra的value
     */
    public static void startNormalApp(Context context, String packageName, String activityPath,
                                      String key, boolean value) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName.trim(), activityPath.trim()));
        intent.setAction(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtils.isEmpty(key)) {
            intent.putExtra(key, value);
        }
        try {
            context.startActivity(intent);
            Log.e("wtf", "start activity success package = " + packageName + ",activity = " + activityPath);
        } catch (Exception e) {
            Log.e("wtf", "start activity Exception" + e.toString());
        }
    }

    /**
     * 开启其他应用,int Extra
     *
     * @param packageName  包名
     * @param activityPath activity全路径名
     * @param key          Extra的key
     * @param value        Extra的value
     */
    public static void startNormalApp(Context context, String packageName, String activityPath,
                                      String key, int value) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName.trim(), activityPath.trim()));
        intent.setAction(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtils.isEmpty(key)) {
            intent.putExtra(key, value);
        }
        try {
            context.startActivity(intent);
            Log.e("wtf", "start activity success package = " + packageName + ",activity = " + activityPath);
        } catch (Exception e) {
            Log.e("wtf", "start activity Exception" + e.toString());
        }
    }

    public static void startService(Context context, String packageName, String servicePath) {
        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName(packageName.trim(), servicePath.trim()));
        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startService(serviceIntent);
        } catch (Exception e) {
            Log.e("wtf", "start service Exception" + e.toString());
        }
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context context, String serviceName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
            if (services.size() <= 0) {
                return false;
            }
            for (int i = 0; i < services.size(); i++) {
                String name = services.get(i).service.getClassName();
                if (name.equals(serviceName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 如果对应的服务未工作就启动对应的服务
     *
     * @param context     上下文
     * @param packageName 服务包名
     * @param serviceName 服务类路径
     */
    public static void startServiceIfNotWorking(Context context, String packageName, String serviceName) {
        if (!isServiceWork(context, serviceName)) {
            startService(context, packageName, serviceName);
        }
    }


}
