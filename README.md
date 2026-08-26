# Iterator Pattern Workshop - "Museum Tour"
Hands-on part of the session. One story, three parts, ca. 15 minutes each.
The tests are the specification - make them green.
## Setup
```bash
./mvnw test          # Linux / macOS / Codespaces
.\mvnw.cmd test      # Windows
```
JDK 21, everything else comes from Maven. The build stays green even when tests
fail - read the results in your IDE test runner or in the surefire report.
## The idea
> Provide a way to access the elements of an aggregate object sequentially
> without exposing its underlying representation.
We deliberately do **not** use `java.util.Iterator` here. The pattern is older
and bigger than the Java interface, so we declare the two roles ourselves:
| Role | In this repo | Responsibility |
|---|---|---|
| Iterator | `TourIterator` | knows the position: `hasNext()`, `next()` |
| Aggregate | `ExhibitCollection` | can hand out an iterator: `createIterator()` |
| Client | `TourGuide` | walks anything, knows only the two interfaces |
| Element | `Exhibit` | the thing being visited |
```
        ┌─────────────────────┐          ┌──────────────────┐
        │ «ExhibitCollection» │ creates  │  «TourIterator»  │
        │  createIterator()   │─────────►│  hasNext()       │
        └──────────▲──────────┘          │  next()          │
                   │                     └────────▲─────────┘
   ┌───────┬───────┼────────┬──────────┐          │
   │       │       │        │          │   ┌──────┴──────────────┐
 Room   Gallery  Museum  ArchiveAdapter│   │ ConcreteTourIterator│
(array)  (list) (sections)  (legacy)   │   │ int position        │
                                       │   └─────────────────────┘
                            ┌──────────┴──────────┐
                            │      TourGuide      │  ← never changes
                            └─────────────────────┘
```
Note what is **missing** from `ExhibitCollection`: no `get(int)`, no `size()`,
no `toList()`. That is the whole point.
## Part 1 - Hide the data structure (`part1`)
`Room` stores exhibits in a raw array. Implement `createIterator()` and the inner
`ConcreteTourIterator` so that `TourGuide` can walk the room.
Discuss:
- Who owns the position - the collection or the iterator? What breaks if the room
  keeps a `currentIndex` field itself?
- Why must every call to `createIterator()` return a *fresh* object?
- What should `next()` do when the tour is over?
Run: `RoomTest`
## Part 2 - Many walks, one collection (`part2`)
`Gallery` sells three tours over the very same list: `straightTour()`,
`reverseTour()` and `highlightsTour(year)`. Same data, three iterators.
Nothing may be copied, sorted or filtered up-front - decide while walking.
Discuss:
- How many `if`-branches would `TourGuide` need if the order lived in the
  collection? Now it needs none.
- Why does the filtering iterator have to look ahead inside `hasNext()`?
- An iterator does not have to return every element - `Stream.filter`, LINQ and
  Python generators work exactly like this.
Run: `GalleryTest`
## Part 3 - The pay-off (`part3`)
`Museum` combines an array-backed `Room`, a list-backed `Gallery`, other museums
and `LegacyArchive` - a foreign class you are not allowed to modify. One tour
walks all of it.
Discuss:
- Nesting a museum inside a museum needs zero extra code. Why?
- `ArchiveAdapter` shows how the pattern lets you plug in code you do not own.
- Closed sections must not end the tour - where does that belong?
Run: `MuseumTest`
## Where you meet this pattern
- `Iterable`/`Iterator` (Java), `IEnumerable`/`IEnumerator` (C#),
  `__iter__`/`__next__` (Python)
- Streams, LINQ, generators / `yield`
- Paginated REST APIs, DB cursors (`ResultSet`) - the "collection" never fits in memory
- Tree and graph traversals offered in several orders (in-order, BFS, DFS)
## Solutions
[docs/SOLUTIONS.md](docs/SOLUTIONS.md) - only after you tried it.