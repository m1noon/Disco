package com.minoon.disco.choreography.builder;

import com.minoon.disco.Disco;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a13587 on 15/09/15.
 */
/* package */ abstract class BaseChoreographyBuilderImpl<T> implements BaseChoreographyBuilder<T> {
    private static final String TAG = BaseChoreographyBuilderImpl.class.getSimpleName();
    /* package */ static final float NO_VALUE = Float.MIN_VALUE;

    private final Map<Enum, BasicChoreography.BasicAnimator> eventAnimators;

    protected Disco disco;

    public BaseChoreographyBuilderImpl(Disco disco) {
        eventAnimators = new HashMap<>();
        this.disco = disco;
    }

    protected abstract T getInstance();

    @Override
    public EventAnimationBuilder<T> at(Enum e) {
        return new BaseChoreographyBuilderImpl.EventAnimationBuilderImpl(e);
    }

    /**
     * implementation of {@link BaseChoreographyBuilder.EventAnimationBuilder}
     */
    private class EventAnimationBuilderImpl implements BaseChoreographyBuilder.EventAnimationBuilder<T> {

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
        public BaseChoreographyBuilder.EventAnimationBuilder<T> alpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        @Override
        public BaseChoreographyBuilder.EventAnimationBuilder<T> translationX(float translationX) {
            this.translationX = translationX;
            return this;
        }

        @Override
        public BaseChoreographyBuilder.EventAnimationBuilder<T> translationY(float translationY) {
            this.translationY = translationY;
            return this;
        }

        @Override
        public BaseChoreographyBuilder.EventAnimationBuilder<T> scaleX(float scaleX) {
            this.scaleX = scaleX;
            return this;
        }

        @Override
        public BaseChoreographyBuilder.EventAnimationBuilder<T> scaleY(float scaleY) {
            this.scaleY = scaleY;
            return this;
        }

        @Override
        public BaseChoreographyBuilder.EventAnimationBuilder<T> duration(long duration) {
            this.duration = duration;
            return this;
        }

        @Override
        public T end() {
            BaseChoreographyBuilderImpl.this.eventAnimators.put(event, new BasicChoreography.BasicAnimator(
                    alpha, translationX, translationY, scaleX, scaleY, duration
            ));
            return BaseChoreographyBuilderImpl.this.getInstance();
        }
    }
}
