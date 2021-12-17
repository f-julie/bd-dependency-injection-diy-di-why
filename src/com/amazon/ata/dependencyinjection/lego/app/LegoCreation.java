package com.amazon.ata.dependencyinjection.lego.app;

import com.amazon.ata.dependencyinjection.lego.construction.Lego;

import java.util.HashSet;
import java.util.Set;

/**
 * A creation made from LEGO.
 */
public class LegoCreation {
    Set<Lego> legos = new HashSet<>();

    /**
     * Add the given Lego object to the creation.
     * @param lego the Lego thing to add
     */
    public void add(Lego lego) {
        if (!legos.contains(lego)) {
            legos.add(lego);
            makeAnyConfiguration();
        } else {
            System.out.println("Already attached this lego piece: " + lego);
        }
    }

    /**
     * Make a cool shape from the LEGO you've got so far.
     */
    public void makeAnyConfiguration() {
        //...creatively combine Lego to existing creation
    }
}
