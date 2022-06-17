package com.xlfd.commonutilsx.exts

import com.xlfd.commonutilsx.NumberUtils

/**
 * 16进制转二进制数组
 */
fun String.hex2ByteArray() = NumberUtils.hexToByteArray(this)