package model.Vouchers;

import model.TravelVoucher;
import model.enums.Duration;
import model.enums.NutritionType;
import model.enums.VehicleType;

import java.security.InvalidParameterException;

/**
 * Class represents voucher for medication.
 * @autor Alexander Rai
 * @version 1.0
 */


public class Medication extends TravelVoucher {

    /** Hospital to treat at */
    private String hospital = null;

    private Medication(){}

    /** Creates a medication object
     * @param hospital - hospital to treat at
     * */
    public Medication(String hospital){
        this(hospital, VehicleType.CAR, NutritionType.THREE_TIMES, Duration.THREE_WEEKS);
    }

    /** Creates a travel object
     * @param hospital - hospital to treat at
     * @param vehicleType - type of vehicle
     * @param nutritionType - type of nutrition
     * @param duration - duration of travel
     * */
    public Medication(String hospital, VehicleType vehicleType, NutritionType nutritionType,
                Duration duration){
        if(hospital.length() == 0)
            throw new InvalidParameterException("Specify a hosp. to treat at");

        this.hospital = hospital;

        this.vehicleType = vehicleType;
        this.nutritionType = nutritionType;
        this.duration = duration;
    }

    /** Sets hospital */
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    /** Returns hospital */
    public String getHospital() {
        return hospital;
    }

    /**
     * Compares two Medication objects
     * */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Medication))
            return false;

        return hashCode() == ((Medication)obj).hashCode();
    }

    /** Returns object's hash code */
    @Override
    public int hashCode() {
        return vehicleType.hashCode() * duration.hashCode() % Integer.MAX_VALUE
                + nutritionType.hashCode() * hospital.hashCode() % Integer.MAX_VALUE;
    }

    /** returns string that represents travel */
    @Override
    public String toString() {
        return "type:Medication\n" + super.toString() + "\nhospital:" + hospital;
    }

    /** Returns builder object */
    public static MedicationBuilder newBuilder(){
        return new Medication().new MedicationBuilder();
    }

    public class MedicationBuilder extends VoucherBuilder<MedicationBuilder>{

        private MedicationBuilder() {
            super();
        }

        /** Sets hospital */
        public MedicationBuilder setHospital(String hospital) {
            Medication.this.hospital = hospital;

            return this;
        }

        /** Returns built Medication class object*/
        public Medication build(){
            return Medication.this;
        }
    }
}
