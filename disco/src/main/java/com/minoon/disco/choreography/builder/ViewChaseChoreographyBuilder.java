package com.minoon.disco.choreography.builder;

import com.minoon.disco.Disco;
import com.minoon.disco.ViewParam;
import com.minoon.disco.choreography.ViewChaseChoreography;

/**
 * Created by a13587 on 15/09/15.
 */
public class ViewChaseChoreographyBuilder extends BaseChoreographyBuilder<ViewChaseChoreographyBuilder, ViewChaseChoreography> {
    private static final String TAG = ViewChaseChoreographyBuilder.class.getSimpleName();

    private BasicChoreography.ViewTagAnimator viewTagAnimator;

    private BasicChoreography.ViewTransformer transformer;

    public ViewChaseChoreographyBuilder(Disco disco) {
        super(disco);
    }

    public ViewTransformerBuilder onChange(ViewParam param, float from, float to) {
        return new ViewTransformerBuilder(param, from, to);
    }

    public ViewTagAnimatorBuilder atTag(ViewParam param, float boundary) {
        return new ViewTagAnimatorBuilder(param, boundary);
    }

    @Override
    public ViewChaseChoreography build() {
        BasicChoreography choreography = new BasicChoreography();
        choreography.addEventAnimators(eventAnimators);
        choreography.setViewTransformer(transformer);
        choreography.setViewTagAnimator(viewTagAnimator);
        return choreography;
    }


    public final class ViewTransformerBuilder extends BaseTransformerBuilder<ViewTransformerBuilder> {

        // for view dependent transformation
        private final ViewParam viewParam;

        private final float fromBounds;
        private final float toBounds;

        private ViewTransformerBuilder(ViewParam viewParam, float fromBounds, float toBounds) {
            this.viewParam = viewParam;
            this.fromBounds = fromBounds;
            this.toBounds = toBounds;
        }

        public ViewChaseChoreographyBuilder end() {
            ViewChaseChoreographyBuilder.this.transformer = new BasicChoreography.ViewTransformer(
                    buildTransformer(), viewParam, fromBounds, toBounds
            );
            return ViewChaseChoreographyBuilder.this;
        }

        public ViewChaseChoreography build() {
            return end().build();
        }
    }


    public final class ViewTagAnimatorBuilder extends BaseAnimatorBuilder<ViewTagAnimatorBuilder> {

        // for view dependent transformation
        private final ViewParam viewParam;

        private final float boundary;

        private Enum onBackEvent;
        private Enum onForwardEvent;

        private ViewTagAnimatorBuilder(ViewParam viewParam, float boundary) {
            this.viewParam = viewParam;
            this.boundary = boundary;
        }

        public ViewTagAnimatorBuilder notifyEvent(Enum beforeEvent, Enum afterEvent) {
            this.onBackEvent = beforeEvent;
            this.onForwardEvent = afterEvent;
            return this;
        }

        public ViewChaseChoreographyBuilder end() {
            ViewChaseChoreographyBuilder.this.viewTagAnimator = new BasicChoreography.ViewTagAnimator(
                    buildFromAnimator(), buildToAnimator(), disco, viewParam, boundary, onBackEvent, onForwardEvent
            );
            return ViewChaseChoreographyBuilder.this;
        }

        public ViewChaseChoreography build() {
            return end().build();
        }
    }
}
