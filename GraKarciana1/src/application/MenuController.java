package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuController {

	Duel duel;



	@FXML
    private Pane menuPane;

	@FXML
	private AnchorPane menuAnchor;

    @FXML
    void actionOfCampaignButton(ActionEvent event) {
    	
    	
    }
    
    @FXML
    void actionOfDuelButton(ActionEvent event) {
    	try {

    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/design/Table.fxml"));
    		Pane tableRoot = fxmlLoader.load();

			duel = new Duel(tableRoot);
			Campaign campaign = new Campaign();

			Stage window = Main.primaryStage2;
			window.getScene().setRoot(tableRoot);
			window.show();

			//duel.startDuel();
			campaign.startLevel1(tableRoot);
  
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }

    @FXML
    void actionOfHowToPlayButton(ActionEvent event) throws IOException {

		AnchorPane howToPlay = FXMLLoader.load(getClass().getResource("/design/howtoplay.fxml"));
		menuAnchor.getChildren().setAll(howToPlay);

/* stare
    	menuPane = FXMLLoader.load(getClass().getResource("/design/howtoplay.fxml")); //wczytywanie pliku fxml
		Scene scene = new Scene(menuPane,700,500);
		
		Main.primaryStage2.setScene(scene);


 */

    }

    @FXML
    void actionOfAuthorsButton() throws IOException {

		AnchorPane author = FXMLLoader.load(getClass().getResource("/design/authors.fxml"));
		menuAnchor.getChildren().setAll(author);
   /* stare
    	menuPane = FXMLLoader.load(getClass().getResource("/design/authors.fxml")); //wczytywanie pliku fxml
		Scene scene = new Scene(menuPane,700,500);

		Main.primaryStage2.setScene(scene);

    */
    }
    
    @FXML
    void actionOfExitButton(ActionEvent event) {
    	try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/design/closingwindow.fxml"));
    		Parent root1 = fxmlLoader.load();
    		Stage exitStage = new Stage();
    		exitStage.initStyle(StageStyle.UNDECORATED);
    		exitStage.setScene(new Scene(root1));
    		exitStage.show();
    		exitStage.setAlwaysOnTop(true);
    		exitStage.setOpacity(0.95);
    		exitStage.setTitle("Exit");

    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }

}

