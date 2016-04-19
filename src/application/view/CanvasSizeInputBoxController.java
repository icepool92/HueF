package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

import javafx.beans.property.StringProperty;

public class CanvasSizeInputBoxController {

	@FXML
	private TextField height;

	@FXML
	private TextField width;

	@FXML
	private Button confirm;

	@FXML
	private Button cancel;

	private Stage stage;
	private int[] heightAndWidth;

	@FXML
	public void initialize(){}

	@FXML
	public void confirmPressed(){
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

	public void setStage(Stage stage){
		this.stage = stage;
	}

	public void setArray(int[] arr){
		heightAndWidth = arr;
	}

	private boolean checkInput(){
		try{
			int h = Integer.parseInt(height.getText());
			int w = Integer.parseInt(width.getText());
			if(h > 0 && w > 0){
			heightAndWidth[0] = h;
			heightAndWidth[1] = w;
				return true;
			}
			return false;
		}
		catch(NumberFormatException e){
			return false;
		}
	}

}
