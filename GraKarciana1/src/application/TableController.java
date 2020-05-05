package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TableController {


    @FXML
    private Button takeHpOpponent;

    @FXML
    private Button takeSdOpponent;

    @FXML
    private Button killCardsPlayer;

    @FXML
    private Button surrender;

    @FXML
    private Text hpCounterOpponent;

    @FXML
    private Text spCounterOpponent;

    @FXML
    private  Text hpCounterPlayer;

    @FXML
    private  Text spCounterPlayer;

    @FXML
    private Text playerName;

    @FXML
    private Text opponentName;

    @FXML
    private Text playerStackOfCardsCounter;

    @FXML
    private Text opponentStackOfCardsCounter;

    @FXML
    private ImageView opponentStackOfCards;

    @FXML
    private ImageView playerStackOfCards;

    @FXML
    private void initialize () {


    }

    @FXML
    private void surrenderClose() {
        // get a handle to the stage
        Stage stage = (Stage) surrender.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}