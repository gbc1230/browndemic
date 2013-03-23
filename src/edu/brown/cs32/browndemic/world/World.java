/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.world;

import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.Serializable;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.region.Region;

/**
 *
 * @author gcarling
 */
public abstract class World implements Serializable{

    //ArrayList of Regions
    protected List<Region> _regions;
    
    //various population stats
    protected int _population, _infected, _dead;

    //Hashtable pairing Region names to their index in _regions
    protected Hashtable<String, Integer> _regIndex;

    //An ArrayList of all diseases present in this world
    protected List<Disease> _diseases;
    
    //An ArrayList keeping track of how many people each disease has won
    protected List<Integer> _killed;

    //Progress towards the cure
    protected List<Double> _cures;
    
    //NOTE: each index of diseases, killed, cures refers to the same disease:
    //i.e. index 2 of each refers to the disease object, how many it has killed,
    //and how far its cure is, respectively
    
 
    
    public World(){
        _regions = new ArrayList<Region>();
        _regIndex = new Hashtable<String, Integer>();
        _diseases = new ArrayList<Disease>();
        _killed = new ArrayList<Integer>();
        _cures = new ArrayList<Double>();
        _dead = 0;
        _infected = 0;
    }

    /**
     * addRegion() puts the Region into _regions, increments _regCount, and adds it to _index
     * @param r the Region to add
     */
    public void addRegion(Region r){
        _regions.add(r);
        _regIndex.put(r.getName(), _regions.size());
    }

    /**
     * addDisease() adds the given Disease to _diseases
     * @param d the Disease to add
     */
    public void addDisease(Disease d){
        _diseases.add(d);
    }

    /**
     * Get the population
     * @return integer population value
     */
    public int getPopulation(){
        return _population;
    }
    
    /**
     * getHealthy() gets the number of healthy people in this world
     * @return integer healthy people value
     */
    public int getHealthy(){
        return _population - _infected;
    }

    /**
     * getInfected() gets the number of infected people in this world (not dead people)
     * @return integer infected value
     */
    public int getInfected(){
        return _infected - _dead;
    }

    /**
     * getDead() gets the number of dead people in this world
     * @return integer dead value
     */
    public int getDead(){
        return _dead;
    }

    public void updateInfected(int inc){
        _infected += inc;
    }
    
    /**
     * Updates the number of people killed by a certain disease
     * @param ind The ID of the disease that killed the people
     * @param inc The number of kills
     */
    public void updateKilled(int ind, int inc){
        int cur = _killed.get(ind);
        _killed.set(ind, cur + inc);
        _dead += inc;
    }
    
    /**
     * Update the cure progress
     */
    public void updateCure(int ind, double add){
        double cur = _cures.get(ind);
        _cures.set(ind, cur + add);
    }
    
    /**
     * Tells me if the game is over, and if it is, what the result is
     * @return 1 if everyone is dead, -1 if the humans have beat the disease,
     * 
     */
    public int isGameOver(int ind){
        if (_cures.get(ind) >= 100.0)
            return -1;
        if (_dead >= _population)
            return 1;
        else return 0;
    }
    
    
}
