package com.example.andy.andydemo.demo;

import javax.inject.Inject;

public class Factory {
    Product product;

    @Inject
    public Factory(Product product) {
        this.product = product;
    }
}
