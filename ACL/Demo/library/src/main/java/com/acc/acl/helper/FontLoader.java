package com.acc.acl.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.acc.acl.R;

/**
 * Created by binhnguyen on 3/21/16.
 * <p>
 * This class provides convenient methods to setup {@link Typeface} based on custom attributes
 */
public class FontLoader {

    /**
     * This method initializes an instance of class {@link Typeface} based on given custom attributes.
     *
     * @param context The current application context
     * @param attrs   The attribute set loaded from xml file
     * @return {@link Typeface} instance if given attributes are valid. null for otherwise.
     */
    public static Typeface extractTypeface(final Context context, final AttributeSet attrs) {
        final TypedArray args = context.obtainStyledAttributes(attrs, R.styleable.AccFont);
        final String family = args.getString(R.styleable.AccFont_accFontName);
        args.recycle();

        if (family == null) {
            return null;
        }
        return Typeface.createFromAsset(context.getAssets(), family);
    }
}
