package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;

/**
 * 
 * @author bmost
 *
 */
public class MainMenu extends UIPanel implements MouseListener {

	private static final long serialVersionUID = -1199792392732674767L;
	JLabel single_, multi_;
	
	public MainMenu() {
		super();
		makeUI();
	}
	
	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).defaultTitle();
	}

	public void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(800, 600));
		
		add(Box.createRigidArea(new Dimension(0, 20)));
		
		JLabel banner = new JLabel(new ImageIcon(Resources.getImage(Images.TITLE)));
		banner.setPreferredSize(new Dimension(800, 200));
		banner.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(banner);
		
		add(Box.createVerticalGlue());

		single_ = new HoverLabel(Resources.getImage(Images.SINGLE_PLAYER), Resources.getImage(Images.SINGLE_PLAYER_HOVER)); 
		single_.setAlignmentX(Component.CENTER_ALIGNMENT);
		single_.addMouseListener(this);
		multi_ = new HoverLabel(Resources.getImage(Images.MULTI_PLAYER), Resources.getImage(Images.MULTI_PLAYER_HOVER));
		multi_.setAlignmentX(Component.CENTER_ALIGNMENT);
		multi_.addMouseListener(this);
		
		add(single_);
		add(multi_);
		
		add(Box.createVerticalGlue());
		
		add(Box.createRigidArea(new Dimension(0, 80)));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) return;
		if (!(e.getSource() instanceof Container)) return;
		if (!((Container)e.getSource()).contains(e.getPoint())) return;
		if (e.getSource() == single_) {
			Utils.getParentFrame(this).setPanel(new SinglePlayer());
		} else if (e.getSource() == multi_) {
			System.out.println("MULTIPLAYER CLICKED");
		}
	}
	
}
