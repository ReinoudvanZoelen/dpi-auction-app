package Application;

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
        primaryStage.setTitle("Auction bidding - Reinoud van Zoelen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
