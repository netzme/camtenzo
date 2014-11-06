package com.katenzo.camtenzo;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;


public class CameraActivityInstrumentationTestCase2 extends ActivityInstrumentationTestCase2<CameraActivity> {
    CameraActivity cameraActivity;
    Button buttonShutter;
    public CameraActivityInstrumentationTestCase2() {
        super(CameraActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        cameraActivity = getActivity();

    }

    public void testSecondActivityGetIntent() {
        Instrumentation.ActivityMonitor monitor =
                getInstrumentation().
                        addMonitor(FilterImageActivity.class.getName(), null, false);

        buttonShutter = (Button) cameraActivity.findViewById(R.id.buttonShutter);

        TouchUtils.clickView(this, buttonShutter);


        FilterImageActivity startedActivity = (FilterImageActivity) monitor
                .waitForActivityWithTimeout(3000);

       assertNotNull(startedActivity);

        Intent triggeredIntent = startedActivity.getIntent();
        assertNotNull("Intent was null", triggeredIntent);


        Uri imageUri = triggeredIntent.getData();
        assertNotNull("Data File URI was null", imageUri);



    }
}
