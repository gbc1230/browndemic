/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

/**
 *
 * @author Graham
 */
public class GameLeave implements GameData{
	private static final long serialVersionUID = -6020080518063992152L;

	public GameLeave(){
    }
    
    @Override
    public String getID(){
        return "GL";
    }
    
}
