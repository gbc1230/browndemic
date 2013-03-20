package ui.panels;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ui.Resources;
import ui.UIConstants.Images;

/**
 * 
 * @author bmost
 *
 */
public class MainMenu extends UIPanel {

	private static final long serialVersionUID = -1199792392732674767L;

	@Override
	public void makeUI() {
		setLayout(new FlowLayout());
		JLabel banner = new JLabel(new ImageIcon(Resources.getImage(Images.DEFAULT)));
		add(banner);
	}
}
