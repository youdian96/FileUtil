package com.xlfd.commonutilsx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.xlfd.commonutilsx.listener.IRegister;
import com.xlfd.commonutilsx.listener.OnUsbStateChangedListener;
import com.xlfd.commonutilsx.listener.impl.OnUsbStateChangedImpl;

import java.io.File;

/**
 * Created by Administrator on 2017/6/26.
 * TODO 需要添加监听器
 * FIXME 需要移出去
 */
public class UsbController extends BroadcastReceiver implements IRegister<OnUsbStateChangedListener> {
    private final static String ACTION_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    private final static String ACTION_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    public static String USB_PATH = "/mnt/sdcard1/";
    private OnUsbStateChangedImpl usbStateChanged = new OnUsbStateChangedImpl();
    private Context mContext;

    public UsbController(Context context) {
        mContext = context;
        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter intentFilterAp = new IntentFilter();
        intentFilterAp.addAction(ACTION_ATTACHED);
        intentFilterAp.addAction(ACTION_DETACHED);
        intentFilterAp.addAction(Intent.ACTION_BOOT_COMPLETED);
        mContext.registerReceiver(this, intentFilterAp);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_ATTACHED)) {
            usbStateChanged.onMountStateChanged(true);
        } else if (action.equals(ACTION_DETACHED)) {
            usbStateChanged.onMountStateChanged(false);
        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            if (isUsbExist()) {
                usbStateChanged.onMountStateChanged(true);
            }
        }
    }

    public boolean isUsbExist() {
        File file = new File(USB_PATH);
        if (file != null && file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 释放资源
     */
    public void releaseResource() {
        mContext.unregisterReceiver(this);
    }

    @Override
    public void register(OnUsbStateChangedListener listener) {
        usbStateChanged.register(listener);
    }

    @Override
    public void unRegister(OnUsbStateChangedListener listener) {
        usbStateChanged.unRegister(listener);
    }
}