package edu.brown.cs32.browndemic.disease;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * This is the abstract disease class which contains all the necessary
 * attributes for a disease to have, all the Accessor/Mutators needed
 * and methods relating to the perks the disease has
 *
 * It leaves implementation of selling perks to subclasses
 * 
 * @author bkoatz
 */
public abstract class Disease implements Serializable{

    //SerialVersionUID for this disease
	private static final long serialVersionUID = 2897681652961462017L;

	//The user-input string name of the disease
    protected String _name;
    
    //The unique integer identifier of the disease
    protected int _id;

    //A double reflecting how infectious this disease is
    protected double _infectivity;

    //A double reflecting how deadly this disease is
    protected double _lethality;
    
    //A double reflecting how visible this disease is
    protected double _visibility;
    
    //A double reflecting how resistant to heat this disease is
    protected double _heatResistance = 1;
    
    //A double reflecting how resistant to cold this disease is
    protected double _coldResistance = 1;
    
    //A double reflecting how resistant to wetness this disease is
    protected double _wetResistance = 1;
    
    //A double reflecting how resistant to dryness this disease is
    protected double _dryResistance = 1;
    
    //A double reflecting how resistant to medicine this disease is
    protected double _medResistance = 1;
    
    //A double reflecting the ability of this disease to be
    //transmitted via water
    protected double _waterTrans;
    
    //A double reflecting the ability of this disease to be
    //transmitted via air
    protected double _airTrans;
    //Maximum infectivity
    protected double MAX_INFECTIVITY;
    //Maximum lethality
    protected double MAX_LETHALITY;
    //Maxium visibility
    protected double MAX_VISIBILITY;
    
    
    //A double reflecting how many points this disease has
    protected int _points;
    
    //An Array of Perks available to this disease
    protected Perk[] _perks;
    
    //These are stats about the disease that will be useful to display
    //at the end of the game
    protected int _numPerksBought;
    protected int _numPerksSold;
    protected int _numPointsEarned;
    protected int _numPointsUsed;
    protected int _numRandomPerksGot;
    
    /**
     * setID(int newID) sets the _id of this Disease to newID
     */
    public void setID(int newID){
     
      this._id = newID;
      
    }
    
    /**
     * addPoint() gives this Disease another point
     */
    public void addPoints(int points){
     
      this._points+= points;
      this._numPointsEarned += points;
      
    }
    
    /**
     * getID() returns the unique integer ID of this Disease
     * @return _id
     */ 
    public int getID(){
      
      return this._id;
      
    }
    
    /**
     * getInfectivity() returns the double infectivity of this Disease
     * @return _infectivity
     */ 
    public double getInfectivity(){
      
      return this._infectivity;
      
    }
    
    /**
     * getLethality() returns the double lethality of this Disease
     * @return _lethality
     */ 
    public double getLethality(){
      
      return this._lethality;
      
    }
    
    /**
     * getVisibility() returns the double Visibility of this Disease
     * @return _visibility
     */ 
    public double getVisibility(){
      
      return this._visibility;
      
    }
    
    /**
     * getName() returns the String name for this disease
     * @return _name
     */
    public String getName(){
        
        return this._name;
        
    }
    
    /**
     * getHeatRes() returns the heat resistance of this disease
     * @return _heatResistance
     */
    public double getHeatRes(){
        
        return this._heatResistance;
        
    }
    
    /**
     * getColdRes() returns the cold resistance of this disease
     * @return _coldResistance
     */
    public double getColdRes(){
        
        return this._coldResistance;
        
    }
    
    /**
     * getWetRes() returns the wetness resistance of this disease
     * @return _wetResistance
     */
    public double getWetRes(){
        
        return this._wetResistance;
        
    }
    
    /**
     * getDryRes() returns the dryness resistance of this disease
     * @return _dryResistance
     */
    public double getDryRes(){
        
        return this._dryResistance;
        
    }
    
    /**
     * getMedRes() returns the resistance to medicine of this disease
     * @return _medResistance
     */
    public double getMedRes(){
        
        return this._medResistance;
        
    }
    
