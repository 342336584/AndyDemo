package com.example.andy.andydemo.demo;

import dagger.Module;
import dagger.Provides;

@Module
public class Dagger2DemoActivityModule {

    private int cacheSize;

    public Dagger2DemoActivityModule(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    @Provides
    OkHttpClient provideOkHttpClient(){
        OkHttpClient client = new OkHttpClient();
        client.setCacheSize(cacheSize);
        return client;
    }

    @Provides
    RetrofitManager provideRetrofitManager(OkHttpClient client){
        return new RetrofitManager(client);
    }
}
