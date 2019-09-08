package com.arvis.nextcrops.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Handler;
import android.util.Log;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.comm.HttpsClientService;
import com.arvis.nextcrops.event.CommError;
import com.arvis.nextcrops.event.Soil;
import com.arvis.nextcrops.event.SoilChemSample;
import com.arvis.nextcrops.model.Estimate;
import com.arvis.nextcrops.model.Sample;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class QLDApiService {

    private String soilChemApiUrl = "https://soil-chem.information.qld.gov.au/odata/";

    private String susLandApiUrl = "https://land-suit.information.qld.gov.au/api/LandSuitability/";

    private static final String TAG = QLDApiService.class.getSimpleName();

    private Context context;

    public QLDApiService(Context context) {

        this.context = context;
    }

    public void getSoilChemSamples(double lat, double lon, double rad) {

        Handler handler = new Handler(context.getMainLooper());

        InputStream inputStream = context.getResources().openRawResource(R.raw.soil_samples);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];

        int len;

        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }

            List<Sample> samples = new Gson().fromJson(outputStream.toString(), new TypeToken<List<Sample>>() {
            }.getType());


            List<Sample> validSamples = new ArrayList<>();

            for (int i = 0; i < 28; i ++) {

               validSamples.add(samples.get(i));
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    EventBus.getDefault().postSticky(new SoilChemSample(validSamples));
                }
            }, 3000);


        } catch (IOException e) {

            EventBus.getDefault().postSticky(new CommError(e.getMessage()));

        } finally {

            try {

                outputStream.close();

                inputStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }
    }
