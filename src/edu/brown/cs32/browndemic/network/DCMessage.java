/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

/**
 *
 * @author Graham
 */
public class DCMessage implements GameData{
    
    private int _id;
    
    public DCMessage(int id){
        _id = id;
    }
    
    @Override
    public String getID(){
        return "DC";
    }
    
    public int getPlayerID(){
    	return _id;
    }
    
}
