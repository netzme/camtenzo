package com.katenzo.camtenzo;

import android.app.Instrumentation;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.Suppress;
import android.text.Layout;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class CameraActivityTest extends ActivityInstrumentationTestCase2<CameraActivity >{

    CameraActivity cameraActivity;
    Button buttonShutter;
    ImageView imageOverlay;




    public CameraActivityTest() {
        super(CameraActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        cameraActivity  = getActivity();
        assertNotNull(cameraActivity);
        buttonShutter = (Button) cameraActivity.findViewById(R.id.buttonShutter);
        assertNotNull(buttonShutter);
        cameraActivity.surfaceViewCamera = (SurfaceView) cameraActivity.findViewById(R.id.surfaceViewCamera) ;
        assertNotNull(cameraActivity.surfaceViewCamera);
        imageOverlay = (ImageView) cameraActivity.findViewById(R.id.imageViewOverlay);
        assertNotNull(imageOverlay);


    }

    public void testIsButtonShutterViewOnScreen() {
        final View origin = getDecorViewActivity();
        ViewAsserts.assertOnScreen(origin, buttonShutter);
    }

    public void testIsSurfaceViewCameraOnScreen() {
        final View origin = getDecorViewActivity();
        ViewAsserts.assertOnScreen(origin, cameraActivity.surfaceViewCamera);
    }

    public void testIsImageOverlayOnScreen() {
        final View origin = getDecorViewActivity();
        ViewAsserts.assertOnScreen(origin, imageOverlay);
    }

   @Suppress
    public void testButtonShutterJustification() {
        final int expected = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        assertEquals(expected, buttonShutter.getGravity());
    }

    public void testWindowFormatTranslucent() {
        WindowManager.LayoutParams lp =  cameraActivity.getWindow().getAttributes();
        assertEquals(PixelFormat.TRANSLUCENT,lp.format);
    }

    public void testWindowActivityNoTitle() {

    }

    public void testWindowActivityFullScreen() {

    }

    public void testSurfaceCreatedOpenCamera() {
        cameraActivity.surfaceCreated(cameraActivity.cameraSurfaceHolder);
        boolean cameraOpen = isCameraUsebyApp();
        assertTrue(cameraOpen);
    }

    public void surfaceChangedShowPreview() {
        cameraActivity.surfaceChanged(cameraActivity.cameraSurfaceHolder,1,600,600);

    }

    @Suppress
    public void testCameraDisplayOrientation90IfActivityNotLandscapeMode() {
        int orientation = cameraActivity.getResources().getConfiguration().orientation;
    }


    public void testSurfaceDestroyCameraRelease() {
        cameraActivity.surfaceDestroyed(cameraActivity.cameraSurfaceHolder);
        assertNull(cameraActivity.camera);
    }

    public void testSurfaceDestroyPreviewIsFalse() {
        cameraActivity.surfaceDestroyed(cameraActivity.cameraSurfaceHolder);
        assertEquals(false,cameraActivity.previewing);
    }

    @UiThreadTest
    public void testOnPauseCameraCameraReleaseAfterSurfaceCreated() {
        Instrumentation mInstr = this.getInstrumentation();
        cameraActivity.surfaceCreated(cameraActivity.cameraSurfaceHolder);
        mInstr.callActivityOnPause(cameraActivity);
        assertNull(cameraActivity.camera);
    }


    private  boolean isCameraUsebyApp() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            return true;
        } finally {
            if (camera != null) camera.release();
        }
        return false;
    }


    private View getDecorViewActivity() {
        return cameraActivity.getWindow().getDecorView();
    }


    public void testImageOverlay_layout() {

        final ViewGroup.LayoutParams layoutParams =
                imageOverlay.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.MATCH_PARENT);

    }

    public void testImageOverlayScaleType() {
        ImageView.ScaleType scaleTypeExpected = ImageView.ScaleType.FIT_XY;
        ImageView.ScaleType scaleType = imageOverlay.getScaleType();
        assertEquals(scaleTypeExpected, scaleType);
    }

    public void testSurfaceView_layout() {
        final ViewGroup.LayoutParams layoutParams =
                cameraActivity.surfaceViewCamera.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.MATCH_PARENT);
    }

    public void testSurfaceViewAndImageOverlayAlignment() {
        ViewAsserts.assertRightAligned(cameraActivity.surfaceViewCamera, imageOverlay);
        ViewAsserts.assertLeftAligned(cameraActivity.surfaceViewCamera, imageOverlay);
        ViewAsserts.assertBottomAligned(cameraActivity.surfaceViewCamera, imageOverlay);
        ViewAsserts.assertTopAligned(cameraActivity.surfaceViewCamera, imageOverlay);
    }

    public void testButtonShutterHaveCenterAlignment() {
        final ViewGroup.LayoutParams layoutParams = buttonShutter.getLayoutParams();
        Layout buttonShutterLayout = buttonShutter.getLayout();

        Layout.Alignment expectedAlignment = Layout.Alignment.ALIGN_CENTER;
        Layout.Alignment actualAlignment = buttonShutterLayout.getAlignment();
        assertEquals(expectedAlignment, actualAlignment);
    }

    @UiThreadTest
    public void testButtonShutterClickTakeAPicture() {
        /* Todo */
        buttonShutter.requestFocus();
        buttonShutter.performClick();


    }


}