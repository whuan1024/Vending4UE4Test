package com.cloudminds.vending.view;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.usb.UsbDevice;
import android.view.Surface;

import com.cloudminds.vending.utils.LogUtil;
import com.serenegiant.usb.USBMonitor.UsbControlBlock;
import com.serenegiant.usb.UVCCamera;
import com.serenegiant.usbcameracommon.UVCCameraHandler;
import com.serenegiant.widget.CameraViewInterface;

import org.webrtc.GlUtils;

import java.util.List;

public class UsbCameraView {

    private static final int CAMERA_PREVIEW_WIDTH = 1280;
    private static final int CAMERA_PREVIEW_HEIGHT = 720;
    private static final float BANDWIDTH_FACTORS = 0.5f;

    private UVCCameraHandler mHandler;
    private UsbDevice mUsbDevice;
    private UsbControlBlock mUsbControlBlock;
    private SurfaceTexture mSurfaceTexture;

    public interface OnImageSavedListener {
        void onImageSaved(List<String> imageList);
    }

    public UsbCameraView(Activity activityParent, CameraViewInterface viewInterface) {
        viewInterface.setAspectRatio(UVCCamera.DEFAULT_PREVIEW_WIDTH / (float) UVCCamera.DEFAULT_PREVIEW_HEIGHT);
        mHandler = UVCCameraHandler.createHandler(activityParent, viewInterface,
                CAMERA_PREVIEW_WIDTH, CAMERA_PREVIEW_HEIGHT, BANDWIDTH_FACTORS);
    }

    public boolean onConnect(UsbDevice device, UsbControlBlock ctrlBlock, boolean createNew) {
        if (mHandler.isOpened()) {
            LogUtil.e("[UsbCameraView] onConnect: camera " + device.getDeviceId() + " is already opened");
            return false;
        } else {
            mUsbDevice = device;
            mUsbControlBlock = ctrlBlock;
            return true;
        }
    }

    public void startPreview() {
        if (mHandler != null && mUsbControlBlock != null) {
            LogUtil.d("[UsbCameraView] start preview: deviceId = " + mUsbDevice.getDeviceId());
            mHandler.open(mUsbControlBlock);

            int oesTextureId = GlUtils.generateTexture(36198);
            mSurfaceTexture = new SurfaceTexture(oesTextureId);
            mHandler.startPreview(new Surface(mSurfaceTexture));
        } else {
            LogUtil.e("[UsbCameraView] start preview: mHandler = " + mHandler + ", mUsbControlBlock = " + mUsbControlBlock);
        }
    }

    public void stopPreview() {
        if (mHandler != null) {
            mHandler.close();
        }
        mSurfaceTexture = null;
    }

//    public void captureStill(String eventId, int cameraNumber, OnImageSavedListener listener) {
//        String imagePath = FileUtil.VENDING_IMAGE_PATH + eventId;
//        String[] imageSuffix = new String[]{"jpeg"};
//        mHandler.captureStill(mUsbDevice.getDeviceId(),
//                new AbstractUVCCameraHandler.OnCaptureListener() {
//                    @Override
//                    public void onCaptureResult(int deviceId, byte[] data, int width, int height) {
//                        if (FileUtil.saveYuv2Jpeg(eventId, String.valueOf(deviceId), data, width, height)) {
//                            List<String> imageList = FileUtil.listFiles(imagePath, imageSuffix, false);
//                            if (imageList.size() == cameraNumber) {
//                                if (listener != null) {
//                                    listener.onImageSaved(imageList);
//                                }
//                            }
//                        } else {
//                            LogUtil.e("[UsbCameraView] Failed to save image: eventId = " + eventId + ", deviceId = " + deviceId);
//                        }
//                    }
//                });
//    }

    public void startRecording(String eventId, int deviceId) {
        if (!mHandler.isRecording()) {
            mHandler.startRecording(eventId, deviceId);
        }
    }

    public void stopRecording() {
        mHandler.stopRecording();
    }

    public void release() {
        if (mHandler != null) {
            mHandler.release();
            mHandler = null;
        }
        mSurfaceTexture = null;
        mUsbDevice = null;
        mUsbControlBlock = null;
    }
}
