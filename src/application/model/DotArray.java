package application.model;

public class DotArray {

	private Dot[][] dotContainer;

	public DotArray(int x, int y){
		dotContainer = new Dot[x][y];
	}

	public void addDot(int x, int y, Dot dot){
		dotContainer[x][y] = dot;
	}
}
