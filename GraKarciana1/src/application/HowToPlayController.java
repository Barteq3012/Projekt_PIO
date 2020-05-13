package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HowToPlayController {

    @FXML
    private AnchorPane howPane;

    @FXML
    private Button returnButton;

    @FXML
    void returnToMenuAction(ActionEvent event)  throws IOException {
        AnchorPane menu = FXMLLoader.load(getClass().getResource("/design/Menu.fxml"));
        howPane.getChildren().setAll(menu);

    }

}

