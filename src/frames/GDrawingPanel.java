package frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import main.GConstants.EShape;
import main.GConstants.EUserAction;
import shapes.AddImg;
import shapes.GAnchors.EAnchors;
import shapes.GSelection;
import shapes.GShape;
import transformer.GDrawer;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotator;
import transformer.GTransformer;


public class GDrawingPanel extends JPanel { 
	
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components 
	private Vector<GShape> shapes;
	private BufferedImage bufferedImage;  
	private Graphics2D graphics2DBufferedImage; 
	private boolean bUpdated;
	
	private Vector<GShape> deleteShape;
	private Vector<GShape> closedShape;
	
	// association attribute 
	private EShape selectedTool; 
	private GShape selectedShape;
	private GShape currentShape;
	private GTransformer transformer ;
	
	private enum EDrawingState {
		eIdle, e2PointTransformation, eNPointTransformation, 
	}
	EDrawingState eDrawingState;
	
	private GToolBar toolBar;
	public void setToolbar(GToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public GDrawingPanel() { 
		this.setBackground(Color.WHITE); 
		this.eDrawingState = EDrawingState.eIdle;
		this.bUpdated = false; 
		
		this.shapes = new Vector<GShape>();
		
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
		
		this.deleteShape = new Vector<GShape>();
		this.closedShape = new Vector<GShape>();
		this.imges = new Vector<Image>();
	}
	
	public void initialize() {
		if(this.getPanelWidth() != 0 && this.getPanelHeight() != 0 ){
			this.bufferedImage = (BufferedImage) this.createImage(this.getPanelWidth(), this.getPanelHeight());
		}else {
			this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());
		}
		this.graphics2DBufferedImage = (Graphics2D) this.bufferedImage.getGraphics();
	}
		
