package com.xlfd.commonutilsx.exts

import android.os.Looper
import com.xlfd.commonutilsx.RxUtils
import io.reactivex.Observable
import java.util.concurrent.Executor

fun <T> Observable<T>.io2Main() = compose(RxUtils.io2Main())

fun <T> Observable<T>.io() = compose(RxUtils.io())

fun <T> Observable<T>.computation() = compose(RxUtils.computation())

fun <T> Observable<T>.newThread() = compose(RxUtils.newThread())

fun <T> Observable<T>.fromLooper(looper: Looper) = compose(RxUtils.fromLooper(looper))

fun <T> Observable<T>.single() = compose(RxUtils.single())

fun <T> Observable<T>.fromExecutor(executor: Executor) = compose(RxUtils.fromExecutor(executor))




