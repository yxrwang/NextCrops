package com.arvis.nextcrops.ui.queensland;

import android.content.Intent;
import android.view.View;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.ui.ArvisFragment;
import com.arvis.nextcrops.ui.Splash;

import butterknife.OnClick;

public class QueensLandBurgerMenu extends ArvisFragment {

    @Override
    protected int getViewLayout() {
        return R.layout.queensland_burger_menu;
    }

    @OnClick(R.id.menu_switch_state)
    public void switchLocation(View view){

        getActivity().startActivity(new Intent(getActivity(), Splash.class));

        getActivity().finish();
    }
}
