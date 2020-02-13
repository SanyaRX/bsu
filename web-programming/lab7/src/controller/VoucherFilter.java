package controller;

import model.TravelVoucher;
import model.enums.Duration;
import model.enums.NutritionType;
import model.enums.VehicleType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class represents filter for vouchers.
 * @autor Alexander Rai
 * @version 1.0
 */


public class VoucherFilter {
    /**
     * Filters vouchers by price
     * @param vouchers - list of vouchers to filter
     * @param price - param to filter with
     */
    public static List<TravelVoucher> getVouchersByPrice(List<TravelVoucher> vouchers, int price){
        return vouchers.stream().filter(voucher -> voucher.getPrice() == price).collect(Collectors.toList());
    }

    /**
     * Filters vouchers by duration
     * @param vouchers - list of vouchers to filter
     * @param duration - param to filter with
     */
    public static List<TravelVoucher> getVouchersByDuration(List<TravelVoucher> vouchers, Duration duration){
        return vouchers.stream().filter(voucher -> voucher.getDuration() == duration).collect(Collectors.toList());
    }

    /**
     * Filters vouchers by vehicle type
     * @param vouchers - list of vouchers to filter
     * @param vehicleType - param to filter with
     */
    public static List<TravelVoucher> getVouchersByVehicle(List<TravelVoucher> vouchers, VehicleType vehicleType){
        return vouchers.stream().filter(voucher -> voucher.getVehicleType() == vehicleType).collect(Collectors.toList());
    }

    /**
     * Filters vouchers by nutrition type
     * @param vouchers - list of vouchers to filter
     * @param nutritionType - param to filter with
     */
    public static List<TravelVoucher> getVouchersByNutrition(List<TravelVoucher> vouchers, NutritionType nutritionType){
        return vouchers.stream().filter(voucher -> voucher.getNutritionType() == nutritionType).collect(Collectors.toList());
    }

}
