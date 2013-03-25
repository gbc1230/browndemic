package edu.brown.cs32.browndemic.ui;

import java.awt.Container;

public class Utils {
	public static BrowndemicFrame getParentFrame(Container c) {
		if (!(c instanceof BrowndemicFrame)) {
			return getParentFrame(c.getParent());
		}
		return (BrowndemicFrame) c;
	}
}
