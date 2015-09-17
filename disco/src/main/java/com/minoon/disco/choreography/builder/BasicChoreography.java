package com.minoon.disco.choreography.builder;

import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

import com.minoon.disco.Disco;
import com.minoon.disco.Logger;
import com.minoon.disco.ViewParam;
import com.minoon.disco.choreography.ScrollChoreography;
import com.minoon.disco.choreography.ViewChaseChoreography;

import java.util.HashMap;
import java.util.Map;

/**
 * A Basic implementation of Choreography.
 *
 * Created by a13587 on 15/09/11.
 */
/* package */ class BasicChoreography implements ScrollChoreography, ViewChaseChoreography {
    private static final String TAG = Logger.createTag(BasicChoreography.class.getSimpleName());
    /* package */ static final float NO_VALUE = Float.MIN_VALUE;

    /* package */ static final int HORIZONTAL = 0;
    /* package */ static final int VERTICAL = 1;

    Map<Enum, Animator> eventAnimator;

    ViewTransformer viewTransformer;

    ViewTagAnimator viewTagAnimator;

    ScrollTransformer scrollTransformer;

    ScrollTagAnimator scrollTagAnimator;

    public BasicChoreography() {
        eventAnimator = new HashMap<>();
    }

    @Override
    public boolean playChase(View anchorView, View chaserView) {
        boolean changed = false;
        if (viewTransformer != null) {
            changed = viewTransformer.transform(anchorView, chaserView);
        }

        if (viewTagAnimator != null) {
            changed = viewTagAnimator.animateIfNeed(anchorView, chaserView) || changed;
        }

        return changed;
    }

    @Override
    public long playEvent(Enum e, View chaserView) {
        Animator animator = eventAnimator.get(e);
        if (animator != null) {
            animator.animate(chaserView);
            return animator.duration;
        }
        return 0;
    }

    @Override
    public boolean playScroll(View chaserView, int dx, int dy, int x, int y) {
        boolean changed = false;
        if (scrollTransformer != null) {
            changed = scrollTransformer.transform(chaserView, x, y);
        }

        if (scrollTagAnimator != null) {
            changed = scrollTagAnimator.animateIfNeed(chaserView, x, y) || changed;
        }

        return changed;
    }


    /* package */ void setScrollTransformer(ScrollTransformer transformer) {
        this.scrollTransformer = transformer;
    }

    /* package */ void setScrollTagAnimator(ScrollTagAnimator tagAnimator) {
        this.scrollTagAnimator = tagAnimator;
    }

    /* package */ void addEventAnimators(Map<Enum, Animator> animators) {
        if (animators != null && animators.size() > 0) {
            eventAnimator.putAll(animators);
        }
    }

    /* package */ void setViewTransformer(ViewTransformer viewTransformer) {
        this.viewTransformer = viewTransformer;
    }

    /* package */ void setViewTagAnimator(ViewTagAnimator viewTagAnimator) {
        this.viewTagAnimator = viewTagAnimator;
    }

    /* package */ static class Animator {
        private final float alpha;
        private final float translationX;
        private final float translationY;
        private final float scaleX;
        private final float scaleY;
        private final long duration;
        private final Interpolator interpolator;

        public Animator(float alpha, float translationX, float translationY, float scaleX, float scaleY, long duration, Interpolator interpolator) {
            this.alpha = alpha;
            this.translationX = translationX;
            this.translationY = translationY;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.duration = duration;
            this.interpolator = interpolator;
        }

        public void animate(View view) {
            view.animate().cancel();
            ViewPropertyAnimator animator = view.animate();
            if (alpha != NO_VALUE) {
                animator.alpha(alpha);
            }
            if (translationX != NO_VALUE) {
                animator.translationX(translationX);
            }
            if (translationY != NO_VALUE) {
                animator.translationY(translationY);
            }
            if (scaleX != NO_VALUE) {
                animator.scaleX(scaleX);
            }
            if (scaleY != NO_VALUE) {
                animator.scaleY(scaleY);
            }
            animator.setDuration(duration);
            animator.setInterpolator(interpolator);
            animator.start();
        }
    }

    /* package */ static class ViewTagAnimator {
        private final ViewParam param;
        private final float bounds;

        private final Animator fromAnimator;
        private final Animator toAnimator;

        private Disco disco;
        private Enum onBackEvent;
        private Enum onForwardEvent;

        private float prevValue;

        public ViewTagAnimator(Animator fromAnimator, Animator toAnimator, Disco disco, ViewParam param, float bounds, Enum onBackEvent, Enum onForwardEvent) {
            this.fromAnimator = fromAnimator;
            this.toAnimator = toAnimator;
            this.disco = disco;
            this.param = param;
            this.bounds = bounds;
            this.onBackEvent = onBackEvent;
            this.onForwardEvent = onForwardEvent;
        }

        boolean animateIfNeed(View anchorView, View chaserView) {
            float value = param.getValue(anchorView);
            boolean changed = false;

            // to upper value
            if (prevValue <= bounds && value > bounds) {
                changed = true;
                toAnimator.animate(chaserView);
                if (onForwardEvent != null) {
                    disco.event(onForwardEvent);
                }
            }

            // to lower value
            if (prevValue >= bounds && value < bounds) {
                changed = true;
                fromAnimator.animate(chaserView);
                if (onBackEvent != null) {
                    disco.event(onBackEvent);
                }
            }

            prevValue = value;
            return changed;
        }
    }

    /* package */ static class ScrollTagAnimator {
        private int orientation;

        private final float bounds;

        private final Animator fromAnimator;
        private final Animator toAnimator;

        private Disco disco;
        private Enum onBackEvent;
        private Enum onForwardEvent;

        private float prevValue;

        public ScrollTagAnimator(int orientation, float bounds, Animator fromAnimator, Animator toAnimator, Disco disco, Enum onBackEvent, Enum onForwardEvent) {
            this.orientation = orientation;
            this.bounds = bounds;
            this.fromAnimator = fromAnimator;
            this.toAnimator = toAnimator;
            this.disco = disco;
            this.onBackEvent = onBackEvent;
            this.onForwardEvent = onForwardEvent;
        }

        boolean animateIfNeed(View view, int x, int y) {
            boolean changed = false;
            int scrollPosition = orientation == HORIZONTAL ? x : y;
            if (prevValue <= bounds && bounds < scrollPosition) {
                changed = true;
                toAnimator.animate(view);
                if (onForwardEvent != null) {
                    disco.event(onForwardEvent);
                }
            }

            if (scrollPosition < bounds && bounds <= prevValue) {
                changed = true;
                fromAnimator.animate(view);
                if (onBackEvent != null) {
                    disco.event(onBackEvent);
                }
            }

            prevValue = scrollPosition;
            return changed;
        }
    }


    /* package */ static class Transformer {
        private final float fromAlpha;
        private final TranslatePosition fromTranslationX;
        private final TranslatePosition fromTranslationY;
        private final float fromScaleX;
        private final float fromScaleY;

        private final float toAlpha;
        private final TranslatePosition toTranslationX;
        private final TranslatePosition toTranslationY;
        private final float toScaleX;
        private final float toScaleY;
        private final Interpolator interpolator;

        public Transformer(float fromAlpha, TranslatePosition fromTranslationX, TranslatePosition fromTranslationY, float fromScaleX, float fromScaleY, float toAlpha, TranslatePosition toTranslationX, TranslatePosition toTranslationY, float toScaleX, float toScaleY, Interpolator interpolator) {
            this.fromAlpha = fromAlpha;
            this.fromTranslationX = fromTranslationX;
            this.fromTranslationY = fromTranslationY;
            this.fromScaleX = fromScaleX;
            this.fromScaleY = fromScaleY;
            this.toAlpha = toAlpha;
            this.toTranslationX = toTranslationX;
            this.toTranslationY = toTranslationY;
            this.toScaleX = toScaleX;
            this.toScaleY = toScaleY;
            this.interpolator = interpolator;
        }

        void transform(View chaserView, float progress) {
            progress = interpolator.getInterpolation(progress);
            // set params
            if (fromAlpha != NO_VALUE && toAlpha != NO_VALUE) {
                chaserView.setAlpha(fromAlpha + (toAlpha - fromAlpha) * progress);
            }
            if (fromTranslationX != null && toTranslationX != null) {
                chaserView.setTranslationX(fromTranslationX.getPosition(chaserView) + (toTranslationX.getPosition(chaserView) - fromTranslationX.getPosition(chaserView)) * progress);
            }
            if (fromTranslationY != null && toTranslationY != null) {
                chaserView.setTranslationY(fromTranslationY.getPosition(chaserView) + (toTranslationY.getPosition(chaserView) - fromTranslationY.getPosition(chaserView)) * progress);
            }
            if (fromScaleX != NO_VALUE && toScaleX != NO_VALUE) {
                chaserView.setScaleX(fromScaleX + (toScaleX - fromScaleX) * progress);
            }
            if (fromScaleY != NO_VALUE && toScaleY != NO_VALUE) {
                chaserView.setScaleY(fromScaleY + (toScaleY - fromScaleY) * progress);
            }
        }
    }


    /* package */ static class ViewTransformer {
        private final ViewParam param;

        private final float fromBounds;
        private final float toBounds;

        private final Transformer transformer;

        private float prevProgress;

        public ViewTransformer(Transformer transformer, ViewParam param, float fromBounds, float toBounds) {
            this.transformer = transformer;
            this.param = param;
            this.fromBounds = fromBounds;
            this.toBounds = toBounds;
        }

        boolean transform(View anchorView, View chaserView) {
            float progress = getProgress(anchorView);
            // set params
            transformer.transform(chaserView, progress);

            final boolean changed = prevProgress != progress;
            prevProgress = progress;
            return changed;
        }

        private float getProgress(View view) {
            float progress = (param.getValue(view) - fromBounds) / (toBounds - fromBounds);
            return Math.min(1, Math.max(0, progress));
        }
    }


    /* package */ static class ScrollTransformer {
        private boolean stopAtBorder;

        private int orientation;
        private float multiplier;
        private int offset;
        private int topOffset;

        private final Transformer transformer;

        private float prevProgress;


        public ScrollTransformer(Transformer transformer, int orientation, float multiplier, int offset, int topOffset, boolean stopAtBorder) {
            this.transformer = transformer;
            this.orientation = orientation;
            this.multiplier = multiplier;
            this.offset = offset;
            this.topOffset = topOffset;
            this.stopAtBorder = stopAtBorder;
        }

        // TODO implements horizontal scroll
        /* package */ boolean transform(View view, int scrollPositionX, int scrollPositionY) {
            // calculate range
            int range = 0;
            if (stopAtBorder) {
                range = Math.max(0, view.getTop() - topOffset);
            } else {
                range = Math.max(0, view.getBottom() - topOffset);
            }
            if (range == 0) {
                // TODO It can not restore the view state if it the view has not been set up.
                range = Integer.MAX_VALUE;
            }
            // calculate scroll position
            int targetScrollPosition = orientation == HORIZONTAL ? scrollPositionX : scrollPositionY;
            int offsetScrollPosition = Math.max(0, targetScrollPosition - offset);
            final float viewScrollPosition = -Math.min(offsetScrollPosition * multiplier, range);

            // scroll the view
            switch (orientation) {
                case HORIZONTAL:
                    view.setTranslationX(viewScrollPosition);
                    break;
                case VERTICAL:
                    view.setTranslationY(viewScrollPosition);
                    break;
            }

            // transform the view
            final float progress = Math.max(0, Math.min(1, Math.abs(viewScrollPosition / (float) range)));
            transformer.transform(view, progress);

            boolean changed = prevProgress != progress;
            prevProgress = progress;
            return changed;
        }
    }
}
