package transformer;

import java.awt.geom.AffineTransform;

import shapes.GAnchors;
import shapes.GShape;

public abstract class GTransformer { // mover, resizer, rotater, Drawer 
	protected GShape shape;
	
	protected AffineTransform affineTransform;
	protected GAnchors anchors;
	
	protected int px, py;
	
	public GTransformer(GShape shape) {
		this.shape = shape;
		this.affineTransform = this.shape.getAffineTransform();
		this.anchors = this.shape.getAnchors();
	}
	
	public abstract void initTransform(int x, int y);
	public abstract void keepTransform(int x, int y);
	public abstract void finalizeTransform(int x, int y);

}
