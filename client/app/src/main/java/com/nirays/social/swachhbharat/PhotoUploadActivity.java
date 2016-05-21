package com.nirays.social.swachhbharat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.nirays.social.swachhbharat.data.PostDataSource;
import com.nirays.social.swachhbharat.data.UploadDataSource;
import com.nirays.social.swachhbharat.data.UserDataSource;
import com.nirays.social.swachhbharat.model.Constant;
import com.nirays.social.swachhbharat.model.PostModel;
import com.nirays.social.swachhbharat.model.UploadModel;
import com.nirays.social.swachhbharat.model.UserModel;

import java.util.Date;

public class PhotoUploadActivity extends AppCompatActivity implements LocationListener {
    private static final int REQUEST_PERMISSION_PHONE_STATE = 1;
    ImageView imgPreview;
    LocationManager locationManager;
    private String latitude;
    private String longitude;
    EditText txtView;
    private RatingBar ratingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);
        Bundle b = getIntent().getExtras();
        final String value = b.getString(Constant.FILE_PATH);
        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_PHONE_STATE);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;

        final Bitmap bitmap = BitmapFactory.decodeFile(value,
                options);
        ImageView imgPreview = (ImageView)findViewById(R.id.image);
        txtView = (EditText)findViewById(R.id.txtValue);
        ratingView = (RatingBar)findViewById(R.id.rating);
        imgPreview.setImageBitmap(bitmap);
        Button btnSubmit = (Button)findViewById(R.id.button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDataSource ps = new PostDataSource(PhotoUploadActivity.this);
                ps.open();
                UploadDataSource uds = new UploadDataSource(PhotoUploadActivity.this);
                uds.open();
                UserDataSource us = new UserDataSource(PhotoUploadActivity.this);
                us.open();
                PostModel pm = new PostModel();
                pm.setLatitude(latitude);
                pm.setLongitude(longitude);
                pm.setPublishDate(new Date());
                pm.setLocalImage(value);
                pm.setDownloadPercent(100);
                pm.setImageStatus(2);
                pm.setTotal((int)ratingView.getRating());
                pm = ps.addPost(pm);
                UploadModel udm = new UploadModel();
                udm.setId(1L);
                udm.setPostId(pm.getId());
                //add to recent added
                uds.addUpload(udm);
                UserModel um = new UserModel();
                um.setPostId(pm.getId());
                um.setId(1);
                um.setComment(txtView.getText().toString());
                um.setDateOn(new Date());
                um.setRate(ratingView.getRating());
                us.addUser(um);
                finish();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude()+"";
        longitude= location.getLongitude()+"";
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
