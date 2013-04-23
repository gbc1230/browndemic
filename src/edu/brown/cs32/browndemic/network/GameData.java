/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;
import java.io.Serializable;

/**
 *
 * @author Graham
 */
public interface GameData extends Serializable{
    
    //makes sure all GameData have an ID to recognize them by
    public String getID();
    
}
