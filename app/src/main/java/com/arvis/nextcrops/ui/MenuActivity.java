package com.arvis.nextcrops.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.arvis.nextcrops.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class MenuActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(getLayoutView());

        ButterKnife.bind(this);

        initBurgerMenu();

    }

    protected abstract int getLayoutView();

    private void initBurgerMenu(){


        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.draw_open,
                R.string.draw_close
        );

        drawerLayout.addDrawerListener(drawerToggle);

        ActionBar aActionBar = getSupportActionBar();
        if (aActionBar != null) {
            aActionBar.setHomeButtonEnabled(true);
            aActionBar.setDisplayHomeAsUpEnabled(true);
            aActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
