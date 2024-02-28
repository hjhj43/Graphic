package main;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

import shapes.GLine;
import shapes.GOval;
import shapes.GPath;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GSelection;
import shapes.GShape;
import shapes.GRoundRectangle;

public class GConstants {

	public enum EUserAction{
		e2point, 
		eNpoint
	}
	
	public enum EShape{
		eSelect("����",new GSelection(),"Selection Tool",new ImageIcon("./Image/rectangle.png"),EUserAction.e2point),  
		eRectanble("�׸�",new GRectangle(),"�׸�",new ImageIcon("./Image/rectangle.png"),EUserAction.e2point),    
		eRoundRectangle("�����簢��",new GRoundRectangle(),"�����簢��",new ImageIcon("./Image/roundRectangle.png"),EUserAction.e2point),
		eOval("���׶��" ,new GOval(),"���׶��",new ImageIcon("./Image/oval.png"),EUserAction.e2point),  
		eLine("��",new GLine(),"��",new ImageIcon("./Image/line.png"),EUserAction.e2point),  
		ePolygon("�ٰ���",new GPolygon(),"�ٰ���",new ImageIcon("./Image/polygon.png"),EUserAction.eNpoint),
		ePath("��",new GPath(),"��",new ImageIcon("./Image/pen.png"),EUserAction.e2point);

		private String label;
		private GShape gShape;
		private String toolTip;
		private ImageIcon img;
		private EUserAction eUserAction;

		private EShape(String lable, GShape gShape,String toolTip,ImageIcon img,EUserAction eUserAction) {
			this.label = lable;
			this.gShape = gShape;		
			this.toolTip = toolTip;
			this.img = img;
			this.eUserAction = eUserAction;
		}
		
		public GShape newShape() {
			return this.gShape.clone();  
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public String getToolTip() {
			return this.toolTip;
		}
		
		public ImageIcon getImg() {
			Image image = this.img.getImage();  //ImageIcon�� Image�� ��ȯ 
			Image newImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ImageIcon newImageIcon = new ImageIcon(newImage); 
			return newImageIcon;			
		}
		
		public EUserAction getETransformationStyle() {
			return this.eUserAction;
		}
	}
	
	public enum EFileMenu{
		eNew("���θ����"), 
		eOpen("����"),
		eSave("����"), 
		eSaveAs("�ٸ��̸����� �����ϱ�"),
		eQuit("����"),
		eImg("�̹��� �߰�");
		
		private String getLabel;

		private EFileMenu(String lable) {
			this.getLabel = lable;
		}
		
		public String getLabel() {
			return this.getLabel;
		}

	}
	
	public enum EEditMenu{
		eUndo("�ڷ� ����"), 
		eRedo("�ٽ� ����"),
		eDelete("��ü ����"),
		eDeleteOne("�����ϱ�"),
		eCopy("�����ϱ�"),		
		 ePaste("�ٿ��ֱ�"),
		 eGroup("��ü �׷칭��"),
		 eUnGroup("�׷�����ϱ�");
		
		private String getLabel;

		private EEditMenu(String lable) {
			this.getLabel = lable;
		}
		
		public String getLabel() {
			return this.getLabel;
		}
	}
	
	public enum EColorMenu{
		Red("RED",Color.RED,new ImageIcon("./Image/red.png")),
		ORANGE("ORANGE", Color.ORANGE,new ImageIcon("./Image/orange.png")), 
		YELLOW("YELLOW", Color.YELLOW,new ImageIcon("./Image/yellow.png")), 
		GREEN("GREEN", Color.GREEN,new ImageIcon("./Image/green.png")), 
		Blue("BLUE",Color.BLUE,new ImageIcon("./Image/blue.png")),
		PINK("PINK", Color.PINK,new ImageIcon("./Image/pink.png")), 
		BLACK("BLACK", Color.BLACK,new ImageIcon("./Image/black.png")),
		WHITE("WHITE", Color.WHITE,new ImageIcon("./Image/white.png"));
		
		private String getLabel;
		private Color getColor;
		private ImageIcon img;
		
		private EColorMenu(String lable, Color color,ImageIcon img) {
			this.getLabel = lable;
			this.getColor = color;
			this.img = img;
		}
		
		public String getLabel() {
			return this.getLabel;
		}
		
		public Color getColor() {
			return this.getColor;
		}
		
		public ImageIcon getImg() {
			Image image = this.img.getImage();  //ImageIcon�� Image�� ��ȯ 
			Image newImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ImageIcon newImageIcon = new ImageIcon(newImage); 
			return newImageIcon;			
		}
	}
	
}
