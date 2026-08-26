# Example solutions

## Part 1 - Room

```java
package de.workshop.museum.part1;

import de.workshop.museum.Exhibit;
import de.workshop.museum.ExhibitCollection;
import de.workshop.museum.TourIterator;

import java.util.NoSuchElementException;


public class Room implements ExhibitCollection {

    private final Exhibit[] exhibits = new Exhibit[50];
    private int stored = 0;


    public Room add(Exhibit exhibit) {
        exhibits[stored++] = exhibit;
        return this;
    }


    @Override
    public TourIterator createIterator() {
        return new ConcreteTourIterator();
    }


    private class ConcreteTourIterator implements TourIterator {

        private int position = 0;


        @Override
        public boolean hasNext() {
            return position < stored;
        }


        @Override
        public Exhibit next() {
            if (!hasNext()) {
                throw new NoSuchElementException("the tour has ended");
            }
            return exhibits[position++];
        }

    }

}
```

## Part 2 - Gallery

```java
package de.workshop.museum.part2;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import de.workshop.museum.Exhibit;
import de.workshop.museum.ExhibitCollection;
import de.workshop.museum.TourIterator;


public class Gallery implements ExhibitCollection {

    private final List<Exhibit> exhibits = new ArrayList<>();


    public Gallery add(Exhibit exhibit) {
        exhibits.add(exhibit);
        return this;
    }


    @Override
    public TourIterator createIterator() {
        return straightTour();
    }


    public TourIterator straightTour() {
        return new TourIterator() {
            private int position = 0;


            public boolean hasNext() {
                return position < exhibits.size();
            }


            public Exhibit next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return exhibits.get(position++);
            }
        };
    }


    public TourIterator reverseTour() {
        return new TourIterator() {
            private int position = exhibits.size() - 1;


            public boolean hasNext() {
                return position >= 0;
            }


            public Exhibit next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return exhibits.get(position--);
            }
        };
    }


    public TourIterator highlightsTour(int year) {
        return new TourIterator() {
            private int position = 0;
            private Exhibit nextStop;


            public boolean hasNext() {
                while (nextStop == null && position < exhibits.size()) {
                    Exhibit candidate = exhibits.get(position++);
                    if (candidate.year() < year) {
                        nextStop = candidate;
                    }
                }
                return nextStop != null;
            }


            public Exhibit next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Exhibit stop = nextStop;
                nextStop = null;
                return stop;
            }
        };
    }

}
```

## Part 3 - Museum

The museum iterator only talks to `ExhibitCollection` and `TourIterator`.
So a nested museum needs no extra code at all.

```java
package de.workshop.museum.part3;

import de.workshop.museum.Exhibit;
import de.workshop.museum.ExhibitCollection;
import de.workshop.museum.TourIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


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

        private int sectionIndex = 0;
        private TourIterator currentSection = EMPTY;


        @Override
        public boolean hasNext() {
            while (!currentSection.hasNext() && sectionIndex < sections.size()) {
                currentSection = sections.get(sectionIndex++).createIterator();
            }
            return currentSection.hasNext();
        }


        @Override
        public Exhibit next() {
            if (!hasNext()) {
                throw new NoSuchElementException("the tour has ended");
            }
            return currentSection.next();
        }

    }

    private static final TourIterator EMPTY = new TourIterator() {
        public boolean hasNext() {
            return false;
        }


        public Exhibit next() {
            throw new NoSuchElementException();
        }
    };

}
```

## Part 3b - ArchiveAdapter

```java
package de.workshop.museum.part3;

import de.workshop.museum.Exhibit;
import de.workshop.museum.ExhibitCollection;
import de.workshop.museum.TourIterator;

import java.util.NoSuchElementException;


public class ArchiveAdapter implements ExhibitCollection {

    private final LegacyArchive archive;


    public ArchiveAdapter(LegacyArchive archive) {
        this.archive = archive;
    }


    @Override
    public TourIterator createIterator() {
        return new TourIterator() {
            private int index = 0;


            public boolean hasNext() {
                return index < archive.recordCount();
            }


            public Exhibit next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Exhibit exhibit = new Exhibit(archive.titleAt(index), archive.artistAt(index), archive.yearAt(index));
                index++;
                return exhibit;
            }
        };
    }

}
```