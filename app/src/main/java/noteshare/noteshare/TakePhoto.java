package noteshare.noteshare;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import CameraUtility.CameraPreview;


public class TakePhoto extends ActionBarActivity {

    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private File pictureFile=null;
    private ZoomControls zoomControls;
    private final int CROP_IMAGE = 1;
    private int bitmap_size;
    private byte[] bitmap_Array;
    private Bitmap tempBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        if(checkCamera(TakePhoto.this))
            mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this,mCamera);
        FrameLayout cameraPreview = (FrameLayout) findViewById(R.id.camera_preview);
        cameraPreview.addView(mCameraPreview);
        Button captureImageButton = (Button) findViewById(R.id.button_capture);

        if(mCamera!=null&&checkCamera(TakePhoto.this))
            mCamera.setDisplayOrientation(90);
        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCamera!=null)
                    mCamera.takePicture(null, null, mPicture);
            }
        });
    }

    private boolean checkCamera(Context context){
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }

    private Camera getCameraInstance(){
        Camera cam = null;
        try{
            cam = Camera.open();

        }catch (Exception e){
            Log.e("Accessing Camera", "Unable to open camera");
            e.printStackTrace();
        }
        return cam;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i("Picture taken","Success");
            pictureFile = getOutputMediaFile();
            if(pictureFile==null){
                return;
            }
            try{
                Log.i("Picture URI", pictureFile.getPath());

                tempBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);


                FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);
                cropImage(Uri.fromFile(pictureFile));
                if(bitmap_Array!=null)
                    fileOutputStream.write(bitmap_Array);
                else
                    fileOutputStream.write(data);
                fileOutputStream.close();
                /*Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image*//*");
                try {
                    startActivityForResult(i, REQ_CODE_PICK_IMAGE);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }*/
                mCamera.stopPreview();
                mCamera.startPreview();
            }catch(FileNotFoundException e){
                Log.e("File not found",e.getMessage());
            }catch (IOException e){
                Log.e("Error accessing file",e.getMessage());
            }
        }
    };

    private void releaseCamera(){
        if(mCamera!=null){
            mCamera.release();
            mCamera=null;
        }
    }

    private static File getOutputMediaFile(){

        //File mediaStorageDir = new File(Environment.getRootDirectory(),"NoteShare");
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"NoteShare");
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                Log.i("Error creating File","Failed to create file");
                return null;
            }
            else
                Log.d("Storage Path",mediaStorageDir.getAbsolutePath());
        }
        String timeExtension = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath()+File.separator+"IMG_"+timeExtension+".jpg");
        return mediaFile;
    }

    private void cropImage(Uri picUri){

        Log.i("Inside cropImageFunc","true");
        try {
            Intent cropImageIntent = new Intent("com.android.camera.action.CROP");

            cropImageIntent.setDataAndType(picUri, "image/*");

            cropImageIntent.putExtra("crop", true);
            cropImageIntent.putExtra("aspectX", 1);
            cropImageIntent.putExtra("aspectY", 1);
            cropImageIntent.putExtra("outputX", 128);
            cropImageIntent.putExtra("outputY", 128);
            cropImageIntent.putExtra("return-data", true);

            startActivityForResult(cropImageIntent, CROP_IMAGE);
        }catch (ActivityNotFoundException anfe){
            Toast.makeText(getApplicationContext(),"Your phone does not support image cropping",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            Log.i("requestCode", String.valueOf(requestCode));
            if(requestCode==CROP_IMAGE){
                Bundle extras = data.getExtras();
                Bitmap croppedPic = extras.getParcelable("data");
                if(tempBitmap!=null){
                    croppedPic.setHeight(tempBitmap.getHeight());
                    croppedPic.setWidth(tempBitmap.getWidth());
                    croppedPic.setDensity(tempBitmap.getDensity());
                    if(tempBitmap.getConfig()!=null)
                        croppedPic.setConfig(tempBitmap.getConfig());
                };

                if(Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB_MR1){
                    bitmap_size = croppedPic.getRowBytes() * croppedPic.getHeight();
                }
                else
                    bitmap_size = croppedPic.getByteCount();

                ByteBuffer buffer = ByteBuffer.allocate(bitmap_size);
                croppedPic.copyPixelsToBuffer(buffer);

                bitmap_Array = buffer.array();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        releaseCamera();
    }

    @Override
    protected void onResume(){
        super.onResume();
        try {
            if(mCamera!=null)
                mCamera.reconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_take_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
