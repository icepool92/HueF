package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Main extends Application{

	private Stage primaryStage;
	private AnchorPane menu;

	//creates all the windows that the program runs in
	@Override
	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("HueF");
		initMenu();
	}

	public static void main(String[] args){
		launch(args);
	}

	public void initMenu(){
		try{
			//load that FXML
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MenuBarWindow.fxml"));
			menu = loader.load();

			//make it the stage and show the stage
			Scene scene = new Scene(menu);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
