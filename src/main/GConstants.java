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
		eSelect("선택",new GSelection(),"Selection Tool",new ImageIcon("./Image/rectangle.png"),EUserAction.e2point),  
		eRectanble("네모",new GRectangle(),"네모",new ImageIcon("./Image/rectangle.png"),EUserAction.e2point),    
		eRoundRectangle("원형사각형",new GRoundRectangle(),"원형사각형",new ImageIcon("./Image/roundRectangle.png"),EUserAction.e2point),
		eOval("동그라미" ,new GOval(),"동그라미",new ImageIcon("./Image/oval.png"),EUserAction.e2point),  
		eLine("선",new GLine(),"선",new ImageIcon("./Image/line.png"),EUserAction.e2point),  
		ePolygon("다각형",new GPolygon(),"다각형",new ImageIcon("./Image/polygon.png"),EUserAction.eNpoint),
		ePath("펜",new GPath(),"펜",new ImageIcon("./Image/pen.png"),EUserAction.e2point);

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
			Image image = this.img.getImage();  //ImageIcon을 Image로 변환 
			Image newImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ImageIcon newImageIcon = new ImageIcon(newImage); 
			return newImageIcon;			
		}
		
		public EUserAction getETransformationStyle() {
			return this.eUserAction;
		}
	}
	
	public enum EFileMenu{
		eNew("새로만들기"), 
		eOpen("열기"),
		eSave("저장"), 
		eSaveAs("다른이름으로 저장하기"),
		eQuit("종료"),
		eImg("이미지 추가");
		
		private String getLabel;

		private EFileMenu(String lable) {
			this.getLabel = lable;
		}
		
		public String getLabel() {
			return this.getLabel;
		}

	}
	
	public enum EEditMenu{
		eUndo("뒤로 가기"), 
		eRedo("다시 실행"),
		eDelete("전체 삭제"),
		eDeleteOne("삭제하기"),
		eCopy("복사하기"),		
		 ePaste("붙여넣기"),
		 eGroup("전체 그룹묶기"),
		 eUnGroup("그룹취소하기");
		
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
			Image image = this.img.getImage();  //ImageIcon을 Image로 변환 
			Image newImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			ImageIcon newImageIcon = new ImageIcon(newImage); 
			return newImageIcon;			
		}
	}
	
}
