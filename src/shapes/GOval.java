package shapes;

import java.awt.Graphics2D;

import java.awt.geom.Ellipse2D;


public class GOval extends GShape {

	private static final long serialVersionUID = 1L;

	public GOval() {
		this.shape = new Ellipse2D.Double();
	}

	@Override
	public GShape clone() {
		return new GOval();
	}

	public void resizePoint(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x, y, 0, 0);
	}

	@Override
	public void movePoint(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(ellipse.getX(), ellipse.getY(), x - ellipse.getX(), y - ellipse.getY());
	}
}
