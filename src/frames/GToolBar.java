package frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import main.GConstants.EShape;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	private GDrawingPanel drawingPanel; 

	private JRadioButton selectedColor;

	private ButtonGroup buttonGroup;
	private EShape eSelectedShape;
	//	private JTextArea jTextArea;

	public EShape getESelectedShape() {
		return this.eSelectedShape;
	}

	public GToolBar() {
		buttonGroup = new ButtonGroup(); 
		ActionHandler actionHandler = new ActionHandler();

		for(EShape eShape: EShape.values()) {
			JRadioButton drawingTool;
			if(eShape == EShape.eSelect) {
				drawingTool = new JRadioButton(eShape.getLabel());
			}else {
				drawingTool = new JRadioButton(eShape.getImg());
			}
			drawingTool.setActionCommand(eShape.name());
			drawingTool.addActionListener(actionHandler);
			drawingTool.setToolTipText(eShape.getToolTip());
			this.add(drawingTool);  
			buttonGroup.add(drawingTool);
		}
		resetESelectedShape();
	}

	public void resetESelectedShape() {
		this.buttonGroup.clearSelection();
		eSelectedShape = EShape.eSelect;
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel; 
		JRadioButton defaultButton = (JRadioButton) this.getComponent(EShape.eSelect.ordinal());
		defaultButton.doClick(); 
	}

	public void initialize() {
	}

	public void changeShapeDraw(Color selectedColor) {
		this.drawingPanel.changeShapeDraw(selectedColor);
	}

	public void changeButtonColor(EShape eTools) {
		JRadioButton clickedButton = (JRadioButton) this.getComponent(eTools.ordinal());
		clickedButton.setBackground(new Color(204, 204, 204));
		EShape[] arr = EShape.values();
		for(EShape rb : arr) {		
			if(rb != eTools) {
				JRadioButton button = (JRadioButton) this.getComponent(rb.ordinal());
				button.setBackground(new Color(230, 230, 230));			
			}
		}
	}

	private class ActionHandler implements ActionListener {
		JColorChooser chooser = new JColorChooser();
		@Override
		public void actionPerformed(ActionEvent e) {
			if (EShape.valueOf(e.getActionCommand()) != null) {
				changeButtonColor(EShape.valueOf(e.getActionCommand()));
				drawingPanel.setSelectedTool(EShape.valueOf(e.getActionCommand()));
			}
		}
	}
}
