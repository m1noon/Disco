package com.minoon.disco;

import android.view.View;

/**
 * Created by a13587 on 15/09/11.
 */
public abstract class Choreography {

    private Disco disco;

    public abstract boolean playChase(View anchorView, View chaserView);

    public abstract long playEvent(Enum e, View chaserView);

    public abstract boolean playScroll(View chaserView, int dx, int dy, int x, int y);

    protected void notifyEvent(Enum e) {
        disco.event(e);
    }

    public Choreography(Disco disco) {
        this.disco = disco;
    }
}
