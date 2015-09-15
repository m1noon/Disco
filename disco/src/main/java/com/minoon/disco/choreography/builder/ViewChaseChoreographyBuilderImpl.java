package com.minoon.disco.choreography.builder;

import com.minoon.disco.Disco;
import com.minoon.disco.ViewParam;
import com.minoon.disco.choreography.ViewChaseChoreography;

/**
 * Created by a13587 on 15/09/15.
 */
/* package */ class ViewChaseChoreographyBuilderImpl extends BaseChoreographyBuilderImpl<ViewChaseChoreographyBuilder> implements ViewChaseChoreographyBuilder {
    private static final String TAG = ViewChaseChoreographyBuilderImpl.class.getSimpleName();

    private BasicChoreography.BasicViewTagAnimator viewTagAnimator;

    private BasicChoreography.BasicViewTransformer transformer;

    public ViewChaseChoreographyBuilderImpl(Disco disco) {
        super(disco);
    }

    @Override
    protected ViewChaseChoreographyBuilder getInstance() {
        return this;
    }

    @Override
    public ViewTransformerBuilder onChange(ViewParam param, float from, float to) {
        return new ViewTransformerBuilderImpl(param, from, to);
    }

    @Override
    public ViewTagAnimatorBuilder atTag(ViewParam param, float boundary) {
        return new ViewTagAnimatorBuilderImpl(param, boundary);
    }

    @Override
    public ViewChaseChoreography build() {
        BasicChoreography choreography = new BasicChoreography();
        choreography.setViewTransformer(transformer);
        choreography.setViewTagAnimator(viewTagAnimator);
        if (viewTagAnimator != null) {
            viewTagAnimator.setDisco(disco);
        }
        return choreography;
    }

    /**
     * implementation of {@link ViewChaseChoreographyBuilder.ViewTransformerBuilder}
     */
    private final class ViewTransformerBuilderImpl implements ViewTransformerBuilder {

        // for view dependent transformation
        private final ViewParam viewParam;

        private final float fromBounds;
        private final float toBounds;

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

        public ViewTransformerBuilderImpl(ViewParam viewParam, float fromBounds, float toBounds) {
            this.viewParam = viewParam;
            this.fromBounds = fromBounds;
            this.toBounds = toBounds;
        }

        @Override
        public ViewTransformerBuilder alpha(float from, float to) {
            fromAlpha = from;
            toAlpha = to;
            return this;
        }

        @Override
        public ViewTransformerBuilder translationY(float from, float to) {
            fromTranslationY = from;
            toTranslationY = to;
            return this;
        }

        @Override
        public ViewTransformerBuilder translationX(float from, float to) {
            fromTranslationX = from;
            toTranslationX = to;
            return this;
        }

        @Override
        public ViewTransformerBuilder scaleX(float from, float to) {
            fromScaleX = from;
            toScaleX = to;
            return this;
        }

        @Override
        public ViewTransformerBuilder scaleY(float from, float to) {
            fromScaleY = from;
            toScaleY = to;
            return this;
        }

        @Override
        public ViewChaseChoreographyBuilder end() {
            ViewChaseChoreographyBuilderImpl.this.transformer = new BasicChoreography.BasicViewTransformer(
                    viewParam, fromBounds, toBounds, fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY,
                    toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY
            );
            return ViewChaseChoreographyBuilderImpl.this;
        }
    }


    private final class ViewTagAnimatorBuilderImpl implements ViewTagAnimatorBuilder {

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

        private Enum onBackEvent;
        private Enum onForwardEvent;

        public ViewTagAnimatorBuilderImpl(ViewParam viewParam, float boundary) {
            this.viewParam = viewParam;
            this.boundary = boundary;
        }

        @Override
        public ViewTagAnimatorBuilder alpha(float from, float to) {
            fromAlpha = from;
            toAlpha = to;
            return this;
        }

        @Override
        public ViewTagAnimatorBuilder translationY(float from, float to) {
            fromTranslationY = from;
            toTranslationY = to;
            return this;
        }

        @Override
        public ViewTagAnimatorBuilder translationX(float from, float to) {
            fromTranslationX = from;
            toTranslationX = to;
            return this;
        }

        @Override
        public ViewTagAnimatorBuilder scaleX(float from, float to) {
            fromScaleX = from;
            toScaleX = to;
            return this;
        }

        @Override
        public ViewTagAnimatorBuilder scaleY(float from, float to) {
            fromScaleY = from;
            toScaleY = to;
            return this;
        }

        @Override
        public ViewTagAnimatorBuilder duration(long duration) {
            this.duration = duration;
            return this;
        }

        @Override
        public ViewTagAnimatorBuilder notifyEvent(Enum beforeEvent, Enum afterEvent) {
            this.onBackEvent = beforeEvent;
            this.onForwardEvent = afterEvent;
            return this;
        }

        @Override
        public ViewChaseChoreographyBuilderImpl end() {
            ViewChaseChoreographyBuilderImpl.this.viewTagAnimator = new BasicChoreography.BasicViewTagAnimator(
                    viewParam, boundary, fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY,
                    toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY, duration, onBackEvent, onForwardEvent
            );
            return ViewChaseChoreographyBuilderImpl.this;
        }
    }
}
