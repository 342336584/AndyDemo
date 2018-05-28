package com.example.andy.andydemo.window;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.andydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WindowDemoActivity extends AppCompatActivity {

    @BindView(R.id.root)
    ViewGroup mRootView;

    @BindView(R.id.detail_tv)
    TextView mDetailTv;

    @BindView(R.id.toast)
    Button mToastBtn;

    @BindView(R.id.snackbar)
    Button mSnackbarBtn;

    @BindView(R.id.dialog)
    Button mDialogBtn;

    @BindView(R.id.popupwindow)
    Button mPopupwindowBtn;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_demo);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
    }

    @OnClick(R.id.snackbar)
    public void clickSnackbar(){
//        Snackbar.make(mSnackbarBtn, "hello snackbar", Snackbar.LENGTH_LONG)
//                .setAction("click", new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        Toasty.info(WindowDemoActivity.this, "onClick").show();
//                    }
//                })
//                .show();


        Snackbar snackBar =Snackbar.make(mSnackbarBtn,"it is snackbar!",Snackbar.LENGTH_SHORT);
        //设置SnackBar背景颜色
        snackBar.getView().setBackgroundResource(R.color.colorAccent);
        //设置按钮文字颜色
        snackBar.setActionTextColor(Color.WHITE);
        //设置点击事件
        snackBar.setAction("点击", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WindowDemoActivity.this,"点击",Toast.LENGTH_SHORT).show();
            }
        });
        //设置回调
        snackBar.setCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                Toast.makeText(WindowDemoActivity.this, "Snackbar dismiss", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
                Toast.makeText(WindowDemoActivity.this, "Snackbar show", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    @OnClick(R.id.popupwindow)
    public void clickPopupWindow(){
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            return;
        }
        Drawable firstStepDrawable = ContextCompat.getDrawable(this, R.drawable.umbrela);
        ImageView firstStepContentView = new ImageView(this);
        firstStepContentView.setImageDrawable(firstStepDrawable);
        firstStepContentView.setClickable(true);
        mPopupWindow = new PopupWindow(firstStepContentView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setClippingEnabled(false);                         //允许弹出窗口超出屏幕范围
        mPopupWindow.setFocusable(true);                                //点击其他地方关闭
        mPopupWindow.setOutsideTouchable(true);        //设置PopupWindow是否能响应外部点击事件
        mPopupWindow.setTouchable(true);                //设置PopupWindow是否能响应点击事件
//        mPopupWindow.showAtLocation(mPopupwindowBtn, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);                //设置位置
        mPopupWindow.showAsDropDown(mPopupwindowBtn , mPopupwindowBtn.getMeasuredWidth(), - firstStepDrawable.getIntrinsicHeight());
    }

}
