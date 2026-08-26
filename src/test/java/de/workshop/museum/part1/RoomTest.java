package de.workshop.museum.part1;

import de.workshop.museum.Exhibit;
import de.workshop.museum.TourGuide;
import de.workshop.museum.TourIterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Part 1 - the aggregate hands out an iterator")
class RoomTest {

    private Room room() {
        return new Room()
                .add(new Exhibit("Mona Lisa", "da Vinci", 1503))
                .add(new Exhibit("The Scream", "Munch", 1893))
                .add(new Exhibit("Guernica", "Picasso", 1937));
    }

    @Test
    @DisplayName("the client can walk the room knowing only the interfaces")
    void clientCanWalkTheRoom() {
        assertEquals(List.of("Mona Lisa", "The Scream", "Guernica"), TourGuide.titles(room()));
    }

    @Test
    @DisplayName("an empty room simply ends the tour immediately")
    void emptyRoom() {
        assertFalse(new Room().createIterator().hasNext());
        assertEquals(0, TourGuide.count(new Room()));
    }

    @Test
    @DisplayName("asking for one exhibit too many is a programming error")
    void tooManyNextCalls() {
        TourIterator tour = new Room().add(new Exhibit("Sunflowers", "van Gogh", 1888)).createIterator();
        tour.next();
        assertThrows(NoSuchElementException.class, tour::next);
    }

    @Test
    @DisplayName("each iterator carries its own position - two visitors do not disturb each other")
    void iteratorsAreIndependent() {
        Room room = room();
        TourIterator visitorA = room.createIterator();
        TourIterator visitorB = room.createIterator();

        visitorA.next();
        visitorA.next();

        assertEquals("Mona Lisa", visitorB.next().title(),
                "the second visitor must start at the entrance, not where visitor A stands");
    }

    @Test
    @DisplayName("the room does not expose its storage")
    void roomHidesItsStructure() {
        List<String> forbidden = List.of("get", "size", "toArray", "getExhibits");
        for (var method : Room.class.getDeclaredMethods()) {
            assertFalse(forbidden.contains(method.getName()),
                    "Room must not expose '" + method.getName() + "' - traversal belongs to the iterator");
        }
    }
}

