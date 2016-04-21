package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SolidColorInputPromptController {

	@FXML
	private TextField red;

	@FXML
	private TextField green;

	@FXML
	private TextField blue;

	@FXML
	private Button Accept;

	@FXML
	private Button Cancel;

	private Stage stage;

	int[] color;

	public void initialize(){}

	public void setStage(Stage stage){
		this.stage = stage;
	}

	@FXML
	public void acceptPressed(){
		if(checkInput()){
			stage.close();
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("error");
			alert.setHeaderText(null);
			alert.setContentText("Invalid Input");
			alert.showAndWait();
		}
	}

	@FXML
	public void cancelPressed(){
		stage.close();
	}

	public void setArray(int[] arr){
		color = arr;
	}

	private boolean checkInput(){
		try{
			int r = Integer.parseInt(red.getText());
			int g = Integer.parseInt(green.getText());
			int b = Integer.parseInt(blue.getText());
			if(r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255){
				color[0] = r;
				color[1] = g;
				color[2] = b;
				return true;
			}
			return false;
		}
		catch(NumberFormatException e){
			return false;
		}
	}

}
