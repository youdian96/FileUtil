package com.xlfd.commonutilsx.listener.impl;

import android.bluetooth.BluetoothDevice;

import com.xlfd.commonutilsx.listener.IRegister;
import com.xlfd.commonutilsx.listener.OnBluetoothStateChangedListener;


public class OnBluetoothStateChangedImpl implements OnBluetoothStateChangedListener, IRegister<OnBluetoothStateChangedListener> {
    private RegisterListenerHelper<OnBluetoothStateChangedListener> registerHelper = new RegisterListenerHelper<>();

    @Override
    public void onBluetoothStateChanged(boolean isOpen) {
        forEach(listener -> listener.onBluetoothStateChanged(isOpen));
    }

    @Override
    public void onConnected(BluetoothDevice device) {
        forEach(listener -> listener.onConnected(device));
    }

    @Override
    public void onDisconnected() {
        forEach(listener -> listener.onDisconnected());
    }

    @Override
    public void register(OnBluetoothStateChangedListener listener) {
        registerHelper.register(listener);
    }

    @Override
    public void unRegister(OnBluetoothStateChangedListener listener) {
        registerHelper.unRegister(listener);
    }

    private void forEach(RegisterListenerHelper.ICallBack<OnBluetoothStateChangedListener> callBack) {
        registerHelper.forEach(callBack);
    }
}
