package com.nz.nztravelmate.webservice;

import android.content.Context;

import com.nz.nztravelmate.R;
import com.nz.nztravelmate.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public RetrofitClient() {
    }

    public static Retrofit getClient(Context context) {
    int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (NetworkUtils.getConnectivityStatus(context)!=0) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                })
               // .addNetworkInterceptor(onlineInterceptor)
                .build();

       /* OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
                                      @Override
                                      public Response intercept(Interceptor.Chain chain) throws IOException {
                                          Request original = chain.request();

                                          Request request = original.newBuilder()
                                                  .header("User-Agent", context.getResources().getString(R.string.app_name))
                                                  .header("Accept", "application/vnd.yourapi.v1.full+json")
                                                  .method(original.method(), original.body())
                                                  .build();

                                          return chain.proceed(request);
                                      }
                                  });

                OkHttpClient client = httpClient.build();*/
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    static Interceptor onlineInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            int maxAge = 60; // read from cache for 60 seconds even if there is internet connection
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    static Interceptor offlineInterceptor= new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
           // if (!isInternetAvailable()) {
                int maxStale = 60 * 60 * 24 * 30; // Offline cache available for 30 days
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
           // }
            return chain.proceed(request);
        }
    };
}
