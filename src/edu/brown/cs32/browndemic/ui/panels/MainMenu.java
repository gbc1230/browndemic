package edu.brown.cs32.browndemic.ui.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;

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

	public void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 600));
		JLabel banner = new JLabel(new ImageIcon(Resources.getImage(Images.TITLE)));
		banner.setPreferredSize(new Dimension(800, 117));
		add(banner, BorderLayout.NORTH);

		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBackground(Colors.MENU_BACKGROUND);

		single_ = new JLabel(new ImageIcon(Resources.getImage(Images.SINGLE_PLAYER)));
		single_.setAlignmentX(Component.CENTER_ALIGNMENT);
		single_.addMouseListener(this);
		multi_ = new JLabel(new ImageIcon(Resources.getImage(Images.MULTI_PLAYER)));
		multi_.setAlignmentX(Component.CENTER_ALIGNMENT);
		multi_.addMouseListener(this);
		
		content.add(single_);
		content.add(multi_);
		content.setPreferredSize(new Dimension(800, 100));
		
		add(content, BorderLayout.CENTER);
		
		JLabel info = new JLabel("Info");
		info.setPreferredSize(new Dimension(800, 100));
		info.setHorizontalAlignment(SwingConstants.CENTER);
		add(info, BorderLayout.SOUTH);
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
		if (e.getSource() == single_) {
			getParentFrame().setPanel(new SinglePlayer());
		} else if (e.getSource() == multi_) {
			System.out.println("MULTIPLAYER CLICKED");
		}
	}
	
}
