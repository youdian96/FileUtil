package com.xlfd.commonutilsx.listener;

import android.bluetooth.BluetoothDevice;

public interface OnBluetoothStateChangedListener {
    void onBluetoothStateChanged(boolean isOpen);
    void onConnected(BluetoothDevice device);
    void onDisconnected();
}
