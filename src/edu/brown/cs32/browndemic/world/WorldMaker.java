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
    
    public static MainWorld makeNewEarthSP(){
        MainWorld w = new MainWorld();
        w.setPopulation(7000000000L);
        //add regions
        return w;
    }
    
    public static ServerWorld makeNewEarthServer(){
        ServerWorld w = new ServerWorld();
        w.setPopulation(7000000000L);
        //add regions
        return w;
    }
   
}