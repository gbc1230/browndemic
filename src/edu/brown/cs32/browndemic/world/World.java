/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.world;

import java.util.List;
import java.util.HashMap;
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
    protected long _population, _infected, _dead;

    //Hashtable pairing Region names to their index in _regions
    protected HashMap<String, Integer> _regIndex;

    //An ArrayList of all diseases present in this world
    protected List<Disease> _diseases;
    
    //An ArrayList keeping track of how many people each disease has killed / infected
    protected List<Integer> _kills, _infects;

    //Progress towards the cure
    protected List<Double> _cures;
    
    //which cures have been sent out already
    protected List<Boolean> _sent, _cured;
    
    //whether or not the game is still going on
    protected boolean _running;
    
    //NOTE: each index of diseases, killed, cures refers to the same disease:
    //i.e. index 2 of each refers to the disease object, how many it has killed,
    //and how far its cure is, respectively
    
 
    
    public World(){
        _regions = new ArrayList<>();
        _regIndex = new HashMap<>();
        _diseases = new ArrayList<>();
        _kills = new ArrayList<>();
        _cures = new ArrayList<>();
        _sent = new ArrayList<>();
        _cured = new ArrayList<>();
        _dead = 0;
        _infected = 0;
        _running = false;
    }

    /**
     * Gets the next world to send out: used only for MP maps
     * @return The next world to send out
     */
    public abstract World getNextCommand();
    
    /**
     * addRegion() puts the Region into _regions and adds it to _regIndex
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
        int id = _diseases.size();
        _diseases.add(d);
        d.setID(id);
    }

    /**
     * Get the population
     * @return integer population value
     */
    public long getPopulation(){
        return _population;
    }
    
    /**
     * getHealthy() gets the number of healthy people in this world
     * @return integer healthy people value
     */
    public long getHealthy(){
        return _population - _infected;
    }

    /**
     * getInfected() gets the number of infected people in this world (not dead people)
     * @return integer infected value
     */
    public long getInfected(){
        return _infected - _dead;
    }

    /**
     * getDead() gets the number of dead people in this world
     * @return integer dead value
     */
    public long getDead(){
        return _dead;
    }
    
    /**
     * Once the world has been initialized, starts the world
     */
    public void start(){
        for (int i = 0; i < _diseases.size(); i++){
            _cures.add(0.0);
            _sent.add(false);
            _cured.add(false);
        }
        _running = true;
    }
    
    /**
     * Updates the number of people killed by all diseases
     */
    public void updateKilled(){
        int dead = 0;
        List<Integer> deaths = new ArrayList<>();
        for (Region r : _regions){
            List<Integer> rKills = r.getKilled();
            for (int i = 0; i < rKills.size(); i++){
                int d = deaths.get(i);
                deaths.set(i, rKills.get(i) + d);
                dead += rKills.get(i);
            }
        }
        _kills = deaths;
        _dead = dead;
    }
    
    
    /**
     * Updates the number of infected people
     */
    public void updateInfected(){
        int infected = 0;
        List<Integer> infects = new ArrayList<>();
        for (Region r : _regions){
            List<Integer> rInfected = r.getInfected();
            for (int i = 0; i < rInfected.size(); i++){
                int d = infects.get(i);
                infects.set(i, infects.get(i) + d);
                infected += rInfected.get(i);
            }
        }
        _infects = infects;
        _infected = infected;
    }
    
    /**
     * Update the cure progress for every disease
     */
    public void updateCures(){
        List<Double> cures = new ArrayList<>();
        for (Region r : _regions){
            List<Double> rCures = r.getCures();
            for (int i = 0; i < rCures.size(); i++){
                double c = cures.get(i);
                cures.set(i, c + rCures.get(i));
            }
        }
        _cures = cures;
    }
    
    /**
     * Tells each region to start curing a disease
     */
    public void sendCures(int d){
        for (Region r : _regions){
            r.cure(d);
        }
    }
    
    public void checkCures(){
        for (int i = 0; i < _cures.size(); i++){
            if (_cures.get(i) >= 100.0 && _sent.get(i)){
                sendCures(i);
                _sent.set(i, true);
            }
        }
    }
    
    /**
     * Updates all the regions
     */
    public void updateRegions(){
        for (Region r : _regions)
            r.update();
    }

    /**
     * Updates which diseases have been cured
     */
    public void updateCured(){
        for (int i = 0; i < _diseases.size(); i++){
            if (!_cured.get(i)){
                boolean cured = true;
                for (int j = 0; j < _regions.size(); j++){
                    List<Boolean> curedList = _regions.get(j).getCured();
                    if (!curedList.get(i)){
                        cured = false;
                        break;
                    }
                }
                _cured.set(i, cured);
            }
        }
    }
    
    public boolean allCured(){
        boolean cured = true;
        for (boolean c : _cured){
            if (!c){
                cured = false;
                break;
            }
        }
        return cured;
    }
    
    /**
     * Updates everything necessary from regions
     */
    public void update(){
        updateRegions();
        updateKilled();
        updateInfected();
        updateCures();
        checkCures();
        
    }
    
    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder();
        ans.append("Population: ").append(_population).append("\n");
        ans.append("Infected: ").append(_infected).append("\n");
        ans.append("Dead: ").append(_dead).append("\n");
        ans.append("Diseases: ");
        for (Disease d : _diseases){
            ans.append(d.getID()).append(", ");
        }
        ans.append("\nRegions: ");
        for (Region r : _regions){
            ans.append(r.getName()).append(", ");
        }
        ans.append("\n");
        return ans.toString();
    }
    
}