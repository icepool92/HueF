package application.view;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import application.Main;
import application.model.Dot;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuBarWindowController {

	@FXML
	private MenuItem createNewCanvas;

	@FXML
	private MenuItem createNewPaletteColor;

	@FXML
	private MenuItem open;

	@FXML
	private MenuItem save;

	@FXML
	private MenuItem export;

	@FXML
	private AnchorPane menuWindow;

	private CanvasController currentCanvas;

	private ArrayList<Dot> Dots;

	private PaletteColorController currentColor;

	@FXML
	public void initialize(){
		Dots = new ArrayList<Dot>();
	}

	@FXML
	public void createNewCanvasPressed(){
		int[] heightAndWidth = new int[2];
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CanvasSizeInputBox.fxml"));
			AnchorPane size = loader.load();


			Stage inputStage = new Stage();
			inputStage.setTitle("Input Canvas Size");
			inputStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(size);
			inputStage.setScene(scene);

			CanvasSizeInputBoxController input = loader.getController();
			input.setArray(heightAndWidth);
			input.setStage(inputStage);

			inputStage.showAndWait();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		if(heightAndWidth[0] > 0 && heightAndWidth[1] > 0){
			createCanvas(heightAndWidth[0], heightAndWidth[1]);
		}
	}

	@FXML
	public void createNewPaletteColorPressed(){
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
			createPaletteColor(new Dot(Color.rgb(color[0], color[1], color[2])));
		}
	}

	@FXML
	public void savePressed(){
		FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());

        if(file != null){
            try {
                WritableImage writableImage = new WritableImage((int) currentCanvas.getCanvas().getWidth(), (int) currentCanvas.getCanvas().getHeight());
                currentCanvas.getCanvas().snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
	}

	public void createCanvas(int h, int w){
		try{
			//load this FXML
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CanvasArea.fxml"));
			AnchorPane canvas = loader.load();

			//this lets the canvas co-exist with the menu(?)
			Stage canvasStage = new Stage();
			canvasStage.setTitle("Canvas");
			canvasStage.initModality(Modality.NONE);

			CanvasController controller = loader.getController();
			controller.createCanvas(h, w);
			currentCanvas = controller;
			currentCanvas.setMenu(this);

			//make that canvas
			Scene scene = new Scene(canvas);
			canvasStage.setScene(scene);
			canvasStage.show();

			if(save.isDisable()){
				save.setDisable(false);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void createPaletteColor(Dot dot){
		try{
			//load this FXML
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/PaletteColor.fxml"));
			BorderPane canvas = loader.load();

			//this lets the canvas co-exist with the menu(?)
			Stage paletteStage = new Stage();
			paletteStage.setTitle("Canvas");
			paletteStage.initModality(Modality.NONE);

			PaletteColorController controller = loader.getController();
			controller.setDot(dot);
			controller.setMenuWindow(this);
			if(currentColor != null){
				currentColor.toggleCurrent();
			}
			currentColor = controller;

			//make that canvas
			Scene scene = new Scene(canvas);
			paletteStage.setScene(scene);
			paletteStage.show();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void setCurrentColor(PaletteColorController color){
		currentColor.toggleCurrent();
		currentColor = color;
		currentColor.toggleCurrent();
	}

	public Dot getDot(){
		return currentColor.getDot();
	}

}
