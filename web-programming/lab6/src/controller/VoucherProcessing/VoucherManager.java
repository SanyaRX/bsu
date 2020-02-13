package controller.VoucherProcessing;

import controller.VoucherFilter;
import controller.sorting.VoucherSorter;
import controller.sorting.comparator.DurationComparator;
import controller.sorting.comparator.PriceComparator;
import model.TravelVoucher;
import model.enums.NutritionType;

import java.rmi.Remote;
import java.util.Arrays;
import java.util.List;

/**
 * Class that represents Voucher Manager
 * @autor Alexander Rai
 * @version 1.0
 */

public class VoucherManager implements Remote {

    /** Firm voucher manager can access to */
    private VoucherFirm voucherFirm = null;

    /** Creates a Voucher Manager with default firm */
    public VoucherManager(){
        this.voucherFirm = new VoucherFirm();
    }

    /**
     * Creates a Voucher Manager with specified firm
     * @param voucherFirm - Voucher Firm to set field with
     * */
    public VoucherManager(VoucherFirm voucherFirm){
        this.voucherFirm = voucherFirm;
    }

    /**
     * Shows a work with VoucherFirm class
     * */
    public void processFirm(){
        List<TravelVoucher> list = voucherFirm.getTravelVouchers();

        System.out.println("Unsorted:");
        System.out.println(Arrays.toString(list.toArray()));

        VoucherSorter voucherSorter = new VoucherSorter(new PriceComparator());
        voucherSorter.sort(list, true);

        System.out.println();
        System.out.println("Sorted by price:");
        System.out.println(Arrays.toString(list.toArray()));

        voucherSorter = new VoucherSorter(new DurationComparator());
        voucherSorter.sort(list, true);

        System.out.println();
        System.out.println("Sorted by duration:");
        System.out.println(Arrays.toString(list.toArray()));

        System.out.println();
        System.out.println("Filtered by nutrition = FOUR_TIMES:");
        System.out.println(Arrays.toString(VoucherFilter.getVouchersByNutrition(list,
                NutritionType.FOUR_TIMES).toArray()));
    }
}
