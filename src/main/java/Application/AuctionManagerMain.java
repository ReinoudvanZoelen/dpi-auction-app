package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AuctionManagerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AuctionManagerUI.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Auction - Administration console");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
