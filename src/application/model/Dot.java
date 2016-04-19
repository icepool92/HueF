package application.model;

import javafx.scene.paint.Color;
import application.view.CanvasController;
import application.view.PaletteColorController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;

public class Dot{

	private int x;
	private int y;
	private Color color;
	private CanvasController canvas;
	private PaletteColorController palette;
	private IntegerProperty r;
	private IntegerProperty g;
	private IntegerProperty b;

	public Dot(int x, int y, Color color, CanvasController canvas, Dot dot){
		this.x = x;
		this.y = y;
		this.color = color;
		this.canvas = canvas;
		draw();

		r = new SimpleIntegerProperty((int) color.getRed());
		g = new SimpleIntegerProperty((int) color.getGreen());
		b = new SimpleIntegerProperty((int) color.getBlue());
		setParentDot(dot);
	}

	public void setParentDot(Dot dot){
		dot.getR().addListener((observable, oldValue, newValue) -> {
			r = dot.getR();
			update();
		});
		dot.getG().addListener((observable, oldValue, newValue) ->{
			g = dot.getG();
			update();
		});
		dot.getB().addListener((observable, oldValue, newValue) ->{
			b = dot.getB();
			update();
		});
	}

	public Dot(Color color){
		this.color = color;
		r = new SimpleIntegerProperty((int) color.getRed());
		g = new SimpleIntegerProperty((int) color.getGreen());
		b = new SimpleIntegerProperty((int) color.getBlue());
	}

	public void setPalette(PaletteColorController palette){
		this.palette = palette;
	}

	public void update(){
		color = Color.rgb(r.getValue(), g.getValue(), b.getValue());
		if(canvas != null){
			draw();
		}
		else if(palette != null){
			palette.fillColor();
		}
	}

	public IntegerProperty getR(){
		return r;
	}

	public IntegerProperty getG(){
		return g;
	}

	public IntegerProperty getB(){
		return b;
	}

	public Color getColor(){
		return color;
	}

	public void setColor(int r, int g, int b){
		this.r.set(r);
		this.g.set(g);
		this.b.set(b);
		update();
	}

	public void draw() {
		canvas.drawDot(x, y, color);
	}

	public Dot copy(int x, int y, CanvasController canvas) {
		return new Dot(x, y, color, canvas, this);
	}

}
