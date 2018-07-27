package com.example.andy.andydemo.demo;

public class RetrofitManager {
    private OkHttpClient client;

    public RetrofitManager(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpClient getClient() {
        return client;
    }
}