	public boolean isUpdated() {
		return this.bUpdated;
	}
	public void setUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;
	}
	
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<GShape>) shapes;
		this.repaint();
	}
	public Object getShapes() {
		return this.shapes;
	}
	
	public void setSelectedTool(EShape selectedTool) {
		this.selectedTool = selectedTool; 
	}
	
	// Overriding
	public void paint(Graphics graphics) {
		super.paint(graphics); 
		this.graphics2DBufferedImage.clearRect(0,0,this.getWidth(),this.getHeight());			
		for (GShape shape : this.shapes) {
			shape.draw(this.graphics2DBufferedImage);
		}
		for(Image img : this.imges) {
			this.graphics2DBufferedImage.drawImage(img, 0, 0, this);
			this.getGraphics().drawImage(img, 0, 0, this);
		}
		graphics.drawImage(this.bufferedImage,0,0,this);
	}

	private void initTransforming(int x, int y) {
		if (selectedTool == EShape.eSelect) {
			currentShape = onShape(x, y);
			if (currentShape != null) {
				EAnchors eAnchor = currentShape.getSelectedAnchor();
				if (eAnchor == EAnchors.MM) {
					this.transformer = new GMover(this.currentShape);
				} else if (eAnchor == EAnchors.RR) {
					this.transformer = new GRotator(this.currentShape);
				} else {
					this.transformer = new GResizer(this.currentShape);
				}
			} else {
				this.currentShape = this.selectedTool.newShape();

				if (this.currentShape != null) {
					this.currentShape.setShapeColor(this.getShapeColor());
					this.graphics2DBufferedImage.setColor(this.currentShape.getShapeColor());

					this.currentShape.setSize(this.getShapeSize());
					this.graphics2DBufferedImage.setStroke(new BasicStroke((float) this.currentShape.getSize()));
				}
				this.transformer = new GDrawer(this.currentShape);
			}
		} else {
			this.currentShape = this.selectedTool.newShape();
			if (this.currentShape != null) {
				this.currentShape.setShapeColor(this.getShapeColor());
				this.graphics2DBufferedImage.setColor(this.currentShape.getShapeColor());

				this.currentShape.setSize(this.getShapeSize());
				this.graphics2DBufferedImage.setStroke(new BasicStroke((float) this.currentShape.getSize()));
			}
			this.transformer = new GDrawer(this.currentShape);
		}
		this.setUpdated(true);
		this.transformer.initTransform(x, y);
		this.graphics2DBufferedImage.setXORMode(this.getBackground());
	}

	private void keepTransforming(int x, int y) { 
		// erase
		this.currentShape.draw(this.graphics2DBufferedImage);
		// draw
		this.transformer.keepTransform(x, y);
		this.currentShape.draw(this.graphics2DBufferedImage);
		this.getGraphics().drawImage(this.bufferedImage, 0, 0, this);
	}

	private void continueTransforming(int x, int y) {
		this.currentShape.addPoint(x, y);
	}
	
	private void finishTransforming(int x, int y) {
		this.graphics2DBufferedImage.setPaintMode();
		this.transformer.finalizeTransform(x, y);
		
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		if(!(this.currentShape instanceof GSelection)) {
			this.shapes.add(this.currentShape);
			this.selectedShape = this.currentShape;
			this.selectedShape.setSelected(true);
		}	
		this.repaint();
	}
	
	private GShape onShape(int x, int y) { 
		for (GShape shape : this.shapes) {
			if (shape.contains(x, y)) {
				return shape; 
			}
		}
		return null;
	}
	
	private void changeSelection(int x, int y) {
		// erase previous selection 
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		this.repaint();	
		
		// draw anchors
		this.selectedShape = this.onShape(x, y);
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(true);
		}
		if(this.getShapefillColor() != null) {
			this.selectedShape.setShapefillColor(this.getShapefillColor());
			this.setShapefillColor(null);
		}		
	}
	
	private void changeCursors(int x, int y) {	
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		if(this.selectedTool == EShape.eSelect) {
			cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			
			this.currentShape = onShape(x, y);
			if (this.currentShape != null) {
				if(this.currentShape.isSelected()) {
					cursor = new Cursor(Cursor.MOVE_CURSOR);
					EAnchors eAnchor = this.currentShape.getSelectedAnchor(); 
					switch(eAnchor) {
					case RR:cursor = new Cursor(Cursor.HAND_CURSOR);    break;
					
					case NW: cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
					case WW: cursor = new Cursor(Cursor.W_RESIZE_CURSOR);  break;
					case SW: cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
					case SS: cursor = new Cursor(Cursor.S_RESIZE_CURSOR);  break;
					case SE: cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
					case EE: cursor = new Cursor(Cursor.E_RESIZE_CURSOR);  break;
					case NE: cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
					case NN: cursor = new Cursor(Cursor.N_RESIZE_CURSOR);  break;
					
					default: break;
					}
				}
			}
		}

		this.setCursor(cursor);
	}


	// attribute
	private int panelWidth;
	private int panelHeight;
	private Color shapefillColor;
	private Color shapeColor = Color.black;
	private float shapeSize = 1;
	private GShape copyShape;
	private Vector<Image> imges;
	
	// getters & setters
	public int getPanelWidth() {return panelWidth;}
	public void setPanelWidth(int panelWidth) {this.panelWidth = panelWidth;}

	public int getPanelHeight() {return panelHeight;}
	public void setPanelHeight(int panelHeight) {this.panelHeight = panelHeight;}
	
	public Color getShapeColor() {return shapeColor;}
	public void setShapeColor(Color shapeColor) {this.shapeColor = shapeColor;}
	
	public Color getShapefillColor() {return shapefillColor;}
		
	public float getShapeSize() {return shapeSize;}
	public void setShapeSize(float shapeSize) {this.shapeSize = shapeSize;}

	public Vector<Image> getImges() {return imges;}
	public void setImges(Vector<Image> imges) {this.imges = imges;}
	
	// method
	public void changePanelSize(int number) {
		this.setPanelHeight(this.getHeight() / 50 * number);
		this.setPanelWidth(this.getWidth() / 50 * number);
		initialize();
	}
	
	public void setShapefillColor(Color shapefillColor) {
		this.shapefillColor = shapefillColor;
	}

	public void changeShapeDraw(Color selectedColor) {
		this.setShapeColor(selectedColor);
	}
	
	public void changeShapeLineSize(float f) {
		this.setShapeSize(f);		
	}

	public void saveShapes() {
		for (GShape shape : this.shapes) {
			this.closedShape.add(shape);
		}
	}

	public void closeFile() {
		this.closedShape = new Vector<GShape>();
		this.shapes.clear();
		for (GShape shape : this.closedShape) {
			this.shapes.add(shape);
		}
		this.repaint();
	}

	public void undoShape() {
		if (this.shapes.size() - 1 > -1) {
			this.deleteShape.add(this.shapes.get(this.shapes.size() - 1));
			this.shapes.remove(this.shapes.size() - 1);
			this.repaint();
		}
	}

	public void redoShape() {
		if (this.deleteShape.size() - 1 > -1) {
			this.shapes.add(this.deleteShape.get(this.deleteShape.size() - 1));
			this.deleteShape.remove(this.deleteShape.size() - 1);
			this.repaint();
		}
	}

	public void clearShapes() {
		this.deleteShape = new Vector<GShape>();
		for (GShape shape : this.shapes) {
			this.deleteShape.add(shape);
		}
		this.shapes.clear();
		this.repaint();
	}

	public void delete() {
		for (GShape shape : this.shapes) {
			if(shape.isSelected() == true) {
				this.deleteShape.add(shape);
				this.shapes.remove(shape);
				break;
			}
		}
		this.repaint();
	}
	
