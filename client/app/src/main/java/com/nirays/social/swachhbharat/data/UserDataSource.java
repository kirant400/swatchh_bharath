package com.nirays.social.swachhbharat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.nirays.social.swachhbharat.model.UserModel;
import com.nirays.social.swachhbharat.util.DBUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirant400 on 09/05/2016.
 */
public class UserDataSource {
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {
            DatabaseHelper.USER_COLUMN_ID,
            DatabaseHelper.USER_COLUMN_NAME,
            DatabaseHelper.USER_COLUMN_COMMENT,
            DatabaseHelper.USER_COLUMN_RATE,
            DatabaseHelper.USER_COLUMN_DATE,
            DatabaseHelper.USER_COLUMN_POST_ID
    };

    public UserDataSource(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public UserModel addUser(UserModel userModel) {
        ContentValues values = new ContentValues();
        if(userModel.getId()>=0)
            values.put(DatabaseHelper.USER_COLUMN_ID, userModel.getId());
        values.put(DatabaseHelper.USER_COLUMN_COMMENT, userModel.getComment());
        values.put(DatabaseHelper.USER_COLUMN_NAME, userModel.getName());
        values.put(DatabaseHelper.USER_COLUMN_RATE, userModel.getRate());
        values.put(DatabaseHelper.USER_COLUMN_DATE, DBUtil.getUTCDateTimeAsString( userModel.getDateOn()));
        values.put(DatabaseHelper.USER_COLUMN_POST_ID,userModel.getPostId());

        long insertId = database.insert(DatabaseHelper.TABLE_USER, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
                allColumns, DatabaseHelper.USER_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        UserModel _userModel = cursorToUser(cursor);
        cursor.close();
        return _userModel;
    }

    private UserModel cursorToUser(Cursor cursor) {
        UserModel userModel = new UserModel();
        userModel.setId(cursor.getLong(0));
        userModel.setName(cursor.getString(1));
        userModel.setComment(cursor.getString(2));
        userModel.setRate(cursor.getInt(3));
        userModel.setDateOn(DBUtil.getUTCDateTimeStringAsDate(cursor.getString(4)));
        userModel.setPostId(cursor.getLong(5));
        return userModel;
    }

    public void deleteUser(Long id) {
        database.delete(DatabaseHelper.TABLE_USER, DatabaseHelper.USER_COLUMN_ID
                + " = " + id, null);
    }

    public void deleteUser() {
        database.delete(DatabaseHelper.TABLE_USER, null, null);
    }

    public void deleteUser(String[] ids) {
        database.delete(DatabaseHelper.TABLE_USER, DatabaseHelper.USER_COLUMN_ID
                +" IN (" + DBUtil.makePlaceholders(ids.length) + ")", ids);
    }

    public  UserModel getUser(Long id) {
        UserModel locationModel = null;
        Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
                allColumns, DatabaseHelper.USER_COLUMN_ID
                        + " = " + id, null, null, null, null);
        if (cursor.moveToFirst()) {
            locationModel = cursorToUser(cursor);
        }
        // make sure to close the cursor
        cursor.close();
        return locationModel;
    }

    public  UserModel getActiveUser() {
        UserModel userModel = null;
        Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
                allColumns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            userModel = cursorToUser(cursor);
        }
        // make sure to close the cursor
        cursor.close();
        if (userModel==null){
            userModel = new UserModel();
            userModel = addUser(userModel);
        }
        return userModel;
    }

    public List<UserModel> getAllUsers() {
        List<UserModel> userModels = new ArrayList<UserModel>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_USER,
                allColumns,null,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserModel userModel = cursorToUser(cursor);
            userModels.add(userModel);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return userModels;
    }

    public List<UserModel> getUsersWithIds(String[] postIds) {
        List<UserModel> userModels = new ArrayList<UserModel>();

        String query = "SELECT * FROM "+DatabaseHelper.TABLE_USER
                + " WHERE "+DatabaseHelper.USER_COLUMN_ID
                +" IN (" + DBUtil.makePlaceholders(postIds.length) + ")";
        Cursor cursor = database.rawQuery(query, postIds);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserModel userModel = cursorToUser(cursor);
            userModels.add(userModel);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return userModels;
    }
}
