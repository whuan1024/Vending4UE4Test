package com.tommy.vending4ue4test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cloudminds.vending.controller.DoorController;
import com.cloudminds.vending.utils.LogUtil;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "wanghuan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DoorController.getInstance().init(this);

        Button Button1 = findViewById(R.id.button1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoorController.getInstance().openDoor("1234", 0);
            }
        });

        Button Button2 = findViewById(R.id.button2);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoorController.getInstance().closeDoor(new DoorController.OnUploadDoneListener() {
                    @Override
                    public void onUploadDone() {

                    }
                });
            }
        });

        Button Button3 = findViewById(R.id.button3);
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoorController.getInstance().sendVideo("path", new DoorController.OnUploadDoneListener() {
                    @Override
                    public void onUploadDone() {

                    }
                });
            }
        });

        Button Button4 = findViewById(R.id.button4);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.w("[MainActivity] 开始录像");
                DoorController.getInstance().startPreview();
                DoorController.getInstance().startRecording("1235");
            }
        });

        findViewById(R.id.button5).setOnClickListener(v -> {
            LogUtil.w("[MainActivity] 结束录像");
            DoorController.getInstance().stopRecording();
            DoorController.getInstance().stopPreview();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DoorController.getInstance().registerUsbCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        DoorController.getInstance().unregisterUsbCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DoorController.getInstance().destroyView();
    }
}