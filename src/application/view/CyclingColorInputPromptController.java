package application.view;

import java.io.IOException;

import application.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CyclingColorInputPromptController {

	@FXML
	private Canvas start;

	@FXML
	private Canvas end;

	@FXML
	private ChoiceBox<String> mode;

	@FXML
	private TextField frames;

	private Color startColor;
	private int[] startColorValue;
	private Color endColor;
	private int[] endColorValue;

	private Stage stage;

	public void initialize(){
		mode.setItems(FXCollections.observableArrayList("Alternator", "Repeating Cycle", "Reversing Cylce"));
		startColor = Color.BLACK;
		endColor = Color.BLACK;
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}

	public void setArrays(int[] start, int[] end){
		startColorValue = start;
		endColorValue = end;
		startColorValue[0] = 0;
		startColorValue[1] = 0;
		startColorValue[2] = 0;
		endColorValue[0] = 0;
		endColorValue[1] = 0;
		endColorValue[2] = 0;
		fillCanvas(startColor, this.start);
		fillCanvas(endColor, this.end);
	}

	@FXML
	public void AcceptClicked(){
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
	public void CancelClicked(){
		startColorValue[3] = 0;
		stage.close();
	}

	@FXML
	public void startClicked(){
		int[] color = getColor();
		startColorValue[0] = color[0];
		startColorValue[1] = color[1];
		startColorValue[2] = color[2];
		startColor = Color.rgb(color[0], color[1], color[2]);
		fillCanvas(startColor, start);
	}

	@FXML
	public void endClicked(){
		int[] color = getColor();
		endColorValue[0] = color[0];
		endColorValue[1] = color[1];
		endColorValue[2] = color[2];
		endColor = Color.rgb(color[0], color[1], color[2]);
		fillCanvas(endColor, end);
	}

	public void fillCanvas(Color color, Canvas canvas){
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setFill(color);
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	private int[] getColor(){
		int[] color = new int[3];
		color[0] = -1;
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/SolidColorInputPrompt.fxml"));
			BorderPane colorInput = loader.load();

			Stage inputStage = new Stage();
			inputStage.setTitle("Input RGB Value");
			inputStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(colorInput);
			inputStage.setScene(scene);

			SolidColorInputPromptController input = loader.getController();
			input.setArray(color);
			input.setStage(inputStage);

			inputStage.showAndWait();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return color;
	}

	public boolean checkInput(){
		try{
			startColorValue[4] = Integer.parseInt(frames.getText());
			startColorValue[3] = mode.getSelectionModel().getSelectedIndex() + 1;
			if(startColorValue[3] != 0){
				return true;
			}
		}
		catch(NumberFormatException e){}
		catch(NullPointerException e){}
		return false;
	}

}
