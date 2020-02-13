package model;

import model.Vouchers.Cruise;
import model.Vouchers.Medication;
import model.Vouchers.Tour;
import model.Vouchers.Travel;
import model.enums.Duration;
import model.enums.NutritionType;
import model.enums.VehicleType;
import model.enums.VoucherType;

/**
 * Class represents factory pattern for TravelVoucher.
 * @autor Alexander Rai
 * @version 1.0
 */

public class VoucherFactory {
    /** Vehicle type for new vouchers */
    VehicleType vehicleType;

    /** Duration for new vouchers */
    Duration duration;

    /** Nutrition type for new vouchers */
    NutritionType nutritionType;

    /** Creates new factory and specifies parameters for new vouchers */
    public VoucherFactory(VehicleType vehicleType, Duration duration, NutritionType nutritionType){
        this.duration = duration;
        this.vehicleType = vehicleType;
        this.nutritionType = nutritionType;
    }

    /** Creates new factory with defaults fields */
    public VoucherFactory(){
        this(VehicleType.CAR, Duration.TWO_WEEKS, NutritionType.THREE_TIMES);
    }

    /** Return specified voucher */
    public TravelVoucher getVoucher(VoucherType voucherType, String param){
        TravelVoucher travelVoucher = null;

        switch (voucherType){
            case MEDICATION: travelVoucher = new Medication(param, vehicleType, nutritionType, duration); break;
            case CRUISE: travelVoucher = new Cruise(param, nutritionType, duration); break;
            case TOUR: travelVoucher = new Tour(param, vehicleType, nutritionType, duration); break;
            case TRAVEL: travelVoucher = new Travel(param, vehicleType, nutritionType, duration); break;
            default:
                travelVoucher = null;
        }

        return travelVoucher;
    }
}
