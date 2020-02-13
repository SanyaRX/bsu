import controller.VoucherProcessing.VoucherFirm;
import controller.VoucherProcessing.VoucherManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class to show work with classes.
 * @autor Alexander Rai
 * @version 1.0
 */

public class Main extends Application {

    /**
     * Method that runs the program
     * */
    public static void main(String[] args) {
        //VoucherManager voucherManager = new VoucherManager();
        //voucherManager.processFirm();
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Index.fxml"));
        primaryStage.setTitle("Voucher Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
