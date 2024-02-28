package shapes;

import java.awt.geom.RoundRectangle2D;

public class GRoundRectangle extends GShape { 

	private static final long serialVersionUID = 1L;
	
	public GRoundRectangle() {
		this.shape = new RoundRectangle2D.Double();	
	}
	
	@Override
	public GShape clone() {
		return new GRoundRectangle();
	}
	
	public void resizePoint(int x, int y) {
		RoundRectangle2D roundRectangle2D = (RoundRectangle2D) this.shape;	
		roundRectangle2D.setRoundRect(x,y,0,0, 70, 70);
	}
	
	@Override
	public void movePoint(int x, int y) { 
		RoundRectangle2D roundRectangle2D = (RoundRectangle2D) this.shape;
		
		roundRectangle2D.setFrame(roundRectangle2D.getX(), roundRectangle2D.getY()
				,x-roundRectangle2D.getX(),y- roundRectangle2D.getY());
	}
	
}