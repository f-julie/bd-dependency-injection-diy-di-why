package com.amazon.ata.dependencyinjection.lego.construction;

import javax.inject.Inject;

/**
 * Represents a green LEGO brick of any size.
 */
public class RedBrick implements Lego {
    /**
     * Constructs a red brick.
     */
    @Inject
    public RedBrick() {
    }
}
