package com.minoon.disco.choreography;

import android.view.View;

/**
 * Created by a13587 on 15/09/15.
 */
public interface ScrollChoreography extends Choreography {

    boolean playScroll(View chaserView, int dx, int dy, int x, int y);
}
