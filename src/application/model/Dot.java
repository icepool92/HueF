package application.model;

import javafx.scene.paint.Color;

public class Dot{

	private Color color;
	private Dot parentDot;

	public Dot(Color color){
		this.color = color;
	}

	public Color getColor(){
		return color;
	}

	public Dot getParentDot(){
		return parentDot;
	}

	public void setColor(int r, int g, int b){
		color = Color.rgb(r, g, b);
	}

	public void finalize() throws Throwable{
		super.finalize();
	}

}
