package com.example.a1k9s9_000.termproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Image_TermProject extends AppCompatActivity {
    Button getphoto, save;
    ImageView image;
    Uri imageuri;
    final int REQUEST_CAMERA = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__term_project);

        getphoto = (Button) findViewById(R.id.photo_b);
        save = (Button)findViewById(R.id.save);
        image = (ImageView) findViewById(R.id.imageview);

        getphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(Environment.getExternalStorageDirectory(),".camera.jpg");
                imageuri = Uri.fromFile(file);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                startActivityForResult(intent,REQUEST_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            try {
                String datapath = imageuri.getPath();
                Bitmap image_bit = BitmapFactory.decodeFile(datapath);

                ExifInterface exif = new ExifInterface(datapath);
                int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                int exifDegree = exifOrientationToDegrees(exifOrientation);
                image_bit = rotate(image_bit, exifDegree);

                image.setImageBitmap(image_bit);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int exifOrientationToDegrees(int exifOrientation) {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270;
        return 0;
    }

    public Bitmap rotate(Bitmap bitmap, int degrees) {
        if(degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float)bitmap.getWidth() / 2, (float)bitmap.getHeight() / 2);

            try {
                Bitmap convert = Bitmap.createBitmap(bitmap, 0,0,bitmap.getWidth(),bitmap.getHeight(),m,true);

                if(bitmap != convert) {
                    bitmap.recycle();
                    bitmap = convert;
                }
            }catch(OutOfMemoryError oe)
            {

            }
        }
        return bitmap;
    }
}