package com.arvis.nextcrops.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.arvis.nextcrops.R;




public class FontedTextView extends AppCompatTextView {


    public FontedTextView(Context context) {
        this(context, null);
    }

    public FontedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public FontedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.FontedTextView);
        
        String customFont = a.getString(R.styleable.FontedTextView_textFont);

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