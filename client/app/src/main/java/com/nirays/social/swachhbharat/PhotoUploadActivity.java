package com.nirays.social.swachhbharat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.nirays.social.swachhbharat.model.Constant;

public class PhotoUploadActivity extends AppCompatActivity {
    ImageView imgPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);
        Bundle b = getIntent().getExtras();
        String value = ""; // or other values
        if(b != null)
            value = b.getString(Constant.FILE_PATH);
        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;

        final Bitmap bitmap = BitmapFactory.decodeFile(value,
                options);
        ImageView imgPreview = (ImageView)findViewById(R.id.image);
        imgPreview.setImageBitmap(bitmap);
    }
}
