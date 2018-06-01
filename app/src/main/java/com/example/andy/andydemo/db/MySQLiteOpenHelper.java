package com.example.andy.andydemo.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.andydemo.AndyApplication;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String LOCK = "LOCK";
    private static final String DATABASE_FOLDER_NAME = "databases";
    private static final String DATABASE_NAME_DEFAULT = "database.db";

    private static String sDbName;
    private static MySQLiteOpenHelper sDBHelper;

    private static DaoMaster mDaoMaster = null;
    private static DaoSession mDaoSession = null;

    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, UserDao.class);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, UserDao.class);
    }


    public static void init(){
        if (sDBHelper == null) {
            sDBHelper = new MySQLiteOpenHelper(AndyApplication.getApplication(), DATABASE_FOLDER_NAME, null);
            SQLiteDatabase db = sDBHelper.getWritableDatabase();
            mDaoMaster = new DaoMaster(db);
            mDaoSession = mDaoMaster.newSession();
        }
    }


    public static DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }


    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }

}
