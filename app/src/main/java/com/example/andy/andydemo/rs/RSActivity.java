package com.example.andy.andydemo.rs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.util.Log;
import android.widget.ImageView;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.ScriptC_grayed;
import com.example.andy.andydemo.base.BaseActivity;

import static android.content.ContentValues.TAG;

public class RSActivity extends BaseActivity {

    public ImageView ivIn;

    public ImageView ivOut;

    private RenderScript mRs;
    private ScriptC_grayed scriptGrayed;
    private Allocation mInAllocation;
    private Allocation mOutAllocation;

    private Bitmap mBitmapIn;
    private Bitmap mBitmapOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rs);

        ivIn = (ImageView) findViewById(R.id.iv_in);
        ivOut = (ImageView) findViewById(R.id.iv_out);

        mBitmapIn = BitmapUtils.zoomBitmap(BitmapUtils.loadBitmapRes(this, R.drawable.umbrela), 720, 600);
        mBitmapOut = Bitmap.createBitmap(mBitmapIn.getWidth(), mBitmapIn.getHeight(),
                mBitmapIn.getConfig());


        ivIn.setImageBitmap(mBitmapIn);
        ivOut.setImageBitmap(mBitmapOut);

        createRS(mBitmapOut);
    }

    private void createRS(Bitmap bmpOut) {
        mRs = RenderScript.create(this);

        mInAllocation = Allocation.createFromBitmap(mRs, mBitmapIn,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        mOutAllocation = Allocation.createFromBitmap(mRs, bmpOut,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);

        Log.e(TAG, "===Sccrit new===");
        scriptGrayed = new ScriptC_grayed(mRs);
        Log.e(TAG, "===Sccrit invoke root===");
        scriptGrayed.forEach_root(mInAllocation, mOutAllocation);
        Log.e(TAG, "===Sccrit root end===");

        mOutAllocation.copyTo(bmpOut);
    }

}
