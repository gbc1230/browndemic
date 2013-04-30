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
    
    private String _msg;
    
    public DCMessage(String msg){
        _msg = msg;
    }
    
    @Override
    public String getID(){
        return "DC";
    }
    
    public String getMessage(){
        return _msg;
    }
    
}
