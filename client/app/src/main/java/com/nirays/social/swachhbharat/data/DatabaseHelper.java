package com.nirays.social.swachhbharat.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kirant400 on 02/03/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;
    //Post Table
    public static final String TABLE_POST = "post";
    public static final String POST_COLUMN_ID = "_id";
    public static final String POST_COLUMN_STATUS = "status";
    public static final String POST_COLUMN_LAT = "latitude";
    public static final String POST_COLUMN_LONG = "longitude";
    public static final String POST_COLUMN_TOTAL = "total";
    public static final String POST_COLUMN_IMAGE = "image";
    public static final String POST_COLUMN_IMAGE_STATUS = "image_status";
    public static final String POST_COLUMN_PUBLISH_DATE = "publish_date";
    public static final String POST_COLUMN_DOWNLOAD_PERCENT = "download_percent";
    public static final String POST_COLUMN_LOCAL_IMAGE = "local_image";

    // Database creation sql statement
    private static final String DATABASE_POST_CREATE = "CREATE TABLE "
            + TABLE_POST + "(" + POST_COLUMN_ID
            + " INTEGER primary key autoincrement, "
            + POST_COLUMN_LAT + " TEXT not null,"
            + POST_COLUMN_LONG + " TEXT not null,"
            + POST_COLUMN_STATUS + " INTEGER not null,"
            + POST_COLUMN_TOTAL + " INTEGER not null,"
            + POST_COLUMN_IMAGE + " TEXT,"
            + POST_COLUMN_IMAGE_STATUS + " INTEGER not null,"
            + POST_COLUMN_PUBLISH_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + POST_COLUMN_LOCAL_IMAGE + "  TEXT,"
            + POST_COLUMN_DOWNLOAD_PERCENT + " INTEGER not null"
            + ");";

    //User
    public static final String TABLE_USER = "user";
    public static final String USER_COLUMN_ID = "_id";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_RATE = "rate";
    public static final String USER_COLUMN_COMMENT = "comment";
    public static final String USER_COLUMN_DATE = "date_on";
    public static final String USER_COLUMN_POST_ID = "post_id";

    // Database creation sql statement
    private static final String DATABASE_USER_CREATE = "CREATE TABLE "
            + TABLE_USER + "(" + USER_COLUMN_ID
            + " INTEGER primary key, "
            + USER_COLUMN_NAME + " TEXT,"
            + USER_COLUMN_COMMENT + " TEXT,"
            + USER_COLUMN_RATE + " INTEGER,"
            + USER_COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + USER_COLUMN_POST_ID + " INTEGER"
            + ");";

    //Advert Table
    public static final String TABLE_CURRENT_USER= "current_user";
    public static final String ADVERT_COLUMN_ID = "_id";
    public static final String ADVERT_COLUMN_POST_ID = "post_id";


    // Database creation sql statement
    private static final String DATABASE_ADVERT_CREATE = "CREATE TABLE "
            + TABLE_CURRENT_USER + "(" + ADVERT_COLUMN_ID
            + " INTEGER , "
            + ADVERT_COLUMN_POST_ID + " INTEGER"
            + ");";

    public static final String DATABASE_NAME = "swachhbharatconfig.db";//"/mnt/sdcard/config.db";//
    private static final int DATABASE_VERSION = 1;

    public static DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_POST_CREATE);
        database.execSQL(DATABASE_USER_CREATE);
        database.execSQL(DATABASE_ADVERT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
               "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_USER);
        onCreate(db);
    }
}
