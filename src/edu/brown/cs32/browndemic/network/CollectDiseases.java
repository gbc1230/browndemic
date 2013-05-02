/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

/**
 *
 * @author Graham
 */
public class CollectDiseases implements GameData{
	
	//the id in the game of this disease
	private int _id;
 
    public CollectDiseases(int id){
        _id = id;
    }
    
    @Override
    public String getID(){
        return "CD";
    } 
    
    public int getDiseaseID(){
    	return _id;
    }
    
}
