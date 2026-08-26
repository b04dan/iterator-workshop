package de.workshop.museum;

//  ITERATOR role of the pattern.
public interface TourIterator {

    boolean hasNext();

    Exhibit next();

}