package com.tommy.vending4ue4test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cloudminds.vending.controller.DoorController;
import com.cloudminds.vending.utils.LogUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "wanghuan";

    private DoorController.SDKListener mSdkListener = new DoorController.SDKListener() {
        @Override
        public void onOpenSuccess() {
            LogUtil.w("[MainActivity] onOpenSuccess");
        }

        @Override
        public void onOpenTimeOut(String eventId) {
            LogUtil.w("[MainActivity] onOpenTimeOut: eventId = " + eventId);
        }

        @Override
        public void onCloseSuccess(String eventId) {
            LogUtil.w("[MainActivity] onCloseSuccess: eventId = " + eventId);
            DoorController.getInstance().closeDoor(new DoorController.OnUploadDoneListener() {
                @Override
                public void onUploadDone(String eventId, String paramsJson) {
                    LogUtil.w("[MainActivity] onUploadDone: eventId = " + eventId + ", paramsJson = " + paramsJson);
                }
            });
        }

        @Override
        public void onCloseTimeOut() {
            LogUtil.w("[MainActivity] onCloseTimeOut");
        }

        @Override
        public void onOpenInnerLock() {
            LogUtil.w("[MainActivity] onOpenInnerLock");
        }

        @Override
        public void onTransSuccess(String dir) {
            LogUtil.w("[MainActivity] onTransSuccess: dir = " + dir);
            DoorController.getInstance().sendVideo(dir, new DoorController.OnUploadDoneListener() {
                @Override
                public void onUploadDone(String eventId, String paramsJson) {
                    LogUtil.w("[MainActivity] onUploadDone: eventId = " + eventId + ", paramsJson = " + paramsJson);
                }
            });
        }

        @Override
        public void onError() {
            LogUtil.w("[MainActivity] onError");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DoorController.getInstance().initSdk(this, mSdkListener);

        Button Button1 = findViewById(R.id.button1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventId = String.valueOf(System.currentTimeMillis());
                DoorController.getInstance().openDoor(eventId, 0);
            }
        });

        Button Button2 = findViewById(R.id.button2);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoorController.getInstance().closeDoor(new DoorController.OnUploadDoneListener() {
                    @Override
                    public void onUploadDone(String eventId, String paramsJson) {

                    }
                });
            }
        });

        Button Button3 = findViewById(R.id.button3);
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoorController.getInstance().sendVideo("/sdcard/CloudVending/videoFile/666", new DoorController.OnUploadDoneListener() {
                    @Override
                    public void onUploadDone(String eventId, String paramsJson) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}