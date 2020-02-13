package model.Vouchers;

import model.TravelVoucher;
import model.enums.Duration;
import model.enums.NutritionType;
import model.enums.VehicleType;

import java.security.InvalidParameterException;

/**
 * Class represents voucher for tour.
 * @autor Alexander Rai
 * @version 1.0
 */


public class Tour extends TravelVoucher {

    /** Places to visit */
    private String placesToVisit = null;

    private Tour(){}

    /** Creates a travel object
     * @param placesToVisit - places to visit
     * @param vehicleType - type of vehicle
     * @param nutritionType - type of nutrition
     * @param duration - duration of travel
     * */
    public Tour(String placesToVisit, VehicleType vehicleType, NutritionType nutritionType,
                  Duration duration, int price){
        if(placesToVisit.length() == 0)
            throw new InvalidParameterException("Specify places to visit");

        this.placesToVisit = placesToVisit;

        this.vehicleType = vehicleType;
        this.nutritionType = nutritionType;
        this.duration = duration;
        this.price = price;
    }

    /** Sets places to visit */
    public void setPlacesToVisit(String placesToVisit) {
        this.placesToVisit = placesToVisit;
    }

    /** Gets places to visit */
    public String getPlacesToVisit() {
        return placesToVisit;
    }

    /**
     * Compares two Tour objects
     * */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Tour))
            return false;

        return hashCode() == ((Tour)obj).hashCode();
    }

    /** Returns object's hash code */
    @Override
    public int hashCode() {
        return vehicleType.hashCode() * duration.hashCode() % Integer.MAX_VALUE
                + nutritionType.hashCode() * placesToVisit.hashCode() % Integer.MAX_VALUE;
    }

    /** returns string that represents travel */
    @Override
    public String toString() {
        return "type:Tour\n" + super.toString() + "\nplaces:" + placesToVisit + "\n";
    }

    /** Returns builder object */
    public static TourBuilder newBuilder(){
        return new Tour().new TourBuilder();
    }

    public class TourBuilder extends VoucherBuilder<TourBuilder>{

        private TourBuilder() {
            super();
        }

        /** Sets places to visit */
        public TourBuilder setPlacesToVisit(String placesToVisit) {
            Tour.this.placesToVisit = placesToVisit;

            return this;
        }

        /** Returns built Tour class object*/
        public Tour build(){
            return Tour.this;
        }
    }
}
