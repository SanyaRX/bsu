import controller.VoucherProcessing.VoucherManager;
import javafx.application.Application;
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
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Window");
        primaryStage.show();
    }
}
