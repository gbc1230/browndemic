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
    
    //An ArrayList keeping track of how many people each disease has killed / has infected currently
    //winners takes care of the winners: if empty at the end of the game,
    //it signifies that all diseases were erradicated
    protected List<Integer> _kills, _infects, _winners;

    //Progress towards the cure
    protected List<Double> _cures;
    
    //which cures have been sent out already
    //NOTE: a cure is the progress towards distributing the cure; a disease
    //is CURED when it is completely erradicated in a region
    protected List<Boolean> _sent, _cured;
    
    //news
    protected List<String> _news;
    
    //whether or not the game is still going on
    protected boolean _gameOver;
    
    //NOTE: each index of diseases, killed, cures refers to the same disease:
    //i.e. index 2 of each refers to the disease object, how many it has killed,
    //and how far its cure is, respectively
    
 
    
    public World(){
        _regions = new ArrayList<>();
        _regIndex = new HashMap<>();
        _diseases = new ArrayList<>();
        _kills = new ArrayList<>();
        _infects = new ArrayList<>();
        _winners = new ArrayList<>();
        _cures = new ArrayList<>();
        _sent = new ArrayList<>();
        _cured = new ArrayList<>();
        _dead = 0;
        _infected = 0;
        _gameOver = false;
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
    
    public List<Region> getRegions(){
        return _regions;
    }
    
    public List<Disease> getDiseases(){
        return _diseases;
    }
    
    public List<Boolean> getCured(){
        return _cured;
    }
    
    public List<String> getNews(){
        List<String> temp = new ArrayList<>();
        temp.addAll(_news);
        _news.clear();
        return temp;
    }
    
    public List<Integer> getWinners(){
        return _winners;
    }
    
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
        run();
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
    
    public void updateNews(){
        for (Region r : _regions){
            _news.addAll(r.getNews());
        }
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
            if (_infects.get(i) == 0 && !_cured.get(i))
                _cured.set(i, true);
        }
    }
    
    /**
     * Lets me know if all diseases have been cured
     * @return boolean
     */
    public boolean allCured(){
        return _infected == 0;
    }
    
    /**
     * Lets me know if all the people in the world are dead
     * @return boolean
     */
    public boolean allKilled(){
        return _dead >= _population;
    }
    
    /**
     * Return a list of winners, just in case there's a tie
     * @return 
     */
    public List<Integer> crownWinners(){
        int cur = _kills.get(0);
        List<Integer> ans = new ArrayList<>();
        ans.add(0);
        for (int i = 1; i < _kills.size(); i++){
            int temp = _kills.get(i);
            if (temp > cur){
                ans.clear();
                ans.add(i);
                cur = temp;
            }
            if (temp == cur)
                ans.add(i);
        }
        return ans;
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
        updateCured();
    }
    
    /**
     * Runs the game
     */
    public void run(){
        while (!_gameOver){
            update();
            if (allCured()){
                _gameOver = true;
                break;
            }
            else if (allKilled()){
                _winners = crownWinners();
                _gameOver = true;
                break;
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
        ans.append("\n");
        return ans.toString();
    }
    
}