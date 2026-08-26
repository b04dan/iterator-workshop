package de.workshop.museum.part3;

/**
 * Pretend this class comes from an external library: you CANT change it.
 * It has its own idea of how to expose data - index based, different names,
 * and it does not know our TourIterator at all.
 */
public class LegacyArchive {

    private final String[][] records = new String[100][];
    private int recordCount = 0;


    public void store(String title, String artist, int year) {
        records[recordCount++] = new String[]{title, artist, String.valueOf(year)};
    }


    public int recordCount() {
        return recordCount;
    }


    public String titleAt(int index) {
        return records[index][0];
    }


    public String artistAt(int index) {
        return records[index][1];
    }


    public int yearAt(int index) {
        return Integer.parseInt(records[index][2]);
    }

}