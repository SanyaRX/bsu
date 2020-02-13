package controller.VoucherProcessing;

import model.TravelVoucher;
import model.Vouchers.Cruise;
import model.Vouchers.Medication;
import model.Vouchers.Tour;
import model.Vouchers.Travel;
import model.enums.Duration;
import model.enums.NutritionType;
import model.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents voucher firm.
 * @autor Alexander Rai
 * @version 1.0
 */

public class VoucherFirm {

    /** List of vouchers */
    private List<TravelVoucher> travelVouchers = null;

    /**
     * Constructor with no parameters. Fills travelVouchers list with default values.
     * */
    public VoucherFirm() {

        travelVouchers = new ArrayList<>(4);

        Tour tour = Tour.newBuilder()
                .setVehicleType(VehicleType.CAR)
                .setPrice(200)
                .setDuration(Duration.THREE_WEEKS)
                .setNutritionType(NutritionType.FIVE_TIMES)
                .setPlacesToVisit("minsk, mozyr, mikashi")
                .build();

        Cruise cruise = Cruise.newBuilder()
                .setCabin("123")
                .setDuration(Duration.TWO_MONTHS)
                .setNutritionType(NutritionType.FOUR_TIMES)
                .setPrice(1000)
                .setVehicleType(VehicleType.LINEAR)
                .build();

        Medication medication = Medication.newBuilder()
                .setHospital("BSMP")
                .setDuration(Duration.ONE_MONTH)
                .setNutritionType(NutritionType.FOUR_TIMES)
                .setPrice(800)
                .setVehicleType(VehicleType.BUS)
                .build();

        Travel travel = Travel.newBuilder()
                .setCityToTravel("LA")
                .setDuration(Duration.ONE_WEEK)
                .setNutritionType(NutritionType.THREE_TIMES)
                .setPrice(250)
                .setVehicleType(VehicleType.PLAIN)
                .build();


        travelVouchers.add(tour);
        travelVouchers.add(cruise);
        travelVouchers.add(medication);
        travelVouchers.add(travel);
    }

    /**
     * Sets travelVouchers list with list getting through parameters.
     * @param travelVouchers - vouchers to set with
     * */
    public VoucherFirm(List<TravelVoucher> travelVouchers){
        this.travelVouchers = travelVouchers;
    }


    /**
     * Adds new voucher to list.
     * @param travelVoucher - voucher to add
     * */
    public void addVoucher(TravelVoucher travelVoucher){
        travelVouchers.add(travelVoucher);
    }

    /**
     * Removes voucher from list by index
     * @param index - index of voucher to remove
     * @return the element previously at the specified position
     * */
    public TravelVoucher removeVoucher(int index){
        return travelVouchers.remove(index);
    }

    /**
     * Removes a specific object from list
     * @param travelVoucher - TravelVoucher object to remove
     * @return true if this list contained the specified element
     * */
    public boolean removeVoucher(TravelVoucher travelVoucher){
        return travelVouchers.remove(travelVoucher);
    }

    /**
     * Returns travel voucher list
     * */
    public List<TravelVoucher> getTravelVouchers() {
        return travelVouchers;
    }
}
