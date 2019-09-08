package com.arvis.nextcrops.ui.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arvis.nextcrops.ui.ArvisFragment;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class CameraScannerFragment extends ArvisFragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mScannerView = new ZXingScannerView(getActivity());

        mScannerView.setResultHandler(this);

        return mScannerView;
    }

    @Override
    protected int getViewLayout() {
        return 0;
    }

    @Override
    public void onResume() {

        super.onResume();

        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {


    }

    @Override
    public void onPause() {

        super.onPause();

        mScannerView.stopCamera();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        mScannerView.setResultHandler(null);
    }
}
