package edu.brown.cs32.browndemic.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.listeners.DoneLoadingListener;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.menus.Loading;
import edu.brown.cs32.browndemic.ui.panels.menus.MainMenu;
import edu.brown.cs32.browndemic.ui.panels.titlebars.DefaultTitleBar;
import edu.brown.cs32.browndemic.ui.panels.titlebars.TitleBar;

/**
 * 
 * @author bmost
 *
 */
public class BrowndemicFrame extends JFrame {

	private static final long serialVersionUID = -8808883923263763897L;
	JPanel _content;
	JPanel _titleContainer;
	
	public BrowndemicFrame() {
		init();
		setPanel(new Loading(new InitialLoadingDone(), Images.MENU_IMAGES));
	}
	
	private class InitialLoadingDone implements DoneLoadingListener {
		public void doneLoading() {
			defaultTitle();
			setPanel(new MainMenu());
		}
	}

	public BrowndemicFrame(UIPanel p) {
		init();
		setPanel(p);
	}
	
	private void init() {
		try {
			UIConstants.init();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public void load(String... paths) {
		Resources.loadImages(paths);
	}
	
	public void defaultTitle() {
		setTitle(new DefaultTitleBar());
	}
	
	public void setTitle(TitleBar t) {
		_titleContainer.removeAll();
		revalidate();
		repaint();
		_titleContainer.add(t);
	}
	
	public void setPanel(UIPanel p) {
		_content.removeAll();
		revalidate();
		repaint();
		_content.add(p);
		p.setupForDisplay();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BrowndemicFrame();
	}
}
