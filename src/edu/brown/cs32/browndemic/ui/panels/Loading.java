package edu.brown.cs32.browndemic.ui.panels;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;

/**
 * A JPanel that will load the images defined in the constructor.
 * When loading is done the doneLoading() method on the DoneLoadingListener
 * will be called.
 * @author Ben
 *
 */
public class Loading extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 5754745059440665566L;
	
	private JProgressBar _progress;
	private DoneLoadingListener _doneLoadingListener;
	
	public Loading(DoneLoadingListener d, String... resources) {
		_doneLoadingListener = d;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(400, 600));
		JLabel l = new JLabel();
		l.setText("Loading...");
		add(l);
		
		_progress = new JProgressBar(0, 100);
		_progress.setValue(0);
		add(_progress);
		
		Task t = new Task();
		t.addPropertyChangeListener(this);
		t.execute();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("progress"))
			_progress.setValue((int)evt.getNewValue());
	}
	
	private class Task extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			int total = Images.MENU_IMAGES.length;
			for (int i = 0; i < total; i++) {
				if (!Resources.loadImage(Images.MENU_IMAGES[i])) {
					System.err.println("Could not load file: " + Images.MENU_IMAGES[i]);
					System.exit(1);
				}
				setProgress((i+1) * 100 / total);
			}
			return null;
		}
		
		@Override
		public void done() {
			_doneLoadingListener.doneLoading();
		}
	}

}
