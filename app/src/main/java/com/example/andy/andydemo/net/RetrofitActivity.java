package com.example.andy.andydemo.net;

import android.content.Intent;
import android.os.Bundle;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.net.gank.GankActivity;
import com.example.andy.andydemo.net.movie.MovieActivity;

import butterknife.OnClick;

public class RetrofitActivity extends NetBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_retrofit);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.movie_btn)
    public void clickMovie(){
        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.award_btn)
    public void clickAward(){
        Intent intent = new Intent(this, GankActivity.class);
        startActivity(intent);
    }
}
