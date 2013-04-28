/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;

/**
 * For generating various types of Worlds
 * @author Graham
 */
public class WorldMaker{
    
    private static void addEarthRegions(MainWorld w){
        //add regions
    }
    
    public static MainWorld makeNewEarthSP(){
        MainWorld w = new MainWorld();
        addEarthRegions(w);
        return w;
    }
    
    public static ServerWorld makeNewEarthServer(){
        ServerWorld w = new ServerWorld();
        addEarthRegions(w);
        return w;
    }
   
}