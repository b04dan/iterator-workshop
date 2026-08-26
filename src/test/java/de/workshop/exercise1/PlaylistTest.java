package de.workshop.exercise1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {

    private Playlist samplePlaylist() {
        Playlist p = new Playlist();
        p.add(new Song("Bohemian Rhapsody", "Queen", "Rock"));
        p.add(new Song("Blue Monday", "New Order", "Synthpop"));
        p.add(new Song("Take Five", "Dave Brubeck", "Jazz"));
        return p;
    }

    @Test
    @DisplayName("for-each returns all songs in insertion order")
    void iteratesInOrder() {
        List<String> titles = new ArrayList<>();
        for (Song s : samplePlaylist()) {
            titles.add(s.title());
        }
        assertEquals(List.of("Bohemian Rhapsody", "Blue Monday", "Take Five"), titles);
    }

    @Test
    @DisplayName("empty playlist has no elements")
    void emptyPlaylist() {
        assertFalse(new Playlist().iterator().hasNext());
    }

    @Test
    @DisplayName("next() throws NoSuchElementException when exhausted")
    void throwsWhenExhausted() {
        Iterator<Song> it = new Playlist().iterator();
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    @DisplayName("two iterators are independent of each other")
    void independentIterators() {
        Playlist p = samplePlaylist();
        Iterator<Song> a = p.iterator();
        Iterator<Song> b = p.iterator();

        a.next();
        assertEquals("Bohemian Rhapsody", b.next().title(),
                "second iterator must start from the beginning");
    }
}