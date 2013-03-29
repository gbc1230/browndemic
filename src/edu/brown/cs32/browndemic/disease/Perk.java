package disease;
import java.util.ArrayList;

/**
 *@author bkoatz 
 */
public class Perk{
 
  //the integer cost of this perk
  private int _cost;
  
  //the unique integer ID of this perk
  private int _id;
  
  //the double change that this perk will provide to infectivity
  private double _infectivityChange;
  
  //the double change that this perk will provide to lethality
  private double _lethalityChange;
  
  //the double change that this perk will provide to visibility
  private double _visibilityChange;
  
  //the double change that this perk will provide to heat resistance
  private double _heatResChange;
  
  //the double change that this perk will provide to cold resistance
  private double _coldResChange;
  
  //the double change that this perk will provide to wetness resistance
  private double _wetResChange;
  
  //the double change that this perk will provide to dryness resistance
  private double _dryResChange;
  
  //the double change that this perk will provide to resistance to medicine
  private double _medResChange;
  
  //the boolean availability of this perk to be purchased
  private boolean _availability = false;
  
  //the boolean saying whether or not this perk has been purchased
  private boolean _bought = false;
  
  //the ArrayList of perks that this perk's purchase makes available
  private ArrayList<Perk> _nextPerks;
  
  //constuctor: sets cost, change in infectivity/lethality/visibility and
  //the perks that, when this perk is bought, become available for purchase
  public Perk(int tempcost, double tempinf, double templeth, double tempvis,
              double tempheat, double tempcold, double tempwet, double tempdry,
              double tempmed, ArrayList<Perk> tempnext){
    
    this._cost = tempcost;
    this._infectivityChange = tempinf;
    this._lethalityChange = templeth;
    this._visibilityChange = tempvis;
    this._heatResChange = tempheat;
    this._coldResChange = tempcold;
    this._wetResChange = tempwet;
    this._dryResChange = tempdry;
    this._medResChange = tempmed;
    this._nextPerks = tempnext;
    
  }
  
  /**
   * sets the availability of of this perk 
   * @param avail     availability of this perk
   */
  public void setAvailability(boolean avail){
    
    this._availability = avail;
    
  }
  
  /**
   * sets whether this perk has been bought
   * @param bought        whether this perk has been bought
   */
  public void setBought(boolean bought){
    
    this._bought = bought;
    
  }
  
  /**
   * sets the ID of this perk
   * @param tempid          the new id of this perk
   */
  public void setID(int tempid){
    
    this._id = tempid;
    
  }
  
  /**
   * gets the integer cost of this perk
   * @return _cost
   */
  public int getCost(){
   
    return this._cost;
    
  }
  
  /**
   * gets the unique integer id of this perk
   * @return _id
   */
  public int getID(){
   
    return this._id;
    
  }
  
  /**
   * gets the change in infectivity this perk gives to a disease
   * @return _infectivityChange
   */
  public double getInf(){
   
    return this._infectivityChange;
    
  }
  
  /**
   * gets the change in lethality this perk gives to a disease
   * @return _lethalityChange
   */
  public double getLeth(){
   
    return this._lethalityChange;
    
  }
  
  /**
   * gets the change in visibility this perk gives to a disease
   * @return _visibilityChange
   */
  public double getVis(){
   
    return this._visibilityChange;
    
  }
  
  /**
   * gets the change in heat resistance this perk gives to a disease
   * @return _heatResChange
   */
  public double getHeatRes(){
   
    return this._heatResChange;
    
  }
  
  /**
   * gets the change in cold resistance this perk gives to a disease
   * @return _coldResChange
   */
  public double getColdRes(){
   
    return this._coldResChange;
    
  }
  
  /**
   * gets the change in wetness resistance this perk gives to a disease
   * @return _wetResChange
   */
  public double getWetRes(){
   
    return this._wetResChange;
    
  }
  
  /**
   * gets the change in dryness resistance this perk gives to a disease
   * @return _dryResChange
   */
  public double getDryRes(){
   
    return this._dryResChange;
    
  }
  
  /**
   * gets the change in resistance to medicine this perk gives to a disease
   * @return _medResChange
   */
  public double getMedRes(){
   
    return this._medResChange;
    
  }
 
  /**
   * gets the boolean availability of this perk
   * @return _availability
   */
  public boolean isAvail(){
   
    return this._availability;
    
  }
  
  /**
   * gets the boolean saying if this perk has been bought
   * @return _bought
   */
  public boolean isBought(){
   
    return this._bought;
    
  }
  
  /**
   * gets the ArrayList of Perks this perk opens up once it is bought
   * @return _nextPerks
   */
  public ArrayList<Perk> getNext(){
      
      return this._nextPerks;
      
  }
  
}
