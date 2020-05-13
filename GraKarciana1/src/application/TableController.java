package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class TableController {

//    public static String playerNameOf;
    private Pane tablePane;


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
    void initialize() {


//        playerName.setText(playerNameOf);


    }



    @FXML
    private void surrenderClose(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/design/Scoreboard.fxml"));
            Pane scoreboard =  fxmlLoader.load();


            Stage scoreStage = new Stage();
            scoreStage.setScene(new Scene(scoreboard));
            scoreStage.show();
            scoreStage.setAlwaysOnTop(false);
            scoreStage.setOpacity(1);
            scoreStage.setTitle("Wynik");
            scoreStage.getIcons().add(new Image("pictures/icon.png"));
            System.out.println("Wynik");



        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();


    }





}