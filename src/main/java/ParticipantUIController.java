import auction.Participant;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ParticipantUIController implements Initializable {

    protected ListProperty<TextMessage> listProperty = new SimpleListProperty<>();
    Participant participant = new Participant("niels", "auction");
    List<TextMessage> receivedTextMessages = new ArrayList<>();

    @FXML
    private TextField messageTextField;
    @FXML
    private ListView receivedMessagesListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        receivedMessagesListView.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(receivedTextMessages));
    }

    public void GetMessage() {
        TextMessage message = null;
        try {
            message = this.participant.receiveMessage();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        System.out.println("Received");
        System.out.println(message);

        if (message != null) {
            this.listProperty.add(message);
        }
    }

    public void OnSubmitButtonClick() throws JMSException {
        String message = this.messageTextField.getText();

        this.participant.sendMessage(message);
        this.GetMessage();
    }
}
