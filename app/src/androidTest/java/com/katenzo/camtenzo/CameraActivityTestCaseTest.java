package com.katenzo.camtenzo;

import android.Manifest;
import android.content.Intent;
import android.test.AndroidTestCase;

/**
 * Created by katenzo on 10/7/14.
 */
public class CameraActivityTestCaseTest extends AndroidTestCase {
    private final static String PKG = "com.katenzo.camtenzo";
    private final static String ACTIVITY =  PKG + ".CameraActivity";
    private final static String PERMISSION = Manifest.permission.CAMERA;

    public CameraActivityTestCaseTest() {
        this("CameraActivityTestCaseTest");
    }

    public CameraActivityTestCaseTest(String name) {
        super();
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testHaveCameraPermission() {
            assertActivityRequiresPermission(PKG, ACTIVITY, PERMISSION);
    }


}
