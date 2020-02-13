package model.Vouchers;

import model.TravelVoucher;
import model.enums.Duration;
import model.enums.NutritionType;
import model.enums.VehicleType;

import java.io.Serializable;
import java.security.InvalidParameterException;


/**
 * Class represents voucher for medication.
 * @autor Alexander Rai
 * @version 1.0
 */


public class Cruise extends TravelVoucher implements Serializable {

    /** Number of ship's cabin */
    private String cabin = null;

    private Cruise(){}

    /** Creates a medication object
     * @param cabin - # of ship's cabin
     * */
    public Cruise(String cabin){
        this(cabin, NutritionType.THREE_TIMES, Duration.THREE_WEEKS);
    }

    /** Creates a travel object
     * @param cabin - # of ship's cabin
     * @param nutritionType - type of nutrition
     * @param duration - duration of travel
     * */
    public Cruise(String cabin, NutritionType nutritionType,
                      Duration duration){
        if(cabin.length() == 0)
            throw new InvalidParameterException("Specify cabin");

        this.cabin = cabin;

        this.vehicleType = VehicleType.LINEAR;
        this.nutritionType = nutritionType;
        this.duration = duration;
    }

    @Override
    public void setVehicleType(VehicleType vehicleType) {
        if (vehicleType != VehicleType.LINEAR)
            throw new InvalidParameterException("The only vehicle for cruise is linear");
        super.setVehicleType(vehicleType);
    }


    /** Sets cabin
     * @param cabin - cabin number
     * */
    public void setCabin(String cabin) {
        this.cabin = cabin;
    }


    /** Returns cabin type */
    public String getCabin() {
        return cabin;
    }

    /**
     * Compares two Cruise objects
     * */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Cruise))
            return false;

        return hashCode() == ((Cruise)obj).hashCode();
    }

    /** Returns object's hash code */
    @Override
    public int hashCode() {
        return vehicleType.hashCode() * duration.hashCode() % Integer.MAX_VALUE
                + nutritionType.hashCode() * cabin.hashCode() % Integer.MAX_VALUE;
    }

    /** returns string that represents travel */
    @Override
    public String toString() {
        return "type: Cruise\n" + super.toString() + "\ncabin:" + cabin + "\n";
    }

    /** Returns builder object */
    public static CruiseBuilder newBuilder(){
        return new Cruise().new CruiseBuilder();
    }

    public class CruiseBuilder extends VoucherBuilder<CruiseBuilder>{

        private CruiseBuilder() {
            super();
        }

        /** Sets cabin */
        public CruiseBuilder setCabin(String cabin) {
            Cruise.this.cabin = cabin;

            return this;
        }

        /** Returns built Cruise class object*/
        public Cruise build(){
            return Cruise.this;
        }
    }
}
