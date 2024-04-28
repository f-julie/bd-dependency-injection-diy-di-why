package com.amazon.ata.dependencyinjection.lego.dependency;

import com.amazon.ata.dependencyinjection.lego.construction.BrownBrick;
import com.amazon.ata.dependencyinjection.lego.construction.RedBrick;
import com.amazon.ata.dependencyinjection.lego.construction.NBrickCombination;
import com.amazon.ata.dependencyinjection.lego.outsidevendor.SingletonBrick;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Module for providing objects for the LegoBuilder that can't be
 * instantiated directly by Dagger.
 */
@Module
public class BrickModule {
    /**
     * Provides a three-brick combination.
     * @param redBrick the red brick to use
     * @param singletonBrick the singleton brick to use
     * @return the combination
     */
    @Provides
    public NBrickCombination provideThreeBrickCombination(RedBrick redBrick, SingletonBrick singletonBrick) {
        return NBrickCombination.builder()
                                .withLego(redBrick)
                                .withLego(new BrownBrick())
                                .withLego(singletonBrick)
                                .build();
    }

    /**
     * Provides an outside vendor's singleton brick.
     * @return The singleton brick
     */
    @Singleton
    @Provides
    public SingletonBrick provideSingletonBrick() {
        // SingletonBrick is defined in another code package
        return new SingletonBrick();
    }
}
