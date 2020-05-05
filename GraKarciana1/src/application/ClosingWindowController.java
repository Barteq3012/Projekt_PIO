package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ClosingWindowController {
	
	@FXML
	void actionifclose(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void actionifnotclose(ActionEvent event) {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}


}