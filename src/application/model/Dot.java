package application.model;

import javafx.scene.paint.Color;
import application.view.CanvasController;
import javafx.scene.canvas.Canvas;

public class Dot{

	int x;
	int y;
	Color color;
	CanvasController canvas;

	public Dot(int x, int y, Color color, CanvasController canvas){
		this.x = x;
		this.y = y;
		this.color = color;
		this.canvas = canvas;
		draw();
	}


	public void draw() {
		canvas.drawDot(x, y, color);
	}

	public Dot copy(int x, int y) {
		return new Dot(x, y, color, canvas);
	}

}
