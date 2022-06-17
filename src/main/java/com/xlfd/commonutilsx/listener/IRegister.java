package com.xlfd.commonutilsx.listener;

public interface IRegister<T> {
    void register(T listener);
    void unRegister(T listener);
}
