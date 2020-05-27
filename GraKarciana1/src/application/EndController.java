package application;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class EndController {

    @FXML
    private AnchorPane endPane;

    @FXML
    private Text firstText;

    @FXML
    private Text secondText;

    @FXML
    private Button menuButton;

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        AnchorPane menu = FXMLLoader.load(getClass().getResource("/design/Menu.fxml"));
        endPane.getChildren().setAll(menu);

    }

    @FXML
    void initialize()
    {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), firstText);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();

        FadeTransition ft2 = new FadeTransition(Duration.millis(2000), secondText);
        ft2.setFromValue(0.0);
        ft2.setToValue(1.0);
        ft2.setCycleCount(1);
        ft2.setAutoReverse(true);

        SequentialTransition seqTransition = new SequentialTransition (
                new PauseTransition(Duration.millis(2000)), // wait a second
                ft2
        );
        seqTransition.play();

        FadeTransition ft3 = new FadeTransition(Duration.millis(3000), menuButton);
        ft3.setFromValue(0.0);
        ft3.setToValue(1.0);
        ft3.setCycleCount(1);
        ft3.setAutoReverse(true);

        SequentialTransition seqTransition2 = new SequentialTransition (
                new PauseTransition(Duration.millis(2000)), // wait a second
                ft3
        );
        seqTransition2.play();

    }
}
