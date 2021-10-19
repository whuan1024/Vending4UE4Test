package com.cloudminds.vending.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.cloudminds.vending.roc.ActivationRcuBean;
import com.cloudminds.vending.roc.RodHeader;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileUtil {

    public static SimpleDateFormat sFileNameDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    public static final String RESTART_APP_FLAG = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cloudminds/restart_app";
    private static final String ACTIVATION_INFO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cloudminds/activation.info";
    private static final String MIDEA_CONFIG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/midea/config";
    private static final String DEFAULT_CONFIG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vending.config";
    private static final String UE4_CONFIG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/UE4Game/RobotEngine/RobotEngine/Saved/Config/Android/config.ini";
    private static final String DEFAULT_DOMAIN = "https://cross.cloudvending.cn";

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 递归删除文件目录（以路径作参数）
     *
     * @param dirPath
     */
    public static void deleteDir(String dirPath) {
        File dir = new File(dirPath);
        deleteDir(dir);
    }

    /**
     * 递归删除文件目录（以文件作参数）
     *
     * @param dir
     */
    public static void deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                deleteDir(file);
            }
        }
        dir.delete();
    }

    public static long getDirectorySize(File dir) {
        long length = 0;
        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        length += file.length();
                    } else {
                        length += getDirectorySize(file);
                    }
                }
            }
        }
        return length;
    }

    public static List<File> listFilesForFolder(File dir, String extension) {
        List<File> files = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(extension)) {
                files.add(file);
            }
        }
        return files;
    }

    /**
     * 存储图片
     *
     * @param dirPath  存储到哪个目录
     * @param fileName 文件名（不需要文件名后缀）
     * @param bitmap
     */
    public static void saveBitmap(String dirPath, String fileName, Bitmap bitmap) {
        saveBitmap(dirPath + File.pathSeparator + fileName + File.separator + "jpeg", bitmap);
    }

    /**
     * 存储图片
     *
     * @param filePath 图片完整的绝对路径（含文件名后缀）
     * @param bitmap
     */
    public static void saveBitmap(String filePath, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成/获取一个新的照片路径
     *
     * @param context
     * @param cameraName 多个摄像头同时拍照需要区分照片来自于哪个摄像头
     * @return
     */
    public static String getPhotoPath(Context context, @Nullable String cameraName) {
        File storageDir = getOwnCacheDirectory(context, "Camera");
        String filename;
        if (TextUtils.isEmpty(cameraName)) {
            filename = sFileNameDateFormat.format(new Date()) + ".jpeg";
        } else {
            filename = cameraName + "_" + sFileNameDateFormat.format(new Date()) + ".jpeg";
        }
        return storageDir.getPath() + "/" + filename;
    }

    private static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        //判断SD卡正常挂载并且有权限的时候创建文件
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) &&
                hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = new File(context.getCacheDir(), cacheDir);
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int permission = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 读取配置文件中的内容
     *
     * @return
     */
    private static String readConfigFile(String path) {
        File file = new File(path);
        StringBuffer sb = new StringBuffer();

        if (file.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                LogUtil.e("[FileUtil] Failed to read config file. " + e.getMessage());
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            LogUtil.w("[FileUtil] Config file " + path + " is not exists.");
        }

        return sb.toString();
    }

    public static String getDomain() {
        String config = readConfigFile(DEFAULT_CONFIG_PATH);
        LogUtil.d("[FileUtil] config content is: " + config);
        String domain = "";

        if (!TextUtils.isEmpty(config)) {
            try {
                JSONObject configJson = new JSONObject(config);
                if (configJson.has("domain")) {
                    domain = configJson.getString("domain");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return TextUtils.isEmpty(domain) ? DEFAULT_DOMAIN : domain;
    }

    public static int getCabinetFloorCount() {
        String config = readConfigFile(MIDEA_CONFIG_PATH);
        LogUtil.d("[FileUtil] Midea config content is: " + config);
        int count = 4;

        if (!TextUtils.isEmpty(config)) {
            try {
                JSONObject configJson = new JSONObject(config);
                if (configJson.has("floorCount")) {
                    count = configJson.getInt("floorCount");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        LogUtil.d("[FileUtil] cabinet floor count: " + count);
        return count;
    }

    public static String getFileMD5(String filePath) {
        String value = "";
        FileInputStream fis = null;
        DigestInputStream dis = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(filePath);
            dis = new DigestInputStream(fis, md5);
            byte[] buffer = new byte[1024];
            while (dis.read(buffer) > 0)
                ;
            md5 = dis.getMessageDigest();
            byte[] resultByteArray = md5.digest();
            value = byteArrayToHex(resultByteArray);
        } catch (Exception e) {
            LogUtil.e("[FileUtil] Failed to get file md5.", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (dis != null) {
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    public static void setRodHeader(Context context) {
        String activationInfo = "";

        File file = new File(UE4_CONFIG_PATH);
        if (file.exists()) {
            try {
                IniReaderHasSection reader = new IniReaderHasSection(UE4_CONFIG_PATH);
                activationInfo = reader.getValue("/Script/HarixRcu.HarixRcuGconfig", "ActivationInfo");
                activationInfo = StringEscapeUtils.unescapeJava(activationInfo); //去除json中的转义符号
                if (activationInfo.length() > 2) {
                    activationInfo = activationInfo.substring(1, activationInfo.length() - 1); //去除json首尾的双引号
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.w("[FileUtil] ue4 config.ini file is not exists.");
        }

        if (!TextUtils.isEmpty(activationInfo)) {
            ActivationRcuBean activationRcuBean = null;
            try {
                activationRcuBean = new Gson().fromJson(activationInfo, ActivationRcuBean.class);
            } catch (JsonSyntaxException e) {
                LogUtil.e("[FileUtil] Failed to parse activation json: " + activationInfo, e);
            }
            if (activationRcuBean != null) {
                ActivationRcuBean.DataBean dataBean = activationRcuBean.getData();
                if (dataBean != null) {
                    RodHeader.getInstance().setTenantId(dataBean.getTenantCode());
                    RodHeader.getInstance().setRobotId(DeviceUnityCodeUtil.getDeviceUnityCode(context));
                    RodHeader.getInstance().setRobotType(dataBean.getRobotType());
                    RodHeader.getInstance().setUserId(dataBean.getUserCode());
                    RodHeader.getInstance().setUploadAddress(dataBean.getFileUploadAddress());
                }
            }
        }
    }
}
