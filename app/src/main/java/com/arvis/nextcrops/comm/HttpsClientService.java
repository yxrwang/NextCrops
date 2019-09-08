package com.arvis.nextcrops.comm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpsClientService {

    private static HttpsClientService instance;

    private static final String TAG = HttpsClientService.class.getSimpleName();

    private OkHttpClient httpClient;

    private Context context;

    private HttpsClientService(Context context){

        this.context = context;

        httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(30, TimeUnit.SECONDS).build();
    }

    public static void init(Context context){

        instance = new HttpsClientService(context);
    }

    public static HttpsClientService getInstance(){

        return instance;
    }

    public void setGetRequest(String requestUrl, Callback callBack, Headers headers){

        Request request = new Request.Builder().url(requestUrl).headers(headers).get().build();

        Log.d(TAG, "Request Url: " + requestUrl);

        httpClient.newCall(request).enqueue(callBack);

    }

    public boolean isNetworkConnectionAvailable(){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }
}
