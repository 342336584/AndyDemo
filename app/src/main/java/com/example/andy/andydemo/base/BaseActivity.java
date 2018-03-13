package com.example.andy.andydemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.Window;

import com.example.andy.andydemo.BuildConfig;
import com.example.andy.andydemo.R;

import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.timber.TimberModule;

/**
 * Created by onething on 2018/3/13.
 */

public class BaseActivity extends Activity {
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        createDebugDrawer();
    }

    private void createDebugDrawer() {
        if (BuildConfig.DEBUG
                && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ViewGroup rootView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            if (rootView != null && rootView.getChildCount() != 0) {
                new DebugDrawer.Builder(this).modules(
                        new TimberModule()
                ).withTheme(R.style.Theme_AppCompat_Light).build();
            }
        }
    }
}
