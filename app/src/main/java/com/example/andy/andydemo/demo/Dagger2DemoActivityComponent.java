package com.example.andy.andydemo.demo;

import dagger.Component;

@Component(modules = Dagger2DemoActivityModule.class)
public interface Dagger2DemoActivityComponent {
    void inject(Dagger2DemoActivity dagger2DemoActivity);
}
