package com.nirays.social.swachhbharat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.nirays.social.swachhbharat.model.UploadModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirant400 on 09/05/2016.
 */
public class UploadDataSource {
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {
            DatabaseHelper.ADVERT_COLUMN_ID,
            DatabaseHelper.ADVERT_COLUMN_POST_ID
    };

    public UploadDataSource(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addUpload(UploadModel uploadModel) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ADVERT_COLUMN_ID, uploadModel.getId());
        values.put(DatabaseHelper.ADVERT_COLUMN_POST_ID, uploadModel.getPostId());

        long insertId = database.insert(DatabaseHelper.TABLE_CURRENT_USER, null,
                values);
    }

    private UploadModel cursorToUpload(Cursor cursor) {
        UploadModel userModel = new UploadModel();
        userModel.setId(cursor.getLong(0));
        userModel.setPostId(cursor.getLong(1));
        return userModel;
    }


    public void deleteUpload() {
        database.delete(DatabaseHelper.TABLE_CURRENT_USER, null, null);
    }

    public List<UploadModel> getAllUploads() {
        List<UploadModel> userModels = new ArrayList<UploadModel>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_CURRENT_USER,
                allColumns,null,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UploadModel userModel = cursorToUpload(cursor);
            userModels.add(userModel);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return userModels;
    }
}
