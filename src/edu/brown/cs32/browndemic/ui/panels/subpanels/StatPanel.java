package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public class StatPanel extends BrowndemicPanel {
	
	private static final long serialVersionUID = -1147093041368550992L;
	
	private Disease _disease;
	private JProgressBar _infectivity, _lethality, _visibility;

	public StatPanel(Disease d) {
		super();
		_disease = d;
		makeUI();
		new Timer(1000/10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_infectivity.setValue((int)_disease.getInfectivity());
			}
		}).start();
	}
	
	private void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		
		JPanel infectivity = new JPanel();
		infectivity.setLayout(new BoxLayout(infectivity, BoxLayout.X_AXIS));
		infectivity.setBackground(Colors.MENU_BACKGROUND);
		infectivity.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		JLabel infectivityLabel = new JLabel("Infectivity: ");
		infectivityLabel.setForeground(Colors.RED_TEXT);
		infectivityLabel.setFont(Fonts.NORMAL_TEXT);
		
		_infectivity = new JProgressBar(0, (int)_disease.getMaxInfectivity());
		_infectivity.setValue((int) _disease.getInfectivity());
		_infectivity.setBackground(Colors.MENU_BACKGROUND);
		_infectivity.setForeground(Colors.RED_TEXT);
		//_infectivity.setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 1));
		
		infectivity.add(infectivityLabel);
		infectivity.add(_infectivity);
		
		
		JPanel lethality = new JPanel();
		lethality.setLayout(new BoxLayout(lethality, BoxLayout.X_AXIS));
		lethality.setBackground(Colors.MENU_BACKGROUND);
		lethality.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		JLabel lethalityLabel = new JLabel("Lethality: ");
		lethalityLabel.setForeground(Colors.RED_TEXT);
		lethalityLabel.setFont(Fonts.NORMAL_TEXT);
		
		_lethality = new JProgressBar(0, (int)_disease.getMaxLethality());
		_lethality.setValue((int) _disease.getLethality());
		_lethality.setBackground(Colors.MENU_BACKGROUND);
		_lethality.setForeground(Colors.RED_TEXT);
		
		lethality.add(lethalityLabel);
		lethality.add(_lethality);
		
		
		JPanel visibility = new JPanel();
		visibility.setLayout(new BoxLayout(visibility, BoxLayout.X_AXIS));
		visibility.setBackground(Colors.MENU_BACKGROUND);
		visibility.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		JLabel visibilityLabel = new JLabel("Visibility: ");
		visibilityLabel.setForeground(Colors.RED_TEXT);
		visibilityLabel.setFont(Fonts.NORMAL_TEXT);
		
		_visibility = new JProgressBar(0, (int)_disease.getMaxVisibility());
		_visibility.setValue((int) _disease.getVisibility());
		_visibility.setBackground(Colors.MENU_BACKGROUND);
		_visibility.setForeground(Colors.RED_TEXT);
		
		visibility.add(visibilityLabel);
		visibility.add(_visibility);
		
		
		
		Utils.setDefaultLook(_infectivity, _lethality, _visibility);
		
		add(Box.createRigidArea(new Dimension(0, 25)));
		add(infectivity);
		add(lethality);
		add(visibility);
		add(Box.createGlue());
	}
}
