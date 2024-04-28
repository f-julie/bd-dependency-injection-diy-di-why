package com.amazon.ata.dependencyinjection.lego.construction;

import javax.inject.Inject;

/**
 * Represents a green LEGO brick of any size.
 */
public class GreenBrick implements Lego {
    /**
     * Constructs a green brick.
     */
    @Inject
    public GreenBrick() {
    }
}
