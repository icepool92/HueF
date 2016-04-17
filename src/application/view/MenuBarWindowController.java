package application.view;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import application.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuBarWindowController {

	@FXML
	private MenuItem createNew;

	@FXML
	private MenuItem open;

	@FXML
	private MenuItem save;

	@FXML
	private MenuItem export;

	@FXML
	private AnchorPane menuWindow;

	private Canvas currentCanvas;

	@FXML
	public void initialize(){
	}

	@FXML
	public void createNewPressed(){
		int[] heightAndWidth = new int[2];
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CanvasSizeInputBox.fxml"));
			AnchorPane size = loader.load();


			Stage inputStage = new Stage();
			inputStage.setTitle("Input Canvse Size");
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
                WritableImage writableImage = new WritableImage((int) currentCanvas.getWidth(), (int) currentCanvas.getHeight());
                currentCanvas.snapshot(null, writableImage);
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
			currentCanvas = controller.getCanvas();

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

}
