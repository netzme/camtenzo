package com.katenzo.camtenzo;

import android.Manifest;
import android.test.AndroidTestCase;


public class CameraActivityTestCaseTest extends AndroidTestCase {
    private final static String PKG = "com.katenzo.camtenzo";
    private final static String ACTIVITY =  PKG + ".CameraActivity";
    private final static String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private final static String WRITE_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
        assertActivityRequiresPermission(PKG, ACTIVITY, CAMERA_PERMISSION);
    }

    public void testHaveWriteExternalStoragePermission() {
        assertActivityRequiresPermission(PKG, ACTIVITY, WRITE_EXTERNAL_STORAGE_PERMISSION);
    }


}
