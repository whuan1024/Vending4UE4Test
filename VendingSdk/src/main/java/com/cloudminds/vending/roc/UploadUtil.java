package com.cloudminds.vending.roc;

import android.text.TextUtils;

import com.cloudminds.vending.utils.FileUtil;
import com.cloudminds.vending.utils.LogUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class UploadUtil {

    private static final int BUFFER_LEN = 10 * 1024 * 1024; //10M

    private static final int RETRY_TIME_MAX = 3;

    public static String uploadFileX(String path, final Map<String, String> headMap) {
        String storageId = "";
        String fileUrl = "";

        if (path == null || path.isEmpty()) {
            LogUtil.w("[UploadUtil] path is null, return directly.");
            return fileUrl;
        }

        File pathFile = new File(path);
        if (!pathFile.exists()) {
            LogUtil.w("[UploadUtil] path file is not exists, return directly.");
            return fileUrl;
        }

        String fileMd5 = FileUtil.getFileMD5(path);
        if (fileMd5 == null || fileMd5.isEmpty()) {
            LogUtil.w("[UploadUtil] generate MD5 failed, return directly.");
            return fileUrl;
        }

        if (headMap == null || headMap.isEmpty()) {
            LogUtil.w("[UploadUtil] read head info failed, return directly.");
            return fileUrl;
        }

        String host = getHost();
        if (host == null || host.isEmpty()) {
            LogUtil.w("[UploadUtil] get host ip failed, return directly.");
            return fileUrl;
        }

        long fileSize = pathFile.length();
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeInMB = df.format((double) fileSize / 1024 / 1024);
        LogUtil.d("[UploadUtil] uploadFileX begin, path: " + path + ", file size: " + fileSize + "Byte, " + fileSizeInMB + "MB.");
        try {
            if (fileSize < BUFFER_LEN) {
                storageId = upload(host, path, headMap);
            } else {
                storageId = uploadx(host, path, fileMd5, headMap);
            }
        } catch (Exception e) {
            LogUtil.e("[UploadUtil] uploadFileX Exception: " + e.getMessage() + ", path: " + path);
        }

        if (!TextUtils.isEmpty(storageId)) {
            fileUrl = host + "rod/storage/file/" + storageId;
        }

        return fileUrl;
    }

    public static String uploadFileAndRetry(String path, final Map<String, String> mHeadMap) {
        String fileUrl = "";
        for (int i = 0; i < RETRY_TIME_MAX; i++) {
            fileUrl = uploadFileX(path, mHeadMap);
            LogUtil.d("[UploadUtil] uploadFileAndRetry i=" + i + ", fileUrl=" + fileUrl + ", path=" + path);
            if (!TextUtils.isEmpty(fileUrl)) {
                break;
            }
        }

        File file = new File(path);
        if (file.exists()) {
            //file.delete();
        }

        return fileUrl;
    }

    private static String getHost() {

        String host = RodHeader.getInstance().getUploadAddress();

        if (host == null || host.isEmpty()) {
            LogUtil.e("[UploadUtil] can't get rod host!");
            return host;
        }

        int index = host.indexOf("rod");

        if (index != -1) {
            host = host.substring(0, index);
        }

        if ((host.indexOf("http") != -1) && (host.indexOf("https") == -1)) {
            host.replaceFirst("http", "https");
        }

        LogUtil.d("[UploadUtil] host: " + host);

        return host;
    }

    private static String upload(String host, String uploadFile, Map<String, String> headMap) throws Exception {
        String storageId = "";

        String url = host + "rod/storage/upload";

        int index = 0;

        File file = new File(uploadFile);

        Gson gson = new Gson();

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {

            byte[] bs = new byte[BUFFER_LEN];

            if ((index = raf.read(bs)) != -1) {
                MultipartBody.Builder mBuilder = new MultipartBody.Builder();
                mBuilder.addFormDataPart("file", "file-" + file.getName(), RequestBody.create(MediaType.parse("application/json"), subarray(bs, 0, index)));
                mBuilder.addFormDataPart("fileId", file.getName());

                HttpClientResponse resp = HttpClientUtil.getInstance().sendHttpPost(url, headMap, mBuilder.build());

                if (resp.getStatus() != 200) {
                    LogUtil.e("[UploadUtil] upload, response code=" + resp.getStatus());
                    throw new Exception("upload error");
                }

                String respMes = resp.getMessage();
                if (respMes == null) {
                    throw new Exception("upload error");
                } else {
                    BaseResp res = gson.fromJson(respMes, BaseResp.class);
                    if (res.getCode() != 0) {
                        LogUtil.e("[UploadUtil] upload, post response=" + respMes);
                        throw new Exception("upload error");
                    }

                    JSONObject data = new JSONObject(gson.toJson(res.getData()));
                    if (data != null) {
                        storageId = data.optString("storageId");
                    } else {
                        storageId = "";
                    }
                }
            }
        }
        LogUtil.d("[UploadUtil] upload storageId=" + storageId + ", path=" + uploadFile);
        return storageId;
    }

    private static String uploadx(String host, String uploadFile, String fileMd5, Map<String, String> headMap) throws Exception {
        String storageId = "";

        String url = host + "rod/storage/uploadx";

        upload_init(host, uploadFile, fileMd5, headMap);

        int index = 0;

        File file = new File(uploadFile);

        Gson gson = new Gson();

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {

            byte[] bs = new byte[BUFFER_LEN];

            while ((index = raf.read(bs)) != -1) {

                UploadCheckResult ucrn = upload_check(host, fileMd5, headMap);

                MultipartBody.Builder mBuilder = new MultipartBody.Builder();
                mBuilder.addFormDataPart("file", "file-" + ucrn.getFilePos(), RequestBody.create(MediaType.parse("application/json"), subarray(bs, 0, index)));
                mBuilder.addFormDataPart("fileId", file.getName());
                mBuilder.addFormDataPart("filePos", ucrn.getFilePos() + "");
                mBuilder.addFormDataPart("fileMd5", fileMd5);

                HttpClientResponse resp = HttpClientUtil.getInstance().sendHttpPost(url, headMap, mBuilder.build());

                if (resp.getStatus() != 200) {
                    LogUtil.e("[UploadUtil] uploadx, response code=" + resp.getStatus());
                    throw new Exception("uploadx error");
                }

                String respMes = resp.getMessage();
                if (respMes == null) {
                    throw new Exception("uploadx error");
                } else {
                    BaseResp res = gson.fromJson(respMes, BaseResp.class);
                    if (res.getCode() != 0) {
                        LogUtil.e("[UploadUtil] uploadx, post response=" + respMes);
                        throw new Exception("uploadx error");
                    }

                    JSONObject data = new JSONObject(gson.toJson(res.getData()));
                    if (data != null) {
                        storageId = data.optString("storageId");
                    } else {
                        storageId = "";
                    }
                }
            }
        }
        LogUtil.d("[UploadUtil] uploadx storageId=" + storageId + ", path=" + uploadFile);
        return storageId;
    }

    private static void upload_init(String host, String file, String fileMd5, Map<String, String> headMap) throws Exception {

        String url = host + "rod/storage/uploadx/init?fileSize={0}&fileMd5={1}";

        File zip = new File(file);

        url = MessageFormat.format(url, zip.length() + "", fileMd5);

        LogUtil.d("[UploadUtil] upload_init, url=" + url);

        HttpClientResponse resp = HttpClientUtil.getInstance().sendHttpGet(url, headMap);

        if (resp.getStatus() != 200) {
            LogUtil.e("[UploadUtil] upload_init, response code=" + resp.getStatus());
            throw new Exception("upload_init error");
        }

        BaseResp res = new Gson().fromJson(resp.getMessage(), BaseResp.class);

        if (res.getCode() != 0) {
            LogUtil.e("[UploadUtil] upload_init, post response=" + resp.getMessage());
            throw new Exception("upload_init error");
        }

    }

    private static UploadCheckResult upload_check(String host, String fileMd5, Map<String, String> headMap) throws Exception {

        Gson gson = new Gson();

        String url = host + "rod/storage/uploadx/check?fileMd5={0}";

        url = MessageFormat.format(url, fileMd5);

        HttpClientResponse resp = HttpClientUtil.getInstance().sendHttpGet(url, headMap);

        if (resp.getStatus() != 200) {
            LogUtil.e("[UploadUtil] upload_check, response code=" + resp.getStatus());
            throw new Exception("upload_check error");
        }

        BaseResp res = gson.fromJson(resp.getMessage(), BaseResp.class);

        if (res.getCode() != 0) {
            LogUtil.e("[UploadUtil] upload_check, post response=" + resp.getMessage());
            throw new Exception("upload_check error");
        }

        return gson.fromJson(res.getData().toString(), UploadCheckResult.class);
    }

    private static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;

        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;

        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return new byte[0];
        }

        byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static Map<String, String> getHeadMap(String business) {

        Map<String, String> headMap = new HashMap<>();

        StringBuffer buffer = new StringBuffer();
        buffer.append("{");

        buffer.append("\"tenantId\":\"");
        buffer.append("cloudvending86test");//buffer.append(RodHeader.getInstance().getTenantId());
        buffer.append("\",");

        buffer.append("\"robotId\":\"");
        buffer.append("862851032100938");//buffer.append(RodHeader.getInstance().getRobotId());
        buffer.append("\",");

        buffer.append("\"robotType\":\"");
        buffer.append("vending");
        buffer.append("\",");

        buffer.append("\"userId\":\"");
        buffer.append("862851032100938");
        buffer.append("\",");

        buffer.append("\"business\":\"");
        buffer.append(business);
        buffer.append("\"");

        buffer.append("}");

        headMap.put("Storage-Agent", buffer.toString());

        LogUtil.d("[UploadUtil] headMap=" + headMap);

        return headMap;
    }

}
