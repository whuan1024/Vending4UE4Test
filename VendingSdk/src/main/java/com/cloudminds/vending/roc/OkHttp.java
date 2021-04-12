package com.cloudminds.vending.roc;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;


public class OkHttp {

    private X509TrustManager trustManager;
    private SSLSocketFactory sslSocketFactory;
    private OkHttpClient mOkHttpClient;

    private static final int TIMEOUT = 30;

    private OkHttp() {
        initTrust();
        initClient();
    }

    private void initClient() {
        mOkHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory).hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();


    }

    private void initTrust() {
        trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        try {
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new X509TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static class OkHttpHolder {
        private static OkHttp sOkHttp = new OkHttp();
    }

    public static OkHttp getInstance() {
        return OkHttpHolder.sOkHttp;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }


}
