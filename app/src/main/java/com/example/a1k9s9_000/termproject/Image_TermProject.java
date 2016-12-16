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
    Button getphoto;
    ImageView image;
    Uri imageuri;
    final int REQUEST_CAMERA = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__term_project);

        getphoto = (Button) findViewById(R.id.photo_b); // 카메라 앱으로 이동하는 버튼
        image = (ImageView) findViewById(R.id.imageview); // 이미지 보여주는 뷰

        // 카메라 앱으로 이동
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
        // 사진이 90도 회전되어 나오는 것을 방지하기 위한 코드
        // 코드 상에서 90도 회전시켜 사진이 보이도록 한다.
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
        // 90도 회전되어있을 때
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        // 180도 회전되어있을 때
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        // 270도 회전되어있을 때
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270;
        return 0;
    }

    // 회전 시켜 이미지를 Bitmap 형태로 저장하여 return 한다.
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