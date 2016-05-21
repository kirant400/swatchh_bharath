package com.nirays.social.swachhbharat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.nirays.social.swachhbharat.model.PostModel;
import com.nirays.social.swachhbharat.util.DBUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kirant400 on 08/05/2016.
 */
public class PostDataSource {
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {
            DatabaseHelper.POST_COLUMN_ID,
            DatabaseHelper.POST_COLUMN_STATUS,
            DatabaseHelper.POST_COLUMN_TOTAL,
            DatabaseHelper.POST_COLUMN_LONG,
            DatabaseHelper.POST_COLUMN_LAT,
            DatabaseHelper.POST_COLUMN_IMAGE,
            DatabaseHelper.POST_COLUMN_IMAGE_STATUS,
            DatabaseHelper.POST_COLUMN_PUBLISH_DATE,
            DatabaseHelper.POST_COLUMN_DOWNLOAD_PERCENT,
            DatabaseHelper.POST_COLUMN_LOCAL_IMAGE
    };

    public PostDataSource(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public PostModel addPost(PostModel postModel) {
        ContentValues values = new ContentValues();
        if( postModel.getId()>0)
            values.put(DatabaseHelper.POST_COLUMN_ID, postModel.getId());
        values.put(DatabaseHelper.POST_COLUMN_STATUS, postModel.getStatus());
        values.put(DatabaseHelper.POST_COLUMN_LAT,postModel.getLatitude());
        values.put(DatabaseHelper.POST_COLUMN_LONG,postModel.getLongitude());
        values.put(DatabaseHelper.POST_COLUMN_TOTAL,postModel.getTotal());
        values.put(DatabaseHelper.POST_COLUMN_IMAGE,postModel.getImage());
        values.put(DatabaseHelper.POST_COLUMN_IMAGE_STATUS,postModel.getStatus());
        values.put(DatabaseHelper.POST_COLUMN_PUBLISH_DATE, DBUtil.getUTCDateTimeAsString(postModel.getPublishDate()));
        values.put(DatabaseHelper.POST_COLUMN_DOWNLOAD_PERCENT,postModel.getDownloadPercent());
        values.put(DatabaseHelper.POST_COLUMN_LOCAL_IMAGE,postModel.getLocalImage());

        long insertId = database.insert(DatabaseHelper.TABLE_POST, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_POST,
                allColumns, DatabaseHelper.POST_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        PostModel _postModel = cursorToPost(cursor);
        cursor.close();
        return _postModel;
    }

    public void safeAdd(PostModel model){
        PostModel am = getPost(model.getId());
        if (am==null){
            addPost(model);
        }else{
            updatePost(model);
        }
    }

    private void updatePost(PostModel model) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.POST_COLUMN_STATUS, model.getStatus());
        values.put(DatabaseHelper.POST_COLUMN_LAT,model.getLatitude());
        values.put(DatabaseHelper.POST_COLUMN_LONG,model.getLongitude());
        values.put(DatabaseHelper.POST_COLUMN_TOTAL,model.getTotal());
        values.put(DatabaseHelper.POST_COLUMN_IMAGE,model.getImage());
        values.put(DatabaseHelper.POST_COLUMN_IMAGE_STATUS,model.getStatus());
        values.put(DatabaseHelper.POST_COLUMN_PUBLISH_DATE, DBUtil.getUTCDateTimeAsString(model.getPublishDate()));
        values.put(DatabaseHelper.POST_COLUMN_DOWNLOAD_PERCENT,model.getDownloadPercent());
        values.put(DatabaseHelper.POST_COLUMN_LOCAL_IMAGE,model.getLocalImage());

        database.update(DatabaseHelper.TABLE_POST,values, DatabaseHelper.POST_COLUMN_ID
                + " = " + model.getId(), null);
    }

    private PostModel cursorToPost(Cursor cursor) {
        PostModel postModel = new PostModel();
        postModel.setId(cursor.getLong(0));
        postModel.setStatus(cursor.getInt(1));
        postModel.setTotal(cursor.getInt(2));
        postModel.setLongitude(cursor.getString(3));
        postModel.setLatitude(cursor.getString(4));
        postModel.setImage(cursor.getString(5));
        postModel.setStatus(cursor.getInt(6));
        postModel.setPublishDate(DBUtil.getUTCDateTimeStringAsDate(cursor.getString(7)));
        postModel.setDownloadPercent(cursor.getInt(8));
        postModel.setLocalImage(cursor.getString(9));
        return postModel;
    }

