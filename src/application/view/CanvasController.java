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

	private Dot currentDot;

	private int mouseDragStartX;
	private int mouseDragEndX;
	private int mouseDragStartY;
	private int mouseDragEndY;

	//happens when the canvas starts
	@FXML
	public void initialize(){
		createCanvas();
		dots = new DotArray((int) canvas.getWidth(), (int) canvas.getHeight());
		currentDot = new SolidDot(0, 0, Color.BLUE, this);
	}

	//makes the canvas, pass in width and height //eventually
	public void createCanvas(){
		//this.width.set(width);
		//this.height.set(height);
		setCanvasSize();
		fillWhite();
		graphicsContext = canvas.getGraphicsContext2D();
		initDraw(graphicsContext);
	}

	//creates all the stuff it needs to draw
	public void initDraw(GraphicsContext gc){
		gc.setLineWidth(1.0);
	}

	//draw when the mouse is just clicked
	@FXML
	public void mousePressed(MouseEvent event){
		dots.addDot((int) event.getX(), (int) event.getY(), currentDot.copy((int) event.getX(), (int) event.getY()));
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
		
		boolean steep = Math.abs(endY - startY) > Math.abs(endX - startX); //this big algorithm didn't work but what it made instead looked real cool so i'm keeping it for possible use later
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
	        		dots.addDot(y, x, currentDot.copy(y, x));
	        }
	        else{
	        		dots.addDot(x, y, currentDot.copy(x, y));
	        }
	        error = error - deltay;
	        if (error < 0) {
	            y = y + ystep;
	            error = error + deltax;
	        }
	    }
	    mouseDragStartX = mouseDragEndX;
	    mouseDragStartY = mouseDragEndY;
		//graphicsContext.lineTo(event.getX(), event.getY());
		//graphicsContext.stroke();*/
	}

	public void drawDot(int x, int y, Color color){
		graphicsContext.setStroke(color);
		graphicsContext.strokeLine(x + 0.5, y + 0.5, x + 0.5, y + 0.5);
		//graphicsContext.strokeLine(x + 1.5, y + 1.5, x + 0.5, y + 0.5);
		//graphicsContext.stroke();

	}

	public void setCanvasSize(){

	}

	public void fillWhite(){

	}

	public void setMainApp(){}

}


















