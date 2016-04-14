package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import application.view.CanvasController;


public class OldMain extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");

		initRootLayout();
		
		showCanvas();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void showCanvas(){
		try {
			//load the persons
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(OldMain.class.getResource("view/CanvasArea.fxml"));
			AnchorPane CanvasArea = (AnchorPane) loader.load();

			//put it at the left
			rootLayout.setCenter(CanvasArea);

			CanvasController controller = loader.getController();
			//controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void initRootLayout() {
		try{
			//load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(OldMain.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			//show the scene in the layout
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
