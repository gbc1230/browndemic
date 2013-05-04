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
public abstract class MainWorld implements Serializable, World, Runnable{

    //ArrayList of Regions
    protected List<Region> _regions;
    
    //various population stats - and time for waiting on the loop
    //cureTotal: what I have to reach to get a cure
    protected long _population, _infected, _dead, _waitTime, _cureTotal;
    
    //Hashtable pairing Region names to their index in _regions
    protected HashMap<String, Region> _regIndex;

    //An ArrayList of all diseases present in this world
    protected List<Disease> _diseases;
    
    //ArrayLists keeping track of how many people each disease has killed / has infected currently
    //also the progress towards the cure
    //oldInf helps me with 
    protected List<Long> _kills, _infects, _cures, _oldInf;
    
    //winners takes care of the winners: if empty at the end of the game,
    //it signifies that all diseases were erradicated
    //benchMarks is for giving out points, tells me how far along they are
    protected List<Integer> _winners, _benchMarks;
    
    //which cures have been sent out already
    //NOTE: a cure is the progress towards distributing the cure; a disease
    //is CURED when it is completely erradicated in a region
    protected List<Boolean> _sent, _cured;
    
    //news
    protected List<String> _news;
    
    //whether or not the game is still going on, whether the game is paused
    protected boolean _started, _gameOver, _paused, _allDiseasesPicked;
    
    //for keeping track of transmissions
    protected List<RegionTransmission> _transmissions;
    
    //minimum ticks to a cure
    protected final int _MINCURETICKS = 540;
    
    //how many disease / starting regions have been picked
    protected int _numDiseasesPicked, _numRegionsPicked;
    
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
        _benchMarks = new ArrayList<>();
        _cures = new ArrayList<>();
        _oldInf = new ArrayList<>();
        _sent = new ArrayList<>();
        _cured = new ArrayList<>();
        _news = new ArrayList<>();
        _transmissions = new ArrayList<>();
        _dead = 0;
        _infected = 0;
        _gameOver = false;
        _waitTime = 333L;
        _paused = true;
        _started = false;
        _cureTotal = 0L;
    }
    
    /**
     * gives a certain disease a perk
     * @param dis The disease
     * @param perk The perk
     * @param buy Whether we're buying or selling
     */
    @Override
    public void addPerk(int dis, int perk, boolean buy){
        System.out.println("Adding perk: " + dis + " , " + perk +  ", " + buy);
        Disease d = _diseases.get(dis);
        try{
            if (buy)
                d.buyPerk(perk);
            else
                d.sellPerk(perk);
        }
        catch(IllegalAccessException e){
            
        }
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
    @Override
    public void addDisease(Disease d){
        System.out.println("Adding a disease: " + d.getName());
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
        return _population - _infected - _dead;
    }

    /**
     * getInfected() gets the number of infected people in this world (not dead people)
     * @return integer infected value
     */
    @Override
    public long getInfected(){
        return _infected;
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
    public double getCurePercentage(int d){
    	return ((double)_cures.get(d) / (double)_cureTotal) * 100.0;
    }
    
    @Override
    public List<String> getNews(){
        return _news;
    }
    
    public void setSpeed(int time){
        if (time == 1)
            _waitTime = 333L;
        else if (time == 2)
            _waitTime = 200L;
        else if (time == 3)
            _waitTime = 100L;
    }
    
    @Override
    public boolean allDiseasesPicked(){
        return _allDiseasesPicked;
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
     * Updates the number of people killed by all diseases
     */
    public void updateKilled(){
        long dead = 0;
        List<Long> deaths = new ArrayList<>();
        for (int i = 0; i < _diseases.size(); i++){
            deaths.add(0L);
        }
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
        for (int i = 0; i < _diseases.size(); i++){
            infects.add(0L);
        }
        for (Region r : _regions){
            List<Long> rInfected = r.getInfected();
            for (int i = 0; i < rInfected.size(); i++){
                long d = infects.get(i);
                infects.set(i, rInfected.get(i) + d);
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
        List<Long> cures = new ArrayList<>();
        for (int i = 0; i < _diseases.size(); i++){
            cures.add(0L);
        }
        for (Region r : _regions){
            List<Long> rCures = r.getCures();
            for (int i = 0; i < rCures.size(); i++){
                long c = cures.get(i);
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
    
    public void updateTransmissions(){
        for (Region r : _regions){
            _transmissions.addAll(r.getTransmissions());
        }
    }
    
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
            if (_cures.get(i) >= _cureTotal && _sent.get(i)){
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
     * Gives each virus a chance to randomly mutate
     */
    public void mutateDiseases(){
        for (Disease d : _diseases){
            d.buyRandomPerk();
        }
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
    
    //gives out points to each disease
    private void givePoints(){
    	for (int i = 0; i < _diseases.size(); i++){
    		Disease d = _diseases.get(i);
    		long newInf = _infects.get(i);
    		long oldInf = _oldInf.get(i);
    		long change = newInf - oldInf;
    		int bench = _benchMarks.get(i);
    		if (bench * 10L <= newInf){
    			_benchMarks.set(i, bench*10);
    			bench = _benchMarks.get(i);
    		}
    		if (change > bench){
	    		long val = change / bench;
	    		for (int c = 0; c < val; c++){
	    			int rand = (int)(Math.random() * 2);
	    			if (rand == 0)
	    				d.addPoints(2);
	    			else 
						d.addPoints(3);
	    		}
	    		_oldInf.set(i, oldInf + val * bench);
    		}
    	}
    }
    
    //sets up disease related lists
    protected void setupDiseases(){
        for (int i = 0; i < _diseases.size(); i++){
            _cures.add(0L);
            _oldInf.add(0L);
            _benchMarks.add(10);
            _kills.add(0L);
            _infects.add(0L);
            _sent.add(false);
            _cured.add(false);
        }
    }
    
    /**
     * Updates everything necessary from regions
     */
    public void update(){
        updateRegions();
        mutateDiseases();
        updateKilled();
        updateInfected();
        updateCures();
        updateNews();
        updateTransmissions();
        givePoints();
        checkCures();
        updateCured();
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