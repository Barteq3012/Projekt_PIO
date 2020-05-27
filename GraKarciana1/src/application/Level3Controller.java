package application;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Level3Controller {

    @FXML
    private ImageView playerImage;

    @FXML
    private ImageView opponentImage;

    @FXML
    private Text playerName;

    @FXML
    private Text opponentName;

    @FXML
    private Text mainText;

    @FXML
    private Text descriptionText;

    @FXML
    private Button nextButton;

    @FXML
    void doNext(ActionEvent event) {
        //uruchomienie poziomku
    }

    @FXML
    void initialize()
    {

        FadeTransition ft = new FadeTransition(Duration.millis(4200), mainText);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();

        FadeTransition ft2 = new FadeTransition(Duration.millis(4200), descriptionText);
        ft2.setFromValue(0.0);
        ft2.setToValue(1.0);
        ft2.setCycleCount(1);
        ft2.setAutoReverse(true);

        SequentialTransition seqTransition = new SequentialTransition (
                new PauseTransition(Duration.millis(2000)), // wait a second
                ft2
        );
        seqTransition.play();

        FadeTransition ft3 = new FadeTransition(Duration.millis(4500), playerImage);
        ft3.setFromValue(0.0);
        ft3.setToValue(1.0);
        ft3.setCycleCount(1);
        ft3.setAutoReverse(true);

        FadeTransition ft3b = new FadeTransition(Duration.millis(1000), playerName);
        ft3b.setFromValue(0.0);
        ft3b.setToValue(1.0);
        ft3b.setCycleCount(1);
        ft3b.setAutoReverse(true);

        FadeTransition ft4 = new FadeTransition(Duration.millis(5000), opponentImage);
        ft4.setFromValue(0.0);
        ft4.setToValue(1.0);
        ft4.setCycleCount(1);
        ft4.setAutoReverse(true);

        FadeTransition ft4b = new FadeTransition(Duration.millis(1000), opponentName);
        ft4b.setFromValue(0.0);
        ft4b.setToValue(1.0);
        ft4b.setCycleCount(1);
        ft4b.setAutoReverse(true);

        SequentialTransition seqTransition2 = new SequentialTransition (
                new PauseTransition(Duration.millis(6000)), // wait a second
                ft3,ft3b, ft4, ft4b
        );
        seqTransition2.play();


    }
}
