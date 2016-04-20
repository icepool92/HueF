package application.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import application.Main;
import application.model.*;
import javafx.beans.property.IntegerProperty;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class PaletteColorController {

	@FXML
	private Canvas canvas;

	@FXML
	private Menu editMenu;

	@FXML
	private Menu displayCurrent;

	@FXML
	private MenuItem setToSolidColor;

	@FXML
	private MenuItem setToCyclingColor;

	private Dot dot;

	private boolean cycling;

	private GraphicsContext graphicsContext;

	private MenuBarWindowController menuWindow;

	@FXML
	public void initialize(){
		graphicsContext = canvas.getGraphicsContext2D();
	}

	public void setFrame(IntegerProperty f){
		f.addListener((observable, newValue, oldValue) ->{
			if(cycling){
				updateColor();
				fillColor();
			}
		});
	}

	public void setMenuWindow(MenuBarWindowController menuWindow){
		this.menuWindow = menuWindow;
	}

	public void setDot(Dot dot){
		this.dot = dot;
		fillColor();
	}

	@FXML
	public void mousePressed(){
		menuWindow.setCurrentColor(this);
	}

	@FXML
	public void setToSolidColorPressed(){
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
		if(color[0] != -1){
			dot.setColor(color[0], color[1], color[2]);
			fillColor();
		}
	}

	private void updateColor(){

	}

	public Dot getDot(){
		return dot;
	}

	public void fillColor(){
		graphicsContext.setFill(dot.getColor());
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public void toggleCurrent(){
		if(displayCurrent.getText().equals("")){
			displayCurrent.setText("- Current -");
		}
		else{
			displayCurrent.setText("");
		}
	}

}
