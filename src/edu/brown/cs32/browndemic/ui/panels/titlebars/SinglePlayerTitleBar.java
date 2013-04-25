package edu.brown.cs32.browndemic.ui.panels.titlebars;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.DragWindow;
import edu.brown.cs32.browndemic.ui.panels.menus.MainMenu;
import edu.brown.cs32.browndemic.world.World;

public class SinglePlayerTitleBar extends TitleBar {

	private static final long serialVersionUID = -8926559135273305855L;
	
	JLabel minimize;
	JMenuItem quit, save, exit;
	World _world;
	
	public SinglePlayerTitleBar(World world) {
		super();
		makeUI();
	}
	
	private void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		new DragWindow(this);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Colors.MENU_BACKGROUND);
		menuBar.setMinimumSize(new Dimension(0, UI.TITLE_HEIGHT));
		menuBar.setBorder(BorderFactory.createEmptyBorder());
		menuBar.setAlignmentY(CENTER_ALIGNMENT);
		

		JMenu menu = new JMenu("Menu");
		menu.setFont(Fonts.TITLE_BAR);
		menu.setBackground(Colors.MENU_BACKGROUND);
		menu.setForeground(Colors.RED_TEXT);

		quit = new JMenuItem("Quit to Main Menu");
		quit.addMouseListener(this);
		quit.setFont(Fonts.TITLE_BAR);
		quit.setBackground(Colors.MENU_BACKGROUND);
		quit.setForeground(Colors.RED_TEXT);
		menu.add(quit);

		save = new JMenuItem("Save");
		save.addMouseListener(this);
		save.setFont(Fonts.TITLE_BAR);
		save.setBackground(Colors.MENU_BACKGROUND);
		save.setForeground(Colors.RED_TEXT);
		menu.add(save);
		
		exit = new JMenuItem("Exit");
		exit.addMouseListener(this);
		exit.setFont(Fonts.TITLE_BAR);
		exit.setBackground(Colors.MENU_BACKGROUND);
		exit.setForeground(Colors.RED_TEXT);
		menu.add(exit);
		
		Utils.setDefaultLook(menuBar, quit, save, exit);
		
		menuBar.add(menu);
		add(menuBar);

		add(Box.createHorizontalGlue());
		
		JLabel title = new JLabel(Strings.TITLE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.revalidate();
		title.setForeground(Colors.RED_TEXT);
		title.setFont(Fonts.TITLE_BAR);
		new DragWindow(title);
		add(title);
		
		add(Box.createRigidArea(new Dimension(UI.WIDTH/2 - UI.TITLE_HEIGHT * 2 - 20,0)));
		
		minimize = new HoverLabel(Resources.getImage(Images.MINIMIZE_BUTTON), Resources.getImage(Images.MINIMIZE_BUTTON_HOVER));
		minimize.addMouseListener(this);
		minimize.setToolTipText(Strings.MINIMIZE);
		add(minimize);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) return;
		if (!(e.getSource() instanceof Container)) return;
		if (!((Container)e.getSource()).contains(e.getPoint())) return;
		if (e.getSource() == minimize) {
			Utils.getParentFrame(this).setState(JFrame.ICONIFIED);
		} else if (e.getSource() == quit) {
			int choice = JOptionPane.showOptionDialog(Utils.getParentFrame(this), 
										"Are you sure you want to quit?  Any unsaved progress will be lost", 
										"Confirm quit", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
										null, null, null);
			if (choice == 0)
				Utils.getParentFrame(this).setPanel(new MainMenu());
		} else if (e.getSource() == save) {
			File saves = new File("saves");
			saves.mkdir();
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(saves);
			
			if (fc.showSaveDialog(Utils.getParentFrame(this)) == JFileChooser.APPROVE_OPTION) {
				System.out.println("SAVE TO: " + fc.getSelectedFile());
				// TODO: Save file
			}
		} else if (e.getSource() == exit) {
			int choice = JOptionPane.showOptionDialog(Utils.getParentFrame(this), 
					"Are you sure you want to exit?  Any unsaved progress will be lost", 
					"Confirm exit", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
					null, null, null);
			if (choice == 0)
				System.exit(0);
		}
	}

}
