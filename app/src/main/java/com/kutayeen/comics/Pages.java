package com.kutayeen.comics;

import java.util.Comparator;
import java.util.zip.ZipEntry;

public class Pages {
    public String name;
    public ZipEntry entry;

    public Pages(String name, ZipEntry entry){
        this.name=name;
        this.entry=entry;
    }
    public static class CustomComparator implements Comparator<Pages> {
        @Override
        public int compare(Pages o1, Pages o2) {
            return o1.name.compareTo(o2.name);
        }
    }

}
