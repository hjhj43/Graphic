package shapes;

import java.awt.Graphics2D;
import java.awt.Polygon;

public class GPolygon extends GShape{

	private static final long serialVersionUID = 1L;

	public GPolygon() {
		this.shape = new Polygon();
	}
	
	@Override
	public GShape clone() {  // ���ο� ���� ����°��� �ƴ϶�, �ڽ��� ���� ��� ���� ����
		return new GPolygon();
	}

	public void resizePoint(int x, int y) {
		this.addPoint(x, y); // ����
		this.addPoint(x, y); // ù��° ��
	}

	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x,y);
	}

	@Override
	public void movePoint(int x, int y) { // ��ǥ�� ���
		// 0�� ù��° ���� �̹� ������ ����
		// ������ �ε��� : ���� - 1
		Polygon polygon = (Polygon) this.shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}

	/////////////////////////////
	public void setCopyShape() {
		Polygon polygon = (Polygon) this.shape;
	}
	public void editReDraw(int x, int y, Graphics2D graphics) {
		// graphics.drawPolygon(x,y);
	}
}