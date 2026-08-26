package de.workshop.museum;

import java.util.ArrayList;
import java.util.List;


/**
 * THE CLIENT. Do not change this class.
 *
 * It knows only the two interfaces of the pattern. It works with a single room,
 * with a whole museum and with any traversal order you invent, without a single
 * if- else about the underlying data structure.
 */

public final class TourGuide {

    private TourGuide() {
    }


    public static List<String> titles(ExhibitCollection collection) {
        List<String> titles = new ArrayList<>();
        TourIterator tour = collection.createIterator();
        while (tour.hasNext()) {
            titles.add(tour.next().title());
        }
        return titles;
    }


    public static int count(ExhibitCollection collection) {
        int count = 0;
        TourIterator tour = collection.createIterator();
        while (tour.hasNext()) {
            tour.next();
            count++;
        }
        return count;
    }

}