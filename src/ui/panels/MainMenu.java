package ui.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ui.MainFrame;
import ui.Resources;
import ui.UIConstants.Images;
import ui.UIConstants.Strings;

/**
 * 
 * @author bmost
 *
 */
public class MainMenu extends UIPanel implements ActionListener {

	private static final long serialVersionUID = -1199792392732674767L;
	JButton single_, multi_;
	MainFrame parent_;
	
	public MainMenu(MainFrame parent) {
		parent_ = parent;
	}

	@Override
	public void makeUI() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 600));
		JLabel banner = new JLabel(new ImageIcon(Resources.getImage(Images.DEFAULT).getScaledInstance(800, 250, Image.SCALE_SMOOTH)));
		banner.setPreferredSize(new Dimension(800, 250));
		add(banner, BorderLayout.NORTH);

		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		single_ = new JButton(Strings.SINGLE_PLAYER);
		single_.setAlignmentX(Component.CENTER_ALIGNMENT);
		single_.addActionListener(this);
		multi_ = new JButton(Strings.MULTI_PLAYER);
		multi_.setAlignmentX(Component.CENTER_ALIGNMENT);
		multi_.addActionListener(this);
		
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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == single_) {
			System.out.println("Go to single player menu");
		} else if (e.getSource() == multi_) {
			System.out.println("Go to multi player menu");
		}
	}
}
