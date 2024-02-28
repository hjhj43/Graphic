package main;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import frames.GDrawingPanel;
import frames.GMenuBar;
import frames.GToolBar;


public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private GMenuBar menuBar;
	private GToolBar toolBar; 
	private GDrawingPanel drawingPanel; 
	
	public GMainFrame() {
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		
		// 속성 
		this.setSize(900, 600);
		this.setTitle("Graphics Editor");
		this.setLocationRelativeTo(null);

		// 자식
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager);

		this.menuBar = new GMenuBar(); 
		this.setJMenuBar(this.menuBar); 
		
		this.toolBar = new GToolBar(); 
		this.add(toolBar, layoutManager.NORTH);
		
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, layoutManager.CENTER);
		
		this.menuBar.associate(this.drawingPanel);
		this.toolBar.associate(this.drawingPanel);

	}

	public void initialize() {
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
	}

	public void handleClosing() {
		if (this.drawingPanel.isUpdated() != false) {
            int answer = showWarningMessage();            
            switch (answer) {
                case JOptionPane.YES_OPTION:
                	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    break;                    
                case JOptionPane.NO_OPTION:
                	JOptionPane.showMessageDialog(null, "창을 종료합니다.");
                    dispose();
                    break;                     
                case JOptionPane.CANCEL_OPTION:
                	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    break;
            }
        } else {
        	JOptionPane.showMessageDialog(null, "창을 종료합니다.");
            dispose();
        } 	
	}
	
	private int showWarningMessage() {
        String[] buttonLabels = new String[] {"Yes", "No", "Cancel"};
        String defaultOption = buttonLabels[0];
        Icon icon = null;
         
        return JOptionPane.showOptionDialog(this,
                "저장되지 않은 그림이 있습니다.\n" +
                "저장을 하고 지우시겠습니까?",
                "Warning",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);    
    }
 
	
	class JFrameWindowClosingEventHandler extends WindowAdapter {
		
		public void windowClosing(WindowEvent e) {
			handleClosing();			
			} 		
		}

}
