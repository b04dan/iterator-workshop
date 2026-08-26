package de.workshop.museum.part3;

import de.workshop.museum.ExhibitCollection;
import de.workshop.museum.TourIterator;


/**
 * TASK (part 3b): let the foreign LegacyArchive take part in the tour.
 * Do not copy the records into a list - read them from the archive while walking.
 */
public class ArchiveAdapter implements ExhibitCollection {

    private final LegacyArchive archive;


    public ArchiveAdapter(LegacyArchive archive) {
        this.archive = archive;
    }


    @Override
    public TourIterator createIterator() {
        throw new UnsupportedOperationException("Part 3: translate the archive into a tour");
    }

}