package com.acc.acl.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.acc.acl.helper.FontLoader;

/**
 * Created by binhnguyen on 3/22/16.
 * <p>
 * Extend built-in {@link CheckBox} with below features:
 * <br>
 * - Developer is able to set custom font within custom attribute.
 * </p>
 */
public class AccCheckBox extends CheckBox {
    public AccCheckBox(Context context) {
        super(context);
    }

    public AccCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadCustomFont(context,attrs);
    }

    public AccCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadCustomFont(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AccCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadCustomFont(context,attrs);
    }

    private void loadCustomFont(Context context, AttributeSet attrs) {
        Typeface typeface = FontLoader.extractTypeface(context, attrs);
        setTypeface(typeface);
    }
}
