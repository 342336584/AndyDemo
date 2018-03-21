package com.example.andy.andydemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.andy.andydemo.BuildConfig;
import com.example.andy.andydemo.R;

import java.util.Arrays;

import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.actions.ActionsModule;
import io.palaima.debugdrawer.actions.ButtonAction;
import io.palaima.debugdrawer.actions.SpinnerAction;
import io.palaima.debugdrawer.actions.SwitchAction;
import io.palaima.debugdrawer.commons.BuildModule;
import io.palaima.debugdrawer.commons.DeviceModule;
import io.palaima.debugdrawer.commons.SettingsModule;
import io.palaima.debugdrawer.timber.TimberModule;

/**
 * Created by onething on 2018/3/13.
 */

public class BaseActivity extends Activity {

//    private OkHttpClient okHttpClient;
//    private Picasso picasso;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewServer.get(this).addWindow(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        createDebugDrawer();
    }

    public void onResume() {
        super.onResume();
//        ViewServer.get(this).setFocusedWindow(this);
    }

    public void onDestroy() {
        super.onDestroy();
//        ViewServer.get(this).removeWindow(this);
    }

    private void createDebugDrawer() {
//        okHttpClient = createOkHttpClientBuilder(this.getApplication()).build();
//        picasso = new Picasso.Builder(this)
//                .downloader(new OkHttp3Downloader(okHttpClient))
//                .listener(new Picasso.Listener() {
//                    @Override
//                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
//                        Log.e("Picasso", "Failed to load image: %s", e);
//                    }
//                })
//                .build();
        SwitchAction switchAction = new SwitchAction("Test switch", new SwitchAction.Listener() {
            @Override
            public void onCheckedChanged(boolean value) {
                Toast.makeText(BaseActivity.this, "Switch checked", Toast.LENGTH_LONG).show();
            }
        });

        ButtonAction buttonAction = new ButtonAction("Test button", new ButtonAction.Listener() {
            @Override
            public void onClick() {
                Toast.makeText(BaseActivity.this, "Button clicked", Toast.LENGTH_LONG).show();
            }
        });

        SpinnerAction<String> spinnerAction = new SpinnerAction<>(
                Arrays.asList("First", "Second", "Third"),
                new SpinnerAction.OnItemSelectedListener<String>() {
                    @Override public void onItemSelected(String value) {
                        Toast.makeText(BaseActivity.this, "Spinner item selected - " + value, Toast.LENGTH_LONG).show();
                    }
                }
        );
        if (BuildConfig.DEBUG
                && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ViewGroup rootView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            if (rootView != null && rootView.getChildCount() != 0) {
                new DebugDrawer.Builder(this).modules(
                        new ActionsModule(switchAction, buttonAction, spinnerAction),
//                        new FpsModule(Takt.stock(getApplication())),
//                        new LocationModule(),
//                        new ScalpelModule(this),
//                        new OkHttp3Module(okHttpClient),
//                        new PicassoModule(picasso),
//                        new GlideModule(Glide.get(getContext())),
                        new DeviceModule(),
                        new BuildModule(),
//                        new NetworkModule(),
                        new SettingsModule(),
                        new TimberModule()
                ).withTheme(R.style.Theme_AppCompat_Light).build();
            }
        }
    }
}
