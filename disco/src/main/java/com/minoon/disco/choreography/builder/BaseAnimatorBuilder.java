package com.minoon.disco.choreography.builder;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by a13587 on 15/09/17.
 */
public abstract class BaseAnimatorBuilder<T extends BaseAnimatorBuilder> {
    private static final String TAG = BaseAnimatorBuilder.class.getSimpleName();
    private static final float NO_VALUE = BasicChoreography.NO_VALUE;

    private float fromAlpha = NO_VALUE;
    private float fromTranslationX = NO_VALUE;
    private float fromTranslationY = NO_VALUE;
    private float fromScaleX = NO_VALUE;
    private float fromScaleY = NO_VALUE;

    private float toAlpha = NO_VALUE;
    private float toTranslationX = NO_VALUE;
    private float toTranslationY = NO_VALUE;
    private float toScaleX = NO_VALUE;
    private float toScaleY = NO_VALUE;

    private long duration = 300;

    private Interpolator interpolator = new LinearInterpolator();

    public T alpha(float from, float to) {
        fromAlpha = from;
        toAlpha = to;
        return (T) this;
    }

    public T translationY(float from, float to) {
        fromTranslationY = from;
        toTranslationY = to;
        return (T) this;
    }

    public T translationX(float from, float to) {
        fromTranslationX = from;
        toTranslationX = to;
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

    public T duration(long duration) {
        this.duration = duration;
        return (T) this;
    }

    public T interpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return (T) this;
    }

    protected BasicChoreography.Animator buildFromAnimator() {
        return new BasicChoreography.Animator(fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY, duration, interpolator);
    }

    protected BasicChoreography.Animator buildToAnimator() {
        return new BasicChoreography.Animator(toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY, duration, interpolator);
    }
}
