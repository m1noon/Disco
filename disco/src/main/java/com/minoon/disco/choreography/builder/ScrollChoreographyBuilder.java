package com.minoon.disco.choreography.builder;

import com.minoon.disco.Disco;
import com.minoon.disco.choreography.ScrollChoreography;

/**
 * Created by a13587 on 15/09/15.
 */
public class ScrollChoreographyBuilder extends BaseChoreographyBuilder<ScrollChoreographyBuilder, ScrollChoreography> {
    private static final String TAG = ScrollChoreographyBuilder.class.getSimpleName();

    private BasicChoreography.ScrollTransformer scrollTransformer;

    private BasicChoreography.ScrollTagAnimator scrollTagAnimator;

    private int orientation = BasicChoreography.VERTICAL;

    public ScrollChoreographyBuilder(Disco disco) {
        super(disco);
    }

    public ScrollChoreographyBuilder horizontal() {
        this.orientation = BasicChoreography.HORIZONTAL;
        return this;
    }

    public ScrollTransformerBuilder onScroll() {
        return new ScrollTransformerBuilder(orientation);
    }

    public ScrollTagAnimatorBuilder tag(int scrollPosition) {
        return new ScrollTagAnimatorBuilder(scrollPosition);
    }

    @Override
    public ScrollChoreography build() {
        BasicChoreography choreography = new BasicChoreography();
        choreography.addEventAnimators(eventAnimators);
        choreography.setScrollTransformer(scrollTransformer);
        choreography.setScrollTagAnimator(scrollTagAnimator);
        return choreography;
    }

    public final class ScrollTransformerBuilder extends BaseTransformerBuilder<ScrollTransformerBuilder> {
        // for scroll transformation
        private int scrollOrientation;

        private boolean stopAtBoder;

        private int offset = 0;
        private int topOffset = 0;
        private float multiplier = 1;

        private ScrollTransformerBuilder(int scrollOrientation) {
            this.scrollOrientation = scrollOrientation;
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

        public ScrollTransformerBuilder multiplier(float multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        public ScrollChoreographyBuilder end() {
            ScrollChoreographyBuilder.this.scrollTransformer = new BasicChoreography.ScrollTransformer(
                    buildTransformer(), scrollOrientation, multiplier, offset, topOffset, stopAtBoder
            );
            return ScrollChoreographyBuilder.this;
        }

        public ScrollChoreography build() {
            return end().build();
        }
    }


    public final class ScrollTagAnimatorBuilder extends BaseAnimatorBuilder<ScrollTagAnimatorBuilder> {
        private final float boundary;

        private Enum onBackEvent;
        private Enum onForwardEvent;

        private ScrollTagAnimatorBuilder(float boundary) {
            this.boundary = boundary;
        }

        public ScrollTagAnimatorBuilder notifyEvent(Enum beforeEvent, Enum afterEvent) {
            this.onBackEvent = beforeEvent;
            this.onForwardEvent = afterEvent;
            return this;
        }

        public ScrollChoreographyBuilder end() {
            ScrollChoreographyBuilder.this.scrollTagAnimator = new BasicChoreography.ScrollTagAnimator(
                    orientation, boundary,
                    buildFromAnimator(),
                    buildToAnimator(),
                    disco, onBackEvent, onForwardEvent
            );
            return ScrollChoreographyBuilder.this;
        }

        public ScrollChoreography build() {
            return end().build();
        }
    }

}
