package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class IntroController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane introPane;

    @FXML
    private Text introText;

    @FXML
    private Text introText2;

    @FXML
    void initialize() throws IOException {

        AnchorPane menu = FXMLLoader.load(getClass().getResource("/design/Menu.fxml"));
        FadeTransition ft = new FadeTransition(Duration.millis(4200), introText);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();

        System.out.println("ahoj");

        FadeTransition ft2 = new FadeTransition(Duration.millis(2200), introText2);
        ft2.setFromValue(0.0);
        ft2.setToValue(1.0);
        ft2.setCycleCount(1);
        ft2.setAutoReverse(true);
        ft2.setOnFinished(event -> introPane.getChildren().setAll(menu));


        SequentialTransition seqTransition = new SequentialTransition (
                new PauseTransition(Duration.millis(2000)), // wait a second
                ft2
        );
        seqTransition.play();

    }
}
