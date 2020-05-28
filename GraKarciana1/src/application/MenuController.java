package application;

import java.io.*;

import com.sun.glass.ui.CommonDialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuController {

	Duel duel;

	static int level = 5; //poziom w kampanii

	@FXML
    private Pane menuPane;

	@FXML
	private AnchorPane menuAnchor;

	@FXML
	private Button openFileButton;

    @FXML
    void actionOfCampaignButton(ActionEvent event) throws IOException {
		AnchorPane campaign = FXMLLoader.load(getClass().getResource("/design/Campaign.fxml"));
		menuAnchor.getChildren().setAll(campaign);
    	
    }
    
    @FXML
    void actionOfDuelButton(ActionEvent event) {
    	try {

    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/design/Table.fxml"));
    		Pane tableRoot = fxmlLoader.load();

			duel = new Duel(tableRoot);

			Stage window = Main.primaryStage2;
			window.getScene().setRoot(tableRoot);
			window.show();

			duel.startDuel();
  
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

	@FXML
	void openFile(ActionEvent event) throws FileNotFoundException {
		Stage openStage = new Stage();
    	System.out.println("otworz");
		FileChooser filechoose = new FileChooser();
		filechoose.setTitle("Wybierz zapis gry");
		filechoose.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Save Files", "*.sav")
		);

		//Set to user directory or go to default if cannot access
		String userDirectoryString = System.getProperty("user.home");
		File userDirectory = new File(userDirectoryString);
		if(!userDirectory.canRead()) {
			userDirectory = new File("c:/");
		}
		filechoose.setInitialDirectory(userDirectory);

		//filechoose.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Save Files", "*.sav"));

		File selectedFile = filechoose.showOpenDialog(openStage);
		if (selectedFile != null && getFileExtension(selectedFile).equals("sav")) {

			InputStream input = new FileInputStream(selectedFile);
			System.out.println(input);
			ReadFile.getLevelByOpen(input);
		}
		else {
			System.out.println("File selection cancelled.");
			//System.out.println(getFileExtension(selectedFile));
		}


	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".")+1);
		else return "";
	}
}

