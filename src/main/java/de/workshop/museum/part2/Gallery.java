package de.workshop.museum.part2;

import java.util.ArrayList;
import java.util.List;

import de.workshop.museum.Exhibit;
import de.workshop.museum.ExhibitCollection;
import de.workshop.museum.TourIterator;


/**
 * PART 2 - One collection, many ways to walk it.
 * <p>
 * The museum sells different tours over the very same exhibits: the default tour,
 * a reverse tour and a highlights tour that only stops in front of works older than a given year.
 * <p>
 * TASK
 * Implement the three factory methods below. Each returns a DIFFERENT iterator
 * over the SAME data.
 * <p>
 * RULE: no iterator may copy, sort or filter the list up-front. Decide on the fly
 * inside hasNext()/next(). A tour is a walk, not a new museum.
 */
public class Gallery implements ExhibitCollection {

    private final List<Exhibit> exhibits = new ArrayList<>();


    public Gallery add(Exhibit exhibit) {
        exhibits.add(exhibit);
        return this;
    }


    // The default tour: entrance to exit
    @Override
    public TourIterator createIterator() {
        return straightTour();
    }


    public TourIterator straightTour() {
        throw new UnsupportedOperationException("Part 2: walk from the first to the last exhibit");
    }


    // Same exhibits, walked backwards
    public TourIterator reverseTour() {
        throw new UnsupportedOperationException("Part 2: walk from the last to the first exhibit");
    }


    // Same exhibits, but only stop in front of works created before 'year'
    public TourIterator highlightsTour(int year) {
        throw new UnsupportedOperationException("Part 2: skip everything created in 'year' or later");
    }

}