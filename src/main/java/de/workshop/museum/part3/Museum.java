package de.workshop.museum.part3;

import java.util.ArrayList;
import java.util.List;

import de.workshop.museum.Exhibit;
import de.workshop.museum.ExhibitCollection;
import de.workshop.museum.TourIterator;


/**
 * PART 3 - Uunrelated structures behind one traversal.
 * <p>
 * A museum consists of a Room (array, part 1), a Gallery (list, part 2), an
 * archive backed by a completely foreign class and even other museums.
 * The visitor wants ONE tour through all of it.
 * <p>
 * TASK
 * 1. Implement the iterator so that TourGuide.titles(museum) visits every exhibit
 * of every section, section by section, in the order the sections were added.
 * 2. Look at ArchiveAdapter - a third-party class that knows nothing about our
 * interfaces. Make it join the tour by implementing ExhibitCollection.
 */
public class Museum implements ExhibitCollection {

    private final List<ExhibitCollection> sections = new ArrayList<>();


    public Museum addSection(ExhibitCollection section) {
        sections.add(section);
        return this;
    }


    @Override
    public TourIterator createIterator() {
        return new MuseumTour();
    }


    private class MuseumTour implements TourIterator {

        // keep track of which section you are in and of the tour inside that section
        // careful: sections may be empty, and an empty section must not end the tour
        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("Part 3: is there another exhibit anywhere?");
        }


        @Override
        public Exhibit next() {
            throw new UnsupportedOperationException("Part 3: hand out the next exhibit");
        }

    }

}