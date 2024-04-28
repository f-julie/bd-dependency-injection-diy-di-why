package com.amazon.ata.dependencyinjection.lego.app;

import com.amazon.ata.dependencyinjection.lego.construction.GreenBrick;
import com.amazon.ata.dependencyinjection.lego.construction.RedBrick;
import com.amazon.ata.dependencyinjection.lego.dependency.DaggerLegoBuilderComponent;
import com.amazon.ata.dependencyinjection.lego.dependency.LegoBuilderComponent;

/**
 * Entry point to our LegoBuilder activity.
 */
public class LegoBuilder {
    /**
     * main(), but not really. Don't try running this.
     * This is a code-reading exercise.
     * @param args Command line args
     */
    public static void main(String[] args) {
        LegoBuilderComponent component = DaggerLegoBuilderComponent.create();

        LegoCreation creation = new LegoCreation();

        // Phase 1
        creation.add(new GreenBrick());
        creation.add(new RedBrick());
        creation.add(component.provideSingletonBrick());
        creation.add(component.provideTwoBrickCombination());

        // Phase 2
        creation.add(component.provideNBrickCombination());
        creation.add(component.provideSingletonBrick());
        creation.makeAnyConfiguration();
    }
}
