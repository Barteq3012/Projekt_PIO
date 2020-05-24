package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static int code = 0;
	
	static Stage primaryStage2;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Pane rootPane = (Pane)FXMLLoader.load(getClass().getResource("/design/Menu.fxml")); //wczytywanie pliku fxml
			Scene scene = new Scene(rootPane,1280,700);
			
			scene.getStylesheets().add(getClass().getResource("/design/Menu.css").toExternalForm());
			//primaryStage = primaryStage2;
			primaryStage.setScene(scene);
			primaryStage.setTitle("Menu"); // tytul
			primaryStage.setOpacity(1); //przezroczystosc 
			primaryStage.setResizable(false); // nie zmieniamy rozmiaru okna
			primaryStage.show();
    		primaryStage.getIcons().add(new Image("pictures/icon.png"));
    		primaryStage2 = primaryStage;

			AllCard.createCards();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		launch(args);

	}
}
