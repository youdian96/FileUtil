package com.xlfd.commonutilsx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.xlfd.commonutilsx.listener.IRegister;
import com.xlfd.commonutilsx.listener.OnWifiStateChangedListener;
import com.xlfd.commonutilsx.listener.impl.OnWifiStateChangedImpl;


/**
 * Created by shijiawei on 2020/11/27
 * WiFi信号检测
 * FIXME 需要移出去
 */
public class HQWifiSignalController extends BroadcastReceiver implements IRegister<OnWifiStateChangedListener> {
    private static final String TAG = "WifiSignalController";

    private WifiManager mWifiManager;
    private OnWifiStateChangedImpl wifiStateImpl = new OnWifiStateChangedImpl();
    private Context mContext;
    public HQWifiSignalController(Context context) {
        mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        registerReceiver();

    }


    public void openWifi() {
        if (!isOpen()) {
            mWifiManager.setWifiEnabled(true);
        }
    }


    public void closeWifi() {
        if (isOpen()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    public boolean isOpen() {
        if (mWifiManager != null) {
            return mWifiManager.isWifiEnabled();
        }
        return false;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver(this, intentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.RSSI_CHANGED_ACTION.equals(intent.getAction())) {
            int rssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -80);
            Log.i(TAG, "onReceive: " + rssi);
            //计算WiFi信号强度
            int level = WifiManager.calculateSignalLevel(rssi, 4);
            notifyRssiChange(level);
//            BaseApplication.sbm.setIcon("wifi", 0, level, "");
        }

        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {

            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                Log.i(TAG, "wifi断开");
                onWifiDisconnect();
//                BaseApplication.sbm.removeIcon("wifi");
            } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                String wifiSSID = mWifiManager.getConnectionInfo().getSSID().trim().replaceAll("\"", "");
                onWifiConnect(wifiSSID);

//                BaseApplication.sbm.setIcon("wifi", 0, 0, "");
            }
        }

        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
            if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                notifyWifiStateChanged(false);
//                BaseApplication.sbm.removeIcon("wifi closed");
            } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                Log.i(TAG, "wifi opened");
                notifyWifiStateChanged(true);
            }
        }

    }

    private void notifyRssiChange(int level) {
        onRSSILevelChange(level);
    }

    private void notifyWifiStateChanged(boolean isOpen) {
        onWifiStateChange(isOpen);
    }

    public void onWifiConnect(String name) {
        wifiStateImpl.onWifiConnect(name);
    }

    public void onWifiDisconnect() {
        wifiStateImpl.onWifiDisconnect();
    }

    public void onRSSILevelChange(int level) {
        wifiStateImpl.onRSSILevelChange(level);
    }

    public void onWifiStateChange(boolean isOpen) {
        wifiStateImpl.onWifiStateChange(isOpen);
    }

    @Override
    public void register(OnWifiStateChangedListener listener) {
        wifiStateImpl.register(listener);
    }

    @Override
    public void unRegister(OnWifiStateChangedListener listener) {
        wifiStateImpl.unRegister(listener);
    }
}
