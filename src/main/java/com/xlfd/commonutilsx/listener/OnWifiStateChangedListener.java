package com.xlfd.commonutilsx.listener;

public interface OnWifiStateChangedListener {
    void onWifiConnect(String name);
    void onWifiDisconnect();
    void onRSSILevelChange(int level);
    void onWifiStateChange(boolean isOpen);
}
