package com.zipingfang.aihuan.utils;

import android.content.Context;

import com.zipingfang.aihuan.constants.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 * 图片,文件等下载
 */
public class FileUtils {

    /**
     * 本地文件全路径
     * 1. 工程路径+ fileName的最后的名称
     * <p>
     * eg: FileUtils.getLocalFileName(context,fileName);
     *
     * @param fileName
     * @return
     */
    public static String getLocalFileName(Context context, String fileName) {
        return Constants.getProjectPath(context) + File.separator + FileUtils.getShortName(fileName);
    }

    /**
     * 取文件名
     *
     * @param fileName
     * @return
     */
    public static String getShortName(String fileName) {
        if (fileName == null) return "";
        else {
            int index = fileName.lastIndexOf("/") + 1;
            if (index > 0) {
                return fileName.substring(index);
            } else {
                index = fileName.lastIndexOf("\\") + 1;
                if (index > 0) {
                    return fileName.substring(index);
                } else {
                    return fileName;
                }
            }
        }
    }

    /**
     * 得到一个文件的上级目录
     * 文件不存在就新增
     *
     * @param fileName
     * @return
     */
    public static String getParentRoot(String fileName) {
        String path;
        if (fileName.indexOf("/") > -1)
            path = fileName.substring(0, fileName.lastIndexOf("/")) + "/";
        else if (fileName.indexOf(File.separator) > -1)
            path = fileName.substring(0, fileName.lastIndexOf(File.separator)) + File.separator;
        else
            path = null;

        return path;
    }


    /**
     * 测试此抽象路径名表示的文件或目录是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean exists(String fileName) {
        if (fileName == null) {
            System.out.print(fileName + " is null fileName.");
            return false;
        }
        File file = new File(fileName);
        return file.exists();
    }

    public static boolean existsFile(String fileName) {
        if (fileName == null) {
            System.out.print(fileName + " is null fileName.");
            return false;
        }
        File file = new File(fileName);
        return file.exists() && file.isFile();
    }

    public static boolean existsDir(String fileName) {
        if (fileName == null) {
            System.out.print(fileName + " is null fileName.");
            return false;
        }
        File file = new File(fileName);
        return file.exists() && file.isDirectory();
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     * @throws Exception
     */
    public static void copyFile(String oldPath, String newPath) throws Exception {
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                inStream = new FileInputStream(oldPath); //读入原文件
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
            }
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fs != null) {
                try {
                    fs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
