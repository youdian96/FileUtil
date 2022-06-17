package com.xlfd.commonutilsx.listener.impl;


import com.xlfd.commonutilsx.listener.IRegister;
import com.xlfd.commonutilsx.listener.OnUsbStateChangedListener;

public class OnUsbStateChangedImpl implements OnUsbStateChangedListener, IRegister<OnUsbStateChangedListener> {
    private RegisterListenerHelper<OnUsbStateChangedListener> registerHelper = new RegisterListenerHelper<>();

    @Override
    public void onMountStateChanged(boolean isAttached) {
        registerHelper.forEach(listener -> listener.onMountStateChanged(isAttached));
    }

    @Override
    public void register(OnUsbStateChangedListener listener) {
        registerHelper.register(listener);
    }

    @Override
    public void unRegister(OnUsbStateChangedListener listener) {
        registerHelper.unRegister(listener);
    }
}
