package transformer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import shapes.GShape;

public class GRotator extends GTransformer {

	private double cx, cy;

	public GRotator(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransform(int x, int y) {
		this.cx = this.shape.getCenterX();
		this.cy = this.shape.getCenterY();
		
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransform(int x, int y) {
		double startAngle = Math.atan2(px-cx, py-cy);
		double endAngle = Math.atan2(x-cx, y-cy);
		double angle = endAngle - startAngle;
		
		this.affineTransform.rotate(angle);
		
		this.px = x;
		this.py = y;
	}

	@Override
	public void finalizeTransform(int x, int y) {
		this.shape.finalize(x, y);
	}
}
