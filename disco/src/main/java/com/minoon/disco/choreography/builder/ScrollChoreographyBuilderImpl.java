package com.minoon.disco.choreography.builder;

import com.minoon.disco.Disco;
import com.minoon.disco.choreography.ScrollChoreography;

/**
 * Created by a13587 on 15/09/15.
 */
/* package */ class ScrollChoreographyBuilderImpl extends BaseChoreographyBuilderImpl<ScrollChoreographyBuilder> implements ScrollChoreographyBuilder {
    private static final String TAG = ScrollChoreographyBuilderImpl.class.getSimpleName();

    private BasicChoreography.BasicScrollTransformer scrollTransformer;

    public ScrollChoreographyBuilderImpl(Disco disco) {
        super(disco);
    }

    @Override
    protected ScrollChoreographyBuilderImpl getInstance() {
        return this;
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
    public ScrollChoreography build() {
        BasicChoreography choreography = new BasicChoreography();
        choreography.setScrollTransformer(scrollTransformer);
        return choreography;
    }


    /**
     * implementation of {@link ScrollChoreographyBuilder.ScrollTransformerBuilder}
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
        private int topOffset = 0;
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
        public ScrollTransformerBuilder topOffset(int topOffset) {
            this.topOffset = topOffset;
            return this;
        }

        @Override
        public ScrollTransformerBuilder multiplier(float multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        @Override
        public ScrollChoreographyBuilder end() {
            ScrollChoreographyBuilderImpl.this.scrollTransformer = new BasicChoreography.BasicScrollTransformer(
                    scrollOrientation, multiplier, offset, topOffset, fromAlpha, fromScaleX, fromScaleY, toAlpha, toScaleX, toScaleY
            );
            return ScrollChoreographyBuilderImpl.this;
        }
    }

}
