package com.katenzo.camtenzo;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;


public class CameraDelegate {
    private Camera realCamera ;
    public CameraDelegate() {

    }
    public  void open() {
        realCamera = Camera.open();
    }

    public void stopPreview() {
        realCamera.stopPreview();
    }

    public void setPreviewDisplay(SurfaceHolder surfaceHolder) throws IOException {
        realCamera.setPreviewDisplay(surfaceHolder);
    }

    public void startPreview() {
        realCamera.startPreview();
    }



}
