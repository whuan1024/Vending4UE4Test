package com.tommy.vending4ue4test;

import android.app.Application;
import android.content.Context;

import com.cloudminds.vending.controller.DoorController;
import com.cloudminds.vending.utils.FileUtil;
import com.cloudminds.vending.utils.LogUtil;

public class ThisApp extends Application {

    private DoorController mController;

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    private void init(Context context) {
        new LogUtil().configure();
        LogUtil.i("[ThisApp] OnCreate");
        FileUtil.deleteFile(FileUtil.RESTART_APP_FLAG);
        FileUtil.setRodHeader(context);
    }
}
