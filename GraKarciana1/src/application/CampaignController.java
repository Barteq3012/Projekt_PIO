package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CampaignController {

    private int levelCompleted = MenuController.level;

   /* public PassController(int levelCompleted)
    {
        this.levelCompleted = levelCompleted;
    }

    */


    @FXML
    private Button oneButton;

    @FXML
    private AnchorPane campAPain;

    @FXML
    private Button twoButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button fiveButton;

    @FXML
    private Button menuButton;

    @FXML
    private ImageView twoLock;

    @FXML
    private ImageView threeLock;

    @FXML
    private ImageView fourLock;

    @FXML
    private ImageView fiveLock;

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        AnchorPane menu = FXMLLoader.load(getClass().getResource("/design/Menu.fxml"));
        campAPain.getChildren().setAll(menu);
    }

    @FXML
    void fiveStart(ActionEvent event) {

    }

    @FXML
    void fourStart(ActionEvent event) {

    }

    @FXML
    void oneStart(ActionEvent event) throws IOException {
        AnchorPane lvl1 = FXMLLoader.load(getClass().getResource("/design/Level1.fxml"));
        campAPain.getChildren().setAll(lvl1);
    }

    @FXML
    void threeStart(ActionEvent event) throws IOException {
        AnchorPane lvl3 = FXMLLoader.load(getClass().getResource("/design/Level3.fxml"));
        campAPain.getChildren().setAll(lvl3);
    }

    @FXML
    void twoStart(ActionEvent event) throws IOException {
        AnchorPane lvl2 = FXMLLoader.load(getClass().getResource("/design/Level2.fxml"));
        campAPain.getChildren().setAll(lvl2);

    }

    @FXML
    void initialize()
    {
        twoButton.setVisible(false);
        threeButton.setVisible(false);
        fourButton.setVisible(false);
        fiveButton.setVisible(false);

        System.out.println(levelCompleted);
        if(levelCompleted >= 1)
        {
            twoButton.setVisible(true);
            twoLock.setVisible(false);
        }
        if(levelCompleted >= 2)
        {
            threeButton.setVisible(true);
            threeLock.setVisible(false);
        }
        if (levelCompleted >= 3)
        {
            fourButton.setVisible(true);
            fourLock.setVisible(false);
        }
        if (levelCompleted >= 4)
        {
            fiveButton.setVisible(true);
            fiveLock.setVisible(false);
        }
    }

}
