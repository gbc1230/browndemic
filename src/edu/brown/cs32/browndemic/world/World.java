/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.world;

import java.util.Hashtable;
import java.util.ArrayList;
import java.io.Serializable;

import edu.brown.cs32.browndemic.disease.Disease;

/**
 *
 * @author ckilfoyl
 */
public abstract class World implements Serializable{
    //ArrayList of Regions
    private ArrayList<Region> _regions;

    //Hashtable pairing Region names to their index in _regions
    private Hashtable<String, Integer> _regIndex;

    //A count of how many Regions are in this world (used for indexing while adding)
    private int _regCount;

    //An ArrayList of all diseases present in this world
    private ArrayList<Disease> _diseases;

    /**
     * addRegion() puts the Region into _regions, increments _regCount, and adds it to _index
     * @param r the Region to add
     */
    public void addRegion(Region r){
        _regions.add(r);
        _regCount++;
        _regIndex.put(r.getName(), _regCount);
    }

    /**
     * addDisease() adds the given Disease to _diseases
     * @param d the Disease to add
     */
    public void addDisease(Disease d){
        _diseases.add(d);
    }

    /**
     * getHealthy() gets the number of healthy people in this world
     * @return
     */
    public int getHealthy(){
        int count = 0;
        for(Region r : _regions)
            count = count + r.getHealthy();
        return count;
    }

    /**
     * getInfected() gets the number of infected people in this world
     * @return
     */
    public int getInfected(){
        int count = 0;
        for(Region r : _regions)
            count = count + r.getInfected();
        return count;
    }

    /**
     * getDead() gets the number of dead people in this world
     * @return
     */
    public int getDead(){
        int count = 0;
        for(Region r : _regions)
            count = count + r.getDead();
        return count;
    }
}
