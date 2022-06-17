package com.xlfd.commonutilsx;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UsbUtils {
    /**
     * 返回当前挂载的USB路径  如果没有挂载返回空
     *
     * @return
     */
    public static String searchUsbPath() {
        String filePath = "/proc/mounts";
        File file = new File(filePath);
        List<String> lineList = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("vfat")) {
                        lineList.add(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String editPath = lineList.get(lineList.size() - 1);
        int start = editPath.indexOf("/mnt");
        int end = editPath.indexOf(" vfat");
        if (start < 0 || end < 0) {
            return null;
        }
        String path = editPath.substring(start, end);
        Log.d("SelectBusLineDialog", "path: " + path);
        return path;
    }
}
