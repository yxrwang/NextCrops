package com.arvis.nextcrops.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.ui.queensland.QueenslandMain;
import com.arvis.nextcrops.ui.vic.VicMain;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Splash extends AppCompatActivity {

    private static final int REQ_REQUEST_PERMISSION = 499;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        setContentView(R.layout.splash);

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 23) {

            checkPermission();

        }
    }

    @OnClick(R.id.btn_vic)
    public void startVicSoil(View view){

        startActivity(new Intent(this, VicMain.class));

        finish();
    }

    @OnClick(R.id.btn_queensland)
    public void startQueenlandSoil(View view){

        startActivity(new Intent(this, QueenslandMain.class));

        finish();
    }

    private void checkPermission() {

        List<String> permissions = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            permissions.add(Manifest.permission.CAMERA);

        }


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(!permissions.isEmpty()){

            String[] permissionsToRequest = new String[permissions.size()];

            for(int i=0;i<permissions.size();i++){

                permissionsToRequest[i] = permissions.get(i);
            }

            ActivityCompat.requestPermissions(this, permissionsToRequest, REQ_REQUEST_PERMISSION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_REQUEST_PERMISSION) {

            if (grantResults.length > 0) {

                boolean isPermissionDenied = false;

                for(int result : grantResults){

                    if(result != PackageManager.PERMISSION_GRANTED){

                        isPermissionDenied = true;

                        break;
                    }
                }

                if(isPermissionDenied)
                    finish();
            }
        }
    }
}
