package com.example.andy.andydemo.net.student;

import com.example.andy.andydemo.net.ApiConfig;
import com.example.andy.andydemo.net.http.HttpCommonInterceptor;
import com.example.andy.andydemo.net.http.ObjectLoader;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by onething on 2018/4/8.
 */

public class StudentLoader extends ObjectLoader {

    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private Retrofit mRetrofit;
    private StudentService mStudentService;

    public StudentLoader() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        // 添加公共参数拦截器
        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("paltform","android")
                .addHeaderParams("userToken","1234343434dfdfd3434")
                .addHeaderParams("userId","123445")
                .build();
        builder.addInterceptor(commonInterceptor);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConfig.STUDENTS_URL)
                .build();
        mStudentService = mRetrofit.create(StudentService.class);
    }


    public Observable<List<Student>> getStudentsInfo(){
        return observe(mStudentService.getStudentsInfo("")).map(new Func1<Students, List<Student>>() {
            @Override
            public List<Student> call(Students students) {
                return students.result;
            }
        });
    }

    public interface StudentService{
        //获取接口信息
        @GET
        Observable<Students> getStudentsInfo(@Url String url);
    }
}
