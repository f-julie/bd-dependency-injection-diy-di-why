package com.amazon.ata.dependencyinjection.lego.construction;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a combination of an arbitrary number of LEGO bricks.
 */
public class NBrickCombination extends LegoCombination {
    private Set<Lego> legos;

    // no public constructor, uses Builder pattern
    private NBrickCombination(Set<Lego> legos) {
        this.legos = legos;
    }

    /**
     * Returns a Builder, suitable for building a NBrickCombination.
     * @return the Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A Builder, suitable for building a NBrickCombination.
     */
    public static class Builder {
        Set<Lego> legos = new HashSet<>();

        /**
         * Adds the given Lego to the combination.
         * @param lego the Lego to add
         * @return The updated Builder
         */
        public Builder withLego(Lego lego) {
            legos.add(lego);
            return this;
        }

        /**
         * Builds the brick combination.
         * @return The brick combination
         */
        public NBrickCombination build() {
            return new NBrickCombination(legos);
        }
    }
}
