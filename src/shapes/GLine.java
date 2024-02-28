package shapes;

import java.awt.geom.Line2D;

public class GLine extends GShape { 

	private static final long serialVersionUID = 1L;
	
	public GLine() {
		this.shape = new Line2D.Double();	
	}
	
	@Override
	public GShape clone() {
		return new GLine();
	}
	
	public void resizePoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x,y,x,y);
	}
	
	@Override
	public void movePoint(int x, int y) { 
		Line2D line = (Line2D) this.shape;
		line.setLine(line.getX1(),line.getY1(),x,y);
	}
	
}