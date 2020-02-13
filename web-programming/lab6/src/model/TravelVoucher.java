package model;

import model.Vouchers.Travel;
import model.enums.*;

import java.io.Serializable;

/**
 * Abstract class provides methods and fields for describing TravelVoucher.
 * @autor Alexander Rai
 * @version 1.0
 */

public abstract class TravelVoucher implements Serializable {

    /** Vehicle for voucher */
    protected VehicleType vehicleType = null;

    /** Nutrition type for voucher */
    protected NutritionType nutritionType = null;

    /** Duration of travelling */
    protected Duration duration = null;

    /** Price of voucher */
    private int price;

    /** Sets vehicle type
     * @param vehicleType - vehicle type
     * */
    public void setVehicleType(VehicleType vehicleType){
        this.vehicleType = vehicleType;
    }

    /** Sets nutrition type
     * @param nutritionType - nutrition type
     * */
    public void setNutritionType(NutritionType nutritionType){
        this.nutritionType = nutritionType;
    }

    /** Sets number of days for travel
     * @param duration - duration type
     * */
    public void setDuration(Duration duration){
        this.duration = duration;
    }

    /** Sets price for travel
     * @param price - duration type
     * */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Compares two Travel objects
     * */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Travel))
            return false;

        return hashCode() == ((TravelVoucher)obj).hashCode();
    }

    /** Returns object's hash code */
    @Override
    public int hashCode() {
        return vehicleType.hashCode() * duration.hashCode() % Integer.MAX_VALUE
                + nutritionType.hashCode() % Integer.MAX_VALUE;
    }

    /** returns string that represents voucher */
    @Override
    public String toString() {
        return "vehicle:" + vehicleType + "\nnutrition:" + nutritionType +
                "\nduration:" + duration + "\nprice:" + price;
    }

    /** Returns number of days for travel */
    public Duration getDuration() {
        return duration;
    }

    /** Returns nutrition type */
    public NutritionType getNutritionType() {
        return nutritionType;
    }

    /** Returns vehicle type */
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    /** Return travel price */
    public int getPrice() { return price; }

    public abstract class VoucherBuilder<T extends VoucherBuilder<T>> {
        protected VoucherBuilder(){}

        /** Sets vehicle type
         * @param vehicleType - vehicle type
         * */
        public T setVehicleType(VehicleType vehicleType){
            TravelVoucher.this.vehicleType = vehicleType;

            return (T)this;
        }

        /** Sets nutrition type
         * @param nutritionType - nutrition type
         * */
        public T setNutritionType(NutritionType nutritionType){
            TravelVoucher.this.nutritionType = nutritionType;

            return (T)this;
        }

        /** Sets number of days for travel
         * @param duration - duration type
         * */
        public T setDuration(Duration duration){
            TravelVoucher.this.duration = duration;

            return (T)this;
        }

        /** Sets price for travel
         * @param price - duration type
         * */
        public T setPrice(int price) {
            TravelVoucher.this.price = price;

            return (T)this;
        }

        /** Returns built TravelVoucher class object*/
        public TravelVoucher build(){
            return TravelVoucher.this;
        }
    }
}
