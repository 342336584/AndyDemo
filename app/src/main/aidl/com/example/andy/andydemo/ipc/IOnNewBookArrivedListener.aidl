// IOnNewBookArrivedListener.aidl
package com.example.andy.andydemo.ipc;

import com.example.andy.andydemo.ipc.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

       void OnNewBookArrivedListener(in Book book);
}
