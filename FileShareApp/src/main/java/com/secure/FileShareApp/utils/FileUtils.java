package com.secure.FileShareApp.utils;

import lombok.RequiredArgsConstructor;

import java.text.DecimalFormat;

@RequiredArgsConstructor
public class FileUtils {

    public static String formatFileSize(long sizeInBytes) {
        if (sizeInBytes <= 0) return "0 KB";

        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double size = sizeInBytes;

        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(size) + " " + units[unitIndex];
    }
}
