package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SaveGifPromptController {

	@FXML
	private TextField frame;

	@FXML
	private TextField frameDelay;

	private int[] input;
	private Stage stage;

	public void initialize(){}

	public void setArray(int[] arr){
		input = arr;
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}

	@FXML
	private void acceptPressed(){
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
	private void cancelPressed(){
		input[0] = 0;
		stage.close();
	}

	private boolean checkInput(){
		try{
			input[0] = Integer.parseInt(frame.getText());
			input[1] = Integer.parseInt(frameDelay.getText());
			if(input[0] > 0 && input[1] > 0){
				return true;
			}
		}
		catch(NumberFormatException e){}
		catch(NullPointerException e){}
		return false;
	}
}
