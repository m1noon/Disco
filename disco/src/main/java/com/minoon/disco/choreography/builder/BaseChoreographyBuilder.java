package com.minoon.disco.choreography.builder;

/**
 * Created by a13587 on 15/09/15.
 */
public interface BaseChoreographyBuilder<T> {

    EventAnimationBuilder<T> at(Enum e);

    interface EventAnimationBuilder<T> {

        EventAnimationBuilder<T> alpha(float alpha);

        EventAnimationBuilder<T> translationX(float translationX);

        EventAnimationBuilder<T> translationY(float translationY);

        EventAnimationBuilder<T> scaleX(float scaleX);

        EventAnimationBuilder<T> scaleY(float scaleY);

        EventAnimationBuilder<T> duration(long duration);

        T end();
    }
}
