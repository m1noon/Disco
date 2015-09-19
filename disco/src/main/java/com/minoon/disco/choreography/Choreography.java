package com.minoon.disco.choreography;

import android.view.View;

/**
 * Created by a13587 on 15/09/11.
 */
public interface Choreography {

    long playEvent(Enum e, View chaserView);


    interface Transformer {
        void transform(View view, float progress);
    }


    interface Animator {
        /**
         * animate the specified view.
         *
         * @param view view to be animated.
         * @return animate duration
         */
        long animate(View view);
    }
}
