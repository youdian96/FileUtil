package com.xlfd.commonutilsx.exts

import android.os.Handler
import android.os.Message
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun Handler.postDelayed(delayMills: Long, r: Runnable) = postDelayed(r, delayMills)

/**
 * 结合 协程 简化 handler 使用复杂度
 */
suspend fun <T> Handler.postDelayWithReturn(delayMills: Long, block: () -> T) =
    suspendCancellableCoroutine<T> { continuation ->
        val message = Message.obtain(this) {
            try {
                continuation.resume(block())
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }.also { it.obj = continuation }
        continuation.invokeOnCancellation {
            removeCallbacksAndMessages(message)
        }
        sendMessageDelayed(message, delayMills)
    }

suspend fun <T> Handler.postWithReturn(block: () -> T) = postDelayWithReturn(0, block)