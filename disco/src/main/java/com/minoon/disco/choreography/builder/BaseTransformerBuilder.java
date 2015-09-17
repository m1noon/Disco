package com.minoon.disco.choreography.builder;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by a13587 on 15/09/17.
 */
public abstract class BaseTransformerBuilder<T extends BaseTransformerBuilder> {
    private static final String TAG = BaseTransformerBuilder.class.getSimpleName();
    private static final float NO_VALUE = BasicChoreography.NO_VALUE;

    private float fromAlpha = NO_VALUE;
    private float fromScaleX = NO_VALUE;
    private float fromScaleY = NO_VALUE;
    private TranslatePosition fromTranslationX;
    private TranslatePosition fromTranslationY;

    private float toAlpha = NO_VALUE;
    private float toScaleX = NO_VALUE;
    private float toScaleY = NO_VALUE;
    private TranslatePosition toTranslationX;
    private TranslatePosition toTranslationY;
    private Interpolator interpolator = new LinearInterpolator();

    public T alpha(float from, float to) {
        fromAlpha = from;
        toAlpha = to;
        return (T) this;
    }

    public T scaleX(float from, float to) {
        fromScaleX = from;
        toScaleX = to;
        return (T) this;
    }

    public T scaleY(float from, float to) {
        fromScaleY = from;
        toScaleY = to;
        return (T) this;
    }

    public T translationY(float from, float to) {
        fromTranslationY = new TranslatePosition(from);
        toTranslationY = new TranslatePosition(to);
        return (T) this;
    }

    public T translationY(Position from, Position to) {
        fromTranslationY = new TranslatePosition(from, 0);
        toTranslationY = new TranslatePosition(to, 0);
        return (T) this;
    }

    public T translationY(Position from, float fromOffset, Position to, float toOffset) {
        fromTranslationY = new TranslatePosition(from, fromOffset);
        toTranslationY = new TranslatePosition(to, toOffset);
        return (T) this;
    }

    public T translationX(float from, float to) {
        fromTranslationX = new TranslatePosition(from);
        toTranslationX = new TranslatePosition(to);
        return (T) this;
    }

    public T translationX(Position from, Position to) {
        fromTranslationX = new TranslatePosition(from, 0);
        toTranslationX = new TranslatePosition(to, 0);
        return (T) this;
    }

    public T translationX(Position from, float fromOffset, Position to, float toOffset) {
        fromTranslationX = new TranslatePosition(from, fromOffset);
        toTranslationX = new TranslatePosition(to, toOffset);
        return (T) this;
    }

    public T interpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return (T) this;
    }

    protected BasicChoreography.Transformer buildTransformer() {
        return new BasicChoreography.Transformer(fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY,
                toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY, interpolator);
    }
}
