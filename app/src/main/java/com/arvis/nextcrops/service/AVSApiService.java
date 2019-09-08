package com.arvis.nextcrops.service;

import android.util.Log;

import com.arvis.nextcrops.comm.HttpsClientService;
import com.arvis.nextcrops.event.CommError;
import com.arvis.nextcrops.event.Soil;
import com.arvis.nextcrops.model.Estimate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

public class AVSApiService {

    private String requestURL = "https://api.vic.gov.au/avr/soils-api/v1.0/estimates";

    private static final String TAG = AVSApiService.class.getSimpleName();

    public void getSoilEstimates(String id, double wgs84e, double wgs84n){

        Headers headers = new Headers.Builder().add("apikey", "934efb66-5c7e-46c3-8142-f78431933374")
                .add("Content-Type", "application/json")
                .add("Accept",  "application/json").build();

        String request = requestURL + "?wgs84_east="+wgs84e+ "&wgs84_north=" +wgs84n;

        try {

            HttpsClientService.getInstance().setGetRequest(request, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    Log.e(TAG, "Soil api error: " + e.getMessage());

                    EventBus.getDefault().postSticky(new CommError(e.getMessage()));
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response){

                    Log.i(TAG, "Call: " + call.request().url() + " is successful: " + response.isSuccessful());

                    if(response.isSuccessful()){

                        try {

                            String responseJson = response.body().string();

                            JSONObject jsonObject = new JSONObject(responseJson);

                            EventBus.getDefault().postSticky(new Soil(id, new Gson().fromJson(jsonObject.optJSONObject("estimates").toString(), Estimate.class)));

                        } catch (IOException e) {

                            Log.e(TAG, "Error when parsing response: " + e.getMessage());

                            EventBus.getDefault().postSticky( new CommError(e.getMessage()));

                        } catch (JSONException e) {

                            Log.e(TAG, "Error when parsing Json: " + e.getMessage());

                            EventBus.getDefault().postSticky(new CommError(e.getMessage()));
                        }

                    }else{

                        EventBus.getDefault().postSticky(new CommError(response.message()));
                    }
                }
            }, headers);

        }catch (Exception e){

            EventBus.getDefault().postSticky(new CommError(e.getMessage()));
        }


    }

}
