package com.minoon.disco;

/**
 * Created by a13587 on 15/09/11.
 */
public interface ChoreographyBuilder {

    EventAnimationBuilder at(Enum e);

    ViewTransformerBuilder onChange(ViewParam param, float from, float to);

    ViewTagAnimatorBuilder atTag(ViewParam param, float boundary);

    Choreography build();

    ScrollTransformerBuilder onScrollVertical();

    ScrollTransformerBuilder onScrollHorizontal();

    interface EventAnimationBuilder {

        EventAnimationBuilder alpha(float alpha);

        EventAnimationBuilder translationX(float translationX);

        EventAnimationBuilder translationY(float translationY);

        EventAnimationBuilder scaleX(float scaleX);

        EventAnimationBuilder scaleY(float scaleY);

        EventAnimationBuilder duration(long duration);

        ChoreographyBuilder end();
    }

    interface ScrollTransformerBuilder {

        ScrollTransformerBuilder alpha(float from, float to);

        ScrollTransformerBuilder scaleX(float from, float to);

        ScrollTransformerBuilder scaleY(float from, float to);

        ScrollTransformerBuilder offset(int offset);

        ScrollTransformerBuilder multiplier(float multiplier);

        ChoreographyBuilder end();
    }

    interface ViewTransformerBuilder {

        ViewTransformerBuilder alpha(float from, float to);

        ViewTransformerBuilder translationY(float from, float to);

        ViewTransformerBuilder translationX(float from, float to);

        ViewTransformerBuilder scaleX(float from, float to);

        ViewTransformerBuilder scaleY(float from, float to);

        ChoreographyBuilder end();
    }

    interface ViewTagAnimatorBuilder {

        ViewTagAnimatorBuilder alpha(float from, float to);

        ViewTagAnimatorBuilder translationY(float from, float to);

        ViewTagAnimatorBuilder translationX(float from, float to);

        ViewTagAnimatorBuilder scaleX(float from, float to);

        ViewTagAnimatorBuilder scaleY(float from, float to);

        ViewTagAnimatorBuilder duration(long duration);

        ChoreographyBuilder end();
    }
}
