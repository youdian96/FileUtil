package com.xlfd.commonutilsx.listener.impl;

import com.xlfd.commonutilsx.listener.IRegister;

import java.util.ArrayList;
import java.util.List;

public class RegisterListenerHelper<T> implements IRegister<T> {

    private List<T> registerList = new ArrayList<>();

    public List<T> getRegisterList() {
        return registerList;
    }

    public void register(T listener) {
        if (listener != null && !registerList.contains(listener))
            registerList.add(listener);
    }

    public void forEach(ICallBack<T> callBack) {
        for (T t : registerList) {
            try{
                callBack.onCallback(t);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void unRegister(T listener) {
        registerList.remove(listener);
    }

    public static interface ICallBack<I> {
        void onCallback(I listener);
    }
}
