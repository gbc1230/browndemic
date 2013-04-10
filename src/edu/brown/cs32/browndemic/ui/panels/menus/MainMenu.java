package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;

/**
 * 
 * @author bmost
 *
 */
public class MainMenu extends UIPanel {

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

	@Override
	protected void makeUI() {
		super.makeUI();
		
		add(Box.createRigidArea(new Dimension(UI.WIDTH, 0)));
		add(Box.createVerticalGlue());
		
		JLabel banner = new JLabel(new ImageIcon(Resources.getImage(Images.TITLE)));
		banner.setPreferredSize(new Dimension(800, 200));
		banner.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(banner);
		
		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());

		single_ = new HoverLabel(Strings.SINGLE_PLAYER, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT); 
		single_.setAlignmentX(Component.CENTER_ALIGNMENT);
		single_.addMouseListener(this);
		multi_ = new HoverLabel(Strings.MULTI_PLAYER, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		multi_.setAlignmentX(Component.CENTER_ALIGNMENT);
		multi_.addMouseListener(this);
		
		add(single_);
		add(Box.createVerticalGlue());
		add(multi_);

		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());
	}

	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == single_) {
			Utils.getParentFrame(this).setPanel(new SinglePlayer());
		} else if (e.getSource() == multi_) {
			Utils.getParentFrame(this).setPanel(new MultiplayerMenu());
		}
	}

	@Override
	public String toString() {
		return Strings.MAIN_MENU;
	}
	
}
