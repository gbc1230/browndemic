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
    	System.setProperty("sun.java2d.opengl", "true");
		new BrowndemicFrame();
    }

}
