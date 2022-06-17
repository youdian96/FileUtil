package com.xlfd.commonutilsx;

import android.content.Context;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

    /**
     * 将asset文件写入缓存
     */
    public static boolean copyAsset2CacheDir(String fileName, Context context) {
        File cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File outFile = new File(cacheDir, fileName);
        if (!outFile.exists()) {
            boolean res = false;
            try {
                res = outFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!res) {
                return false;
            }
        } else {
            if (outFile.length() > 10) {//表示已经写入一次
                return true;
            }
        }
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = context.getAssets().open(fileName);
            fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(is);
            close(fos);

        }

        return false;
    }

    /**
     * Close closable and hide possible {@link IOException}
     *
     * @param closeable closeable object
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 删除目录下所有文件
     *
     * @param Path 路径
     */
    public static void deleteAllFile(String Path) {

        // 删除目录下所有文件
        File path = new File(Path);
        File files[] = path.listFiles();
        if (files != null) {
            for (File tfi : files) {
                if (tfi.isDirectory()) {
                    LogUtils.i(tfi.getName());
                } else {
                    tfi.delete();
                }
            }
        }
    }
}
