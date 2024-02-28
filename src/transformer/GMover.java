package transformer;

import shapes.GShape;

public class GMover extends GTransformer {
	public GMover(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransform(int x, int y) {
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransform(int x, int y) {
		this.affineTransform.translate(x - this.px, y - this.py);
		this.px = x;
		this.py = y;
	}

	@Override
	public void finalizeTransform(int x, int y) {
//		this.shape.finalize(x, y);
	}
}
