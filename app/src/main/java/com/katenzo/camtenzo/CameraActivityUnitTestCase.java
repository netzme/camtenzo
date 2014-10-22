package com.katenzo.camtenzo;

import android.content.Intent;
import android.graphics.Camera;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
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
    public void testButtonShutterClickTakeAPicture() {
        /* Todo */

        buttonShutter.requestFocus();
        buttonShutter.performClick();
        Intent triggeredIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", triggeredIntent);



    }


}

