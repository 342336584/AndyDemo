package com.example.andy.andydemo.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import timber.log.Timber;

public class ICalcService extends Service {

    private IBinder iBinder = new ICalclInterface.Stub(){

        @Override
        public int add(int num1, int num2) throws RemoteException {
            Timber.d("收到了来自客户端的请求" + num1 + "+" + num2);
            return num1 + num2;
        }
    };

    public ICalcService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return iBinder;
    }
}
