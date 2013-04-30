package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.brown.cs32.browndemic.disease.Bacteria;
import edu.brown.cs32.browndemic.disease.Parasite;
import edu.brown.cs32.browndemic.disease.Virus;
import edu.brown.cs32.browndemic.ui.BrowndemicFrame;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.components.SelectButton;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;
import edu.brown.cs32.browndemic.world.MainWorld;
import edu.brown.cs32.browndemic.world.WorldMaker;

public class SinglePlayerMenu extends UIPanel {

	private static final long serialVersionUID = -1231229219691042468L;
	
	BrowndemicFrame _parent;
	JTextField _diseaseName;
	SelectButton _disease1, _disease2, _disease3;
	JLabel _start, _load;
	
	public SinglePlayerMenu() {
		super();
		makeUI();
	}
	
	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new BackTitleBar(new MainMenu()));
	}
	
	@Override
	protected void makeUI() {
		
		super.makeUI();

		add(Box.createGlue());
		_load = new HoverLabel(Strings.LOAD_GAME, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_load.setAlignmentX(CENTER_ALIGNMENT);
		_load.addMouseListener(this);
		add(_load);
		add(Box.createGlue());
		add(Box.createGlue());
		
		JPanel diseaseName = new JPanel();
		diseaseName.setLayout(new BoxLayout(diseaseName, BoxLayout.X_AXIS));
		diseaseName.setMaximumSize(new Dimension(UI.WIDTH-150, 200));
		diseaseName.setBackground(Colors.TRANSPARENT);
		
		JLabel diseaseNameLabel = new JLabel(Strings.ENTER_DISEASE_NAME);
		diseaseNameLabel.setFont(Fonts.BIG_TEXT);
		diseaseNameLabel.setForeground(Colors.RED_TEXT);
		_diseaseName = new JTextField();
		Utils.setDefaultLook(_diseaseName);
		_diseaseName.setFont(Fonts.BIG_TEXT);
		_diseaseName.setForeground(Colors.RED_TEXT);
		_diseaseName.setBackground(Colors.MENU_BACKGROUND);
		diseaseName.add(diseaseNameLabel);
		diseaseName.add(_diseaseName);
		add(diseaseName);
		
		add(Box.createGlue());
		
		JLabel selectDisease = new JLabel(Strings.SELECT_DISEASE);
		selectDisease.setFont(Fonts.BIG_TEXT);
		selectDisease.setForeground(Colors.RED_TEXT);
		selectDisease.setAlignmentX(CENTER_ALIGNMENT);
		add(selectDisease);
		
		JPanel disease = new JPanel();
		disease.setLayout(new BoxLayout(disease, BoxLayout.X_AXIS));
		disease.setBackground(Colors.TRANSPARENT);
		disease.setOpaque(false);

		_disease1 = new SelectButton(Resources.getImage(Images.DISEASE1), Resources.getImage(Images.DISEASE1_SELECTED));
		_disease2 = new SelectButton(Resources.getImage(Images.DISEASE2), Resources.getImage(Images.DISEASE2_SELECTED));
		_disease3 = new SelectButton(Resources.getImage(Images.DISEASE3), Resources.getImage(Images.DISEASE3_SELECTED));
		_disease2.addOnSelectAction(new SelectAction(_disease1, _disease3));
		_disease1.addOnSelectAction(new SelectAction(_disease2, _disease3));
		_disease3.addOnSelectAction(new SelectAction(_disease2, _disease1));

		disease.add(Box.createGlue());
		disease.add(_disease1);
		disease.add(Box.createGlue());
		disease.add(_disease2);
		disease.add(Box.createGlue());
		disease.add(_disease3);
		disease.add(Box.createGlue());
		add(disease);
		
		add(Box.createGlue());
		
		_start = new HoverLabel(Strings.START_GAME, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_start.addMouseListener(this);
		_start.setEnabled(false);
		_start.setAlignmentX(CENTER_ALIGNMENT);
		add(_start);
		
		add(Box.createGlue());
	}
	
	private class SelectAction implements Action {
		
		private SelectButton[] other;
		
		public SelectAction(SelectButton... other) {
			this.other = other;
		}

		@Override
		public void doAction() {
			_start.setEnabled(true);
			for (SelectButton b : other) {
				b.deSelect();
			}
		}
		
	}
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {		
		if (e.getSource() == _start && _start.isEnabled()) {
			int disease;
			if (_disease1.isSelected())
				disease = 1;
			else if (_disease2.isSelected())
				disease = 2;
			else
				disease = 3;
			String name = _diseaseName.getText();
			if (name.equals("")) {
				//TODO: Error, invalid disease name
			}
			
			MainWorld w;
			try {
				w = WorldMaker.makeNewEarthSP();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
			if (disease == 1)
				w.addDisease(new Bacteria(name));
			if (disease == 2)
				w.addDisease(new Virus(name));
			if (disease == 3)
				w.addDisease(new Parasite(name));
			
			Utils.getParentFrame(this).setPanel(new GameMenu(w, 0, false));
		} else if (e.getSource() == _load) {
			File saves = new File("saves");
			saves.mkdir();
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(saves);
			
			if (fc.showOpenDialog(Utils.getParentFrame(this)) == JFileChooser.APPROVE_OPTION) {
				System.out.println("LOAD FROM: " + fc.getSelectedFile());
				// TODO: Load file
				Utils.getParentFrame(this).setPanel(new GameMenu(null, 0, false));
			}
		}
	}
	
	@Override
	public String toString() {
		return Strings.SINGLEPLAYER_MENU;
	}
}
