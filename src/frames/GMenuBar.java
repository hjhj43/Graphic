package frames;

import javax.swing.JMenuBar;

import menu.GGraphicMenu;
import menu.GEditMenu;
import menu.GFileMenu;

import java.awt.*;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private GFileMenu fileMenu; 
	private GEditMenu editMenu;
	private GGraphicMenu colorMenu;

	private GDrawingPanel drawingPanel;
	
	public GMenuBar() {
		this.fileMenu = new GFileMenu("파일");
		this.add(this.fileMenu);
			
		this.editMenu = new GEditMenu("편집");
		this.add(this.editMenu);
		
		this.colorMenu = new GGraphicMenu("속성");
		this.add(this.colorMenu);
		
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel; 	
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
		this.colorMenu.associate(this.drawingPanel);
	}

	public void initialize() {
		this.fileMenu.initialize();
		this.editMenu.initialize();
		
	}

}
