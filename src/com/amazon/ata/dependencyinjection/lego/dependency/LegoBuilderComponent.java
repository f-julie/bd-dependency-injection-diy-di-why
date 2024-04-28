package com.amazon.ata.dependencyinjection.lego.dependency;

import com.amazon.ata.dependencyinjection.lego.construction.NBrickCombination;
import com.amazon.ata.dependencyinjection.lego.construction.TwoBrickCombination;
import com.amazon.ata.dependencyinjection.lego.outsidevendor.SingletonBrick;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger Component interface providing root objects for LegoBuilder.
 */
@Component(modules = { BrickModule.class })
@Singleton
public interface LegoBuilderComponent {
    /**
     * Provides a combination of two LEGO bricks.
     * @return The combination
     */
    TwoBrickCombination provideTwoBrickCombination();

    /**
     * Provides a combination of an arbitrary number of LEGO bricks.
     * @return The combination
     */
    NBrickCombination provideNBrickCombination();

    /**
     * Provides a singleton brick.
     * @return The singleton brick
     */
    SingletonBrick provideSingletonBrick();
}
