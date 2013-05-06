package edu.brown.cs32.browndemic.ui.panels.menus;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.ResourcelessTitleBar;

/**
 * A JPanel that will load the images defined in the constructor.
 * When loading is done the doneLoading() method on the DoneLoadingListener
 * will be called.
 * @author Ben
 *
 */
public class Loading extends UIPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 5754745059440665566L;
	
	private JProgressBar _progress;
	private boolean _useResources;
	
	public Loading(SwingWorker<Void, Void> t) {
		super();
		_useResources = false;
		
		makeUI();
		
		t.addPropertyChangeListener(this);
		t.execute();
	}
	
	public Loading(boolean useResources, SwingWorker<Void, Void> t) {
		super();
		_useResources = useResources;
		
		makeUI();
		
		t.addPropertyChangeListener(this);
		t.execute();
	}
	
	public void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(UI.WIDTH, UI.CONTENT_HEIGHT));
		setBackground(Colors.MENU_BACKGROUND);
		
		add(Box.createRigidArea(new Dimension(0, 225)));
		JLabel loading = new JLabel(Strings.LOADING);
		loading.setForeground(Colors.RED_TEXT);
		loading.setFont(Fonts.BIG_TEXT);
		loading.setAlignmentX(CENTER_ALIGNMENT);
		add(loading);

		_progress = new JProgressBar(0, 100);
		_progress.setValue(0);
		_progress.setMaximumSize(new Dimension((int)(UI.WIDTH/1.5), 40));
		_progress.setPreferredSize(new Dimension((int)(UI.WIDTH/1.5), 40));
		_progress.setForeground(Colors.RED_TEXT);
		_progress.setStringPainted(true);
		add(_progress);
		
		add(Box.createGlue());
	}
	
	@Override
	public void setupForDisplay() {
		if (_useResources) {
			Utils.getParentFrame(this).setTitle(new ResourcelessTitleBar());
			//Utils.getParentFrame(this).defaultTitle();
		} else {
			Utils.getParentFrame(this).setTitle(new ResourcelessTitleBar());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("progress"))
			_progress.setValue((int)evt.getNewValue());
	}
	
	public static class LoadImageWorker extends SwingWorker<Void, Void> {
		private String[] resources;
		private Action _done;
		
		public LoadImageWorker(Action done, String... resources) {
			this.resources = resources;
			_done = done;
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			int total = resources.length;
			for (int i = 0; i < total; i++) {
				if (!Resources.loadImage(resources[i])) {
					System.err.println("Could not load file: " + resources[i]);
					System.exit(1);
				}
				setProgress((i+1) * 100 / total);
			}
			return null;
		}
		
		@Override
		public void done() {
			_done.doAction();
		}
	}
	
	@Override
	public String toString() {
		return Strings.LOADING_MENU;
	}

}
