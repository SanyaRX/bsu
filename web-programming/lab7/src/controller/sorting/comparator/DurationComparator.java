package controller.sorting.comparator;

import model.TravelVoucher;

import java.util.Comparator;

/**
 * Class represents comparator for duration field.
 * @autor Alexander Rai
 * @version 1.0
 */


public class DurationComparator implements Comparator<TravelVoucher> {

    @Override
    public int compare(TravelVoucher o1, TravelVoucher o2) {
        return o1.getDuration().compareTo(o2.getDuration());
    }
}
