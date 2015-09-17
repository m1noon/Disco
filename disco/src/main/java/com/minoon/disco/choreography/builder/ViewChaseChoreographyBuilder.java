package com.minoon.disco.choreography.builder;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.minoon.disco.Disco;
import com.minoon.disco.ViewParam;
import com.minoon.disco.choreography.ViewChaseChoreography;

/**
 * Created by a13587 on 15/09/15.
 */
public class ViewChaseChoreographyBuilder extends BaseChoreographyBuilder<ViewChaseChoreographyBuilder> {
    private static final String TAG = ViewChaseChoreographyBuilder.class.getSimpleName();

    private BasicChoreography.BasicViewTagAnimator viewTagAnimator;

    private BasicChoreography.BasicViewTransformer transformer;

    public ViewChaseChoreographyBuilder(Disco disco) {
        super(disco);
    }

    @Override
    protected ViewChaseChoreographyBuilder getBuilderInstance() {
        return this;
    }

    public ViewTransformerBuilder onChange(ViewParam param, float from, float to) {
        return new ViewTransformerBuilder(param, from, to);
    }

    public ViewTagAnimatorBuilder atTag(ViewParam param, float boundary) {
        return new ViewTagAnimatorBuilder(param, boundary);
    }

    public ViewChaseChoreography build() {
        BasicChoreography choreography = new BasicChoreography();
        choreography.setViewTransformer(transformer);
        choreography.setViewTagAnimator(viewTagAnimator);
        if (viewTagAnimator != null) {
            viewTagAnimator.setDisco(disco);
        }
        return choreography;
    }


    public final class ViewTransformerBuilder {

        // for view dependent transformation
        private final ViewParam viewParam;

        private final float fromBounds;
        private final float toBounds;

        private float fromAlpha = NO_VALUE;
        private TranslatePosition fromTranslationX;
        private TranslatePosition fromTranslationY;
        private float fromScaleX = NO_VALUE;
        private float fromScaleY = NO_VALUE;

        private float toAlpha = NO_VALUE;
        private TranslatePosition toTranslationX = null;
        private TranslatePosition toTranslationY = null;
        private float toScaleX = NO_VALUE;
        private float toScaleY = NO_VALUE;

        Interpolator interpolator = new LinearInterpolator();

        public ViewTransformerBuilder(ViewParam viewParam, float fromBounds, float toBounds) {
            this.viewParam = viewParam;
            this.fromBounds = fromBounds;
            this.toBounds = toBounds;
        }

        public ViewTransformerBuilder alpha(float from, float to) {
            fromAlpha = from;
            toAlpha = to;
            return this;
        }

        public ViewTransformerBuilder translationY(float from, float to) {
            fromTranslationY = new TranslatePosition(from);
            toTranslationY = new TranslatePosition(to);
            return this;
        }

        public ViewTransformerBuilder translattionY(Position from, Position to) {
            fromTranslationY = new TranslatePosition(from, 0);
            toTranslationY = new TranslatePosition(to, 0);
            return this;
        }

        public ViewTransformerBuilder translattionY(Position from, float fromOffset, Position to, float toOffset) {
            fromTranslationY = new TranslatePosition(from, fromOffset);
            toTranslationY = new TranslatePosition(to, toOffset);
            return this;
        }

        public ViewTransformerBuilder translationX(float from, float to) {
            fromTranslationX = new TranslatePosition(from);
            toTranslationX = new TranslatePosition(to);
            return this;
        }

        public ViewTransformerBuilder translationX(Position from, Position to) {
            fromTranslationX = new TranslatePosition(from, 0);
            toTranslationX = new TranslatePosition(to, 0);
            return this;
        }

        public ViewTransformerBuilder translationX(Position from, float fromOffset, Position to, float toOffset) {
            fromTranslationX = new TranslatePosition(from, fromOffset);
            toTranslationX = new TranslatePosition(to, toOffset);
            return this;
        }

        public ViewTransformerBuilder scaleX(float from, float to) {
            fromScaleX = from;
            toScaleX = to;
            return this;
        }

        public ViewTransformerBuilder scaleY(float from, float to) {
            fromScaleY = from;
            toScaleY = to;
            return this;
        }

        public ViewTransformerBuilder interpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public ViewChaseChoreographyBuilder end() {
            ViewChaseChoreographyBuilder.this.transformer = new BasicChoreography.BasicViewTransformer(
                    viewParam, fromBounds, toBounds, fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY,
                    toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY, interpolator
            );
            return ViewChaseChoreographyBuilder.this;
        }
    }


    public final class ViewTagAnimatorBuilder {

        // for view dependent transformation
        private final ViewParam viewParam;

        private final float boundary;

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

        private Enum onBackEvent;
        private Enum onForwardEvent;

        public ViewTagAnimatorBuilder(ViewParam viewParam, float boundary) {
            this.viewParam = viewParam;
            this.boundary = boundary;
        }

        public ViewTagAnimatorBuilder alpha(float from, float to) {
            fromAlpha = from;
            toAlpha = to;
            return this;
        }

        public ViewTagAnimatorBuilder translationY(float from, float to) {
            fromTranslationY = from;
            toTranslationY = to;
            return this;
        }

        public ViewTagAnimatorBuilder translationX(float from, float to) {
            fromTranslationX = from;
            toTranslationX = to;
            return this;
        }

        public ViewTagAnimatorBuilder scaleX(float from, float to) {
            fromScaleX = from;
            toScaleX = to;
            return this;
        }

        public ViewTagAnimatorBuilder scaleY(float from, float to) {
            fromScaleY = from;
            toScaleY = to;
            return this;
        }

        public ViewTagAnimatorBuilder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public ViewTagAnimatorBuilder interpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public ViewTagAnimatorBuilder notifyEvent(Enum beforeEvent, Enum afterEvent) {
            this.onBackEvent = beforeEvent;
            this.onForwardEvent = afterEvent;
            return this;
        }

        public ViewChaseChoreographyBuilder end() {
            ViewChaseChoreographyBuilder.this.viewTagAnimator = new BasicChoreography.BasicViewTagAnimator(
                    viewParam, boundary, fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY,
                    toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY, duration, onBackEvent, onForwardEvent, interpolator
            );
            return ViewChaseChoreographyBuilder.this;
        }
    }
}
