package controller.sorting.comparator;

import model.TravelVoucher;

import java.util.Comparator;

/**
 * Class represents comparator for price field.
 * @autor Alexander Rai
 * @version 1.0
 */


public class PriceComparator implements Comparator<TravelVoucher> {

    @Override
    public int compare(TravelVoucher o1, TravelVoucher o2) {
        return Integer.compare(o1.getPrice(), o2.getPrice());
    }
}