    /**
     * getWaterTrans() returns the transmissibility of this disease via water
     * @return _waterTrans
     */
    public double getWaterTrans(){
        
        return this._waterTrans;
        
    }
    
    /**
     * getAirTrans() returns the transmissibility of this disease via air
     * @return _airTrans
     */
    public double getAirTrans(){
        
        return this._airTrans;
        
    }
    
    /**
     * getPoints() returns the number of points this disease has accrued
     * @return _points
     */
    public int getPoints(){
        return _points;
    }
    
    /**
     * Get all available perks for this disease
     * @return a list of the perks available to this disease
     */
    public List<Perk> getAvailablePerks(){
        List<Perk> ans = new ArrayList<Perk>();
        for (Perk p : _perks){
            if (p.isAvail() && !p.isOwned())
                ans.add(p);
        }
        return ans;
    }

    /**
     * Get all owned perks for this disease
     * @return a list of the perks owned by this disease
     */
    public List<Perk> getOwnedPerks(){
        List<Perk> ans = new ArrayList<Perk>();
        for (Perk p : _perks){
            if (p.isOwned())
                ans.add(p);
        }
        return ans;
    }

    /**
     * Get all purchasable perks for this disease
     * @return a list of the perks that can be purchased by this disease
     */
    public List<Perk> getPurchasablePerks(){
        List<Perk> ans = new ArrayList<Perk>();
        for (Perk p : _perks){
            if (p.isAvail() && !p.isOwned() && p.getCost() <= this._points)
                ans.add(p);
        }
        return ans;
    }
    
    /**
     * gets the array of all perks this disease can have
     * @return the array of perks this disease can own at some point
     */
    public Perk[] getPerks(){

        return this._perks;

    }
  
    /**
     * buyPerk(int perkID) buys a perk, sets the availability of appropriate
     * perks to true, and appropriately changes the qualities of this disease
     * to match
     */
    public void buyPerk(int perkID) throws IllegalAccessException{

        if(!this._perks[perkID].isAvail()
                || this._perks[perkID].getCost() > this._points){

            throw new IllegalAccessException();

        }
        
        this._perks[perkID].setOwned(true);
        Perk boughtPerk = this._perks[perkID];

        for(Integer p: boughtPerk.getNext()){

            this._perks[p].setAvailability(true);

        }

        this._infectivity += boughtPerk.getInf();
        this._lethality += boughtPerk.getLeth();
        this._visibility += boughtPerk.getVis();
        this._heatResistance += boughtPerk.getHeatRes();
        this._coldResistance += boughtPerk.getColdRes();
        this._wetResistance += boughtPerk.getWetRes();
        this._dryResistance += boughtPerk.getDryRes();
        this._medResistance += boughtPerk.getMedRes();
        this._points -= boughtPerk.getCost();
        this._numPointsUsed+=boughtPerk.getCost();
        this._numPerksBought++;
    }

    /**
     * buyPerkWithoutPay(int perkID) buys a perk without paying for it, sets the
     * availability of appropriate perks to true, and appropriately changes the
     * qualities of this disease to match
     */
    public void buyPerkWithoutPay(int perkID) throws IllegalAccessException{
    	
        if(!this._perks[perkID].isAvail()){

            throw new IllegalAccessException();

        }

        this._perks[perkID].setOwned(true);
        Perk boughtPerk = this._perks[perkID];

        for(Integer p: boughtPerk.getNext()){

            this._perks[p].setAvailability(true);

        }

        this._infectivity += boughtPerk.getInf();
        this._lethality += boughtPerk.getLeth();
        this._visibility += boughtPerk.getVis();
        this._heatResistance += boughtPerk.getHeatRes();
        this._coldResistance += boughtPerk.getColdRes();
        this._wetResistance += boughtPerk.getWetRes();
        this._dryResistance += boughtPerk.getDryRes();
        this._medResistance += boughtPerk.getMedRes();
        this._waterTrans += boughtPerk.getWaterTrans();
        this._airTrans += boughtPerk.getAirTrans();
        this._numRandomPerksGot++;
        
    }

