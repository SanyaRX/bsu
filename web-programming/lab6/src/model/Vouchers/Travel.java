package model.Vouchers;

import model.TravelVoucher;
import model.enums.Duration;
import model.enums.NutritionType;
import model.enums.VehicleType;

import java.io.Serializable;
import java.security.InvalidParameterException;

/**
 * Class represents voucher for travel.
 * @autor Alexander Rai
 * @version 1.0
 */


public class Travel extends TravelVoucher implements Serializable {

    /** City to travel to */
    private String cityToTravel = null;

    private Travel(){}

    /** Creates a travel object
     * @param cityToTravel - city to travel to
     * */
    public Travel(String cityToTravel){
       this(cityToTravel, VehicleType.BUS, NutritionType.THREE_TIMES, Duration.ONE_WEEK);
    }

    /** Creates a travel object
     * @param cityToTravel - city to travel to
     * @param vehicleType - type of vehicle
     * @param nutritionType - type of nutrition
     * @param duration - duration of travel
     * */
    public Travel(String cityToTravel, VehicleType vehicleType, NutritionType nutritionType,
                  Duration duration){
       if(cityToTravel.length() == 0)
           throw new InvalidParameterException("Invalid city");

       this.cityToTravel = cityToTravel;

        this.vehicleType = vehicleType;
        this.nutritionType = nutritionType;
        this.duration = duration;
    }


    /** Set's cityToTravel field
     * @param cityToTravel - city to travel to
     * */
    public void setCityToTravel(String cityToTravel){
        this.cityToTravel = cityToTravel;
    }

    /** Returns city to travel */
    public String getCityToTravel() {
        return cityToTravel;
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

        return hashCode() == ((Travel)obj).hashCode();
    }

    /** Returns object's hash code */
    @Override
    public int hashCode() {
        return vehicleType.hashCode() * duration.hashCode() % Integer.MAX_VALUE
                + nutritionType.hashCode() * cityToTravel.hashCode() % Integer.MAX_VALUE;
    }

    /** returns string that represents travel */
    @Override
    public String toString() {
        return "type: Cruise\n" + super.toString() + "\ncity:" + cityToTravel + "\n";
    }

    /** Returns builder object */
    public static TravelBuilder newBuilder(){
        return new Travel().new TravelBuilder();
    }

    public class TravelBuilder extends VoucherBuilder<TravelBuilder>{

        private TravelBuilder() {
            super();
        }

        /** Sets cityToTravel */
        public TravelBuilder setCityToTravel(String cityToTravel) {
            Travel.this.cityToTravel = cityToTravel;

            return this;
        }

        /** Returns built Travel class object*/
        public Travel build(){
            return Travel.this;
        }
    }
}