    public void deletePost(Long postId) {
        database.delete(DatabaseHelper.TABLE_POST, DatabaseHelper.POST_COLUMN_ID
                + " = " + postId, null);
    }

    public void deletePost() {
        database.delete(DatabaseHelper.TABLE_POST, null, null);
    }

    public void deletePostBefore( Date createDate) {
        database.delete(DatabaseHelper.TABLE_POST,
                        DatabaseHelper.POST_COLUMN_PUBLISH_DATE +
                        " < '" + DBUtil.getUTCDateTimeAsString(createDate)
                        + "'", null);
    }

    public PostModel getPost(Long postId) {
        PostModel postModel = null;
        Cursor cursor = database.query(DatabaseHelper.TABLE_POST,
                allColumns, DatabaseHelper.POST_COLUMN_ID
                        + " = " + postId, null, null, null, null);
        if (cursor.moveToFirst()){
            postModel = cursorToPost(cursor);
        }
        // make sure to close the cursor
        cursor.close();
        return postModel;
    }

    public List<PostModel> getPostBefore( Date createDate) {
        List<PostModel> postModels = new ArrayList<PostModel>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_POST,
                allColumns,DatabaseHelper.POST_COLUMN_PUBLISH_DATE +
                        " < '" + DBUtil.getUTCDateTimeAsString(createDate)
                        + "'",
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PostModel postModel = cursorToPost(cursor);
            postModels.add(postModel);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return  postModels;
    }

    public List<PostModel> getAllPosts() {
        List<PostModel> postModels = new ArrayList<PostModel>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_POST,
                allColumns,null,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PostModel postModel = cursorToPost(cursor);
            postModels.add(postModel);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return postModels;
    }

    public List<PostModel> getPostsWithIds(String[] postIds) {
        List<PostModel> postModels = new ArrayList<PostModel>();
        if(postIds!=null&&postIds.length>0) {
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_POST
                    + " WHERE " + DatabaseHelper.POST_COLUMN_ID
                    + " IN (" + DBUtil.makePlaceholders(postIds.length) + ")";
            Cursor cursor = database.rawQuery(query, postIds);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PostModel postModel = cursorToPost(cursor);
                postModels.add(postModel);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return postModels;
    }

    public void updatePercentage(Long postId,int percent) {
        ContentValues values = new ContentValues();
        int status = 0;
        if (percent <= 0) {
            percent = 0;
            status = 0;
        } else if (percent >= 100) {
            percent = 100;
            status = 2;
        } else {
            status = 1;
        }
        values.put(DatabaseHelper.POST_COLUMN_DOWNLOAD_PERCENT, percent);
        values.put(DatabaseHelper.POST_COLUMN_IMAGE_STATUS, status);
        database.update(DatabaseHelper.TABLE_POST, values, DatabaseHelper.POST_COLUMN_ID + "=" + postId, null);
    }

    public void updateStatus(Long postId,int status) {
        ContentValues values = new ContentValues();
        int percent = 0;
        if (status <= 0) {
            percent = 0;
            status = 0;
        } else if (status >= 2) {
            percent = 100;
            status = 2;
        } else {
            status = 1;
        }
        values.put(DatabaseHelper.POST_COLUMN_DOWNLOAD_PERCENT, percent);
        values.put(DatabaseHelper.POST_COLUMN_IMAGE_STATUS, status);
        database.update(DatabaseHelper.TABLE_POST, values, DatabaseHelper.POST_COLUMN_ID + "=" + postId, null);
    }

    public void updateImage(Long postId,int status,String image) {
        ContentValues values = new ContentValues();
        int percent = 0;
        if (status <= 0) {
            percent = 0;
            status = 0;
        } else if (status >= 2) {
            percent = 100;
            status = 2;
        } else {
            status = 1;
        }
        values.put(DatabaseHelper.POST_COLUMN_DOWNLOAD_PERCENT, percent);
        values.put(DatabaseHelper.POST_COLUMN_IMAGE_STATUS, status);
        values.put(DatabaseHelper.POST_COLUMN_IMAGE,image);
        database.update(DatabaseHelper.TABLE_POST, values, DatabaseHelper.POST_COLUMN_ID + "=" + postId, null);
    }

    public void updateImage(Long postId,String image) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.POST_COLUMN_IMAGE,image);
        database.update(DatabaseHelper.TABLE_POST, values, DatabaseHelper.POST_COLUMN_ID + "=" + postId, null);
    }
}
