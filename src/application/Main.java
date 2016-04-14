package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Main extends Application{

	private Stage primaryStage;
	private Stage canvasStage;
	private AnchorPane menu;
	private AnchorPane canvas;

	@Override
	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Drôr");

		initMenu();
		initCanvas();
		drawToCanvas();
	}

	public void drawToCanvas(){

	}

	public static void main(String[] args){
		launch(args);
	}

	public void initCanvas(){
		try{
			//load the FXML
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CanvasArea.fxml"));
			canvas = loader.load();

			//this lets the canvas co-exist with the menu(?)
			canvasStage = new Stage();
			canvasStage.setTitle("Canvas");
			canvasStage.initModality(Modality.NONE);
			canvasStage.initOwner(primaryStage);

			//make that canvas
			Scene scene = new Scene(canvas);
			canvasStage.setScene(scene);
			canvasStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void initMenu(){
		try{
			//load that FXML
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MenuBarWindow.fxml"));
			menu = loader.load();

			//make it the stage and show that thing
			Scene scene = new Scene(menu);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
