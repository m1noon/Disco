package com.minoon.disco;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by a13587 on 15/09/11.
 */
/* package */ class ChoreographyBuilderImpl implements ChoreographyBuilder {
    private static final String TAG = Logger.createTag(ChoreographyBuilderImpl.class.getSimpleName());
    private static final float NO_VALUE = BasicChoreography.NO_VALUE;

    private final Disco disco;

    private final Map<Enum, BasicChoreography.BasicAnimator> eventAnimators;

    private BasicChoreography.BasicViewTagAnimator viewTagAnimator;

    private BasicChoreography.BasicViewTransformer transformer;

    private BasicChoreography.BasicScrollTransformer scrollTransformer;

    /**
     * Constructor
     *
     * @param disco
     */
    /* package */ ChoreographyBuilderImpl(Disco disco) {
        eventAnimators = new HashMap<>();
        this.disco = disco;
    }


    /** {@link ChoreographyBuilder} */


    @Override
    public EventAnimationBuilder at(Enum e) {
        return new EventAnimationBuilderImpl(e);
    }

    @Override
    public ViewTagAnimatorBuilder atTag(ViewParam param, float boundary) {
        return new ViewTagAnimatorBuilderImpl(param, boundary);
    }

    @Override
    public ViewTransformerBuilder onChange(ViewParam param, float from, float to) {
        return new ViewTransformerBuilderImpl(param, from, to);
    }

    @Override
    public ScrollTransformerBuilder onScrollVertical() {
        return new ScrollTransformerBuilderImpl(BasicChoreography.BasicScrollTransformer.VERTICAL);
    }

    @Override
    public ScrollTransformerBuilder onScrollHorizontal() {
        return new ScrollTransformerBuilderImpl(BasicChoreography.BasicScrollTransformer.HORIZONTAL);
    }

    @Override
    public Choreography build() {
        BasicChoreography choreography = new BasicChoreography(disco);
        choreography.addEventAnimators(eventAnimators);
        choreography.setViewTransformer(transformer);
        choreography.setScrollTransformer(scrollTransformer);
        choreography.setViewTagAnimator(viewTagAnimator);
        return choreography;
    }


    /**
     * implementation of {@link ScrollTransformerBuilder}
     */
    private final class ScrollTransformerBuilderImpl implements ScrollTransformerBuilder {
        // for scroll transformation
        private int scrollOrientation;

        private float fromAlpha = NO_VALUE;
        private float fromScaleX = NO_VALUE;
        private float fromScaleY = NO_VALUE;

        private float toAlpha = NO_VALUE;
        private float toScaleX = NO_VALUE;
        private float toScaleY = NO_VALUE;

        private int offset = 0;
        private float multiplier = 1;

        public ScrollTransformerBuilderImpl(int scrollOrientation) {
            this.scrollOrientation = scrollOrientation;
        }

        @Override
        public ScrollTransformerBuilder alpha(float from, float to) {
            fromAlpha = from;
            toAlpha = to;
            return this;
        }

        @Override
        public ScrollTransformerBuilder scaleX(float from, float to) {
            fromScaleX = from;
            toScaleX = to;
            return this;
        }

        @Override
        public ScrollTransformerBuilder scaleY(float from, float to) {
            fromScaleY = from;
            toScaleY = to;
            return this;
        }

        @Override
        public ScrollTransformerBuilder offset(int offset) {
            this.offset = offset;
            return this;
        }

        @Override
        public ScrollTransformerBuilder multiplier(float multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        @Override
        public ChoreographyBuilder end() {
            ChoreographyBuilderImpl.this.scrollTransformer = new BasicChoreography.BasicScrollTransformer(
                    scrollOrientation, multiplier, offset, fromAlpha, fromScaleX, fromScaleY, toAlpha, toScaleX, toScaleY
            );
            return ChoreographyBuilderImpl.this;
        }
    }


    /**
     * implementation of {@link EventAnimationBuilder}
     */
    private final class EventAnimationBuilderImpl implements EventAnimationBuilder {

        // for event animation
        private Enum event;
        private float alpha = NO_VALUE;
        private float translationX = NO_VALUE;
        private float translationY = NO_VALUE;
        private float scaleX = NO_VALUE;
        private float scaleY = NO_VALUE;
        private long duration = 300;

        public EventAnimationBuilderImpl(Enum event) {
            this.event = event;
        }

        @Override
        public EventAnimationBuilder alpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        @Override
        public EventAnimationBuilder translationX(float translationX) {
            this.translationX = translationX;
            return this;
        }

        @Override
        public EventAnimationBuilder translationY(float translationY) {
            this.translationY = translationY;
            return this;
        }

        @Override
        public EventAnimationBuilder scaleX(float scaleX) {
            this.scaleX = scaleX;
            return this;
        }

        @Override
        public EventAnimationBuilder scaleY(float scaleY) {
            this.scaleY = scaleY;
            return this;
        }

        @Override
        public EventAnimationBuilder duration(long duration) {
            this.duration = duration;
            return this;
        }

        @Override
        public ChoreographyBuilder end() {
            ChoreographyBuilderImpl.this.eventAnimators.put(event, new BasicChoreography.BasicAnimator(
                    alpha, translationX, translationY, scaleX, scaleY, duration
            ));
            return ChoreographyBuilderImpl.this;
        }
    }


    /**
     * implementation of {@link ViewTransformerBuilder}
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
        public ChoreographyBuilder end() {
            ChoreographyBuilderImpl.this.transformer = new BasicChoreography.BasicViewTransformer(
                    viewParam, fromBounds, toBounds, fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY,
                    toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY
            );
            return ChoreographyBuilderImpl.this;
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
        public ChoreographyBuilder end() {
            ChoreographyBuilderImpl.this.viewTagAnimator = new BasicChoreography.BasicViewTagAnimator(
                    viewParam, boundary, fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY,
                    toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY, duration
            );
            return ChoreographyBuilderImpl.this;
        }
    }
}
