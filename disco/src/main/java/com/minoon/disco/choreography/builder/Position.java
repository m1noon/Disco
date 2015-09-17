package com.minoon.disco.choreography.builder;

import android.view.View;

/**
 * Created by a13587 on 15/09/17.
 */
public enum Position {
    DEFAULT {
        @Override
        float getPosition(View view) {
            return 0;
        }
    },

    LEFT {
        @Override
        float getPosition(View view) {
            return -(view.getLeft() + (view.getWidth() * (1 - view.getScaleX()) / 2));
        }
    },
    LEFT_OVER {
        @Override
        float getPosition(View view) {
            return -(view.getRight() + (view.getWidth() * (1 - view.getScaleX()) / 2));
        }
    };

    /* package */
    abstract float getPosition(View view);
}
