package application.view;

import javafx.fxml.FXML;
import application.model.*;
import javafx.beans.property.IntegerProperty;
import javafx.event.EventHandler;
import javafx.stage.Stage;
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

	//happens when the canvas starts
	@FXML
	public void initialize(){}

	public void setMenu(MenuBarWindowController menu){
		this.menu = menu;
	}

	//makes the canvas, pass in width and height //eventually
	public void createCanvas(int h, int w){
		canvas.setWidth(w);
		canvas.setHeight(h);

		graphicsContext = canvas.getGraphicsContext2D();
		fillWhite();
		graphicsContext.setLineWidth(1.0);
		dots = new DotArray((int) canvas.getWidth(), (int) canvas.getHeight());
	}

	//draw when the mouse is just clicked
	@FXML
	public void mousePressed(MouseEvent event){
		dots.addDot((int) event.getX(), (int) event.getY(), menu.getDot().copy((int) event.getX(), (int) event.getY(), this));
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
	        		dots.addDot(y, x, menu.getDot().copy(y, x, this));
	        	}
	        }
	        else{
	        	if(x < canvas.getWidth() && y < canvas.getHeight() && !(x < 0) && !(y < 0)){
	        		dots.addDot(x, y, menu.getDot().copy(x, y, this));
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


















