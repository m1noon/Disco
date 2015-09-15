package com.minoon.disco.choreography.builder;

import com.minoon.disco.choreography.ScrollChoreography;

/**
 * Created by a13587 on 15/09/11.
 */
public interface ScrollChoreographyBuilder extends BaseChoreographyBuilder<ScrollChoreographyBuilder> {

    ScrollTransformerBuilder onScrollVertical();

    ScrollTransformerBuilder onScrollHorizontal();

    ScrollChoreography build();


    interface ScrollTransformerBuilder {

        ScrollTransformerBuilder alpha(float from, float to);

        ScrollTransformerBuilder scaleX(float from, float to);

        ScrollTransformerBuilder scaleY(float from, float to);

        ScrollTransformerBuilder offset(int offset);

        ScrollTransformerBuilder topOffset(int offset);

        ScrollTransformerBuilder multiplier(float multiplier);

        ScrollChoreographyBuilder end();
    }
}