    /**
     * lets a caller set everything in this disease to 0
     * (return it to factory settings) incase a user
     * quits
     */
    public void die(){
    	
    	this._infectivity = 0;
    	this._lethality = 0;
    	this._visibility = 0;
    	this._heatResistance = 0;
    	this._coldResistance = 0;
    	this._wetResistance = 0;
    	this._dryResistance = 0;
    	this._medResistance = 0;
    	this._waterTrans = 0;
    	this._airTrans = 0;
    	
    }
    
    /**
     * gets the number of perks bought over this disease's lifespan
     * @return _numPerksBought
     */
    public int getNumPerksBought(){
    	return this._numPerksBought;
    }
    
    /**
     * gets the number of perks sold over this disease's lifespan
     * @return _numPerksSold
     */
    public int getNumPerksSold(){
    	return this._numPerksSold;
    }
    
    /**
     * gets the number of points earned over this disease's lifespan
     * @return _numPointsEarned
     */
    public int getNumPointsEarned(){
    	return this._numPointsEarned;
    }
    
    /**
     * gets the number of points used over this disease's lifespan
     * @return _numPointsUsed
     */
    public int getNumPointsUsed(){
    	return this._numPointsUsed;
    }
    
    /**
     * gets the number of random perks accrued over this disease's 
     * lifespan
     * @return _numRandomPerksGot
     */
    public int getNumRandomPerksGot(){
    	return this._numRandomPerksGot;
    }


    /**
     * gets the maximum infectivity a disease can have if it buys all
     * infectivity-positive perks
     * @return MAX_INFECTIVITY
     */
    public abstract double getMaxInfectivity();

    /**
     * gets the maximum letahlity a disease can have if it buys all
     * lethality-positive perks
     * @return MAX_LETHALITY
     */
    public abstract double getMaxLethality();

    /**
     * gets the maximum visibility a disease can have if it buys all
     * visbility-positive perks
     * @return MAX_LETHALITY
     */
    public abstract double getMaxVisibility();

    /**
     * gets the starting infectivity a disease has
     * @return START_INFECTIVITY
     */
    public abstract double getStartInfectivity();

    /**
     * gets the starting letahlity a disease has
     * @return START_LETHALITY
     */
    public abstract double getStartLethality();

    /**
     * gets the starting visibility a disease has
     * @return START_LETHALITY
     */
    public abstract double getStartVisibility();

    /**
     * The cumulative method that sells every perk that is exlusively relies on
     * perkID to be owned at the time of selling.
     * @param perkID                           the ID of the root perk being sold
     * @throws java.lang.IllegalAccessException if you are selling an unowned perk
     */
    public abstract void sellCumPerk(int perkID) throws IllegalAccessException;

    /**
     * Sells only this individual perk
     * @param perkID                           the ID of the perk being sold
     * @throws java.lang.IllegalAccessException if you are selling an unowned perk,
     *                                          or a perk whos 'next' perks are
     *                                          owned
     */
    public abstract void sellPerk(int perkID) throws IllegalAccessException;

    /**
     * Returns the sellable perks this disease has. It will be a list of the owned
     * perks for non-viral diseases, because it doesn't cost those diseases money
     * to sell perks
     * @return the perks sellable by this disease
     */
    public abstract List<Perk> getSellablePerks();

    /**
     * A method that has a random chance of buying a new perk for a disease and
     * giving it random points. The likelihood of a disease having either of
     * these things happen is: Virus>Bacteria>Parasite
     */
    public abstract void randomPerkEvents();
    
    //IMPORTANT PLEASE READ
    //The following code relies on the uniqueness of the String ID

    /**
     * toString gets a String of the unique ID, the infectivity, the mortality
     * @return the id, name, infectivity, lethality and visibility of this disease
     */
    @Override
    public String toString(){
        return this._id + ", name: " + this._name + ", infectivity: " + this._infectivity + ", lethality: " +
                this._lethality + ", visibility: " + this._visibility;
    }

}
