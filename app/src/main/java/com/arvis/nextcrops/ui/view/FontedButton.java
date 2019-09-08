package com.arvis.nextcrops.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.arvis.nextcrops.R;


public class FontedButton extends AppCompatButton {

	
    public FontedButton(Context context) {
		super(context);
        setCustomFont(context, null);

	}

    public FontedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public FontedButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {

        setAllCaps(false);

        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.FontedButton);
       
        String customFont = a.getString(R.styleable.FontedButton_textFont);
        
        if(customFont == null) {
        	customFont = ctx.getResources().getString(R.string.default_font);
        }
        
        Typeface tf = Fonts.getInstance(ctx).getTypeface(customFont);
        
        if(tf != null) {
        	setTypeface(tf);
        }
        
        a.recycle();
    }


}