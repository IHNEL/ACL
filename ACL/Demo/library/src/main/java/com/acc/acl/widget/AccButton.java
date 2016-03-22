package com.acc.acl.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import com.acc.acl.R;
import com.acc.acl.helper.FontLoader;

/**
 * Created by binhnguyen on 3/22/16.
 * <p>
 * Extend built-in {@link Button} with below features:<p>
 * - Developers are able to set custom font within custom attribute.<p>
 *  + set accFontName with *.ttf file name<p>
 * - Auto add touch effect to button,<p>
 *  + to disable this effect by set touchEffect = false<p>
 *  + to define custom alpha value, set accTouchEffectAlpha<p>
 *  + to define custom touched background color, set accTouchEffectBackground<p>
 *<p>
 *  * Note: the touching effect will be disabled automatically if developers using state-list background<p>
 */
public class AccButton extends Button {
    public AccButton(Context context) {
        super(context);
    }

    public AccButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        extractCustomAttribute(context, attrs);
    }

    public AccButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        extractCustomAttribute(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AccButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        extractCustomAttribute(context, attrs);
    }

    private void extractCustomAttribute(Context context, AttributeSet attrs) {
        Typeface typeface = FontLoader.extractTypeface(context, attrs);
        setTypeface(typeface);

        //check if user disable touch effect
        final TypedArray args = context.obtainStyledAttributes(attrs, R.styleable.AccButton);
        boolean usingTouchEffect = args.getBoolean(R.styleable.AccButton_accEnableTouchEffect, true);
        float touchEffectAlpha = args.getFloat(R.styleable.AccButton_accTouchEffectAlpha, -1);

        int touchEffectBackground = args.getColor(R.styleable.AccButton_accTouchEffectBackground, Color.TRANSPARENT);

        if (getBackground() instanceof StateListDrawable) {
            //Do not use touching effect if user using state-list drawable for button background
            usingTouchEffect = false;
        }

        args.recycle();
        if (usingTouchEffect) {
            setOnTouchListener(new ASetOnTouchListener(this, touchEffectAlpha, touchEffectBackground));
        }
    }

    private static class ASetOnTouchListener implements View.OnTouchListener {

        float touchedAlpha = 0.7f;
        final int FIXED_DURATION = 50;
        float alphaOrginally = 1.0f;
        int touchedBackground;
        Drawable originalBackground;

        public ASetOnTouchListener(View v, float touchAlpha, int touchedBackground) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                this.alphaOrginally = v.getAlpha();
            }
            if (touchAlpha >= 0)
                this.touchedAlpha = touchAlpha;
            this.touchedBackground = touchedBackground;
            this.originalBackground = v.getBackground();
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        final AlphaAnimation animation = new AlphaAnimation(
                                alphaOrginally, touchedAlpha);
                        animation.setDuration(FIXED_DURATION);
                        animation.setFillAfter(true);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (touchedBackground != Color.TRANSPARENT)
                                    v.setBackgroundColor(touchedBackground);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        v.startAnimation(animation);
                    } else {
                        v.animate().setDuration(FIXED_DURATION).alpha(touchedAlpha).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                if (touchedBackground != Color.TRANSPARENT)
                                    v.setBackgroundColor(touchedBackground);
                            }
                        });
                    }
                }
                break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        final AlphaAnimation animation = new AlphaAnimation(
                                touchedAlpha, alphaOrginally);
                        animation.setDuration(FIXED_DURATION);
                        animation.setFillAfter(true);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                v.setBackground(originalBackground);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        v.startAnimation(animation);
                    } else {
                        v.animate().setDuration(FIXED_DURATION).alpha(alphaOrginally).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                v.setBackground(originalBackground);
                            }
                        });
                    }
                }
                break;
            }
            return false;
        }
    }
}
