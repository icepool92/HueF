package application.model;

import application.view.CanvasController;
import javafx.beans.property.IntegerProperty;

public class DotArray {

	private Dot[][] dotContainer;

	private CanvasController canvas;

	public DotArray(int x, int y, CanvasController canvas, IntegerProperty f){
		dotContainer = new Dot[x][y];
		this.canvas = canvas;
		f.addListener((observable, oldValue, newValue) ->{
			update();
		});
	}

	public void addDot(int x, int y, Dot dot){
		if(dotContainer[x][y] != null){

		}
		dotContainer[x][y] = dot;
	}

	public void update(){
		for(int i = 0; i < dotContainer.length; i++){
			for(int j = 0; j < dotContainer[0].length; j++){
				if(dotContainer[i][j] != null){
					canvas.drawDot(i, j, dotContainer[i][j].getColor());
				}
			}
		}
	}

	public Dot getDot(int x, int y){
		return dotContainer[x][y];
	}
}