//	public void deletedShape(int x, int y) {
//		int index = 0;
//		for (GShape shape : this.shapes) {
//			if (shape.contains(x, y)) {
//				break;
//			}
//			index++;
//		}
//		int result = JOptionPane.showConfirmDialog(null, "도형을 지우시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
//		if (result == JOptionPane.YES_OPTION) {
//			this.deleteShape.add(this.shapes.get(index));
//			this.shapes.remove(index);
//			this.repaint();
//		} else {
//			JOptionPane.showMessageDialog(null, "도형을 지우기에 실패하였습니다.");
//		}
//	}

	public void paste() {
		if(copyShape != null) {
			this.setUpdated(true);
			this.shapes.add(this.copyShape);
			
			this.selectedShape = this.copyShape;		
			this.selectedShape.setSelected(true);	
		}
		copyShape = null;
		this.repaint();
	}
	
	public void copy() {
		for (GShape shape : this.shapes) {
			if (shape.isSelected() == true) {
				try {	
					this.copyShape = shape.deepClone();				
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}		
				break;
			}
		}
	}
	
	public void groupAll() {
		for (GShape shape : this.shapes) {
			shape.setSelected(true);
			shape.draw(this.graphics2DBufferedImage);
		}
		this.repaint();
	}
	
	public void unGroup() {
		for (GShape shape : this.shapes) {
			if(shape.isSelected() == true) {
				shape.setSelected(false);
				shape.draw(this.graphics2DBufferedImage);
			}		
		}
		this.repaint();
	}

	public void addImg() {
		AddImg addImg = new AddImg();
		BufferedImage img = addImg.addImg();
		
		this.graphics2DBufferedImage.drawImage(img, 0, 0, this);
		this.getGraphics().drawImage(img, 0, 0, this);
		this.imges.add(img);
		this.setUpdated(true);
	}
	
	private class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					this.lButtonClicked(e);			
				} else if (e.getClickCount() == 2) {
					this.lButtonDoubleClicked(e);
				}
			}	
//			if(e.getButton() == MouseEvent.BUTTON3) {
//				deletedShape(e.getX(), e.getY());
//			}
		}

		private void lButtonClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				changeSelection(e.getX(), e.getY()); 
				if (selectedTool.getETransformationStyle() == EUserAction.eNpoint) {
					initTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointTransformation;
				}

			} else if (eDrawingState == EDrawingState.eNPointTransformation) {
				continueTransforming(e.getX(), e.getY());
			}
		}

		private void lButtonDoubleClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointTransformation) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointTransformation) {
				keepTransforming(e.getX(), e.getY()); 
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursors(e.getX(), e.getY()); 
			}
		}

		@Override
		public void mousePressed(MouseEvent e) { 
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool.getETransformationStyle() == EUserAction.e2point) {
					initTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointTransformation;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointTransformation) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointTransformation) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
		}
	}



}
