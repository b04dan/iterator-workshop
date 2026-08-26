package de.workshop.museum.part2;

import de.workshop.museum.Exhibit;
import de.workshop.museum.TourGuide;
import de.workshop.museum.TourIterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Part 2 - several traversals over one collection")
class GalleryTest {

    private Gallery gallery() {
        return new Gallery()
                .add(new Exhibit("Mona Lisa", "da Vinci", 1503))
                .add(new Exhibit("The Scream", "Munch", 1893))
                .add(new Exhibit("Guernica", "Picasso", 1937))
                .add(new Exhibit("Balloon Dog", "Koons", 1994));
    }

    private List<String> walk(TourIterator tour) {
        return TourGuide.titles(() -> tour);
    }

    @Test
    @DisplayName("the straight tour follows the order of arrival")
    void straightTour() {
        assertEquals(List.of("Mona Lisa", "The Scream", "Guernica", "Balloon Dog"),
                walk(gallery().straightTour()));
    }

    @Test
    @DisplayName("the reverse tour starts at the exit")
    void reverseTour() {
        assertEquals(List.of("Balloon Dog", "Guernica", "The Scream", "Mona Lisa"),
                walk(gallery().reverseTour()));
    }

    @Test
    @DisplayName("the highlights tour stops only in front of the old masters")
    void highlightsTour() {
        assertEquals(List.of("Mona Lisa", "The Scream"),
                walk(gallery().highlightsTour(1900)));
    }

    @Test
    @DisplayName("the highlights tour may end up empty and that is fine")
    void highlightsCanBeEmpty() {
        TourIterator tour = gallery().highlightsTour(1400);
        assertFalse(tour.hasNext());
        assertThrows(NoSuchElementException.class, tour::next);
    }

    @Test
    @DisplayName("hasNext() only looks - it never consumes an exhibit")
    void hasNextDoesNotConsume() {
        TourIterator tour = gallery().highlightsTour(1900);
        assertTrue(tour.hasNext());
        assertTrue(tour.hasNext());
        assertTrue(tour.hasNext());
        assertEquals("Mona Lisa", tour.next().title());
    }

    @Test
    @DisplayName("next() works even if the visitor never asks hasNext()")
    void nextWithoutHasNext() {
        TourIterator tour = gallery().highlightsTour(1900);
        assertEquals("Mona Lisa", tour.next().title());
        assertEquals("The Scream", tour.next().title());
        assertThrows(NoSuchElementException.class, tour::next);
    }

    @Test
    @DisplayName("running all three tours at the same time changes nothing")
    void toursDoNotInterfere() {
        Gallery gallery = gallery();
        TourIterator straight = gallery.straightTour();
        TourIterator reverse = gallery.reverseTour();
        TourIterator highlights = gallery.highlightsTour(1900);

        assertEquals("Mona Lisa", straight.next().title());
        assertEquals("Balloon Dog", reverse.next().title());
        assertEquals("Mona Lisa", highlights.next().title());
        assertEquals("The Scream", straight.next().title());
    }

    @Test
    @DisplayName("the client code never changes - only the iterator does")
    void sameClientForEveryTour() {
        Gallery gallery = gallery();
        assertEquals(4, TourGuide.count(gallery::straightTour));
        assertEquals(4, TourGuide.count(gallery::reverseTour));
        assertEquals(2, TourGuide.count(() -> gallery.highlightsTour(1900)));
    }
}

