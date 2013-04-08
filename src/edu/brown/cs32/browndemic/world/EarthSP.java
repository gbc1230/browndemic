/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;

/**
 *
 * @author Graham
 */
public class EarthSP extends Earth{
    
    public EarthSP(){
        super();
    }
    
    @Override
    public World getNextCommand(){
        throw new Error("ERROR: Single Player maps should not throw be"
                + " sending commands.");
    }
    
}
