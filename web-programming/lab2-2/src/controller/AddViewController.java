package controller;

import controller.VoucherProcessing.VoucherFirm;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.TravelVoucher;
import model.VoucherFactory;
import model.enums.Duration;
import model.enums.NutritionType;
import model.enums.VehicleType;
import model.enums.VoucherType;

import java.lang.management.PlatformLoggingMXBean;

/**
 * Class represents controller for AddView window.
 * @autor Alexander Rai
 * @version 1.0
 */
public class AddViewController {
    /** List of voucher types for combo box */
    private ObservableList<VoucherType> voucherTypesList = FXCollections.observableArrayList(VoucherType.TRAVEL,
            VoucherType.TOUR, VoucherType.MEDICATION, VoucherType.CRUISE);

    /** List of vehicle types for combo box */
    private ObservableList<VehicleType> vehicleTypesList = FXCollections.observableArrayList( VehicleType.CAR,
            VehicleType.TRAIN, VehicleType.BUS, VehicleType.PLAIN, VehicleType.LINEAR);

    /** List of nutrition enum constants for combo box */
    private ObservableList<NutritionType> nutritionTypesList = FXCollections.observableArrayList(NutritionType.WITHOUT,
            NutritionType.ONE_TIME, NutritionType.TWO_TIMES, NutritionType.THREE_TIMES,
            NutritionType.FOUR_TIMES, NutritionType.FIVE_TIMES);

    /** List of duration enum constants for combo box */
    private ObservableList<Duration> durationTypesList = FXCollections.observableArrayList(Duration.ONE_WEEK,
            Duration.TWO_WEEKS, Duration.THREE_WEEKS, Duration.ONE_MONTH, Duration.TWO_MONTHS, Duration.THREE_MONTHS);

    /** Combo box for voucher type */
    @FXML
    private ComboBox<VoucherType> voucherTypeComboBox;

    /** Combo box for vehicle type */
    @FXML
    private ComboBox<VehicleType> vehicleTypeComboBox;

    /** Combo box for nutrition */
    @FXML
    private ComboBox<NutritionType> nutritionComboBox;

    /** Combo box for duration */
    @FXML
    private ComboBox<Duration> durationTypeComboBox;

    /** Text field for price parameter */
    @FXML
    private TextField priceTextField;

    /** Text field for additional parameter */
    @FXML
    private TextField addParamTextField;

    /** Label for additional parameter text field */
    @FXML
    private Label addParamLabel;

    /** Button for adding */
    @FXML
    private Button addButton;


    private VoucherFirm voucherFirm = null;
    private IndexController indexController = null;

    /** Initializes new form */
    @FXML
    private void initialize(){
        voucherTypeComboBox.setItems(voucherTypesList);
        voucherTypeComboBox.setValue(VoucherType.TRAVEL);

        vehicleTypeComboBox.setItems(vehicleTypesList);
        vehicleTypeComboBox.setValue(VehicleType.CAR);

        nutritionComboBox.setItems(nutritionTypesList);
        nutritionComboBox.setValue(NutritionType.THREE_TIMES);

        durationTypeComboBox.setItems(durationTypesList);
        durationTypeComboBox.setValue(Duration.TWO_MONTHS);

        voucherTypeChanged();
    }


    /** Initializes fields by params passing by parent form */
    public void init(VoucherFirm voucherFirm, IndexController indexController){
        this.voucherFirm = voucherFirm;
        this.indexController = indexController;
    }

    /** Shows an error message in new window */
    private void showMessage(String shortMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(shortMessage);
        //alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(description)));
        alert.showAndWait();
    }

    /** Adding button press handle */
    public void handleAdding() {
        int price;

        try {
            price = Integer.parseInt(priceTextField.getText());
        } catch (Exception ex) {
            showMessage("Invalid price");
            return;
        }

        if (price <= 0) {
            showMessage("Price can't be less than 1");
            return;
        }

        if (addParamTextField.getText() == "") {
            showMessage("Enter additional parameter");
            return;
        }

        VoucherFactory voucherFactory = new VoucherFactory(vehicleTypeComboBox.getValue(),
                durationTypeComboBox.getValue(), nutritionComboBox.getValue());

        TravelVoucher travelVoucher = voucherFactory.getVoucher(voucherTypeComboBox.getValue(),
                addParamTextField.getText());

        voucherFirm.addVoucher(travelVoucher);
        indexController.printAllVouchers();
        closeWindow();
    }

    /** Closes current window */
    @FXML
    private void closeWindow(){
        Stage stage = (Stage)addButton.getScene().getWindow();
        stage.close();
    }

    /** Voucher type combo box onAction handler */
    public void voucherTypeChanged(){
        String voucherText = "";
        switch (voucherTypeComboBox.getValue()){
            case TRAVEL: voucherText = "City to travel:"; break;
            case TOUR: voucherText = "Places to visit:"; break;
            case CRUISE: voucherText = "Cabin:"; break;
            case MEDICATION: voucherText = "Hospital:"; break;
            default:
                voucherText = "Add parameter:";
        }

        addParamLabel.setText(voucherText);
    }
}
