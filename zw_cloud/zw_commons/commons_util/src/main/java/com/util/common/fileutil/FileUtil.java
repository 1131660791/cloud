package com.util.common.fileutil;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

/**
 * File处理工具类
 *
 * @author Administrator
 */
public class FileUtil {

    /**
     * 删除文件
     *
     * @param path   文件地址
     * @param delete 是否确认删除
     * @return true/false 是否删除成功
     * @author hzw
     * @Date 2015年10月9日
     */
    public static boolean deleteFile(String path, boolean delete) {
        File file = new File(path);
        if (file.exists() && delete) {
            file.delete();
            return true;
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param file
     * @return boolean
     */
    private static boolean deleteFile(File file) {
        return file.delete();
    }


    /**
     * 文件上传
     *
     * @param
     * @param url  传入文件上传的文件地址
     * @param path 需要上传到的位置
     * @throws Exception
     * @author hzw
     * @Date 2015年10月9日
     */
    public static void FileUpload(String url, String path) throws Exception {
        // 传入需要上传的文件地址
        FileInputStream fis = new FileInputStream(url);
        // 需要上传到哪里的地址
        FileOutputStream fos = new FileOutputStream(path);
        // 创建缓冲区 一次行读取 1KB
        byte[] buffer = new byte[1024];
        // 定义一个变量 用于判断 文件的长度
        int length;
        // 如果文件长度大于0 就让它继续读取
        do {
            length = fis.read(buffer);
            fos.write(buffer); // 每次写入 1KB
        } while (length > 0);
        //关闭流
        fis.close();
        fos.flush();
        fos.close();
    }

    /**
     * 文件大小计算
     *
     * @param fileS
     * @return
     */
    public static String formetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 剪切文件
     *
     * @param oldFilePath 文件原路径
     * @param newFilePath 文件新路径
     */
    public static void moveFile(String oldFilePath, String newFilePath, String fileName) {
        String fileUrl = null;
        InputStream inStream = null;
        FileOutputStream fs = null;
        File oldfile = new File(oldFilePath + File.separator + fileName);
        try {
            int bytesum = 0;
            int byteread = 0;
            if (oldfile.exists()) {
                File file = new File(newFilePath);
                if (!file.exists()) {
                    //创建新文件路径
                    file.mkdirs();
                }
                inStream = new FileInputStream(oldFilePath + File.separator + fileName); // 读入原文件
                fs = new FileOutputStream(newFilePath + File.separator + fileName);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oldfile.exists()) {
                oldfile.delete();
            }
        }
    }

    /**
     * 删除目录及其下面的所有子文件和子文件夹，注意一个目录下如果还有其他文件或文件夹
     * 则直接调用delete方法是不行的，必须待其子文件和子文件夹完全删除了才能够调用delete
     *
     * @param path path为该目录的路径
     */
    private static boolean deleteDirectory(String path) {
        boolean flag = true;
        File dirFile = new File(path);
        if (!dirFile.isDirectory()) {
            return flag;
        }
        File[] files = dirFile.listFiles();
        for (File file : files) { // 删除该文件夹下的文件和文件夹
            // Delete file.
            if (file.isFile()) {
                flag = deleteFile(file);
            } else if (file.isDirectory()) {// Delete folder
                flag = deleteDirectory(file.getAbsolutePath());
            }
            if (!flag) { // 只要有一个失败就立刻不再继续
                break;
            }
        }
        flag = dirFile.delete(); // 删除空目录
        return flag;
    }

    /**
     * 判断路径是否存在，否：创建此路径
     *
     * @param dir      文件路径
     * @param realName 文件名
     * @throws IOException
     */
    public static File mkdirsMy(String dir, String realName) throws IOException {
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }


}

