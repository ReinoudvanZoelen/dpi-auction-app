import auction.participants.Participant;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ParticipantUI.fxml"));

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Veiling");
        stage.setScene(scene);
        stage.show();
    }


}
