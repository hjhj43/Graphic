package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.Serializable;

import shapes.GAnchors.EAnchors;

import java.awt.geom.AffineTransform;

abstract public class GShape implements Serializable, Cloneable { 

	private static final long serialVersionUID = 1L;

	private boolean bSelected;
	private Color shapeColor;
	private float size;
	private Color shapefillColor = null;

	protected Shape shape;
	private AffineTransform affineTransform;
	private GAnchors anchors;	
	
	public boolean isSelected() {
		return this.bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	public EAnchors getSelectedAnchor() {return this.anchors.getSelecetedAnchor();}
	
	public Color getShapeColor() {
		return shapeColor;
	}
	public void setShapeColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public Color getShapefillColor() {
		return shapefillColor;
	}
	public void setShapefillColor(Color shapefillColor) {
		this.shapefillColor = shapefillColor;
	}
	public int getCenterX() {
		return (int) this.shape.getBounds2D().getCenterX();
	}
	public int getCenterY() {
		return (int) this.shape.getBounds2D().getCenterY();
	}
	
	public AffineTransform getAffineTransform() {
		return affineTransform;
	}
	public GAnchors getAnchors() {
		return anchors;
	}
	
	public GShape() {
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
		
		this.anchors = new GAnchors();
		this.bSelected = false;
	}
	public abstract GShape clone();
	public void initailize() {};
	
	public GShape deepClone() throws CloneNotSupportedException {      
		GShape copy = (GShape) super.clone();
		this.affineTransform = (AffineTransform) this.affineTransform.clone();

		return copy;
    }

	public boolean contains(int x, int y) {	
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if(isSelected()) {
			if( this.anchors.contains(x, y)) {
				return true;
			}
		}	
		
	    if(transformedShape.contains(x, y)) {
	    	this.anchors.setSelectedAnchor(EAnchors.MM);
	    	return true;
		}    
		return false;
	}	
	
	public EAnchors onShape(int x, int y) {
		return null;
	}

	public void draw(Graphics2D graphics2D) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		graphics2D.setColor(this.getShapeColor());
		graphics2D.setStroke(new BasicStroke((float) this.getSize()));
			
		if(this.getShapefillColor() != null) {
			graphics2D.setColor(this.getShapefillColor());
			graphics2D.fill(transformedShape);
		}else {
			graphics2D.draw(transformedShape);	
		}
		
		if(isSelected()) {
			graphics2D.setColor(Color.black);
			this.anchors.setPosition(graphics2D, transformedShape.getBounds());
		}  
	}

	public abstract void resizePoint(int x, int y);
	public abstract void movePoint(int x, int y);
	public void addPoint(int x, int y) {}
	
    public void finalize(int x, int y) {   	
    	this.shape = this.affineTransform.createTransformedShape(this.shape); 	
    	this.affineTransform.setToIdentity();
	}   

	
}
