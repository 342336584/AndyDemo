package com.example.andy.andydemo.ndk;

/**
 * Created by onething on 2018/4/13.
 */

public class myJNI {
    static {
        System.loadLibrary("JniTest");
    }

    public static native String sayHello();

    public static native int plus(int x, int y);
}
