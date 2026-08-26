package de.workshop.museum.part1;

import de.workshop.museum.Exhibit;
import de.workshop.museum.ExhibitCollection;
import de.workshop.museum.TourIterator;


/**
 * PART 1 - Hide the data structure
 * <p>
 * The room keeps its exhibits in a raw array. TourGuide must be able to walk
 * through the room without ever learning that.
 * <p>
 * TASK
 * 1. Make Room a proper AGGREGATE: hand out a fresh ConcreteTourIterator.
 * 2. Implement ConcreteTourIterator so that TourGuide.titles(room) works.
 */

public class Room implements ExhibitCollection {

    private final Exhibit[] exhibits = new Exhibit[50];
    private int stored = 0;


    public Room add(Exhibit exhibit) {
        exhibits[stored++] = exhibit;
        return this;
    }


    @Override
    public TourIterator createIterator() {
        throw new UnsupportedOperationException("Part 1: return a new ConcreteTourIterator");
    }


    private class ConcreteTourIterator implements TourIterator {

        // the iterator owns the position of the traversal
        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Part 1: is there another exhibit?");
        }


        @Override
        public Exhibit next() {
            // hand out the current exhibit and move on.
            // when nothing is left: throw new NoSuchElementException()
            throw new UnsupportedOperationException("Part 1: hand out the current exhibit");
        }

    }

}