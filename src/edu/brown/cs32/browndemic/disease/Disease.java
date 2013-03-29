package disease;

/**
 *
 * @author bkoatz
 */
public abstract class Disease {
    
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
    protected double _heatResistance;
    
    //A double reflecting how resistant to cold this disease is
    protected double _coldResistance;
    
    //A double reflecting how resistant to wetness this disease is
    protected double _wetResistance;
    
    //A double reflecting how resistant to dryness this disease is
    protected double _dryResistance;
    
    //A double reflecting how resistant to medicine this disease is
    protected double _medResistance;
    
    //A double reflecting how many points this disease has
    protected int _points;
    
    //An Array of Perks available to this disease
    protected Perk[] _perks;
    
    /**
     * setID(int newID) sets the _id of this Disease to newID
     */
    public void setID(int newID){
     
      this._id = newID;
      
    }
    
    /**
     * addPoint() gives this Disease another point
     */
    public void addPoint(){
     
      this._points++;
      
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
     * getPoints() returns the number of points this disease has accrued
     * @return _points
     */
    public int getPoints(){
        return _points;
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
        
        this._perks[perkID].setBought(true);
        Perk boughtPerk = this._perks[perkID];
        
        for(Perk p: boughtPerk.getNext()){
            
            this._perks[p.getID()].setAvailability(true);
            
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
    }

    //IMPORTANT PLEASE READ
    //The following code relies on the uniqueness of the String ID

    /**
     * toString gets a String of the unique ID, the infectivity, the mortality
     * @return the id, name, infectivity, lethality and visibility of this disease
     */
    @Override
    public String toString(){
        return _id + ", name: " + _name + ", infectivity: " + _infectivity + ", lethality: " +
                _lethality + ", visibility: " + _visibility;
    }

}
