package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WinController {

    @FXML
    private AnchorPane scoreAnchorPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text gameStatus;

    @FXML
    private Text playerPoints;

    @FXML
    private Text opponentPoints;

    @FXML
    private Button menuButton;

    @FXML
    private Button campaignButton;

    @FXML
    void backToCampaign(ActionEvent event) throws IOException {

        AnchorPane camp = FXMLLoader.load(getClass().getResource("/design/Campaign.fxml"));
        scoreAnchorPane.getChildren().setAll(camp);
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        AnchorPane menu = FXMLLoader.load(getClass().getResource("/design/Menu.fxml"));
        scoreAnchorPane.getChildren().setAll(menu);
    }

    @FXML
    private void initialize() {

        menuButton.setVisible(false);
        campaignButton.setVisible(false);

        if(Main.mode == 1) {
            campaignButton.setVisible(true);
        }
        else {
            menuButton.setVisible(true);
        }

    }

}
