package edu.brown.cs32.browndemic.ui.panels;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.brown.cs32.browndemic.ui.BrowndemicFrame;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;

public class SinglePlayer extends UIPanel {

	private static final long serialVersionUID = -1231229219691042468L;
	
	BrowndemicFrame _parent;
	JTextField _diseaseName;
	JLabel _disease1, _disease2, _disease3;
	
	public SinglePlayer() {
		super();
		makeUI();
	}
	
	private void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(UI.WIDTH, UI.CONTENT_HEIGHT));
		setBackground(Colors.MENU_BACKGROUND);
		
		add(Box.createRigidArea(new Dimension(1, 100)));
		
		JPanel diseaseName = new JPanel();
		diseaseName.setLayout(new BoxLayout(diseaseName, BoxLayout.X_AXIS));
		diseaseName.setMaximumSize(new Dimension(UI.WIDTH/2, 200));
		diseaseName.setBackground(Colors.MENU_BACKGROUND);
		
		JLabel l = new JLabel(Strings.ENTER_DISEASE_NAME);
		l.setFont(Fonts.NORMAL_TEXT);
		l.setForeground(Colors.RED_TEXT);
		_diseaseName = new JTextField();
		_diseaseName.setFont(Fonts.NORMAL_TEXT);
		diseaseName.add(l);
		diseaseName.add(_diseaseName);
		add(diseaseName);
		
		add(Box.createGlue());
		
		JPanel disease = new JPanel();
		disease.setLayout(new BoxLayout(disease, BoxLayout.X_AXIS));

		_disease1 = new JLabel(new ImageIcon(Resources.getImage(Images.DEFAULT).getScaledInstance(200, 200, Image.SCALE_FAST)));
		_disease1.setPreferredSize(new Dimension(200, 200));
		_disease2 = new JLabel(new ImageIcon(Resources.getImage(Images.DEFAULT).getScaledInstance(200, 200, Image.SCALE_FAST)));
		_disease2.setPreferredSize(new Dimension(200, 200));
		_disease3 = new JLabel(new ImageIcon(Resources.getImage(Images.DEFAULT).getScaledInstance(200, 200, Image.SCALE_FAST)));
		_disease3.setPreferredSize(new Dimension(200, 200));
		disease.add(_disease1);
		disease.add(_disease2);
		disease.add(_disease3);
		add(disease);
		
		add(Box.createGlue());
	}
}
