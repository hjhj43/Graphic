package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import frames.GDrawingPanel;
import main.GConstants.EFileMenu;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private File file; 
	private GDrawingPanel drawingPanel;

	public GFileMenu(String title) {
		super(title);

		this.file = null;
		
		KeyStroke key;
		
		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenu eMenuItem : EFileMenu.values()) {
			JMenuItem MenuItem = new JMenuItem(eMenuItem.getLabel());
			MenuItem.setActionCommand(eMenuItem.name());
			
			if(eMenuItem.equals(eMenuItem.eNew)) {
				MenuItem.setMnemonic(KeyEvent.VK_N);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
				MenuItem.setAccelerator(key);
			}
			if(eMenuItem.equals(eMenuItem.eSave)) {
				MenuItem.setMnemonic(KeyEvent.VK_S);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
				MenuItem.setAccelerator(key);
			}
			if(eMenuItem.equals(eMenuItem.eSaveAs)) {
				MenuItem.setMnemonic(KeyEvent.VK_F12);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_F12, ActionEvent.ALT_MASK);
				MenuItem.setAccelerator(key);
			}
			if(eMenuItem.equals(eMenuItem.eOpen)) {
				MenuItem.setMnemonic(KeyEvent.VK_F);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK);
				MenuItem.setAccelerator(key);
			}
			if(eMenuItem.equals(eMenuItem.eQuit)) {
				MenuItem.setMnemonic(KeyEvent.VK_F4);
				key = KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK);
				MenuItem.setAccelerator(key);
			}
			
			MenuItem.addActionListener(actionHandler);
			this.add(MenuItem); 
		}
	}

	public void initialize() {
	}
	
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	private void load(File file) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream);

			Object object = objectOutputStream.readObject();
			
			this.drawingPanel.saveShapes();
			this.drawingPanel.setShapes(object);
			objectOutputStream.close();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void store(File file) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			
			objectOutputStream.writeObject(this.drawingPanel.getShapes());
			
			objectOutputStream.writeObject(this.drawingPanel.getImges());
			objectOutputStream.close();

			JOptionPane.showMessageDialog(null, "저장되었습니다!");
			this.drawingPanel.setUpdated(false);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newPanel() {
		if (this.drawingPanel.isUpdated() == false) {
			int result0 = JOptionPane.showConfirmDialog(null, "새창을 여시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (result0 == JOptionPane.YES_OPTION) {		
				this.drawingPanel.closeFile();
				this.file = null;
			}
		} else {
			int result1 = JOptionPane.showConfirmDialog(null, "그림이 존재합니다. 저장하시겠습니까?", "Confirm",
					JOptionPane.YES_NO_OPTION);
			if (result1 == JOptionPane.YES_OPTION) {
				save();
				this.drawingPanel.setUpdated(false);
				newPanel();
			}else if(result1 == JOptionPane.NO_OPTION) {
				int result0 = JOptionPane.showConfirmDialog(null,
						"저장하지 않고 새창을 여시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (result0 == JOptionPane.YES_OPTION) {		
					this.drawingPanel.closeFile();
					this.drawingPanel.setUpdated(false);
					this.file = null;
				}
			}
		}
	}

	private void open() {
		if(this.drawingPanel.isUpdated() == false) {
			JFileChooser chooser = new JFileChooser();
			String path = "C:\\ECLIPSE\\ShapeSave";
			File Folder = new File(path);
			chooser.setCurrentDirectory(Folder);
			int returnVal = chooser.showOpenDialog(this.drawingPanel);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.file = chooser.getSelectedFile();
				this.load(this.file);
			}
		}else {
			JOptionPane.showMessageDialog(null, "그림이 존재합니다.");
		}
	}

	public void save() {
		if (this.drawingPanel.isUpdated() == false) {
			JOptionPane.showMessageDialog(null, "저장할 그림이 없습니다.");
		} else {
			int result = JOptionPane.showConfirmDialog(null, 
					"저장하시겠습니까?", "Confirm",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.CLOSED_OPTION) {
			} else if (result == JOptionPane.YES_OPTION) {
		
				if (this.file == null) {
					this.saveAs();				
				} else {
					this.store(this.file);
				}
			}
		}
	}

	private void saveAs() {
		String path = "C:\\ECLIPSE\\ShapeSave";
		File Folder = new File(path);
		
		if(!Folder.exists()) {
			Folder.mkdir();
			System.out.println("폴더가 생성되었습니다.");
		}else {
//			System.out.println("폴더가 존재합니다.");
		}
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(Folder);
		int returnVal = chooser.showSaveDialog(this.drawingPanel);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile();
			this.store(this.file);
		}
	}

	private void quit() {
		int result = JOptionPane.showConfirmDialog(null, 
				"그림판을 종료하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
		if(this.drawingPanel.isUpdated() == true) {
			int result1 = JOptionPane.showConfirmDialog(null, 
					"그림이 존재합니다.그래도 종료하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (result1 == JOptionPane.CLOSED_OPTION) {
			} else if (result1 == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "취소되었습니다.");
			}
			
		}else {
			if (result == JOptionPane.CLOSED_OPTION) {
			} else if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "취소되었습니다.");
			}
		}
	}

	public void addImg() {
		this.drawingPanel.addImg();
	}
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals(EFileMenu.eNew.name())) {
				newPanel();
			} else if (e.getActionCommand().equals(EFileMenu.eOpen.name())) {
				open();
			}else if (e.getActionCommand().equals(EFileMenu.eSave.name())) {
				save();
			} else if (e.getActionCommand().equals(EFileMenu.eSaveAs.name())) {
				saveAs();
			} else if (e.getActionCommand().equals(EFileMenu.eQuit.name())) {
				quit();
			} else if (e.getActionCommand().equals(EFileMenu.eImg.name())) {
				addImg();
			}
		}

	}

}
