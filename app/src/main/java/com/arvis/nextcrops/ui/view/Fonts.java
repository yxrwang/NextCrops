package com.arvis.nextcrops.ui.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;

public class Fonts {

    private static final String TAG = Fonts.class.getSimpleName();

    public static final float TEXT_X_SCALE = 0.9f;

    private static Fonts sInstance;

    private HashMap<String, Typeface> fontsMap;

    private final AssetManager assets;


    public static Fonts getInstance(Context context) {

        return Fonts.getInstance(context, null);
    }

    public static Fonts getInstance(Context context, String[] fonts) {

        if (sInstance == null) {
            sInstance = new Fonts(context);
        }

        if (fonts != null) {
            sInstance.loadFonts(fonts);
        }

        return sInstance;
    }



    private Fonts(Context context) {

        this.assets = context.getResources().getAssets();

        this.fontsMap = new HashMap<>();
    }

    private void loadFonts(String[] fonts) {

        for (String font : fonts) {
            loadFont(font);
        }
    }

    private void loadFont(String font) {

        if (!fontsMap.containsKey(font)) {

            Typeface newFont = Typeface.createFromAsset(assets, font);

            if (newFont == null) {

                Log.e(TAG, "WARNING!! Font: \"" + font + "\" could not be found in the assets folder.");
            } else {
                fontsMap.put(font, newFont);
            }
        }
    }

    public Typeface getTypeface(String font) {

        loadFont(font);

        return fontsMap.get(font);
    }
}
