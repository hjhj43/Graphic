package shapes;

import java.awt.Rectangle;


public class GSelection extends GShape{
	private static final long serialVersionUID = 1L;

	public GSelection() {
		this.shape = new Rectangle(); 
	}
	
	@Override
	public GShape clone() {
		return new GSelection();
	}
	
	public void resizePoint(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setBounds(x,y,0,0);
	}
	
	@Override
	public void movePoint(int x, int y) {  
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x-rectangle.x,y-rectangle.y);
	}
	
}
	
