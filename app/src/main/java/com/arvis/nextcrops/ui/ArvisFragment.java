package com.arvis.nextcrops.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.arvis.nextcrops.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ArvisFragment extends Fragment {

    @Nullable
    @BindView(R.id.progress_view)
    View progressView;

    @Nullable
    @BindView(R.id.progress_status)
    TextView progressText;

    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getViewLayout(), container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    protected abstract int getViewLayout();

    @Override
    public void onDestroy() {

        super.onDestroy();

        if(unbinder != null){

            unbinder.unbind();
        }
    }

    protected void createProgressDialog(@StringRes int textRes){

        if(progressView != null && progressText != null){

            progressView.setVisibility(View.VISIBLE);

            progressText.setText(textRes);

        }
    }

    protected void removeProgressDialog(){

        if(progressView != null){

            progressView.setVisibility(View.GONE);
        }
    }
}
