package com.minoon.disco.choreography.builder;

import com.minoon.disco.choreography.Choreography;

/**
 * Internal Builder interface for Choreography
 * such as {@link Choreography.Transformer}, {@link Choreography.Animator}.
 *
 * @param <B> parent builder of choreography type
 * @param <C> choreography type
 */
public interface InternalBuilder<B, C extends Choreography> {

    /**
     * finish internal build and return parent builder.
     *
     * @return parent builder class.
     */
    B end();

    /**
     * build choreography.
     *
     * @return choreography
     */
    C build();
}
