package com.example.andy.andydemo.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

public class BookManagerService extends Service {
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListeners = new RemoteCallbackList<>();

    //创建一个Binder对象
    private Binder mBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListeners.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListeners.unregister(listener);
        }
    };

    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2, "Ios"));
        new Thread(new serviceWork()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    //将book添加到图书列表中（mBookList），并通知所有观察者
    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        final int N = mListeners.beginBroadcast();
        Timber.d("onNewBookArrived registener listener size: " + N);
        for (int i = 0; i < N; i++){
            IOnNewBookArrivedListener l = mListeners.getBroadcastItem(i);
            if (l!=null){
                l.OnNewBookArrivedListener(book);
            }
        }
        mListeners.finishBroadcast();
    }

    //线程类。在每休眠5秒后，会自动添加一本书， 并通过OnNewBookArrived()方法通知所有观察者。
    private class serviceWork implements Runnable{

        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId,"new Book #" + bookId);

                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
