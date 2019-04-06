package Application;

import Controller.BidderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Bidder1Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/BidderUI.fxml"));
        Parent root = fxmlLoader.load();

        BidderController controller = fxmlLoader.<BidderController>getController();
        controller.username = "Reinoud van Zoelen";

        primaryStage.setTitle("Auction bidding - " + controller.username);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
