package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class BrushSelectorController {

	@FXML
	private Rectangle square;

	@FXML
	private Circle circle;

	@FXML
	private Slider sizeSelector;

	private int[] mode;

	@FXML
	private TextArea sizeBox;

	public void initialize(){
		square.setFill(Color.DARKBLUE);
		circle.setFill(Color.BLUE);
		mode = new int[2];
		mode[0] = 0;
		sizeSelector.setValue(5.0);
		sizeSelector.valueProperty().addListener((observable, oldValue, newValue) ->{
			sizeBox.setText(newValue.toString().substring(0, newValue.toString().indexOf(".")));
		});
		sizeBox.textProperty().addListener((observable, oldValue, newValue) ->{
			try{
				mode[1] = Integer.parseInt(newValue);
			} catch(NumberFormatException e){}
		});
	}

	public int[] getMode(){
		return mode;
	}

	@FXML
	public void squarePressed(){
		if(mode[0] == 1){
			square.setFill(Color.DARKBLUE);
			circle.setFill(Color.BLUE);
			mode[0] = 0;
		}
	}

	@FXML
	public void circlePressed(){
		if(mode[0] == 0){
			circle.setFill(Color.DARKBLUE);
			square.setFill(Color.BLUE);
			mode[0] = 1;
		}
	}

}
