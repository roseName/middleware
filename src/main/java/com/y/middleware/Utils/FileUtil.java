package com.y.middleware.Utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具类
 * Created by ywk on 2016/8/24.
 */

public class FileUtil {


    private static final String DEFAULT_STORE_PATH = "ecfit";

    /**
     * <b>Description:</b> This method is used to get saving file path of the
     * RCS.'
     *
     * @return Specifies the path of the RCS
     */
    public static String getSaveFilePath() {
        String savePath = null;
        long x = getExternaltStorageAvailableSpace();
        long y = getSdcard2StorageAvailableSpace();
        long z = getEmmcStorageAvailableSpace();

        long max = compareValue(x, y, z);
        if (max == x) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else if (max == y) {
            savePath = getSdcard2StorageDirectory();
        } else if (max == z) {
            savePath = getEmmcStorageDirectory();
        }
        return savePath + File.separator + DEFAULT_STORE_PATH + File.separator;
    }

    /**
     * get external Storage available space
     */
    private static long getExternaltStorageAvailableSpace() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs statfs = new StatFs(path.getPath());
        long blockSize = statfs.getBlockSize();
        long availableBlocks = statfs.getAvailableBlocks();
        return blockSize * availableBlocks;
    }

    /**
     * get sdcard2 external Storage available space
     */
    private static long getSdcard2StorageAvailableSpace() {

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        String path = getSdcard2StorageDirectory();
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        StatFs statfs = new StatFs(path);
        long blockSize = statfs.getBlockSize();
        long availableBlocks = statfs.getAvailableBlocks();
        return blockSize * availableBlocks;
    }

    /**
     * get EMMC internal Storage available space
     */
    private static long getEmmcStorageAvailableSpace() {
        String path = getEmmcStorageDirectory();
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        StatFs statfs = new StatFs(path);
        long blockSize = statfs.getBlockSize();
        long availableBlocks = statfs.getAvailableBlocks();
        return blockSize * availableBlocks;
    }

    private final static String getSdcard2StorageDirectory() {
        return "/mnt/sdcard2";
    }

    private final static String getEmmcStorageDirectory() {
        return "/mnt/emmc";
    }

    private static long compareValue(long x, long y, long z) {
        long m1 = x > y ? x : y;
        long max = m1 > z ? m1 : z;
        return max;
    }

    /**
     * <b>Description:</b> This method is used to compress multiples files into
     * a .zip file. <br>
     * <b>Purpose:</b> The UI invokes this method to compress multiples files
     * into a .zip file.
     *
     * @param srcfile Indicates the arrays of source files to be compressed.
     * @param zipfile Indicates the compressed files.
     */
    public static void zipFiles(File[] srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try {
            // Create the ZIP file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    zipfile));
            // Compress the files
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                // Complete the entry
                out.closeEntry();
                in.close();
            }
            // Complete the ZIP file
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * <b>Description:</b> This method is used to create a file. <br>
     * <b>Purpose:</b> The UI invokes this method to create a file.
     *
     * @param filePath Indicates the path of a file to be created. /mnt/sdcard/hrslog
     *                 is an example file path.
     * @return File: Specifies the object of a created file.
     * @throws IOException when exception occur
     */
    public static File createFile(String filePath) throws IOException {
        if (TextUtils.isEmpty(filePath)) {
            LogUtil.d("filePath is null.");
            return null;
        }
        filePath = filePath.replaceAll(File.separator + File.separator,
                File.separator);

        File file = new File(filePath);

        File parentFile = file.getParentFile();
        if (null != parentFile && !parentFile.exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.createNewFile()) {
            LogUtil.i("make success");
        } else {
            LogUtil.i("the file is exits");
        }
        return file;
    }


    /**
     * <b>Description:</b> This method is used to delete a file. <br>
     * <b>Purpose:</b> The UI invokes this method to delete a file.
     *
     * @param filePath Indicates the path of a file to be deleted. /mnt/sdcard/hrslog
     *                 is an example file path.
     */
    public static void deleteFile(String filePath) {
        LogUtil.d("deleteFile() filePath:" + filePath);

        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        if (file != null) {
            file.delete();
        }
    }

    /**
     * <b>Description:</b> This method is used to check whether a file exists. <br>
     * <b>Purpose:</b> The UI invokes this method to check whether a file
     * exists.
     *
     * @param path Indicates the path of a file to be checked. /mnt/sdcard/hrslog
     *             is an example file path.
     * @return True Specifies that a file exists. <br>
     * False Specifies that a file does not exist.
     */
    public static boolean isFileExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        return file.exists();
    }


    private static List<String> fileList;

    /**
     * <b>Description:</b> This method is used to obtain all files in a file
     * folder. <br>
     * <b>Purpose:</b> The UI invokes this method to obtain all files in a file
     * folder.
     *
     * @param path    Indicates a file path, such as /mnt/sdcard/hrslog.
     * @param suffix  Indicates a file suffix name, such as txt.
     * @param isdepth Indicates whether to traverse subdirectories.
     * @return List<String>: Specifies file directory lists that meet
     * requirements.
     */
    public static List<String> getListFiles(String path, String suffix,
                                            boolean isdepth) {
        fileList = new ArrayList<String>();
        File file = new File(path);
        return getListFiles(file, suffix, isdepth);
    }

    private static List<String> getListFiles(File f, String suffix,
                                             boolean isdepth) {

        // 是目录，同时需要遍历子目录
        if (f.isDirectory() && isdepth == true) {
            File[] t = f.listFiles();
            for (int i = 0; i < t.length; i++) {
                getListFiles(t[i], suffix, isdepth);
            }
        } else {
            String filePath = f.getAbsolutePath();

            System.out.println("suffix = " + suffix);
            if (suffix == null || suffix.equals("")) {
                // 后缀名为null则为所有文件
                System.out.println("----------------");
                fileList.add(filePath);
            } else {
                int begIndex = filePath.lastIndexOf(".");// 最后一个.(即后缀名前面的.)的索引
                String tempsuffix = "";

                if (begIndex != -1)// 防止是文件但却没有后缀名结束的文件
                {
                    tempsuffix = filePath.substring(begIndex + 1,
                            filePath.length());
                }

                if (tempsuffix.equals(suffix)) {
                    fileList.add(filePath);
                }
            }
        }
        return fileList;
    }


    /**
     * <b>Description:</b> This method is used to copy a file. <br>
     * <b>Purpose:</b> The UI invokes this method to copy a file.
     *
     * @param fromFile Indicates a file to be copied.
     * @param toFile   Indicates a copied file.
     * @param rewrite  Indicates whether a file is overwritten if the copied file
     *                 exists.
     * @return True Specifies that the copy succeeds. <br>
     * False Specifies that the copy fails.
     */
    public static boolean copyfile(File fromFile, File toFile, boolean rewrite) {
        if (!fromFile.exists() || !fromFile.isFile() || !fromFile.canRead()) {
            return false;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }

        FileInputStream fosfrom = null;
        FileOutputStream fosto = null;
        boolean result = false;
        try {
            fosfrom = new FileInputStream(fromFile);
            fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            result = true;
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        } finally {
            try {
                if (null != fosfrom) {
                    fosfrom.close();
                }
                if (null != fosto) {
                    fosto.close();
                }

            } catch (Exception ex) {
                LogUtil.e(ex.getMessage());
            }
        }
        return result;
    }


    public static boolean copyfile(InputStream fosfrom, File toFile,
                                   boolean rewrite) {
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }

        FileOutputStream fosto = null;
        boolean result = false;
        try {
            fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            result = true;
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        } finally {
            try {
                if (null != fosfrom) {
                    fosfrom.close();
                }
                if (null != fosto) {
                    fosto.close();
                }

            } catch (Exception ex) {
                LogUtil.e(ex.getMessage());
            }
        }
        return result;
    }


    /**
     * <b>Description:</b> This method is used to determine whether a file
     * exists. If the file exists, a newly created file is to be renamed. <br>
     * <b>Purpose:</b> The UI invokes this method to determine whether a file
     * exists when a newly created file cannot overwrite a file with the same
     * name.
     *
     * @param filePath Indicates the path of a file to be checked.
     * @return Specifies the final file path.
     */
    public static String existThenRenameFile(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int pointPosition = fileName.lastIndexOf(".");
        if (-1 != pointPosition) {
            String filePrefix = fileName.substring(0, pointPosition);
            String filePostfix = fileName.substring(pointPosition + 1);
            int count = 0;
            while (file.exists()) {
                fileName = filePrefix + "(" + (++count) + ")" + "."
                        + filePostfix;
                file = new File(file.getParent(), fileName);
            }
        } else {
            String filePrefix = fileName;
            int count = 0;
            while (file.exists()) {
                fileName = filePrefix + "(" + (++count) + ")";
                file = new File(file.getParent(), fileName);
            }
        }

        return file.getParent() + File.separator + fileName;
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param filePath
     * @throws IOException
     */
    public static File savePic(Bitmap bm, String filePath) {
        BufferedOutputStream bos = null;
        File myCaptureFile = null;
        try {
//            checkDir();
            myCaptureFile = new File(filePath);
            if (!myCaptureFile.exists()) {
                myCaptureFile = createFile(filePath);
            }
            // myCaptureFile = createFile(filePath);

            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myCaptureFile;
    }


}
