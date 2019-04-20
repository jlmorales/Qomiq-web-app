package com.comic.Comparators;

import com.comic.model.Series;

import java.util.Comparator;

public class SeriesDateComparator implements Comparator<Series> {

    @Override
    public int compare(Series series1, Series series2){
        int comparison = Integer.compare(series1.getCreationDate(), series2.getCreationDate());
        return comparison;
    }
}
