package com.minoon.disco.choreography.builder;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.minoon.disco.Disco;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a13587 on 15/09/15.
 */
public abstract class BaseChoreographyBuilder<T> {
    private static final String TAG = BaseChoreographyBuilder.class.getSimpleName();
    /* package */ static final float NO_VALUE = Float.MIN_VALUE;

    private final Map<Enum, BasicChoreography.BasicAnimator> eventAnimators;

    protected Disco disco;

    public BaseChoreographyBuilder(Disco disco) {
        eventAnimators = new HashMap<>();
        this.disco = disco;
    }

    protected abstract T getBuilderInstance();

    public EventAnimationBuilder at(Enum e) {
        return new EventAnimationBuilder(e);
    }

    public class EventAnimationBuilder {

        // for event animation
        private Enum event;
        private float alpha = NO_VALUE;
        private float translationX = NO_VALUE;
        private float translationY = NO_VALUE;
        private float scaleX = NO_VALUE;
        private float scaleY = NO_VALUE;
        private long duration = 300;
        private Interpolator interpolator = new LinearInterpolator();

        public EventAnimationBuilder(Enum event) {
            this.event = event;
        }

        public EventAnimationBuilder alpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        public EventAnimationBuilder translationX(float translationX) {
            this.translationX = translationX;
            return this;
        }

        public EventAnimationBuilder translationY(float translationY) {
            this.translationY = translationY;
            return this;
        }

        public EventAnimationBuilder scaleX(float scaleX) {
            this.scaleX = scaleX;
            return this;
        }

        public EventAnimationBuilder scaleY(float scaleY) {
            this.scaleY = scaleY;
            return this;
        }

        public EventAnimationBuilder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public EventAnimationBuilder interpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public T end() {
            BaseChoreographyBuilder.this.eventAnimators.put(event, new BasicChoreography.BasicAnimator(
                    alpha, translationX, translationY, scaleX, scaleY, duration, interpolator
            ));
            return BaseChoreographyBuilder.this.getBuilderInstance();
        }
    }
}
