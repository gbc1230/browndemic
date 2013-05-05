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
public abstract class MainWorld implements World, Runnable, Serializable{
	private static final long serialVersionUID = 1774845179387841713L;

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
    //oldInf helps me with giving out points by tracking gains in infection
    //oldKills helps me give out points by tracking gains in kills
    protected List<Long> _kills, _infects, _cures, _oldInfects, _oldKills;
    
    //winners takes care of the winners: if empty at the end of the game,
    //it signifies that all diseases were erradicated
    //benchMarks is for giving out points, tells me how far along they are
    protected List<Integer> _winners, _infectBenchMarks, _killBenchMarks;
    
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
    
    //finals for setting varios speeds
    protected final long _SPEED1 = 333L;
    protected final long _SPEED2 = 200L;
    protected final long _SPEED3 = 100L;
    
    //NOTE: each index of diseases, killed, cures refers to the same disease:
    //i.e. index 2 of each refers to the disease object, how many it has killed,
    //and how far its cure is, respectively
    
 
    //set everything up
    public MainWorld(){
        _regions = new ArrayList<>();
        _regIndex = new HashMap<>();
        _diseases = new ArrayList<>();
        _kills = new ArrayList<>();
        _infects = new ArrayList<>();
        _winners = new ArrayList<>();
        _infectBenchMarks = new ArrayList<>();
        _killBenchMarks = new ArrayList<>();
        _cures = new ArrayList<>();
        _oldInfects = new ArrayList<>();
        _oldKills = new ArrayList<>();
        _sent = new ArrayList<>();
        _cured = new ArrayList<>();
        _news = new ArrayList<>();
        _transmissions = new ArrayList<>();
        _dead = 0;
        _infected = 0;
        _gameOver = false;
        _waitTime = _SPEED1;
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
    
    //various accessors
    
    @Override
    public long getInfected(int d){
        return _infects.get(d);
    }
    
    @Override
    public long getDead(int d){
        return _kills.get(d);
    }
    
    @Override
    public List<Region> getRegions(){
        return _regions;
    }
    
    @Override
    public Disease getDisease(int d){
    	return _diseases.get(d);
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
    
    /**
     * Current percentage to a cure
     */
    @Override
    public double getCurePercentage(int d){
    	return ((double)_cures.get(d) / (double)_cureTotal) * 100.0;
    }
    
    @Override
    public List<String> getNews(){
    	return new ArrayList<>(_news);
    }
    
    //set the speed of the game (wait time between updates)
    public void setSpeed(int time){
        if (time == 1)
            _waitTime = _SPEED1;
        else if (time == 2)
            _waitTime = _SPEED2;
        else if (time == 3)
            _waitTime = _SPEED3;
    }
    
    //tells me is all diseases have chosen (before the game starts)
    @Override
    public boolean allDiseasesPicked(){
        return _allDiseasesPicked;
    }
    
    //pause the game
    @Override
    public void pause(){
        _paused = true;
    }
    
    //unpause the game
    @Override
    public void unpause(){
        _paused = false;
    }
    
    /**
     * For airplanes and stuff
     */
    @Override
    public List<RegionTransmission> getTransmissions(){
        List<RegionTransmission> temp = new ArrayList<>();
        temp.addAll(_transmissions);
        _transmissions.clear();
        return temp;
    }
    
    //list of winner(s) (probably only one but list just in case)
    @Override
    public List<Integer> getWinners(){
        return _winners;
    }
    
    //game over?
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
    
    /**
     * Update news events from regions
     */
    public void updateNews(){
        for (Region r : _regions){
            _news.addAll(r.getNews());
        }
    }
    
    /**
     * Update transmissions from regions (planes and boats)
     */
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
            d.randomPerkEvents();
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
    
    /**
     * gives out points to each disease
     */
    private void givePoints(){
    	for (int i = 0; i < _diseases.size(); i++){
    		Disease d = _diseases.get(i);
    		//points based on infections
    		long newInf = _infects.get(i);
    		long oldInf = _oldInfects.get(i);
    		long infChange = newInf - oldInf;
    		int infBench = _infectBenchMarks.get(i);
    		if (infBench * 10L <= newInf){
    			_infectBenchMarks.set(i, infBench*10);
    			infBench = _infectBenchMarks.get(i);
    		}
    		if (infChange >= infBench){
	    		long val = infChange / infBench;
	    		for (int c = 0; c < val; c++){
	    			d.addPoints(1);
	    		}
	    		_oldInfects.set(i, oldInf + val * infBench);
    		}
    		//points based on kills
    		long newKill = _kills.get(i);
    		long oldKill = _oldKills.get(i);
    		long killChange = newKill - oldKill;
    		int killBench = _killBenchMarks.get(i);
    		if (killBench * 10L <= newKill){
    			_killBenchMarks.set(i, killBench*10);
    			killBench = _killBenchMarks.get(i);
    		}
    		if (killChange >= killBench){
	    		long val = killChange / killBench;
	    		for (int c = 0; c < val; c++){
	    			d.addPoints(1);
	    		}
	    		_oldKills.set(i, oldKill + val * killBench);
    		}
    	}
    }
    
    /**
     * Has no extra requirement here
     */
    public void leaveGame(){
    	
    }
    
    /**
     * Sets up disease related lists
     */
    protected void setupDiseases(){
        for (int i = 0; i < _diseases.size(); i++){
            _cures.add(0L);
            _oldInfects.add(0L);
            _infectBenchMarks.add(10);
            _oldKills.add(0L);
            _killBenchMarks.add(10);
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
    
    /**
     * Basic toString
     */
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