package de.workshop.exercise1;

import java.util.Iterator;

// TASK: Make this class iterable so you can iterate it like this for (Song s : playlist) { ... }
public class Playlist implements Iterable<Song> {

    private final Song[] songs = new Song[100];
    private int size = 0;

    public void add(Song song) {
        songs[size++] = song;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Song> iterator() {
        return new PlaylistIterator();
    }

    private class PlaylistIterator implements Iterator<Song> {

        private int cursor = 0;

        @Override
        public boolean hasNext() {

            // check is there another element?
            
            return false;
        }

        @Override
        public Song next() {

            // return the current element and advance the cursor.
            // throw NoSuchElementException if exhausted.

            throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}