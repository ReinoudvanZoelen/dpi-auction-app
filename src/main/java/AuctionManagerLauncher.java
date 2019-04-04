import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AuctionManagerLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AuctionManagerUI.fxml"));

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Veiling Management");
        stage.setScene(scene);
        stage.show();
    }

}
