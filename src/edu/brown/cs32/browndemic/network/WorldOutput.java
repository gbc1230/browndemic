/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;
import edu.brown.cs32.browndemic.world.MainWorld;

/**
 *
 * @author Graham
 */
public class WorldOutput implements GameData{
    
    //the world that this class stores
    private MainWorld _world;
    
    public WorldOutput(MainWorld w){
        _world = w;
    }
    
    public MainWorld getWorld(){
        return _world;
    }
    
    @Override
    public String getID(){
        return "W";
    }
    
}
