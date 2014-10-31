package com.katenzo.camtenzo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.hardware.Camera.PictureCallback;
import static android.hardware.Camera.ShutterCallback;


public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    static final String INTENT_NAME = "com.katenzo.camtenzo.EXTRA_URI";
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


        surfaceViewCamera = (SurfaceView) findViewById(R.id.surfaceViewCamera);
        imageOverlay = (ImageView) findViewById(R.id.imageViewOverlay);

        cameraSurfaceHolder = surfaceViewCamera.getHolder();
        cameraSurfaceHolder.addCallback(this);

        buttonShutter = (Button) findViewById(R.id.buttonShutter);
        buttonShutter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                camera.takePicture(cameraShutterCallBack,cameraPictureCallback,cameraPictureCallbackJpeg);

            }
        });


    }


    ShutterCallback cameraShutterCallBack = new ShutterCallback() {
        @Override
        public void onShutter() {

        }
    };

    PictureCallback cameraPictureCallback = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    PictureCallback cameraPictureCallbackJpeg = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap newImage = getBitmapFromCamera(data, camera);
            File file = saveBitmapToFile(newImage);
            flushImage(newImage);
            setIntent(file);

        }
    };

    private Bitmap getBitmapFromCamera(byte[] data, Camera camera) {
        Bitmap cameraBitmap = BitmapFactory.decodeByteArray
                (data, 0, data.length);
        int   wid = cameraBitmap.getWidth();
        int  hgt = cameraBitmap.getHeight();

        //  Toast.makeText(getApplicationContext(), wid+""+hgt, Toast.LENGTH_SHORT).show();
        Bitmap newImage = Bitmap.createBitmap
                (wid, hgt, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newImage);

        canvas.drawBitmap(cameraBitmap, 0f, 0f, null);

       /* Drawable drawable = getResources().getDrawable
                (R.drawable.overlay);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
*/

        return newImage;

    }

    private File saveBitmapToFile(Bitmap newImage) {

        File storagePath = new File(Environment.
                getExternalStorageDirectory() + "/camtenzo/");
        storagePath.mkdirs();

        File myImage = new File(storagePath,
                Long.toString(System.currentTimeMillis()) + ".jpg");

        try
        {
            FileOutputStream out = new FileOutputStream(myImage);
            newImage.compress(Bitmap.CompressFormat.JPEG, 80, out);


            out.flush();
            out.close();
        }
        catch(FileNotFoundException e)
        {
            Log.d("In Saving File", e + "");
        }
        catch(IOException e)
        {
            Log.d("In Saving File", e + "");
        }

        camera.startPreview();
        return myImage;
    }

    private void flushImage(Bitmap newImage) {
        newImage.recycle();
        newImage = null;
    }

    private void setIntent(File myImage) {

        Intent intent = new Intent(this,FilterImageActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(INTENT_NAME,getUriFileName(myImage));
        intent.setDataAndType(getUriFileName(myImage), "image/*");
        startActivity(intent);

    }

    private Uri getUriFileName(File myImage) {
        return Uri.parse("file://" + myImage.getAbsolutePath());
    }


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
        cameraOpen();


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
        setCameraDisplayOrientation(this,Camera.CameraInfo.CAMERA_FACING_FRONT,camera);
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
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

    @Override
    protected void onResume() {
        super.onResume();
        cameraReopen();
    }

    private void cameraOpen() {
        try {
            camera = Camera.open();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void cameraRelease() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void cameraReopen() {
        if (camera == null) {
            cameraOpen();
        }
    }
}
