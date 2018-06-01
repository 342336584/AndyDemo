package com.example.andy.andydemo;

import android.app.Application;

import com.example.andy.andydemo.db.MySQLiteOpenHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import io.palaima.debugdrawer.timber.data.LumberYard;
import timber.log.Timber;

/**
 * Created by onething on 2018/3/13.
 */

public class AndyApplication extends Application {
    private static final int MEMORY_SIZE = 5 * 1024 * 1024;
    private static final int DISK_SIZE = 20 * 1024 * 1024;

    static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;

        initDB();

        initTimber();
        initDebugDrawer();

        initNetConfig();
        initCustomerActivityonCrash();
    }

    private void initDB() {
        MySQLiteOpenHelper.init();
    }

    /**
     * 获取全局的ApplicationContext
     */
    public static Application getApplication() {
        return sApplication;
    }

    private void initCustomerActivityonCrash() {
        CaocConfig.Builder.create()
                .apply();
    }

    private void initNetConfig() {

        // 初始化 Image-Loader
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new LruMemoryCache(MEMORY_SIZE))
                .diskCache(new UnlimitedDiscCache(new File(getCacheDir(), "caches")))
                .diskCacheSize(DISK_SIZE)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(configuration);
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private void initDebugDrawer() {
        if (BuildConfig.DEBUG
                && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            // TimberModule
            LumberYard lumberYard = LumberYard.getInstance(this);
            lumberYard.cleanUp();
            Timber.plant(lumberYard.tree());
        }
    }



    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {

        }
    }
}
