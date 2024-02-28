package shapes;

import java.awt.Graphics2D;
import java.awt.Polygon;

public class GPolygon extends GShape{

	private static final long serialVersionUID = 1L;

	public GPolygon() {
		this.shape = new Polygon();
	}
	
	@Override
	public GShape clone() {  // 새로운 것을 만드는것이 아니라, 자신이 가진 모든 것을 전달
		return new GPolygon();
	}

	public void resizePoint(int x, int y) {
		this.addPoint(x, y); // 원점
		this.addPoint(x, y); // 첫번째 점
	}

	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x,y);
	}

	@Override
	public void movePoint(int x, int y) { // 좌표만 계산
		// 0과 첫번째 점은 이미 가지고 있음
		// 마지막 인덱스 : 갯수 - 1
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
