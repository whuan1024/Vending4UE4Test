package com.cloudminds.vending.net;

import com.cloudminds.vending.utils.FileUtil;
import com.cloudminds.vending.utils.LogUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static final String BASE_URL = FileUtil.getDomain() + "/crss-mobile/cves/v1/vending/";
    private static final int TIMEOUT = 10;

    private static volatile RetrofitUtil mInstance;
    private Retrofit mRetrofit;

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtil();
                }
            }
        }
//        ping(Uri.parse(BASE_URL).getHost());
        return mInstance;
    }

    private static void ping(String host) {
        try {
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 1 " + host);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String content = "";
            while ((content = br.readLine()) != null) {
                sb.append(content);
            }
            LogUtil.i("[RetrofitUtil] ping result: " + sb.toString());
//            int status = p.waitFor();
//            if (status == 0) {
//                LogUtil.i("[RetrofitUtil] ping " + host + " success!");
//            } else {
//                LogUtil.e("[RetrofitUtil] ping " + host + " failed!");
//            }
        } catch (Exception e) {
            LogUtil.e("[RetrofitUtil] Failed to ping", e);
        }
    }

    private RetrofitUtil() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
//            LogUtil.d("[RetrofitUtil] httpLog: " + message);  //too many logs, open it for test only
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }
}
