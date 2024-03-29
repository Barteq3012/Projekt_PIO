package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AuthorsController {

	@FXML
	private AnchorPane authorsAnchor;


	@FXML
	void returnToMenuAction(ActionEvent event) throws IOException {

		/* stare
		authorPane = FXMLLoader.load(getClass().getResource("/design/Menu.fxml")); // wczytywanie pliku fxml
		Scene scene = new Scene(authorPane, 700, 500);
		Main.primaryStage2.setScene(scene);
		*/

		AnchorPane menu = FXMLLoader.load(getClass().getResource("/design/Menu.fxml"));
		authorsAnchor.getChildren().setAll(menu);
	}

	@FXML
	private void initialize() {

	}

}
