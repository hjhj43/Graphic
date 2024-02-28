package transformer;

import shapes.GShape;

public class GDrawer extends GTransformer {
	public GDrawer(GShape shape) {
		super(shape);
	}
	@Override
	public void initTransform(int x, int y) {
		this.shape.resizePoint(x, y);
	}
	@Override
	public void keepTransform(int x, int y) {
		this.shape.movePoint(x, y);
	}
	@Override
	public void finalizeTransform(int x, int y) {
		this.shape.finalize(x, y);
	}
}