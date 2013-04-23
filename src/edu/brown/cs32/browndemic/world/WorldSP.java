/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;

/**
 * Single player world
 * @author Graham
 */
public class WorldSP extends MainWorld{
    
    public WorldSP(){
        super();
    }
    
    @Override
    public void removeDisease(int id){
        throw new Error("ERROR: Single player maps can't remove diseases.");
    }
    
    @Override
    public MainWorld getNextCommand(){
        throw new Error("ERROR: Single player maps don't send commands.");
    }
    
}
