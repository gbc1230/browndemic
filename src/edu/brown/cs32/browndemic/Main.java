/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic;

import edu.brown.cs32.browndemic.ui.BrowndemicFrame;

/**
 *
 * @author gcarling
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
		if (!System.getProperty("os.name").startsWith("Windows"))
			System.setProperty("sun.java2d.opengl", "true");
		System.setProperty("sun.java2d.ddscale", "true");
		System.setProperty("sun.java2d.translaccel", "true");
		System.setProperty("sun.java2d.ddforcevram", "true");
		System.setProperty("sun.java2d.accthreshold", "0");
		new BrowndemicFrame();
    }

}
