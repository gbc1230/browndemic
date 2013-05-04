/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

import edu.brown.cs32.browndemic.world.ServerWorld;

/**
 *
 * @author Graham
 */
public class CollectDiseases implements GameData{
	private static final long serialVersionUID = 4428289060200550669L;
	
	//the id in the game of this disease
	private int _id;
	//the first version of the world for the clients
	private ServerWorld _world;
 
    public CollectDiseases(int id, ServerWorld world){
        _id = id;
        _world = world;
    }
    
    @Override
    public String getID(){
        return "CD";
    } 
    
    public int getDiseaseID(){
    	return _id;
    }
    
    public ServerWorld getWorld(){
    	return _world;
    }
    
}
