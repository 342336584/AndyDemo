package com.example.andy.andydemo.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.andy.andydemo.AndyApplication;
import com.example.andy.andydemo.sys.Version;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

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
    public void onCreate(Database db) {
        super.onCreate(db);
        initVersion(db);
    }

    private void initVersion(Database db) {
        List<Version> mVersion = new ArrayList<>();
        mVersion.add(new Version("平台版本", "api", "VERSION_CODE", "说明"));
        mVersion.add(new Version("Android 8.1", "27", "Oreo", "奥利奥"));
        mVersion.add(new Version("Android 8", "26", "Oreo", "奥利奥"));
        mVersion.add(new Version("Android 7.1", "25", "Nougat", "牛轧糖"));
        mVersion.add(new Version("Android 7.0", "24", "Nougat", "牛轧糖"));
        mVersion.add(new Version("Android 6.0", "23", "Marshmallow", "棉花糖"));
        mVersion.add(new Version("Android 5.1", "22", "LOLLIPOP_MR1", "棒棒糖"));
        mVersion.add(new Version("Android 5.0", "21", "LOLLIPOP", "棒棒糖"));
        mVersion.add(new Version("Android 4.4W", "20", "KITKAT_WATCH", ""));
        mVersion.add(new Version("Android 4.4", "19", "KITKAT", "巧克力棒"));
        mVersion.add(new Version("Android 4.3", "18", "JELLY_BEAN_MR2", "糖豆"));
        mVersion.add(new Version("Android 4.2/4.2.2", "17", "JELLY_BEAN_MR1", "糖豆"));
        mVersion.add(new Version("Android 4.1/4.1.1", "16", "JELLY_BEAN", "糖豆"));

        if (mDaoMaster == null)
            mDaoMaster = new DaoMaster(db);

        if (mDaoSession == null)
            mDaoSession = mDaoMaster.newSession();

        mDaoSession.getVersionDao().insertInTx(mVersion);
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
        SQLiteDatabase db = null;
        if (sDBHelper == null) {
            sDBHelper = new MySQLiteOpenHelper(AndyApplication.getApplication(), DATABASE_FOLDER_NAME, null);
            db = sDBHelper.getWritableDatabase();
        }

        if (mDaoMaster == null)
            mDaoMaster = new DaoMaster(db);

        if (mDaoSession == null)
        mDaoSession = mDaoMaster.newSession();
    }


    public static DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }


    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }

}
