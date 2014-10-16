package com.katenzo.camtenzo;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    SurfaceView surfaceViewCamera;
    SurfaceHolder cameraSurfaceHolder;
    Camera camera = null;
    boolean previewing = false;
    private Button buttonShutter;
    private ImageView imageOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_camera);

        buttonShutter = (Button) findViewById(R.id.buttonShutter);
        surfaceViewCamera = (SurfaceView) findViewById(R.id.surfaceViewCamera);
        imageOverlay = (ImageView) findViewById(R.id.imageViewOverlay);

        cameraSurfaceHolder = surfaceViewCamera.getHolder();
        cameraSurfaceHolder.addCallback(this);

        buttonShutter.setOnClickListener(buttonShutterClick);


    }

    View.OnClickListener buttonShutterClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            camera = Camera.open();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (previewing) {
            camera.stopPreview();
            previewing = false;
        }

        try {
            setupCameraDisplayOrientation();
            camera.setPreviewDisplay(cameraSurfaceHolder);
            camera.startPreview();
            previewing = true;

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    private void setupCameraDisplayOrientation() {
        if ( getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(90);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraRelease();
        previewing = false;

    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraRelease();
    }

    private void cameraRelease() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
