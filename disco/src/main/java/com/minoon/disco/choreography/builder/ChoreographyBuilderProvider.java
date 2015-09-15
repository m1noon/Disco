package com.minoon.disco.choreography.builder;

import com.minoon.disco.Disco;

/**
 * Created by a13587 on 15/09/15.
 */
public class ChoreographyBuilderProvider {
    private static final String TAG = ChoreographyBuilderProvider.class.getSimpleName();

    public static ScrollChoreographyBuilder getScrollChoreographyBuilder(Disco disco) {
        return new ScrollChoreographyBuilderImpl(disco);
    }

    public static ViewChaseChoreographyBuilder getViewChaseChoreographyBuilder(Disco disco) {
        return new ViewChaseChoreographyBuilderImpl(disco);
    }
}
