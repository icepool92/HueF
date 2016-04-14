package application.model;

import javafx.scene.paint.Color;
import application.view.CanvasController;
import javafx.scene.canvas.Canvas;

public class SolidDot extends Dot{

	int x;
	int y;
	Color color;
	CanvasController canvas;

	public SolidDot(int x, int y, Color color, CanvasController canvas){
		this.x = x;
		this.y = y;
		this.color = color;
		this.canvas = canvas;
		draw();
	}

	@Override
	public void draw() {
		canvas.drawDot(x, y, color);
	}

	@Override
	public Dot copy(int x, int y) {
		return new SolidDot(x, y, color, canvas);
	}

}
