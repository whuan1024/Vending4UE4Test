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
import com.cloudminds.vending.vo.PictureVisionRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.midea.cabinet.sdk4data.MideaCabinetSDK;
import com.midea.cabinet.sdk4data.bean.CabinetGridDataBean;
import com.midea.cabinet.sdk4data.bean.ErrorMsgBean;
import com.midea.sdk.algorithm.MideaNetworkControl;
import com.serenegiant.usb.DeviceFilter;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usb.USBMonitor.OnDeviceConnectListener;
import com.serenegiant.usb.USBMonitor.UsbControlBlock;
import com.serenegiant.widget.UVCCameraTextureView;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String MIDEA_PATH = Environment.getExternalStorageDirectory().getPath() + "/mideaSDK";
    private static final String MIDEA_PATH_GEN2 = Environment.getExternalStorageDirectory().getPath() + "/midea/capture";
    private static final long MONITOR_THRESHOLD = 300 * 1024 * 1024;

    private String mEventId;
    private Map<String, Integer> mSceneTypeMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> mOpenWeightMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> mCloseWeightMap = new ConcurrentHashMap<>();
    private static volatile DoorController mInstance;

    private USBMonitor mUSBMonitor;
    private List<UsbDevice> mCommodityCameraList = new ArrayList<>();
    private HashMap<Integer, UsbCameraView> mCameraViewMap = new HashMap<>();

    private OnDeviceConnectListener mOnDeviceConnectListener = new OnDeviceConnectListener() {
        @Override
        public void onAttach(UsbDevice device) {
            if (mCommodityCameraList.contains(device)) {
                LogUtil.d("[OnDeviceConnectListener] onAttach: camera " + device.getDeviceId() + " request permission");
                mUSBMonitor.requestPermission(device);
            }
        }

        @Override
        public void onDettach(UsbDevice device) {

        }

        @Override
        public void onConnect(UsbDevice device, UsbControlBlock ctrlBlock, boolean createNew) {
            UsbCameraView usbCameraView = mCameraViewMap.get(device.getDeviceId());
            if (usbCameraView != null) {
                if (usbCameraView.onConnect(device, ctrlBlock, createNew)) {
                    LogUtil.d("[OnDeviceConnectListener] onConnect: deviceId = " + device.getDeviceId() + ", createNew = " + createNew);
                }
            }

            for (UsbDevice dev : mCommodityCameraList) {
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

    public void init(Activity activity) {
        mUSBMonitor = new USBMonitor(activity, mOnDeviceConnectListener);
        mCommodityCameraList = getCommodityCameraDevices(activity);
    }

    public void initSdk(Activity activity, SDKListener listener) {
        MideaCabinetSDK.INSTANCE.init(activity, Constants.APP_ID, Constants.SECRET, Constants.TYPE, Constants.UART_PATH, 0, "1",
                FileUtil.getCabinetFloorCount(), new MideaCabinetSDK.SDKInitCallBack() {
                    @Override
                    public void initFailed(@NotNull String msg) {
                        LogUtil.e("[MideaCabinetSDK] initFailed: " + msg);
                    }

                    @Override
                    public void initSuccess() {
                        LogUtil.i("[MideaCabinetSDK] initSuccess: Midea SDK Version = " + MideaCabinetSDK.INSTANCE.getSDKVersion());
                        MideaCabinetSDK.INSTANCE.setAutoCapture(false);
                        MideaCabinetSDK.INSTANCE.getBaseCabinetControl().setDoorCount(1);
                        //MideaCabinetSDK.INSTANCE.getBaseCabinetControl().setDeviceCount(4);
                        MideaCabinetSDK.INSTANCE.setCabinetSDKListener(new MideaCabinetSDK.SDKListener() {
                            @Override
                            public void onOpenSuccess() {
                                //开门成功
                                LogUtil.i("[MideaCabinetSDK] onOpenSuccess");
                                listener.onOpenSuccess();
                                //mController.openDoor();
                                //mClient.reportStatus("door_state", 1);
                            }

                            @Override
                            public void onOpenTimeOut() {
                                //开门超时
                                LogUtil.i("[MideaCabinetSDK] onOpenTimeOut");
                                listener.onOpenTimeOut(mEventId);
                                openTimeout();
                            }

                            @Override
                            public void onCloseSuccess() {
                                //关门成功
                                LogUtil.i("[MideaCabinetSDK] onCloseSuccess");
                                listener.onCloseSuccess(mEventId);
                                //mClient.reportStatus("door_state", 0);
                            }

                            @Override
                            public void onCloseTimeOut() {
                                //关门超时
                                LogUtil.i("[MideaCabinetSDK] onCloseTimeOut");
                                listener.onCloseTimeOut();
                            }

                            @Override
                            public void onOpenInnerLock(@NotNull CabinetGridDataBean cabinetGridDataBean) {
                                //开小门成功
                                LogUtil.i("[MideaCabinetSDK] onOpenInnerLock");
                                listener.onOpenInnerLock();
                            }

                            @Override
                            public void onTransSuccess(@NotNull String dir) {
                                //视频转码成功
                                LogUtil.i("[MideaCabinetSDK] onTransSuccess: dir = " + dir);
                                //mController.sendVideo(dir);
                                listener.onTransSuccess();
                            }

                            @Override
                            public void onError(@NotNull ErrorMsgBean errorMsgBean) {
                                //异常监听
                                LogUtil.e("[MideaCabinetSDK] onError: errorMsgBean = " + errorMsgBean);
                                if ("2643".equals(errorMsgBean.getCode()) || "2644".equals(errorMsgBean.getCode())) {
                                    reportVendingEvent(mEventId, EventStatus.ACTION_RCU_DONE,
                                            EventStatus.CODE_CAMERA_ERROR, errorMsgBean.getMsg(), errorMsgBean.getExtra(), "", "");
                                }
                                if ("2646".equals(errorMsgBean.getCode())) {
                                    reportVendingEvent(mEventId, EventStatus.ACTION_RCU_DONE,
                                            EventStatus.CODE_VIDEO_TRANSCODING_ERROR, errorMsgBean.getMsg(), errorMsgBean.getExtra(), "", "");
                                }
                                listener.onError();
                                //mClient.reportError(errorMsgBean.getCode(), errorMsgBean.getMsg(), errorMsgBean.getExtra());
                            }
                        });
                    }
                });
    }

    public void registerUsbCamera(){
        mUSBMonitor.register();
    }

    public void unregisterUsbCamera(){
        mUSBMonitor.unregister();
    }

    public void destroyView() {
        for (UsbCameraView view : mCameraViewMap.values()) {
            view.release();
        }
        if (mUSBMonitor != null) {
            mUSBMonitor.destroy();
            mUSBMonitor = null;
        }
    }

    private List<UsbDevice> getCommodityCameraDevices(Activity activity) {
        List<DeviceFilter> filter = DeviceFilter.getDeviceFilters(activity, R.xml.device_filter);
        List<UsbDevice> allDevices = mUSBMonitor.getDeviceList();
        List<UsbDevice> result = new ArrayList<>();
        for (UsbDevice device : allDevices) {
            LogUtil.d("[MainActivity] usb device info: deviceId = " + device.getDeviceId() +
                    ", productName = " + device.getProductName());
            if ("USB Camera".equals(device.getManufacturerName())) {
                result.add(device);
                UVCCameraTextureView uvcCameraTextureView = new UVCCameraTextureView(activity);
                UsbCameraView usbCameraView = new UsbCameraView(activity, uvcCameraTextureView);
                mCameraViewMap.put(device.getDeviceId(), usbCameraView);
            }
        }
        LogUtil.w("mCameraViewMap:"+mCameraViewMap);
        return result;
    }

    public void startPreview() {
        for (UsbCameraView view : mCameraViewMap.values()) {
            view.startPreview();
        }
    }

    public void stopPreview() {
        for (UsbCameraView view : mCameraViewMap.values()) {
            view.stopPreview();
        }
    }

    public void startRecording(String eventId) {
        for (Integer deviceId : mCameraViewMap.keySet()) {
            UsbCameraView view = mCameraViewMap.get(deviceId);
            if (view != null) {
                view.startRecording(eventId, deviceId);
            }
        }
    }

    public void stopRecording() {
        for (UsbCameraView view : mCameraViewMap.values()) {
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
        void onUploadDone(String eventId, String paramsJson);
    }

    public interface SDKListener {
        void onOpenSuccess();
        void onOpenTimeOut(String eventId);
        void onCloseSuccess(String eventId);
        void onCloseTimeOut();
        void onOpenInnerLock();
        void onTransSuccess();
        void onError();
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
            startPreview();
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
                                startPreview();
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
        stopPreview();
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
        stopPreview();
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
                            listener.onUploadDone(eventId, constructVisionRequest(eventId, params));
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

    private String constructVisionRequest(String eventId, Map<String, String> params) {
        String result = "";
        try {
            PictureVisionRequest pictureVisionRequest = new PictureVisionRequest();
            pictureVisionRequest.setExtraBody(eventId);
            pictureVisionRequest.setExtraType("isVending");
            pictureVisionRequest.setRecognizeType(PictureVisionRequest.RecognizeType.VENDING_DYNAMIC);
            pictureVisionRequest.setParams(params);
            String pictureVisionRequestStr = new Gson().toJson(pictureVisionRequest);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("videoData", "");
            jsonObject.put("videoVisionRequestData", pictureVisionRequestStr);
            result = jsonObject.toString();
            LogUtil.i("[DoorController] VisionRequest: " + result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
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
