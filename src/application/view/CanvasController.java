package application.view;

import javafx.fxml.FXML;
import application.model.*;
import javafx.beans.property.IntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class CanvasController {

	@FXML
	private IntegerProperty height;

	@FXML
	private IntegerProperty width;


	@FXML
	private AnchorPane canvasStage;

	@FXML
	private Canvas canvas;

	private GraphicsContext graphicsContext;

	private DotArray dots;

	private MenuBarWindowController menu;

	private int mouseDragStartX;
	private int mouseDragEndX;
	private int mouseDragStartY;
	private int mouseDragEndY;

	private int[] brushSettings;

	//happens when the canvas starts
	@FXML
	public void initialize(){}

	public void setMenu(MenuBarWindowController menu){
		this.menu = menu;
	}

	public void setBrushSettings(int[] brushSettings){
		this.brushSettings = brushSettings;
	}

	//makes the canvas, pass in width and height //eventually
	public void createCanvas(int h, int w, IntegerProperty f){
		canvas.setWidth(w);
		canvas.setHeight(h);

		graphicsContext = canvas.getGraphicsContext2D();
		fillWhite();
		graphicsContext.setLineWidth(1.0);
		dots = new DotArray((int) canvas.getWidth(), (int) canvas.getHeight(), this, f);
	}

	//draw when the mouse is just clicked
	@FXML
	public void mousePressed(MouseEvent event){
		draw((int) event.getX(), (int) event.getY());
		mouseDragStartX = (int) event.getX();
		mouseDragStartY = (int) event.getY();
	}

	//draw when the mouse is dragged
	@FXML
	public void mouseDragged(MouseEvent event){
		mouseDragEndX = (int) event.getX();
		mouseDragEndY = (int) event.getY();

		int startX = mouseDragStartX;
		int endX = mouseDragEndX;
		int startY = mouseDragStartY;
		int endY = mouseDragEndY;

		boolean steep = Math.abs(endY - startY) > Math.abs(endX - startX);
	    if (steep) {
	        int t;
	        // swap(x0, y0);
	        t = startX;
	        startX = startY;
	        startY = t;
	        // swap(x1, y1);
	        t = endX;
	        endX = endY;
	        endY = t;
	    }
	    if (startX > endX) {
	        int t;
	        // swap(x0, x1);
	        t = startX;
	        startX = endX;
	        endX = t;

	        // swap(y0, y1);
	        t = startY;
	        startY = endY;
	        endY = t;
	    }
	    int deltax = endX - startX;
	    int deltay = Math.abs(endY - startY);
	    int error = deltax / 2;
	    int ystep;
	    int y = startY;
	    if (startY < endY)
	        ystep = 1;
	    else
	        ystep = -1;

	    for (int x = startX; x < endX; x++) {
	        if (steep){
	        	if(y < canvas.getWidth() && x < canvas.getHeight() && !(x < 0) && !(y < 0)){
	        		draw(y, x);
	        	}
	        }
	        else{
	        	if(x < canvas.getWidth() && y < canvas.getHeight() && !(x < 0) && !(y < 0)){
	        		draw(x, y);
	        	}
	        }
	        error = error - deltay;
	        if (error < 0) {
	            y = y + ystep;
	            error = error + deltax;
	        }
	    }
	    mouseDragStartX = mouseDragEndX;
	    mouseDragStartY = mouseDragEndY;
	}

	public void draw(int x, int y){
		int b = brushSettings[1];
		if(brushSettings[0] == 0){
			for(int i = x - b; i <= x + b; i++){
				for(int j = y - b; j <= y + b; j++){
					addDot(i, j);
				}
			}
		}
		else if(brushSettings[0] == 1){
			 circle(x, y, b);
		}
	}

	public void circle(int y0, int x0, int b){
		int x, y; // The pixel plotting coordinates
		int d; // The decision variable, the main focus of this code

		for(int R = 1; R<= b; R++) {
			// Midpoint algorithm with incremental update of decision variable d
			x = 0;
			y = R;
			d = 5-(R<<2);


			while(x<=y) { // Keep going until we cross the line x=y
				addDot(y0+y,x0+x); // Mirror (x,y) to all eight octants
				addDot(y0+y, x0-x);
				addDot(y0-y, x0+x);
				addDot(y0-y, x0-x);
				addDot(y0+x, x0+y);
				addDot(y0+x, x0-y);
				addDot(y0-x, x0+y);
				addDot(y0-x, x0-y);

				x = x + 1; // Always take a step to the right
				if(d>0) {
						d = d + (x<<3) - (y<<3) + 12; // Update d
						y = y-1; // Take a step down when needed
					}
					else {
						d = d + (x<<3) + 4; // Update d
					}
		    	} // end while
			}
	}

	public void addDot(int x, int y){
		if(x > 0 && x < canvas.getWidth() && y > 0 && y < canvas.getHeight()){
			if(dots.getDot(x, y) != menu.getDot()){
				dots.addDot(x, y, menu.getDot());
			}
		}
	}

	//called by a dot to draw on the canvas
	public void drawDot(int x, int y, Color color){
		graphicsContext.setStroke(color);
		graphicsContext.strokeLine(x + 0.5, y + 0.5, x + 0.5, y + 0.5);

	}

	public Canvas getCanvas(){
		return canvas;
	}

	//set the background of the canvas to white, eventually
	public void fillWhite(){
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

}


















