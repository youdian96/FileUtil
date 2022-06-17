package com.xlfd.commonutilsx;

import android.text.TextUtils;

/**
 * 处理数值转换的工具类
 */
public class NumberUtils {
    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        if (TextUtils.isEmpty(inHex)) {
            return new byte[0];
        }
        inHex = inHex.toLowerCase().replace("0x", "");
        int hexLen = inHex.length();
        if ((hexLen & 1) == 1) {
            //奇数
            hexLen++;
            inHex = "0" + inHex;
        }
        //偶数
        byte[] result = new byte[(hexLen >> 1)];
        for (int i = 0; i < hexLen; i += 2) {
            result[i / 2] = hexToByte(inHex.substring(i, i + 2));
        }
        return result;
    }

    /**
     * @param org    原始的数值
     * @param toZero 将某几位设置为0的数
     * @return 转换后的数据
     */
    public byte bit2Zero(byte org, byte toZero) {
        return (byte) (org & (~toZero));
    }
}