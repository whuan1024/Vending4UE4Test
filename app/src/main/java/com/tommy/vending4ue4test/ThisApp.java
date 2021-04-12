package com.tommy.vending4ue4test;

import android.app.Application;
import android.content.Context;

import com.cloudminds.vending.Constants;
import com.cloudminds.vending.controller.DoorController;
import com.cloudminds.vending.utils.FileUtil;
import com.cloudminds.vending.utils.LogUtil;
import com.cloudminds.vending.vo.EventStatus;
import com.midea.cabinet.sdk4data.MideaCabinetSDK;
import com.midea.cabinet.sdk4data.bean.CabinetGridDataBean;
import com.midea.cabinet.sdk4data.bean.ErrorMsgBean;

import org.jetbrains.annotations.NotNull;

public class ThisApp extends Application {

    private DoorController mController;

    @Override
    public void onCreate() {
        super.onCreate();
        //Options.getInstance().init(this);
        init(this);
    }

    private void init(Context context) {
        new LogUtil().configure();
        LogUtil.i("[ThisApp] OnCreate");
        mController = DoorController.getInstance();
        FileUtil.deleteFile(FileUtil.RESTART_APP_FLAG);
        FileUtil.setRodHeader(context);
        MideaCabinetSDK.INSTANCE.init(context, Constants.APP_ID, Constants.SECRET, Constants.TYPE, Constants.UART_PATH, 0, "1",
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
                                //mController.openDoor();
                                //mClient.reportStatus("door_state", 1);
                            }

                            @Override
                            public void onOpenTimeOut() {
                                //开门超时
                                LogUtil.i("[MideaCabinetSDK] onOpenTimeOut");
                                mController.openTimeout();
                            }

                            @Override
                            public void onCloseSuccess() {
                                //关门成功
                                LogUtil.i("[MideaCabinetSDK] onCloseSuccess");
                                mController.closeDoor(new DoorController.OnUploadDoneListener() {
                                    @Override
                                    public void onUploadDone() {

                                    }
                                });
                                //mClient.reportStatus("door_state", 0);
                            }

                            @Override
                            public void onCloseTimeOut() {
                                //关门超时
                                LogUtil.i("[MideaCabinetSDK] onCloseTimeOut");
                            }

                            @Override
                            public void onOpenInnerLock(@NotNull CabinetGridDataBean cabinetGridDataBean) {
                                //开小门成功
                                LogUtil.i("[MideaCabinetSDK] onOpenInnerLock");
                            }

                            @Override
                            public void onTransSuccess(@NotNull String dir) {
                                //视频转码成功
                                LogUtil.i("[MideaCabinetSDK] onTransSuccess: dir = " + dir);
                                //mController.sendVideo(dir);
                            }

                            @Override
                            public void onError(@NotNull ErrorMsgBean errorMsgBean) {
                                //异常监听
                                LogUtil.e("[MideaCabinetSDK] onError: errorMsgBean = " + errorMsgBean);
                                if ("2643".equals(errorMsgBean.getCode()) || "2644".equals(errorMsgBean.getCode())) {
                                    mController.reportVendingEvent(mController.getEventId(), EventStatus.ACTION_RCU_DONE,
                                            EventStatus.CODE_CAMERA_ERROR, errorMsgBean.getMsg(), errorMsgBean.getExtra(), "", "");
                                }
                                if ("2646".equals(errorMsgBean.getCode())) {
                                    mController.reportVendingEvent(mController.getEventId(), EventStatus.ACTION_RCU_DONE,
                                            EventStatus.CODE_VIDEO_TRANSCODING_ERROR, errorMsgBean.getMsg(), errorMsgBean.getExtra(), "", "");
                                }
                                //mClient.reportError(errorMsgBean.getCode(), errorMsgBean.getMsg(), errorMsgBean.getExtra());
                            }
                        });
                    }
                });
    }
}
