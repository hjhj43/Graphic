package transformer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

import shapes.GShape;
import shapes.GAnchors.EAnchors;

public class GResizer extends GTransformer {
	
	private double xScale, yScale;
	private double cx, cy;
	
	public GResizer(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransform(int x, int y) {
		// ���콺 point ��ġ (�⺻ ��)
		this.px = x;
		this.py = y;
		// ���� ���
		Point2D resizeAnchorPoint = this.anchors.getResizeAnchorPoint(x, y); // Anchor ������ (����� ���� Anchor �ݴ��� = ����)
		this.cx = resizeAnchorPoint.getX(); // ���� ����
		this.cy = resizeAnchorPoint.getY();
	}

	@Override
	public void keepTransform(int x, int y) {
		this.getResizeScale(x, y);
		
		this.affineTransform.translate(cx, cy);
		this.affineTransform.scale(this.xScale, this.yScale); // ��ŭ ���������� �����ش�
		this.affineTransform.translate(-cx, -cy);
		
		this.px = x;
		this.py = y;
	}

	@Override
	public void finalizeTransform(int x, int y) {
		this.shape.finalize(x, y);
	}
	
	protected void getResizeScale(int x, int y) {
		EAnchors eResizeAnchor = this.anchors.getResizeAnchor();
		double w1 = px - cx;
		double w2 = x - cx; 
		
		double h1 = py - cy;
		double h2 = y - cy;

		switch (eResizeAnchor) {
		case NW: xScale = w2/w1;  yScale= h2/h1;  break;
		case WW: xScale = w2/w1; yScale= 1;      break;
		case SW: xScale = w2/w1;  yScale= h2/h1; break;
		case SS: xScale = 1;      yScale= h2/h1; break;
		case SE: xScale = w2/w1;  yScale= h2/h1;  break;
		case EE: xScale = w2/w1;  yScale= 1;      break;
		case NE: xScale = w2/w1; yScale= h2/h1;  break;
		case NN: xScale = 1;      yScale= h2/h1;  break;
		default:  break;
		}
	}
	
}
