package de.workshop.museum;

/**
 * Iterable collection
 *
 * Note that get(int), size(), toList() etc are missing. The aggregate does
 * not reveal how or where it stores its exhibits, it just returns iterator
 */
public interface ExhibitCollection {
    TourIterator createIterator();
}