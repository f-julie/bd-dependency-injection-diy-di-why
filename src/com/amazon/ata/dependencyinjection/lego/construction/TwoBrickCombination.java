package com.amazon.ata.dependencyinjection.lego.construction;

import javax.inject.Inject;

/**
 * Represents a combination of one green and one red brick.
 */
public class TwoBrickCombination extends LegoCombination {

    private GreenBrick greenBrick;
    private RedBrick redBrick;

    /**
     * Constructs a two-brick combination from a green and a red brick.
     * @param greenBrick The green brick
     * @param redBrick The red brick
     */
    @Inject
    TwoBrickCombination(GreenBrick greenBrick,
                        RedBrick redBrick) {
        this.greenBrick = greenBrick;
        this.redBrick = redBrick;
    }
}
