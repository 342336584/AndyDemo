package com.example.andy.andydemo.net.movie;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.andy.andydemo.R;
import com.example.andy.andydemo.net.NetBaseActivity;
import com.example.andy.andydemo.net.http.Fault;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

public class MovieActivity extends NetBaseActivity implements View.OnClickListener{
    private MovieLoader mMovieLoader;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.movie_activity);
        super.onCreate(savedInstanceState);

        mMovieLoader = new MovieLoader();
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(R.string.movie_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new MovieDecoration());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mMovieAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mMovieAdapter);
        getMovieList();

    }

    /**
     * 获取电影列表
     */
    private void getMovieList(){
        Subscription subscribe = mMovieLoader.getMovie(0,10).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
                mMovieAdapter.setMovies(movies);
                mMovieAdapter.notifyDataSetChanged();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("TAG","error message:"+throwable.getMessage());
                if(throwable instanceof Fault){
                    Fault fault = (Fault) throwable;
                    if(fault.getErrorCode() == 404){
                        //错误处理
                    }else if(fault.getErrorCode() == 500){
                        //错误处理
                    }else if(fault.getErrorCode() == 501){
                        //错误处理
                    }
                }
            }
        });

        addSubscription(subscribe);
    }


    @Override
    public void onClick(View v) {
        //getMovieRx();
    }

    public static class MovieDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0,0,0,20);
        }
    }
}
