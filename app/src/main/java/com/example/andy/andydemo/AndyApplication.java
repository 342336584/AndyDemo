package com.example.andy.andydemo;

import android.app.Application;

import io.palaima.debugdrawer.timber.data.LumberYard;
import timber.log.Timber;

/**
 * Created by onething on 2018/3/13.
 */

public class AndyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
        initDebugDrawer();
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
