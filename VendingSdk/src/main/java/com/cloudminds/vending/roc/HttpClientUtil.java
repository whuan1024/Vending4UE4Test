package com.cloudminds.vending.roc;

import com.cloudminds.vending.utils.LogUtil;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientUtil {

    private static HttpClientUtil instance = null;

    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        if (instance == null) {
            instance = new HttpClientUtil();
        }
        return instance;
    }


    public HttpClientResponse sendHttpGet(String httpUrl, Map<String, String> headMap) {
        Request.Builder builder = new Request.Builder()
                .url(httpUrl)
                .get();

        if (headMap != null) {
            for (String key : headMap.keySet()) {
                builder.addHeader(key, headMap.get(key));
            }
        }

        return sendHttpRequest(builder.build());
    }


    /**
     * @param httpUrl
     * @param headMap
     * @param multipartBody
     * @return
     */
    public HttpClientResponse sendHttpPost(String httpUrl, Map<String, String> headMap, MultipartBody multipartBody) {

        Request.Builder builder = new Request.Builder()
                .url(httpUrl).post(multipartBody);

        if (headMap != null) {
            for (String key : headMap.keySet()) {
                builder.addHeader(key, headMap.get(key));
            }
        }

        return sendHttpRequest(builder.build());
    }

    public HttpClientResponse sendHttpRequest(Request request) {

        LogUtil.d("[HttpClientUtil] sendHttpRequest: request=" + request.toString());

        OkHttpClient okHttpClient = OkHttp.getInstance().getOkHttpClient();

        HttpClientResponse responseObject = new HttpClientResponse();

        Call call = okHttpClient.newCall(request);

        try {

            Response response = call.execute();

            if (response.isSuccessful()) {
                responseObject.setStatus(response.code());
                responseObject.setMessage(response.body().string());
            } else {
                LogUtil.e("[HttpClientUtil] http request failed");
            }
        } catch (Exception e) {
            LogUtil.e("[HttpClientUtil] sendHttpRequest Exception: " + e);
        }

        return responseObject;
    }

}
