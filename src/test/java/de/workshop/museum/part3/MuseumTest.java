package de.workshop.museum.part3;

import de.workshop.museum.Exhibit;
import de.workshop.museum.ExhibitCollection;
import de.workshop.museum.TourGuide;
import de.workshop.museum.TourIterator;
import de.workshop.museum.part1.Room;
import de.workshop.museum.part2.Gallery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Part 3 - one tour through completely different structures")
class MuseumTest {

    private static final ExhibitCollection CLOSED_FOR_RENOVATION = () -> new TourIterator() {
        public boolean hasNext() {
            return false;
        }

        public Exhibit next() {
            throw new NoSuchElementException();
        }
    };

    private Room room() {
        return new Room().add(new Exhibit("Mona Lisa", "da Vinci", 1503));
    }

    private Gallery gallery() {
        return new Gallery()
                .add(new Exhibit("The Scream", "Munch", 1893))
                .add(new Exhibit("Guernica", "Picasso", 1937));
    }

    @Test
    @DisplayName("array-backed room and list-backed gallery form one continuous tour")
    void walksThroughEverySection() {
        Museum museum = new Museum().addSection(room()).addSection(gallery());

        assertEquals(List.of("Mona Lisa", "The Scream", "Guernica"), TourGuide.titles(museum));
    }

    @Test
    @DisplayName("sections closed for renovation are skipped, wherever they are")
    void skipsEmptySections() {
        Museum museum = new Museum()
                .addSection(CLOSED_FOR_RENOVATION)
                .addSection(room())
                .addSection(CLOSED_FOR_RENOVATION)
                .addSection(CLOSED_FOR_RENOVATION)
                .addSection(gallery())
                .addSection(CLOSED_FOR_RENOVATION);

        assertEquals(List.of("Mona Lisa", "The Scream", "Guernica"), TourGuide.titles(museum));
    }

    @Test
    @DisplayName("a museum without sections is simply a very short tour")
    void emptyMuseum() {
        TourIterator tour = new Museum().createIterator();
        assertFalse(tour.hasNext());
        assertThrows(NoSuchElementException.class, tour::next);
    }

    @Test
    @DisplayName("a museum is a section too - nesting works without extra code")
    void museumsCanContainMuseums() {
        Museum wing = new Museum().addSection(gallery());
        Museum museum = new Museum().addSection(room()).addSection(wing);

        assertEquals(List.of("Mona Lisa", "The Scream", "Guernica"), TourGuide.titles(museum));
        assertEquals(3, TourGuide.count(museum));
    }

    @Test
    @DisplayName("the foreign archive joins the tour through an adapter")
    void adapterBringsInAForeignStructure() {
        LegacyArchive archive = new LegacyArchive();
        archive.store("Sunflowers", "van Gogh", 1888);
        archive.store("Balloon Dog", "Koons", 1994);

        assertEquals(List.of("Sunflowers", "Balloon Dog"), TourGuide.titles(new ArchiveAdapter(archive)));
    }

    @Test
    @DisplayName("adding a brand new kind of section does not touch the client")
    void everythingCombined() {
        LegacyArchive archive = new LegacyArchive();
        archive.store("Sunflowers", "van Gogh", 1888);

        Museum museum = new Museum()
                .addSection(room())
                .addSection(new ArchiveAdapter(archive))
                .addSection(gallery());

        assertEquals(List.of("Mona Lisa", "Sunflowers", "The Scream", "Guernica"),
                TourGuide.titles(museum));
    }

    @Test
    @DisplayName("two visitors can tour the museum at the same time")
    void toursAreIndependent() {
        Museum museum = new Museum().addSection(room()).addSection(gallery());

        TourIterator visitorA = museum.createIterator();
        visitorA.next();
        visitorA.next();

        assertEquals("Mona Lisa", museum.createIterator().next().title());
    }
}

