package com.nirays.social.swachhbharat;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.ListView;

import com.nirays.social.swachhbharat.adapter.ChatAdapter;
import com.nirays.social.swachhbharat.model.UserModel;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    private ChatAdapter mListAdapter;
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mListAdapter = new ChatAdapter(this,generateData());
        ListView mListView = (ListView) findViewById(R.id.chatList);
        mListView.setAdapter(mListAdapter);
        imageView = (ImageView)findViewById(R.id.image);
        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());
    }

    private ArrayList<UserModel> generateData() {
        ArrayList<UserModel> models = new ArrayList<UserModel>();
//        if(dataSource == null){
//            dataSource = new ConfigDataSource(getActivity());
//            dataSource.open();
//        }

        //List<PhoneModel> configs = dataSource.getPhones(groupId);
        //for (PhoneModel phoneModel : configs) {
        models.add(new UserModel());
        models.add(new UserModel());
        models.add(new UserModel());
        models.add(new UserModel());
        models.add(new UserModel());
        //}
        return models;
    }

    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            matrix.setScale(scaleFactor, scaleFactor);
            imageView.setImageMatrix(matrix);
            return true;
        }
    }
}
