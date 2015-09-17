package com.minoon.disco.choreography.builder;

import com.minoon.disco.Disco;
import com.minoon.disco.choreography.ScrollChoreography;

/**
 * Created by a13587 on 15/09/15.
 */
public class ScrollChoreographyBuilder extends BaseChoreographyBuilder<ScrollChoreographyBuilder> {
    private static final String TAG = ScrollChoreographyBuilder.class.getSimpleName();

    private BasicChoreography.BasicScrollTransformer scrollTransformer;

    public ScrollChoreographyBuilder(Disco disco) {
        super(disco);
    }

    @Override
    protected ScrollChoreographyBuilder getBuilderInstance() {
        return this;
    }

    public ScrollTransformerBuilder onScrollVertical() {
        return new ScrollTransformerBuilder(BasicChoreography.BasicScrollTransformer.VERTICAL);
    }


    public ScrollTransformerBuilder onScrollHorizontal() {
        return new ScrollTransformerBuilder(BasicChoreography.BasicScrollTransformer.HORIZONTAL);
    }


    public ScrollChoreography build() {
        BasicChoreography choreography = new BasicChoreography();
        choreography.setScrollTransformer(scrollTransformer);
        return choreography;
    }

    public final class ScrollTransformerBuilder {
        // for scroll transformation
        private int scrollOrientation;

        private boolean stopAtBoder;

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

        private int offset = 0;
        private int topOffset = 0;
        private float multiplier = 1;

        public ScrollTransformerBuilder(int scrollOrientation) {
            this.scrollOrientation = scrollOrientation;
        }

        public ScrollTransformerBuilder alpha(float from, float to) {
            fromAlpha = from;
            toAlpha = to;
            return this;
        }

        public ScrollTransformerBuilder scaleX(float from, float to) {
            fromScaleX = from;
            toScaleX = to;
            return this;
        }

        public ScrollTransformerBuilder scaleY(float from, float to) {
            fromScaleY = from;
            toScaleY = to;
            return this;
        }

        public ScrollTransformerBuilder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public ScrollTransformerBuilder topOffset(int topOffset) {
            this.topOffset = topOffset;
            return this;
        }

        public ScrollTransformerBuilder stopAtBorder() {
            this.stopAtBoder = true;
            return this;
        }

        public ScrollTransformerBuilder translationY(Position from, Position to) {
            fromTranslationY = new TranslatePosition(from, 0);
            toTranslationY = new TranslatePosition(to, 0);
            return this;
        }

        public ScrollTransformerBuilder translationY(Position from, float fromOffset, Position to, float toOffset) {
            fromTranslationY = new TranslatePosition(from, fromOffset);
            toTranslationY = new TranslatePosition(to, toOffset);
            return this;
        }

        public ScrollTransformerBuilder translationX(Position from, Position to) {
            fromTranslationX = new TranslatePosition(from, 0);
            toTranslationX = new TranslatePosition(to, 0);
            return this;
        }

        public ScrollTransformerBuilder translationX(Position from, float fromOffset, Position to, float toOffset) {
            fromTranslationX = new TranslatePosition(from, fromOffset);
            toTranslationX = new TranslatePosition(to, toOffset);
            return this;
        }

        public ScrollTransformerBuilder multiplier(float multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        public ScrollChoreographyBuilder end() {
            ScrollChoreographyBuilder.this.scrollTransformer = new BasicChoreography.BasicScrollTransformer(
                    scrollOrientation, multiplier, offset, topOffset, fromAlpha, fromScaleX, fromScaleY, fromTranslationX, fromTranslationY,
                    toAlpha, toScaleX, toScaleY, stopAtBoder, toTranslationX, toTranslationY
            );
            return ScrollChoreographyBuilder.this;
        }
    }

}
