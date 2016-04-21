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

	private int colorMode = 0;
	private int frame = 0;
	private int cycleLength;
	private int[] startColor;
	private int[] endColor;
	private boolean cycle = true;

	private GraphicsContext graphicsContext;

	private MenuBarWindowController menuWindow;

	@FXML
	public void initialize(){
		graphicsContext = canvas.getGraphicsContext2D();
	}

	public void setFrame(IntegerProperty f){
		f.addListener((observable, newValue, oldValue) ->{
			updateColor();
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
			colorMode = 0;
			fillColor();
		}
	}

	@FXML
	public void setToCyclingColorPressed(){
		int[] start = new int[5];
		int[] end = new int[3];
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CyclingColorInputPrompt.fxml"));
			BorderPane colorInput = loader.load();

			Stage inputStage = new Stage();
			inputStage.setTitle("Create Cycling Color");
			inputStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(colorInput);
			inputStage.setScene(scene);

			CyclingColorInputPromptController input = loader.getController();
			input.setArrays(start, end);
			input.setStage(inputStage);

			inputStage.showAndWait();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		if(start[3] != 0){
			startColor = new int[]{start[0], start[1], start[2]};
			endColor = end;
			cycleLength = start[4];
			colorMode = start[3];
		}
	}

	private void updateColor(){
		if(colorMode == 1){
			frame++;
			if(frame ==  cycleLength / 2){
				dot.setColor(endColor[0], endColor[1], endColor[2]);
			}
			else if(frame == cycleLength){
				dot.setColor(startColor[0], startColor[1], startColor[2]);
				frame = 0;
			}
			fillColor();
		}
		else if(colorMode == 2){
			frame++;
			int r = (endColor[0] * frame + startColor[0] * (cycleLength - frame)) / cycleLength;
			int g = (endColor[1] * frame + startColor[1] * (cycleLength - frame)) / cycleLength;
			int b = (endColor[2] * frame + startColor[2] * (cycleLength - frame)) / cycleLength;
			dot.setColor(r, g, b);
			fillColor();
			if(frame == cycleLength){
				frame = 0;
			}
		}
		else if(colorMode == 3){
			if(cycle){
				frame++;
			}
			else{
				frame--;
			}
			int r = (endColor[0] * frame + startColor[0] * (cycleLength - frame)) / cycleLength;
			int g = (endColor[1] * frame + startColor[1] * (cycleLength - frame)) / cycleLength;
			int b = (endColor[2] * frame + startColor[2] * (cycleLength - frame)) / cycleLength;
			dot.setColor(r, g, b);
			fillColor();
			if(frame == cycleLength){
				cycle = false;
			}
			else if(frame == 0){
				cycle = true;
			}
		}
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
