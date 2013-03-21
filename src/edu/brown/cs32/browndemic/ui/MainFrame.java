package edu.brown.cs32.browndemic.ui;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.panels.Loading;
import edu.brown.cs32.browndemic.ui.panels.MainMenu;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;

/**
 * 
 * @author bmost
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8808883923263763897L;
	
	public MainFrame() {
		init();
	}

	public MainFrame(UIPanel p) {
		init();
		setPanel(p);
	}
	
	private void init() {
		setTitle(Strings.TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		setLayout(new FlowLayout());
	}
	
	public void setPanel(UIPanel p) {
		getContentPane().removeAll();
		revalidate();
		repaint();
		p.makeUI();
		add(p);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		MainFrame m = new MainFrame();
		m.setPanel(new Loading(m, new MainMenu(m)));
		//new MainFrame(new MainMenu());
	}
}
