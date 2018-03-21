package com.example.andy.andydemo.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.base.BaseActivity;

import java.util.List;

import timber.log.Timber;

public class CalcActivity extends BaseActivity implements View.OnClickListener {
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private EditText num1;
    private EditText num2;
    private Button button;
    private TextView text;

    private Button mCustomDataBtn;

    private ServiceConnection mConn;
    private ICalclInterface mICalclInterface;

    private ServiceConnection mCustomDataConn;
    private IBookManager mIBookManager;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Timber.d("received new book:" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };


    private IOnNewBookArrivedListener mNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void OnNewBookArrivedListener(Book book) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        mConn = new ServiceConnection(){

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mICalclInterface = ICalclInterface.Stub.asInterface(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mICalclInterface = null;
            }

            @Override
            public void onBindingDied(ComponentName name) {

            }
        };

        mCustomDataConn = new ServiceConnection(){

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mIBookManager = IBookManager.Stub.asInterface(iBinder);

                try {
                    List<Book> list = mIBookManager.getBookList();
                    Timber.d("query book list,list type: " + list.getClass().getCanonicalName());
                    Timber.d("query book list: " + list.toString());
                    Book newBook = new Book(3, "android进阶");
                    mIBookManager.addBook(newBook);
                    Timber.d("add book: " + newBook);
                    List<Book> newList =  mIBookManager.getBookList();
                    Timber.d("query book list: " + newList.toString());
                    mIBookManager.registerListener(mNewBookArrivedListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mIBookManager = null;
                Timber.d("mIBookManager onServiceDisconnected");
            }

            @Override
            public void onBindingDied(ComponentName name) {

            }
        };

        bindService();
        initView();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, ICalcService.class);

        bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }


    private void initView() {
        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        button = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.text);
        mCustomDataBtn = findViewById(R.id.custom_data_button);

        button.setOnClickListener(this);
        mCustomDataBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                int num11 = Integer.parseInt(num1.getText().toString());
                int num22 = Integer.parseInt(num2.getText().toString());

                try {
                    int res = mICalclInterface.add(num11,num22);
                    text.setText(num11 +"+"+ num22 +"="+ res);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.custom_data_button:
                Intent customDataIntent = new Intent();
                customDataIntent.setClass(this, BookManagerService.class);
                bindService(customDataIntent, mCustomDataConn, Context.BIND_AUTO_CREATE);

                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
        unbindService(mCustomDataConn);
    }
}
