package com.cloudminds.vending.controller;

import android.app.Activity;
import android.hardware.usb.UsbDevice;
import android.os.Environment;
import android.text.TextUtils;

import com.cloudminds.vending.Constants;
import com.cloudminds.vending.R;
import com.cloudminds.vending.net.ApiService;
import com.cloudminds.vending.net.RetrofitUtil;
import com.cloudminds.vending.roc.UploadUtil;
import com.cloudminds.vending.utils.FileUtil;
import com.cloudminds.vending.utils.LogUtil;
import com.cloudminds.vending.utils.ZipUtil;
import com.cloudminds.vending.view.UsbCameraView;
import com.cloudminds.vending.vo.BaseResult;
import com.cloudminds.vending.vo.EventStatus;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.midea.cabinet.sdk4data.MideaCabinetSDK;
import com.midea.sdk.algorithm.MideaNetworkControl;
import com.serenegiant.usb.DeviceFilter;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usb.USBMonitor.OnDeviceConnectListener;
import com.serenegiant.usb.USBMonitor.UsbControlBlock;
import com.serenegiant.widget.UVCCameraTextureView;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoorController {

    private static final long MONITOR_THRESHOLD = 300 * 1024 * 1024;
    private static final int VIDEO_CAMERA_PREVIEW_WIDTH = 1280;
    private static final int VIDEO_CAMERA_PREVIEW_HEIGHT = 720;
    private static final int FACE_CAMERA_PREVIEW_WIDTH = 640;
    private static final int FACE_CAMERA_PREVIEW_HEIGHT = 480;

    private String mEventId;
    private Map<String, Integer> mSceneTypeMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> mOpenWeightMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> mCloseWeightMap = new ConcurrentHashMap<>();
    private static volatile DoorController mInstance;

    private USBMonitor mUSBMonitor;
    private List<UsbDevice> mCommodityAndFaceCameraList = new ArrayList<>();
    private HashMap<Integer, UsbCameraView> mCommodityCameraViewMap = new HashMap<>();
    private HashMap<Integer, UsbCameraView> mFaceCameraViewMap = new HashMap<>();

    private OnDeviceConnectListener mOnDeviceConnectListener = new OnDeviceConnectListener() {
        @Override
        public void onAttach(UsbDevice device) {
            if (mCommodityAndFaceCameraList.contains(device)) {
                LogUtil.d("[OnDeviceConnectListener] onAttach: camera " + device.getDeviceId() + " request permission");
                mUSBMonitor.requestPermission(device);
            }
        }

        @Override
        public void onDettach(UsbDevice device) {

        }

        @Override
        public void onConnect(UsbDevice device, UsbControlBlock ctrlBlock, boolean createNew) {
            UsbCameraView commodityCameraView = mCommodityCameraViewMap.get(device.getDeviceId());
            UsbCameraView faceCameraView = mFaceCameraViewMap.get(device.getDeviceId());
            if (commodityCameraView != null) {
                if (commodityCameraView.onConnect(device, ctrlBlock, createNew)) {
                    LogUtil.d("[OnDeviceConnectListener] onConnect: deviceId = " + device.getDeviceId() + ", createNew = " + createNew);
                }
            }
            if (faceCameraView != null) {
                if (faceCameraView.onConnect(device, ctrlBlock, createNew)) {
                    LogUtil.d("[OnDeviceConnectListener] onConnect: deviceId = " + device.getDeviceId() + ", createNew = " + createNew);
                }
            }

            for (UsbDevice dev : mCommodityAndFaceCameraList) {
                if (!mUSBMonitor.hasPermission(dev)) {
                    LogUtil.d("[OnDeviceConnectListener] onConnect: camera " + dev.getDeviceId() + " request permission");
                    mUSBMonitor.requestPermission(dev);
                }
            }
        }

        @Override
        public void onDisconnect(UsbDevice device, UsbControlBlock ctrlBlock) {
            LogUtil.d("[OnDeviceConnectListener] onDisconnect: deviceId = " + device.getDeviceId());
        }

        @Override
        public void onCancel(UsbDevice device) {

        }
    };

    public void init(Activity activity, int viewId) {
        mUSBMonitor = new USBMonitor(activity, mOnDeviceConnectListener);
        mCommodityAndFaceCameraList = getCommodityAndFaceCameraDevices(activity, viewId);
    }

    public void registerUsbCamera(){
        mUSBMonitor.register();
    }

    public void unregisterUsbCamera(){
        mUSBMonitor.unregister();
    }

    public void destroyView() {
        for (UsbCameraView view : mCommodityCameraViewMap.values()) {
            view.release();
        }
        for (UsbCameraView view : mFaceCameraViewMap.values()) {
            view.release();
        }
        if (mUSBMonitor != null) {
            mUSBMonitor.destroy();
            mUSBMonitor = null;
        }
    }

    private List<UsbDevice> getCommodityAndFaceCameraDevices(Activity activity, int viewId) {
        List<DeviceFilter> filter = DeviceFilter.getDeviceFilters(activity, R.xml.device_filter);
        List<UsbDevice> allDevices = mUSBMonitor.getDeviceList();
        List<UsbDevice> result = new ArrayList<>();
        for (UsbDevice device : allDevices) {
            LogUtil.d("[MainActivity] usb device info: deviceId = " + device.getDeviceId() +
                    ", productName = " + device.getProductName() + ", manufacturerName = " + device.getManufacturerName() + ", deviceName = " + device.getDeviceName());
            if ("USB Camera".equals(device.getManufacturerName())) {
                result.add(device);
                UVCCameraTextureView uvcCameraTextureView = new UVCCameraTextureView(activity);
                UsbCameraView usbCameraView = new UsbCameraView(activity, uvcCameraTextureView, VIDEO_CAMERA_PREVIEW_WIDTH, VIDEO_CAMERA_PREVIEW_HEIGHT);
                mCommodityCameraViewMap.put(device.getDeviceId(), usbCameraView);
            } else if ("T1RGB".equals(device.getManufacturerName())) {
                result.add(device);
                UVCCameraTextureView uvcCameraTextureView = activity.findViewById(viewId);
                UsbCameraView usbCameraView = new UsbCameraView(activity, uvcCameraTextureView, FACE_CAMERA_PREVIEW_WIDTH, FACE_CAMERA_PREVIEW_HEIGHT);
                mFaceCameraViewMap.put(device.getDeviceId(), usbCameraView);
            }
        }
        LogUtil.w("mCommodityCameraViewMap:"+ mCommodityCameraViewMap+"////mFaceCameraViewMap:"+mFaceCameraViewMap);
        return result;
    }

    public void startPreview(boolean isFace) {
        if (isFace) {
            for (UsbCameraView view : mFaceCameraViewMap.values()) {
                view.startPreview(isFace);
            }
        } else {
            for (UsbCameraView view : mCommodityCameraViewMap.values()) {
                view.startPreview(isFace);
            }
        }
    }

    public void stopPreview(boolean isFace) {
        if (isFace) {
            for (UsbCameraView view : mFaceCameraViewMap.values()) {
                view.stopPreview();
            }
        } else {
            for (UsbCameraView view : mCommodityCameraViewMap.values()) {
                view.stopPreview();
            }
        }
    }

    public void startRecording(String eventId) {
        for (Integer deviceId : mCommodityCameraViewMap.keySet()) {
            UsbCameraView view = mCommodityCameraViewMap.get(deviceId);
            if (view != null) {
                view.startRecording(eventId, deviceId);
            }
        }
    }

    public void stopRecording() {
        for (UsbCameraView view : mCommodityCameraViewMap.values()) {
            view.stopRecording();
        }
    }

    public static DoorController getInstance() {
        if (mInstance == null) {
            synchronized (DoorController.class) {
                if (mInstance == null) {
                    mInstance = new DoorController();
                }
            }
        }
        return mInstance;
    }

    private DoorController() {
    }

    public interface OnUploadDoneListener {
        void onUploadDone();
    }

    public String getEventId() {
        return mEventId;
    }

    public void setData(String eventId, int sceneType, List<String> openWeightList) {
        mEventId = eventId;
        mSceneTypeMap.put(mEventId, sceneType);
        mOpenWeightMap.put(mEventId, new ArrayList<>(openWeightList));
    }

    public void openDoor(String eventId, int sceneType) {
        LogUtil.w("[DoorController] openDoor");
        List<String> openWeightList = MideaCabinetSDK.INSTANCE.getWeight(1);
        setData(eventId, sceneType, openWeightList);
        if (MideaNetworkControl.INSTANCE.checkLicense()) {
            LogUtil.i("[DoorController] License is valid");
            MideaCabinetSDK.INSTANCE.openLock("1", true, eventId);
            LogUtil.i("[DoorController] 门锁已开 eventId=" + eventId + ", openWeightList=" + openWeightList);
            startPreview(false);
            startRecording(eventId);
        } else {
            MideaNetworkControl.INSTANCE.getLicense(Constants.APP_ID, Constants.SECRET,
                    new MideaNetworkControl.LicenseCallback() {
                        @Override
                        public void onLicenseResult(boolean success, @NotNull String result) {
                            if (success) {
                                LogUtil.i("[DoorController] Succeed to get license: " + result);
                                MideaCabinetSDK.INSTANCE.openLock("1", true, eventId);
                                LogUtil.i("[DoorController] 门锁已开 eventId=" + eventId + ", openWeightList=" + openWeightList);
                                startPreview(false);
                                startRecording(eventId);
                            } else {
                                LogUtil.e("[DoorController] Failed to get license: " + result);
                            }
                        }
                    });
        }
//        if (openWeightList.contains("255")) {
//            reportVendingEvent(eventId, EventStatus.ACTION_RCU_DONE,
//                    EventStatus.CODE_WEIGHT_ERROR, "", "open_weight_error", "", openWeightList.toString());
//                    for (int i = 1; i < 5; i++) {
//                        if ("255".equals(openWeightList.get(i - 1))) {
//                            reportError("2680_" + i, "ERROR", "gravity sensor " + i);
//                        }
//                    }
//        }
    }

    public void closeDoor(OnUploadDoneListener listener) {
        stopRecording();
        stopPreview(false);
        new Thread(() -> {
            try {
                Thread.sleep(3000); //关门后三秒再去获取重力，避免因为抖动造成的重力称数据异常
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<String> closeWeightList = MideaCabinetSDK.INSTANCE.getWeight(1);
            if (closeWeightList.contains("255")) {
                reportVendingEvent(mEventId, EventStatus.ACTION_RCU_DONE,
                        EventStatus.CODE_WEIGHT_ERROR, "", "close_weight_error", "", closeWeightList.toString());
//                for (int i = 1; i < 5; i++) {
//                    if ("255".equals(closeWeightList.get(i - 1))) {
//                        VendingClient.getInstance(mContext).reportError("2680_" + i, "ERROR", "gravity sensor " + i);
//                    }
//                }
            }

            if (mEventId != null) {
                mCloseWeightMap.put(mEventId, new ArrayList<>(closeWeightList));
            }

            String videoData = "/sdcard/CloudVending/videoFile/"+mEventId;
            sendVideo(videoData, listener);

            LogUtil.i("[DoorController] 正在结算 eventId=" + mEventId + ", closeWeightList=" + closeWeightList);
        }).start();
        reportVendingEvent(mEventId, EventStatus.ACTION_DOOR_CLOSED, EventStatus.CODE_NORMAL_CLOSE, "", "", "", "");
    }

    public void openTimeout() {
        stopRecording();
        stopPreview(false);
//        try {
//            FileUtils.deleteDirectory(new File(MIDEA_PATH_GEN2 + "/" + mEventId));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        reportVendingEvent(mEventId, EventStatus.ACTION_DOOR_CLOSED, EventStatus.CODE_OPEN_TIMEOUT, "", "", "", "");
    }

    private static class FileNameComparator implements Comparator<File> {
        @Override
        public int compare(File file1, File file2) {
            String filename1 = file1.getName();
            String filename2 = file2.getName();
            long eventId1 = Long.parseLong(filename1.split("\\.")[0]);
            long eventId2 = Long.parseLong(filename2.split("\\.")[0]);
            return Long.compare(eventId1, eventId2);
        }
    }

    private void deleteOldZip(String path) throws IOException {
        while (FileUtils.sizeOfDirectory(new File(path)) > MONITOR_THRESHOLD) {
            List<File> files = new ArrayList<File>(
                    FileUtils.listFiles(new File(path), new String[]{"zip"}, false));
            if (files.size() > 1) {
                Collections.sort(files, new FileNameComparator());
                File oldestFile = files.get(0);
                FileUtils.forceDelete(oldestFile);
                LogUtil.i("[DoorController] delete old zip file: " + oldestFile.getName());
            } else {
                break;
            }
        }
    }

    public void sendVideo(String videoData, OnUploadDoneListener listener) {
        LogUtil.i("[DoorController] mSceneTypeMap:" + mSceneTypeMap + ", mOpenWeightMap:" + mOpenWeightMap + ", mCloseWeightMap:" + mCloseWeightMap);
        String eventId = videoData.substring(videoData.lastIndexOf("/") + 1);
        Integer value = mSceneTypeMap.get(eventId);
        int sceneType = value != null ? value : -1;
        reportVendingEvent(eventId, EventStatus.ACTION_RCU_DONE, EventStatus.CODE_VIDEO_TRANSCODING_DONE, "", "", "", "");
        new Thread(() -> {
            try {
                if (sceneType == 0) {
                    Map<String, String> params = new HashMap<>();
                    String zipVideoPath = videoData + ".zip";
                    if (ZipUtil.zipFile(videoData, zipVideoPath)) {
                        //FileUtils.deleteDirectory(new File(videoData));
                        String weightDetails = constructWeightJson(mOpenWeightMap.get(eventId), mCloseWeightMap.get(eventId));
                        String fileUrl = UploadUtil.uploadFileAndRetry(zipVideoPath, UploadUtil.getHeadMap("video"));
                        params.put("weightjson", weightDetails);
                        params.put("out_order_no", eventId);
                        params.put("checkupflag", FileUtil.getFileMD5(zipVideoPath));
                        params.put("device_index", "1");
                        params.put("fileUrl", fileUrl);
                        LogUtil.i("[DoorController] params: " + params);
                        //VendingClient.getInstance(mContext).commodityRecognizeDynamic("", eventId, params);
                        if (TextUtils.isEmpty(fileUrl)) {
                            LogUtil.e("[DoorController] Failed to get file url for " + eventId);
                            reportVendingEvent(eventId, EventStatus.ACTION_RCU_DONE,
                                    EventStatus.CODE_UPLOAD_FILE_ERROR, "", "", fileUrl, weightDetails);
                        } else {
                            listener.onUploadDone();
                            reportVendingEvent(eventId, EventStatus.ACTION_RCU_DONE,
                                    EventStatus.CODE_RCU_DONE, "", "", fileUrl, weightDetails);
                        }
                    } else {
                        LogUtil.e("[DoorController] Failed to zip file: " + videoData);
                    }
                } else if (sceneType == 1) {
                    FileUtils.deleteDirectory(new File(videoData));
                    LogUtil.i("[DoorController] 这是一次上货事件 eventId=" + eventId);
                }
                //deleteOldZip(MIDEA_PATH_GEN2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String constructWeightJson(List<String> list1, List<String> list2) {
        JsonArray jsonArrayBefore = new JsonArray();
        if (list1 != null) {
            for (String weight : list1) {
                jsonArrayBefore.add(weightUnitConversion(weight));
            }
        }

        JsonArray jsonArrayAfter = new JsonArray();
        if (list2 != null) {
            for (String weight : list2) {
                jsonArrayAfter.add(weightUnitConversion(weight));
            }
        }

        JsonObject weightJsonObject = new JsonObject();
        weightJsonObject.add("before", jsonArrayBefore);
        weightJsonObject.add("after", jsonArrayAfter);

        return weightJsonObject.toString();
    }

    private String weightUnitConversion(String weight) {
        return Double.toString(Double.parseDouble(weight) * 1000);
    }

    public void reportVendingEvent(String eventId, String action, int errCode, String errMsg, String errDetails,
                                   String videoUrl, String weightDetails) {
        EventStatus eventStatus = new EventStatus();
        eventStatus.setEventId(eventId);
        eventStatus.setAction(action);
        eventStatus.setErrCode(errCode);
        eventStatus.setErrMsg(errMsg);
        eventStatus.setErrDetails(errDetails);
        eventStatus.setVideoUrl(videoUrl);
        eventStatus.setWeightDetails(weightDetails);
        eventStatus.setReportTime(System.currentTimeMillis());
        LogUtil.i("[DoorController] report vending event: " + eventStatus);

        ApiService apiService = RetrofitUtil.getInstance().create(ApiService.class);
        apiService.reportVendingEvent(eventStatus).enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                if (response.code() == 200) {
                    LogUtil.i("[DoorController] onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {
                LogUtil.e("[DoorController] onFailure: ", t);
            }
        });
    }
}
