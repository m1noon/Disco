package com.minoon.disco.choreography.builder;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.minoon.disco.Disco;
import com.minoon.disco.choreography.Choreography;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a13587 on 15/09/15.
 */
public abstract class BaseChoreographyBuilder<B extends BaseChoreographyBuilder<?, C>, C extends Choreography> {
    private static final String TAG = BaseChoreographyBuilder.class.getSimpleName();
    /* package */ static final float NO_VALUE = BasicChoreography.NO_VALUE;

    protected final Map<Enum, Choreography.Animator> eventAnimators;

    protected Disco disco;

    public BaseChoreographyBuilder(Disco disco) {
        eventAnimators = new HashMap<>();
        this.disco = disco;
    }

    protected abstract C build();

    public EventAnimationBuilder at(Enum e) {
        return new EventAnimationBuilder(e);
    }

    public class EventAnimationBuilder implements InternalBuilder<B, C> {

        Choreography.Animator animator;

        // for event animation
        private Enum event;
        private float alpha = NO_VALUE;
        private float translationX = NO_VALUE;
        private float translationY = NO_VALUE;
        private float scaleX = NO_VALUE;
        private float scaleY = NO_VALUE;
        private long duration = 300;
        private Interpolator interpolator = new LinearInterpolator();

        private EventAnimationBuilder(Enum event) {
            this.event = event;
        }

        public InternalBuilder<B, C> animator(Choreography.Animator animator) {
            this.animator = animator;
            return this;
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

        @Override
        public B end() {
            BaseChoreographyBuilder.this.eventAnimators.put(event, buildAnimator());
            return (B) BaseChoreographyBuilder.this;
        }

        private Choreography.Animator buildAnimator() {
            if (animator != null) {
                return animator;
            }
            return new BasicChoreography.Animator(
                    alpha, translationX, translationY, scaleX, scaleY, duration, interpolator
            );
        }

        @Override
        public C build() {
            return end().build();
        }
    }


    /**
     * Base class to build {@link Choreography.Transformer}.
     *
     * @param <M>
     */
    protected abstract class BaseTransformerBuilder<M extends BaseTransformerBuilder> implements InternalBuilder<B, C> {
        private static final float NO_VALUE = BasicChoreography.NO_VALUE;

        private Choreography.Transformer transformer;

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

        public InternalBuilder<B, C> transformer(Choreography.Transformer transformer) {
            this.transformer = transformer;
            return this;
        }

        public M alpha(float from, float to) {
            fromAlpha = from;
            toAlpha = to;
            return (M) this;
        }

        public M scaleX(float from, float to) {
            fromScaleX = from;
            toScaleX = to;
            return (M) this;
        }

        public M scaleY(float from, float to) {
            fromScaleY = from;
            toScaleY = to;
            return (M) this;
        }

        public M translationY(float from, float to) {
            fromTranslationY = new TranslatePosition(from);
            toTranslationY = new TranslatePosition(to);
            return (M) this;
        }

        public M translationY(Position from, Position to) {
            fromTranslationY = new TranslatePosition(from, 0);
            toTranslationY = new TranslatePosition(to, 0);
            return (M) this;
        }

        public M translationY(Position from, float fromOffset, Position to, float toOffset) {
            fromTranslationY = new TranslatePosition(from, fromOffset);
            toTranslationY = new TranslatePosition(to, toOffset);
            return (M) this;
        }

        public M translationX(float from, float to) {
            fromTranslationX = new TranslatePosition(from);
            toTranslationX = new TranslatePosition(to);
            return (M) this;
        }

        public M translationX(Position from, Position to) {
            fromTranslationX = new TranslatePosition(from, 0);
            toTranslationX = new TranslatePosition(to, 0);
            return (M) this;
        }

        public M translationX(Position from, float fromOffset, Position to, float toOffset) {
            fromTranslationX = new TranslatePosition(from, fromOffset);
            toTranslationX = new TranslatePosition(to, toOffset);
            return (M) this;
        }

        public M interpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return (M) this;
        }

        protected Choreography.Transformer buildTransformer() {
            if (transformer != null) {
                return transformer;
            }

            return new BasicChoreography.Transformer(fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY,
                    toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY, interpolator);
        }
    }


    /**
     * Base class to build {@link Choreography.Animator}
     *
     * @param <M>
     */
    protected abstract class BaseAnimatorBuilder<M extends BaseAnimatorBuilder> implements InternalBuilder<B, C> {
        private static final float NO_VALUE = BasicChoreography.NO_VALUE;

        private Choreography.Animator fromAnimator;
        private Choreography.Animator toAnimator;

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

        public InternalBuilder<B, C> animator(Choreography.Animator fromAnimator, Choreography.Animator toAnimator) {
            this.fromAnimator = fromAnimator;
            this.toAnimator = toAnimator;
            return this;
        }

        public M alpha(float from, float to) {
            fromAlpha = from;
            toAlpha = to;
            return (M) this;
        }

        public M translationY(float from, float to) {
            fromTranslationY = from;
            toTranslationY = to;
            return (M) this;
        }

        public M translationX(float from, float to) {
            fromTranslationX = from;
            toTranslationX = to;
            return (M) this;
        }

        public M scaleX(float from, float to) {
            fromScaleX = from;
            toScaleX = to;
            return (M) this;
        }

        public M scaleY(float from, float to) {
            fromScaleY = from;
            toScaleY = to;
            return (M) this;
        }

        public M duration(long duration) {
            this.duration = duration;
            return (M) this;
        }

        public M interpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return (M) this;
        }

        protected Choreography.Animator buildFromAnimator() {
            if (fromAnimator != null) {
                return fromAnimator;
            }
            return new BasicChoreography.Animator(fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY, duration, interpolator);
        }

        protected Choreography.Animator buildToAnimator() {
            if (toAnimator != null) {
                return toAnimator;
            }
            return new BasicChoreography.Animator(toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY, duration, interpolator);
        }
    }
}
