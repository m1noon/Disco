package com.minoon.disco.choreography.builder;

import android.view.View;

/**
 * Created by a13587 on 15/09/17.
 */
/* package */ class TranslatePosition {
    private static final String TAG = TranslatePosition.class.getSimpleName();


    private static final float NO_POSITION = -1;

    private float position = NO_POSITION;

    private float offset;

    private Position type;

    public TranslatePosition(float position) {
        this.position = position;
    }

    public TranslatePosition(Position type, float offset) {
        this.type = type;
        this.offset = offset;
    }

    float getPosition(View view) {
        if (type != null) {
            return type.getPosition(view) + offset;
        }
        return position;
    }
}
