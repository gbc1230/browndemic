package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;
import edu.brown.cs32.browndemic.world.World;

public class StatPanel extends BrowndemicPanel {
	
	private static final long serialVersionUID = -1147093041368550992L;
	
	private int _disease;
	private World _world;
	private JProgressBar _infectivity, _lethality, _visibility, _heat, _cold, _wet, _dry, _medicine;
	private Timer _timer;

	public StatPanel(World world, int disease) {
		super();
		_world = world;
		_disease = disease;
		makeUI();
		_timer = new Timer(1000/5, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_infectivity.setValue((int)_world.getDisease(_disease).getInfectivity());
				_infectivity.setToolTipText(String.format("%.0f/%.0f", 
						_world.getDisease(_disease).getInfectivity(), _world.getDisease(_disease).getMaxInfectivity()));
				_lethality.setValue((int)_world.getDisease(_disease).getLethality());
				_lethality.setToolTipText(String.format("%.0f/%.0f", 
						_world.getDisease(_disease).getLethality(), _world.getDisease(_disease).getMaxLethality()));
				_visibility.setValue((int)_world.getDisease(_disease).getVisibility());
				_visibility.setToolTipText(String.format("%.0f/%.0f", 
						_world.getDisease(_disease).getVisibility(), _world.getDisease(_disease).getMaxVisibility()));
				_heat.setValue((int)_world.getDisease(_disease).getHeatRes());
				_heat.setToolTipText(String.format("%.0f/%d", 
						_world.getDisease(_disease).getHeatRes(), 4));
				_cold.setValue((int)_world.getDisease(_disease).getColdRes());
				_cold.setToolTipText(String.format("%.0f/%d", 
						_world.getDisease(_disease).getColdRes(), 4));
				_wet.setValue((int)_world.getDisease(_disease).getWetRes());
				_wet.setToolTipText(String.format("%.0f/%d", 
						_world.getDisease(_disease).getWetRes(), 4));
				_dry.setValue((int)_world.getDisease(_disease).getDryRes());
				_dry.setToolTipText(String.format("%.0f/%d", 
						_world.getDisease(_disease).getDryRes(), 4));
				_medicine.setValue((int)_world.getDisease(_disease).getMedRes());
				_medicine.setToolTipText(String.format("%.0f/%d", 
						_world.getDisease(_disease).getMedRes(), 4));
			}
		});
		_timer.setInitialDelay(0);
		_timer.start();
	}
	
	private void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		
		
		_infectivity = new JProgressBar(0, (int)_world.getDisease(_disease).getMaxInfectivity());
		JPanel infectivity = createPanel("Infectivity: ", _infectivity);
		
		_lethality = new JProgressBar(0, (int)_world.getDisease(_disease).getMaxLethality());
		JPanel lethality = createPanel("Lethality: ", _lethality);
		
		_visibility = new JProgressBar(0, (int)_world.getDisease(_disease).getMaxVisibility());
		JPanel visibility = createPanel("Visibility: ", _visibility);
		
		_heat = new JProgressBar(0, 4);
		JPanel heat = createPanel("Heat Resistance: ", _heat);
		
		_cold = new JProgressBar(0,4);
		JPanel cold = createPanel("Cold Resistance: ", _cold);
		
		_wet = new JProgressBar(0,4);
		JPanel wet = createPanel("Water Resistance: ", _wet);
		
		_dry = new JProgressBar(0,4);
		JPanel dry = createPanel("Dry Resistance: ", _dry);
		
		_medicine = new JProgressBar(0,4);
		JPanel medicine = createPanel("Medicine Resistance: ", _medicine);
		
		
		Utils.setDefaultLook(_infectivity, _lethality, _visibility, _heat, _cold, _wet, _dry, _medicine);
		
		add(Box.createGlue());
		add(infectivity);
		add(lethality);
		add(visibility);
		add(heat);
		add(cold);
		add(wet);
		add(dry);
		add(medicine);
		add(Box.createGlue());
	}
	
	private JPanel createPanel(String text, JProgressBar pb) {
		JPanel out = new JPanel();
		out.setLayout(new BoxLayout(out, BoxLayout.X_AXIS));
		out.setBackground(Colors.MENU_BACKGROUND);
		out.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		
		JLabel outLabel = new JLabel(text);
		outLabel.setForeground(Colors.RED_TEXT);
		outLabel.setFont(Fonts.NORMAL_TEXT);
		
		pb.setBackground(Colors.MENU_BACKGROUND);
		pb.setForeground(Colors.RED_TEXT);
		
		out.add(outLabel);
		out.add(pb);
		
		return out;
	}
	
	public void stop() {
		_timer.stop();
	}
}
