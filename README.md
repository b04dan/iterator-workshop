# Iterator Pattern Workshop
Your goal: **make all tests green.**
Every exercise has failing tests. Read the test, write the code, run again.

## Open in GitHub Codespaces
Click the button, create codespace, wait 1-2 min, then run the tests in the terminal using tests panel.
[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://codespaces.new/b04dan/iterator-workshop?quickstart=1)

```bash
./mvnw test          
```

## The two roles
- `TourIterator` - knows where you are: `hasNext()`, `next()`
- `ExhibitCollection` - hands out an iterator: `createIterator()`
- `TourGuide` - the client. Never change it.
- 
## Part 1 - `Room` (part1)
A room keeps its exhibits in an array. Make it hand out an iterator so `TourGuide`
can walk the room without knowing about the array.
Run: `RoomTest`

## Part 2 - `Gallery` (part2)
Same list of exhibits, three different walks: forward, backward and only the old
works. Do not copy or sort anything - decide while walking.
Run: `GalleryTest`

## Part 3 - `Museum` (part3)
A museum holds rooms, galleries, other museums and a foreign `LegacyArchive` you
may not change. Make one tour walk through all of it.
Run: `MuseumTest`

## Solutions
[docs/SOLUTIONS.md](docs/SOLUTIONS.md) 