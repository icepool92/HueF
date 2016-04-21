package application.view;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import application.Main;
import application.model.Dot;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
	private MenuItem saveStill;

	@FXML
	private MenuItem saveGIF;

	@FXML
	private AnchorPane menuWindow;

	private CanvasController currentCanvas;

	private PaletteColorController currentColor;

	private IntegerProperty frame;
	private int frameTracker = 0;
	private boolean paused = false;

	private int[] brushSettings;

	@FXML
	public void initialize(){
		frame = new SimpleIntegerProperty(0);
		new AnimationTimer() {
			@Override
			public void handle(long now){
				if(!paused){
					frameTracker++;
				}
				if(frameTracker % 3 == 0){
					if(frame.intValue() == 1){
					frame.set(0);
					}
					else{
					frame.set(1);
					}
				}
			}
		}.start();

		createBrushSelector();
	}

	public void createBrushSelector(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/BrushSelector.fxml"));
			BorderPane selector = loader.load();

			Stage brushStage = new Stage();
			brushStage.setTitle("Brush Settings");
			brushStage.initModality(Modality.NONE);
			Scene scene = new Scene(selector);
			brushStage.setScene(scene);

			brushSettings = ((BrushSelectorController) loader.getController()).getMode();

			brushStage.show();
		}
		catch(IOException e){
			e.printStackTrace();
		}
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
	public void saveStaticPressed(){
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

	@FXML
	public void saveGIFPressed(){
		int[] frames = {0, 0};
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/SaveGifPrompt.fxml"));
			AnchorPane frameInput = loader.load();

			Stage inputStage = new Stage();
			inputStage.setTitle("Input Animation Length");
			inputStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(frameInput);
			inputStage.setScene(scene);

			SaveGifPromptController input = loader.getController();
			input.setArray(frames);
			input.setStage(inputStage);

			inputStage.showAndWait();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		if(frames[0] != 0){
			boolean current = paused;
			paused = true;
			saveGIF(frames[0], frames[1], current);
		}
	}

	@FXML
	public static void helpPressed() {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(new URL("").toURI());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}


	private void saveGIF(int f, int tf, boolean paused){
		FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("gif files (*.gif)", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());

		BufferedImage[] images = new BufferedImage[f];
		frame.setValue(0);
		for(int i = 0; i < images.length; i++){
			images[i] = convertRenderedImage(getFrame());
			frame.setValue(0);
			frame.setValue(1);
		}
		if(file != null){
		try{
			// grab the output image type from the first image in the sequence
			BufferedImage firstImage = images[0];

			// create a new BufferedOutputStream with the last argument
			ImageOutputStream output =
					new FileImageOutputStream(file);

			// create a gif sequence with the type of the first image, 1 second
			// between frames, which loops continuously
			GifSequenceWriter writer =
					new GifSequenceWriter(output, firstImage.getType(), tf, false);

			// write out the first image to our sequence...
	    	writer.writeToSequence(firstImage);
	    	for(int i=1; i<images.length-1; i++) {
	    		BufferedImage nextImage = images[i];
	    		writer.writeToSequence(nextImage);
	    	}
	    } catch(IOException e){}
		}
		this.paused = paused;
	}

	private RenderedImage getFrame(){
        WritableImage writableImage = new WritableImage((int) currentCanvas.getCanvas().getWidth(), (int) currentCanvas.getCanvas().getHeight());
        currentCanvas.getCanvas().snapshot(null, writableImage);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        return renderedImage;
	}

	public BufferedImage convertRenderedImage(RenderedImage img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		ColorModel cm = img.getColorModel();
		int width = img.getWidth();
		int height = img.getHeight();
		WritableRaster raster = cm
				.createCompatibleWritableRaster(width, height);
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		Hashtable properties = new Hashtable();
		String[] keys = img.getPropertyNames();
		if (keys != null) {
			for (int i = 0; i < keys.length; i++) {
				properties.put(keys[i], img.getProperty(keys[i]));
			}
		}
		BufferedImage result = new BufferedImage(cm, raster,
				isAlphaPremultiplied, properties);
		img.copyData(raster);
		return result;
	}

	private void createCanvas(int h, int w){
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
			controller.createCanvas(h, w, frame);
			controller.setBrushSettings(brushSettings);
			currentCanvas = controller;
			currentCanvas.setMenu(this);

			//make that canvas
			Scene scene = new Scene(canvas);
			canvasStage.setScene(scene);
			createNewCanvas.setDisable(true);
			canvasStage.show();

			if(saveStill.isDisable()){
				saveStill.setDisable(false);
				saveGIF.setDisable(false);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void createPaletteColor(Dot dot){
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
			controller.setFrame(frame);
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
