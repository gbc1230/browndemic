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
	private static final long serialVersionUID = -3317533768431261843L;
	
	private String _name;
	private int _id;
    
    public DCMessage(String name, int id){
        _name = name;
        _id = id;
    }
    
    @Override
    public String getID(){
        return "DC";
    }
    
    public String getName(){
    	return _name;
    }
    
    public int getPlayerID(){
    	return _id;
    }
    
}
