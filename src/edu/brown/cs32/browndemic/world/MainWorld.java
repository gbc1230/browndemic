/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.region.Region;
import edu.brown.cs32.browndemic.region.RegionTransmission;

/**
 *
 * @author gcarling
 */
public class MainWorld implements Serializable, World{

    //ArrayList of Regions
    protected List<Region> _regions;
    
    //various population stats - and time for waiting on the loop
    protected long _population, _infected, _dead, _waitTime;

    //Hashtable pairing Region names to their index in _regions
    protected HashMap<String, Region> _regIndex;

    //An ArrayList of all diseases present in this world
    protected List<Disease> _diseases;
    
    //ArrayLists keeping track of how many people each disease has killed / has infected currently
    protected List<Long> _kills, _infects;
    
        //winners takes care of the winners: if empty at the end of the game,
    //it signifies that all diseases were erradicated
    protected List<Integer> _winners;

    //Progress towards the cure
    protected List<Double> _cures;
    
    //which cures have been sent out already
    //NOTE: a cure is the progress towards distributing the cure; a disease
    //is CURED when it is completely erradicated in a region
    protected List<Boolean> _sent, _cured;
    
    //news
    protected List<String> _news;
    
    //whether or not the game is still going on
    protected boolean _gameOver, _paused;
    
    //for keeping track of transmissions
    protected List<RegionTransmission> _transmissions;
    
    //NOTE: each index of diseases, killed, cures refers to the same disease:
    //i.e. index 2 of each refers to the disease object, how many it has killed,
    //and how far its cure is, respectively
    
 
    
    public MainWorld(){
        _regions = new ArrayList<>();
        _regIndex = new HashMap<>();
        _diseases = new ArrayList<>();
        _kills = new ArrayList<>();
        _infects = new ArrayList<>();
        _winners = new ArrayList<>();
        _cures = new ArrayList<>();
        _sent = new ArrayList<>();
        _cured = new ArrayList<>();
        _transmissions = new ArrayList<>();
        _dead = 0;
        _infected = 0;
        _gameOver = false;
        _waitTime = 333L;
        _paused = true;
    }
    
    /**
     * gives a certain disease a perk
     * @param dis The disease
     * @param perk The perk
     * @param buy Whether we're buying or selling
     */
    @Override
    public void addPerk(int dis, int perk, boolean buy){
        System.out.println("Got perk: " + dis + ", " + perk + ", " + buy);
        /*
        Disease d = _diseases.get(dis);
        try{
            if (buy)
                d.buyPerk(perk);
            else
                d.sellPerk(perk);
        }
        catch(IllegalAccessException e){
            
        }*/
    }
    
    /**
     * addRegion() puts the Region into _regions and adds it to _regIndex
     * @param r the Region to add
     */
    public void addRegion(Region r){
        _regions.add(r);
        _regIndex.put(r.getName(), r);
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
    @Override
    public long getPopulation(){
        return _population;
    }

    /**
     * getHealthy() gets the number of healthy people in this world
     * @return integer healthy people value
     */
    @Override
    public long getHealthy(){
        return _population - _infected;
    }

    /**
     * getInfected() gets the number of infected people in this world (not dead people)
     * @return integer infected value
     */
    @Override
    public long getInfected(){
        return _infected - _dead;
    }

    /**
     * getDead() gets the number of dead people in this world
     * @return integer dead value
     */
    @Override
    public long getDead(){
        return _dead;
    }
    
    @Override
    public List<Region> getRegions(){
        return _regions;
    }
    
    @Override
    public Region getRegion(String name){
        return _regIndex.get(name);
    }
    
    @Override
    public Region getRegion(int id) {
    	return _regions.get(id-1);
    }
    
    @Override
    public List<Disease> getDiseases(){
        return _diseases;
    }
    
    @Override
    public List<Boolean> getCured(){
        return _cured;
    }
    
    @Override
    public List<String> getNews(){
        return _news;
    }
    
    public void setSpeed(int time){
        if (time == 1)
            _waitTime = 500L;
        else if (time == 2)
            _waitTime = 333L;
        else if (time == 3)
            _waitTime = 175L;
    }
    
    public void pause(){
        _paused = true;
    }
    
    public void unpause(){
        _paused = false;
    }
    
    @Override
    public List<RegionTransmission> getTransmissions(){
        List<RegionTransmission> temp = new ArrayList<>();
        temp.addAll(_transmissions);
        _transmissions.clear();
        return temp;
    }
    
    @Override
    public List<Integer> getWinners(){
        return _winners;
    }
    
    @Override
    public boolean isGameOver(){
        return _gameOver;
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
        for (Region r : _regions){
            _population += r.getPopulation();
        }
        _paused = false;
        run();
    }
    
    /**
     * Updates the number of people killed by all diseases
     */
    public void updateKilled(){
        long dead = 0;
        List<Long> deaths = new ArrayList<>();
        for (Region r : _regions){
            List<Long> rKills = r.getKilled();
            for (int i = 0; i < rKills.size(); i++){
                long d = deaths.get(i);
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
        long infected = 0L;
        List<Long> infects = new ArrayList<>();
        for (Region r : _regions){
            List<Long> rInfected = r.getInfected();
            for (int i = 0; i < rInfected.size(); i++){
                long d = infects.get(i);
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
    /*public void updateCures(){
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
    
    public void updateNews(){
        for (Region r : _regions){
            _news.addAll(r.getNews());
        }
    }*/
    
    /**
     * Tells each region to start curing a disease
     */
    public void sendCures(int d){
        for (Region r : _regions){
            r.cure(_diseases.get(d));
        }
    }
    
    /**
     * Lets me know which cures have been sent
     */
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
            if (_infects.get(i) == 0 && !_cured.get(i))
                _cured.set(i, true);
        }
    }
    
    /**
     * Lets me know if all diseases have been cured
     * @return boolean
     */
    @Override
    public boolean allCured(){
        return _infected == 0;
    }
    
    /**
     * Lets me know if all the people in the world are dead
     * @return boolean
     */
    @Override
    public boolean allKilled(){
        return _dead >= _population;
    }
    
    /**
     * Return a list of winners, just in case there's a tie
     */
    public void crownWinners(){
        long cur = _kills.get(0);
        List<Integer> ans = new ArrayList<>();
        ans.add(0);
        for (int i = 1; i < _kills.size(); i++){
            long temp = _kills.get(i);
            if (temp > cur){
                ans.clear();
                ans.add(i);
                cur = temp;
            }
            if (temp == cur)
                ans.add(i);
        }
        _winners = ans;
    }
    
    /**
     * Updates everything necessary from regions
     */
    public void update(){
        updateRegions();
        updateKilled();
        updateInfected();
        //updateCures();
        checkCures();
        updateCured();
    }
    
    /**
     * Runs the game
     */
    public void run(){
        while (!_gameOver){
            if (!_paused){
                long start = System.currentTimeMillis();
                update();
                if (allCured()){
                    _gameOver = true;
                    break;
                }
                else if (allKilled()){
                    crownWinners();
                    _gameOver = true;
                    break;
                }
                long end = System.currentTimeMillis();
                long offset = end - start;
                try{
                    Thread.sleep(_waitTime - offset);
                }
                catch(InterruptedException e){
                    System.out.println("Couldn't sleep...");
                }
            }
        }
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
        return ans.toString();
    }
    
}