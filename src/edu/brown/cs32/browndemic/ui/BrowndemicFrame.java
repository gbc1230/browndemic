package edu.brown.cs32.browndemic.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.menus.Loading;
import edu.brown.cs32.browndemic.ui.panels.menus.MainMenu;
import edu.brown.cs32.browndemic.ui.panels.titlebars.DefaultTitleBar;
import edu.brown.cs32.browndemic.ui.panels.titlebars.TitleBar;

/**
 * BrowndemicFrame is the main JFrame for the project.
 * The Frame has two sections, a Title Bar and a Content
 * section.  The Title Bar and Content can be changed
 * independently and both are UIPanels.
 * @author bmost
 *
 */
public class BrowndemicFrame extends JFrame {

	private static final long serialVersionUID = -8808883923263763897L;
	private JPanel _content;
	private JPanel _titleContainer;
	
	/**
	 * Creates a BrowndemicFrame and does initial loading.  When
	 * loading is done it will go the the MainMenu
	 */
	public BrowndemicFrame() {
		init();
		setPanel(new Loading(new Loading.LoadImageWorker(new InitialLoadingDone(), Images.MENU_IMAGES)));
	}
	
	private class InitialLoadingDone implements Action {
		public void doAction() {
			defaultTitle();
			setPanel(new MainMenu());
		}
	}

	/**
	 * Creates a BrowndemicFrame with the specified UIPanel.
	 * @param p The UIPanel to set the content area to.
	 */
	public BrowndemicFrame(UIPanel p) {
		init();
		setPanel(p);
		defaultTitle();
	}
	
	private void init() {
		try {
			UIConstants.init();
		} catch (FileNotFoundException e) {
			System.out.println("Could not load file: ");
			e.printStackTrace();
			System.exit(1);
		} catch (FontFormatException e) {
			System.out.println("Invalid Font");
			e.printStackTrace();
			System.exit(2);
		} catch (IOException e) {
			System.out.println("Could not read file: ");
			e.printStackTrace();
			System.exit(3);
		}
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
		}
		
		setTitle(Strings.TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setSize(UI.WIDTH, UI.HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());
		
		_content = new JPanel();
		_content.setLayout(new BoxLayout(_content, BoxLayout.X_AXIS));
		_content.setPreferredSize(new Dimension(UI.WIDTH, UI.CONTENT_HEIGHT));
		
		_titleContainer = new JPanel();
		_titleContainer.setLayout(new BoxLayout(_titleContainer, BoxLayout.X_AXIS));
		_titleContainer.setPreferredSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		
		add(_content, BorderLayout.CENTER);
		_content.setBackground(Color.GREEN);
		add(_titleContainer, BorderLayout.NORTH);
		_titleContainer.setBackground(Color.BLUE);
		
	}
	
	/**
	 * Sets the Title Bar to the default.
	 */
	public void defaultTitle() {
		setTitle(new DefaultTitleBar());
	}
	
	/**
	 * Sets a custom Title Bar
	 * @param t The TitleBar to be set.
	 */
	public void setTitle(TitleBar t) {
		_titleContainer.removeAll();
		revalidate();
		repaint();
		_titleContainer.add(t);
	}
	
	/**
	 * Sets the content panel
	 * @param p The UIPanel to be set.
	 */
	public void setPanel(UIPanel p) {
		_content.removeAll();
		revalidate();
		repaint();
		_content.add(p);
		p.setupForDisplay();
		setVisible(true);
	}
	
	public void setPanel(UIPanel p, boolean doSetup) {
		_content.removeAll();
		revalidate();
		repaint();
		_content.add(p);
		if (doSetup)
			p.setupForDisplay();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BrowndemicFrame();
	}
}
