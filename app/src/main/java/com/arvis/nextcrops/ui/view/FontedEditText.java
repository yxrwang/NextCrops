package com.arvis.nextcrops.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.arvis.nextcrops.R;


public class FontedEditText extends AppCompatEditText {

    public FontedEditText(Context context) {

        this(context, null);
    }

    public FontedEditText(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        setCustomFont(context, attrs);
    }


    public FontedEditText(Context context, AttributeSet attrs) {

        super(context, attrs);

        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontedEditText);

        String customFont = typedArray.getString(R.styleable.FontedEditText_textFont);

        if (customFont == null) {
            customFont = context.getResources().getString(R.string.default_font);
        }

        Typeface typeFace = Fonts.getInstance(context).getTypeface(customFont);

        if (typeFace != null) {
            setTypeface(typeFace);
        }

        typedArray.recycle();
    }
}
