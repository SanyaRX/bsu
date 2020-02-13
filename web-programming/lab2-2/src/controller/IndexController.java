package controller;

import controller.VoucherProcessing.VoucherFilter;
import controller.VoucherProcessing.VoucherFirm;
import controller.sorting.comparator.DurationComparator;
import controller.sorting.comparator.PriceComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.TravelVoucher;
import model.enums.Duration;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * Class represents controller for Index window.
 * @autor Alexander Rai
 * @version 1.0
 */

public class IndexController {

    /** List of sorting parameters for combo box */
    private ObservableList<String> sortingParameters = FXCollections.observableArrayList("by price", "by duration");
    public static VoucherFirm voucherFirm = null;

    /** List of duration enum constants for combo box */
    private ObservableList<Duration> durationTypesList = FXCollections.observableArrayList(Duration.ONE_WEEK,
            Duration.TWO_WEEKS, Duration.THREE_WEEKS, Duration.ONE_MONTH, Duration.TWO_MONTHS, Duration.THREE_MONTHS);

    /** ComboBox for sorting field */
    @FXML
    private ComboBox sortingFields;

    /** ComboBox for filtering by duration */
    @FXML
    private ComboBox<Duration> durationFields;

    /** Text area for output */
    @FXML
    private TextArea outputTextArea;

    /** Text field for index voucher to remove */
    @FXML
    private TextField removeTextField;

    /** Text field for price to filter list with */
    @FXML
    private TextField priceFilterTextField;

    /** Initializes new form */
    @FXML
    private void initialize(){
        voucherFirm = new VoucherFirm();
        sortingFields.setValue("by price");
        sortingFields.setItems(sortingParameters);

        durationFields.setValue(Duration.ONE_WEEK);
        durationFields.setItems(durationTypesList);

        outputTextArea.setEditable(false);

        printAllVouchers();
    }

    /** Prints all voucher to output area */
    public void printAllVouchers(){
        List vouchers = voucherFirm.getTravelVouchers();
        outputTextArea.setText("");
        for (int i = 0; i < vouchers.size(); i++){
            outputTextArea.appendText((i + 1) + ")" + vouchers.get(i) + "\n-----------\n" );
        }
    }

    /** Prints list of vouchers to output area */
    private void printParticularVouchers(List<TravelVoucher> vouchers){
        outputTextArea.setText("");
        for (int i = 0; i < vouchers.size(); i++) {
            outputTextArea.appendText((i + 1) + ")" + vouchers.get(i) + "\n-----------\n");
        }
    }

    /** Handles sort button pressing */
    public void handleSortButton(){
        String value = ((String)sortingFields.getValue()).split("\\s+")[1];
        Comparator comparator = null;
        switch (value){
            case "price": comparator = new PriceComparator(); break;
            case "duration": comparator = new DurationComparator(); break;
            default: comparator = new PriceComparator(); break;
        }
        voucherFirm.sort(comparator, true);
        printAllVouchers();
    }

    /** Shows an error message in new window */
    private void showMessage(String shortMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(shortMessage);
        //alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(description)));
        alert.showAndWait();
    }

    /** Handles remove button pressing */
    public void handleRemove(){
        int index = -1;
        System.out.println(removeTextField.getText());
        try {
            index = Integer.parseInt(removeTextField.getText());
        } catch (Exception ex){
            showMessage("You must enter a positive integer number");
            return;
        }
        if (index < 0 || index > voucherFirm.getTravelVouchers().size()){
            showMessage("Index not in range of array length");
            return;
        }
        voucherFirm.removeVoucher(index - 1);
        printAllVouchers();
    }

    /** Handles filter by price button pressing */
    public void handleFilterByPrice(){
        List<TravelVoucher> filteredVouchers = null;
        try {
            filteredVouchers = VoucherFilter.getVouchersByPrice(voucherFirm.getTravelVouchers(),
                    Integer.parseInt(priceFilterTextField.getText()));
        } catch (Exception ex){
            showMessage("You must enter a positive integer number");
            return;
        }
        printParticularVouchers(filteredVouchers);
    }

    /** Handles filter by duration button pressing*/
    public void handleFilterByDuration(){
        List<TravelVoucher> filteredVouchers = null;

        filteredVouchers = VoucherFilter.getVouchersByDuration(voucherFirm.getTravelVouchers(),
                durationFields.getValue());

        printParticularVouchers(filteredVouchers);
    }

    /** Handles add new voucher button pressing */
    public void handleAddingNewVoucher(){
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddView.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Voucher Adding");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);

        AddViewController addViewController = loader.getController();
        addViewController.init(voucherFirm, this);

        primaryStage.showAndWait();


    }



}
