package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class GAnchors implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	
	public enum EAnchors {
		NW,
		WW,
		SW,
		SS,
		SE,
		EE,
		NE,
		NN,
		RR,
		MM
	}
	private Ellipse2D anchors[];
	private EAnchors eSelecetedAnchor;
	private EAnchors eResizeAnchor;
	
	public EAnchors getSelecetedAnchor() {
		return this.eSelecetedAnchor;
	}
	public void setSelectedAnchor(EAnchors eSelecetedAnchor) {
		this.eSelecetedAnchor=eSelecetedAnchor;
	}
	public EAnchors getResizeAnchor() {
		return this.eResizeAnchor;
	}

	public GAnchors() {
		this.anchors = new Ellipse2D[EAnchors.values().length-1];		
		for(int i=0; i<EAnchors.values().length-1; i++ ) {
			this.anchors[i] = new Ellipse2D.Double();
		}
	}
	
	public boolean contains(int x, int y) {
		for(int i=0; i<EAnchors.values().length-1; i++ ) {
			if( this.anchors[i].contains(x, y) ) {
				this.eSelecetedAnchor = EAnchors.values()[i];
				return true;
			}
		} 
		return false;
	}
	
	public EAnchors onShape(int x, int y) {
//		for (int i=0; i<anchors.length; i++) {
//			if(anchors[i].contains(x, y)) {
//				return EAnchors.values()[i];
//			}
//		}
		return null;
	}
	
	public void setPosition(Graphics2D graphics2D, Rectangle boungindRectangle) {
		
		graphics2D.setStroke(new BasicStroke((float) 1));
		
		for(int i=0; i<EAnchors.values().length-1; i++ ) {
			EAnchors eAnchor = EAnchors.values()[i];
			int x= boungindRectangle.x;
			int y= boungindRectangle.y;
			int w = boungindRectangle.width;
			int h = boungindRectangle.height;
			
			switch (eAnchor) {
			case NW:                              break;
			case WW:                y = y + h/2;  break;
			case SW:                y = y + h;    break;
			case SS: x = x + w/2;   y = y + h;    break;
			case SE: x = x + w;     y = y + h;    break;
			case EE: x = x + w;     y = y + h/2;  break;
			case NE: x = x + w;                   break;
			case NN: x = x + w/2;                 break;
			case RR: x = x + w/2;   y = y - h/2;  break;
			default:                               break;
			}
			x = x - WIDTH/2;
			y = y - HEIGHT/2;
			
			this.anchors[eAnchor.ordinal()].setFrame(x,y, WIDTH,HEIGHT);
			Color foreground = graphics2D.getColor();
			
			graphics2D.setColor(Color.BLACK);  
			graphics2D.fill(this.anchors[eAnchor.ordinal()]);
			
			graphics2D.setColor(foreground);
			graphics2D.draw(this.anchors[eAnchor.ordinal()]);  
		}
	}
	public Point2D getResizeAnchorPoint(int x, int y) {
		this.eResizeAnchor = null;
		switch (this.eSelecetedAnchor) {
		case NW: eResizeAnchor = EAnchors.SE; break;
		case WW: eResizeAnchor = EAnchors.EE; break;
		case SW: eResizeAnchor = EAnchors.NE; break;
		case SS: eResizeAnchor = EAnchors.NN; break;
		case SE: eResizeAnchor = EAnchors.NW; break;
		case EE: eResizeAnchor = EAnchors.WW; break;
		case NE: eResizeAnchor = EAnchors.SW; break;
		case NN: eResizeAnchor = EAnchors.SS; break;
		default: break;
		}
		// resize 하기위한 좌표를 구한 것임 
		double cx = this.anchors[eResizeAnchor.ordinal()].getCenterX();
		double cy = this.anchors[eResizeAnchor.ordinal()].getCenterY();
		return new Point2D.Double(cx, cy);
	}
	
	private int width, height;
	public int getWidth() {return width;}
	public void setWidth(int width) {this.width = width;}
	
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height;}
	
	public Point2D getRotateAnchorPoint() {
		double x1 = this.anchors[EAnchors.NW.ordinal()].getCenterX();
		double y1 = this.anchors[EAnchors.NW.ordinal()].getCenterY();
		
		double x2 = this.anchors[EAnchors.NE.ordinal()].getCenterX();
		double y2 = this.anchors[EAnchors.NE.ordinal()].getCenterY();
		
		double x3 = this.anchors[EAnchors.SW.ordinal()].getCenterX();
		double y3 = this.anchors[EAnchors.SW.ordinal()].getCenterY();
		
		this.width = (int) (x2 - x1);
		this.height = (int) (y3 - y1);
		
		return new Point2D.Double(x1, y1);
	}
	public void setRotateAnchorPoint() {
	}
}
