package shapes;

import java.awt.geom.Path2D;

public class GPath extends GShape { 

	private static final long serialVersionUID = 1L;
	
	public GPath() {
		this.shape = new Path2D.Double();	
	}
	
	@Override
	public GShape clone() {
		return new GPath();
	}
	
	public void resizePoint(int x, int y) {
		Path2D path2D = (Path2D) this.shape;	
		path2D.moveTo(x,y);
	}	
	
	@Override
	public void movePoint(int x, int y) { 		
		Path2D path2D = (Path2D) this.shape;			
		path2D.lineTo(x,y);
	}
	
}