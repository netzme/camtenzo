package com.katenzo.camtenzo;

import android.content.Intent;
import android.graphics.Camera;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.Suppress;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivityUnitTestCase extends ActivityUnitTestCase<CameraActivity> {

    CameraActivity cameraActivity;
    Button buttonShutter;


    public CameraActivityUnitTestCase() {
        super(CameraActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        cameraActivity = getActivity();
        buttonShutter = (Button) cameraActivity.findViewById(R.id.buttonShutter);
        assertNotNull(buttonShutter);
        cameraActivity.surfaceViewCamera = (SurfaceView) cameraActivity.findViewById(R.id.surfaceViewCamera) ;
        assertNotNull(cameraActivity.surfaceViewCamera);
        cameraActivity.camera = android.hardware.Camera.open();

    }

    @UiThreadTest
    @Suppress
    public void testButtonShutterClickTakeAPicture() {
        /*
        * Test ini mengecek intent yang digenerate oleh PictureCallBackJpg
        * Di karenakan callback prosesnya asynchronous tidak bisa dilakuakun dengan dengan performClick
        * Perlu Pendekatan lain untuk mengecek Intent
        *
        * */

        buttonShutter.requestFocus();
        buttonShutter.performClick();
        Intent triggeredIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", triggeredIntent);



    }


}

