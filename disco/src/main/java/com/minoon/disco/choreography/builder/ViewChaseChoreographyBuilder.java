package com.minoon.disco.choreography.builder;

import com.minoon.disco.ViewParam;
import com.minoon.disco.choreography.ViewChaseChoreography;

/**
 * Created by a13587 on 15/09/11.
 */
public interface ViewChaseChoreographyBuilder extends BaseChoreographyBuilder<ViewChaseChoreographyBuilder> {

    ViewTransformerBuilder onChange(ViewParam param, float from, float to);

    ViewTagAnimatorBuilder atTag(ViewParam param, float boundary);

    ViewChaseChoreography build();


    interface ViewTransformerBuilder {

        ViewTransformerBuilder alpha(float from, float to);

        ViewTransformerBuilder translationY(float from, float to);

        ViewTransformerBuilder translationX(float from, float to);

        ViewTransformerBuilder scaleX(float from, float to);

        ViewTransformerBuilder scaleY(float from, float to);

        com.minoon.disco.choreography.builder.ViewChaseChoreographyBuilder end();
    }

    interface ViewTagAnimatorBuilder {

        ViewTagAnimatorBuilder alpha(float from, float to);

        ViewTagAnimatorBuilder translationY(float from, float to);

        ViewTagAnimatorBuilder translationX(float from, float to);

        ViewTagAnimatorBuilder scaleX(float from, float to);

        ViewTagAnimatorBuilder scaleY(float from, float to);

        ViewTagAnimatorBuilder duration(long duration);

        ViewTagAnimatorBuilder notifyEvent(Enum beforeEvent, Enum afterEvent);

        com.minoon.disco.choreography.builder.ViewChaseChoreographyBuilder end();
    }
}
