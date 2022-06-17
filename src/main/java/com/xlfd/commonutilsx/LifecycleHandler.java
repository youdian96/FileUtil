package com.xlfd.commonutilsx;


import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 感知生命周期的Handler
 */
public class LifecycleHandler extends Handler implements LifecycleObserver {

    private LifecycleOwner lifecycleOwner;

    public LifecycleHandler(LifecycleOwner lifecycleOwner) {
        init(lifecycleOwner);
    }

    public LifecycleHandler(Callback callback, LifecycleOwner lifecycleOwner) {
        super(callback);
        init(lifecycleOwner);
    }

    public LifecycleHandler(Looper looper, LifecycleOwner lifecycleOwner) {
        super(looper);
        init(lifecycleOwner);
    }

    public LifecycleHandler(Looper looper, Callback callback, LifecycleOwner lifecycleOwner) {
        super(looper, callback);
        init(lifecycleOwner);
    }

    private void init(LifecycleOwner lifecycleOwner){
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    private void addObserver() {
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void release() {
        removeCallbacksAndMessages(null);
        lifecycleOwner.getLifecycle().removeObserver(this);
    }


}
