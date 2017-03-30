package com.example.vaibhavshah.seapp2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BarcodeActivity extends AppCompatActivity {

    SurfaceView cameraView;
    TextView barcodeInfo;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    Camera mCamera;
    private int camId=1;
    String cust;
    //Intent intent;
    //ImageView myCamera
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);



        //step 1
        cameraView = (SurfaceView)findViewById(R.id.camera_view);
        barcodeInfo = (TextView)findViewById(R.id.code_info);


        //step 2
        //fetch stream of images from camera and display it in surface view
        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();


        //step 3
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());//to start drawing a preview of frame
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();//stop camera source
            }
        });



        //step4
        //what the barcode should do when it detects a QR_CODE

        //Inside the receiveDetections method, get the SparseArray of Barcode objects by calling the getDetectedItems method of the Detector.Detections
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                //show ORCODE display value in the textview.
                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            // barcodeInfo.setText(    // Update the TextView
                            if((barcodes.valueAt(0).displayValue).equals("Table1")){

                                //Toast.makeText(getApplicationContext(),"Hello barcode",Toast.LENGTH_SHORT).show();;
                                //Intent intent=new Intent(BarcodeActivity.this,SecondActivity.class);
                                cust="Table No.1";
                                startActivity(new Intent(BarcodeActivity.this,OrderList.class).putExtra("key1",cust));
                                //cust="Table1";
                                //intent.putExtra("key1",cust);

                            }
                            else if((barcodes.valueAt(0).displayValue).equals("Table2")){

                                cust="Table No.2";
                                startActivity(new Intent(BarcodeActivity.this,OrderList.class).putExtra("key1",cust));

                            }

                            //);
                        }
                    });
                }

            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    /*private void releaseCameraAndPreview() {
            //myCameraPreview.setCamera(null);
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }*/
    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);

            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
            }
        }


    }
}

