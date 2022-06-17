package com.xlfd.commonutilsx.listener.impl;

import com.xlfd.commonutilsx.listener.IRegister;
import com.xlfd.commonutilsx.listener.OnWifiStateChangedListener;


public class OnWifiStateChangedImpl implements OnWifiStateChangedListener, IRegister<OnWifiStateChangedListener> {
    private RegisterListenerHelper<OnWifiStateChangedListener> registerHelper = new RegisterListenerHelper<>();

    @Override
    public void onWifiConnect(String name) {
        registerHelper.forEach(listener -> listener.onWifiConnect(name));
    }

    @Override
    public void onWifiDisconnect() {
        registerHelper.forEach(OnWifiStateChangedListener::onWifiDisconnect);
    }

    @Override
    public void onRSSILevelChange(int level) {
        registerHelper.forEach(listener -> listener.onRSSILevelChange(level));
    }

    @Override
    public void onWifiStateChange(boolean isOpen) {
        registerHelper.forEach(listener -> listener.onWifiStateChange(isOpen));
    }

    @Override
    public void register(OnWifiStateChangedListener listener) {
        registerHelper.register(listener);
    }

    @Override
    public void unRegister(OnWifiStateChangedListener listener) {
        registerHelper.unRegister(listener);
    }
}
