package com.xlfd.commonutilsx;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.xlfd.commonutilsx.listener.IRegister;
import com.xlfd.commonutilsx.listener.OnBluetoothStateChangedListener;
import com.xlfd.commonutilsx.listener.impl.OnBluetoothStateChangedImpl;

/**
 * Created by shijiawei on 2020/11/27
 * FIXME 需要移出去
 */
public class BluetoothController extends BroadcastReceiver implements IRegister<OnBluetoothStateChangedListener> {
    private static final String TAG = "BluetoothController";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mCurrentDevice;
    private OnBluetoothStateChangedImpl bluetoothStateListener = new OnBluetoothStateChangedImpl();
    private Context mContext;

    public BluetoothController(Context context){
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        registerReceiver();

    }

    public void openBluetooth(){
        if(!isOpen()){
            mBluetoothAdapter.enable();
        }
    }


    public void closeBluetooth(){
        if(isOpen()){
            mBluetoothAdapter.disable();
        }
    }


    public boolean isOpen(){
        if(mBluetoothAdapter != null){
            return mBluetoothAdapter.isEnabled();
        }
        return false;
    }

    public boolean isConnected(){
        if(isOpen()){
            int a2dp = mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP);
            int headset = mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET);
            if (a2dp == BluetoothProfile.STATE_CONNECTED) {
                return true;
            } else if (headset == BluetoothProfile.STATE_CONNECTED) {
                return true;
            }
        }
        return false;
    }


    private void registerReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
        mContext.registerReceiver(this, intentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            // Reregister Profile Broadcast Receiver as part of TURN OFF
            if (state == BluetoothAdapter.STATE_OFF) {
                Log.d(TAG, "bluetooth off ");
//                BaseApplication.sbm.removeIcon("bluetooth");
                //TODO 蓝牙关闭
                notifyConnectStateChanged(false, null);
                notifyBluetoothStateChanged(false);
                mCurrentDevice = null;
            }else if(state == BluetoothAdapter.STATE_ON){
                notifyBluetoothStateChanged(true);
            }
        }

        if(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)){
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE,
                    BluetoothAdapter.ERROR);
            if(state == BluetoothAdapter.STATE_CONNECTED){
//                BaseApplication.logger.d("bluetooth connected");
                //TODO 蓝牙连接
                mCurrentDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                notifyConnectStateChanged(true, mCurrentDevice);
//                BaseApplication.sbm.setIcon("bluetooth", 0, 0, "");
            }else {
                //TODO 蓝牙连接丢失
//                BaseApplication.logger.d("bluetooth disconnected");
                notifyConnectStateChanged(false, null);
//                BaseApplication.sbm.removeIcon("bluetooth");
                mCurrentDevice = null;
            }

        }

        if(BluetoothDevice.ACTION_NAME_CHANGED.equals(action) ){
            Log.d(TAG, "bluetooth device name change" );
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if(device != null && mCurrentDevice != null && device.getAddress().equals(mCurrentDevice.getAddress())){
                mCurrentDevice = device;
                notifyConnectStateChanged(true, mCurrentDevice);
            }
        }


    }


    private void notifyConnectStateChanged(boolean isConnect, BluetoothDevice device){
        if(bluetoothStateListener != null){
            if(isConnect){
                bluetoothStateListener.onConnected(device);
            }else {
                bluetoothStateListener.onDisconnected();
            }

        }

    }

    private void notifyBluetoothStateChanged(boolean isOpen){
        if(bluetoothStateListener != null){
            bluetoothStateListener.onBluetoothStateChanged(isOpen);
        }
    }

    @Override
    public void register(OnBluetoothStateChangedListener listener) {
        bluetoothStateListener.register(listener);
    }

    @Override
    public void unRegister(OnBluetoothStateChangedListener listener) {
        bluetoothStateListener.unRegister(listener);
    }


}
