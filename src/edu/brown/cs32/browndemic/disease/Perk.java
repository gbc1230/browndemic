package edu.brown.cs32.browndemic.disease;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for a perk. Has all the necessary attributes of a perk,
 * with Accesor/Mutators for its own variables, and the ability to
 * return cumulative sums of variables in all directly connected perks (in the
 * case of a user selling a perk which has perks after it already bought)
 *@author bkoatz 
 */
public class Perk implements Serializable{
	private static final long serialVersionUID = -5944513730395801585L;

//the name of this perk
  private String _name;
  
  //the integer cost to buy this perk
  private int _cost;
  
  //the integer price connected to selling this perk. A cost to viruses, refund
  //to others.
  private int _sellPrice;
  
  //the unique integer ID of this perk
  private int _id;
  
  //the String description of this perk
  private String _description;
  
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

  //the double change that this perk will provide to transmission
  //through water
  private double _waterTransChange;

  //the double change that this perk will provide to transmission
  //through air
  private double _airTransChange;
  
  //the boolean availability of this perk to be purchased
  private boolean _availability = false;
  
  //the boolean saying whether or not this perk is owned by the disease
  private boolean _owned = false;

  //the ArrayList of perk ids that this perk's purchase makes available
  private List<Integer> _nextPerks = new ArrayList<Integer>();

  //the ArrayList of perk ids whose purchase makes this perk available
  private List<Integer> _prevPerks = new ArrayList<Integer>();

  //Array of all other perks
  private Perk[] _perks;

  //constuctor: sets cost, change in infectivity/lethality/visibility and
  //the perks that, when this perk is bought, become available for purchase
  public Perk(int ID, String tempname, String tempdesc, int tempcost, int tempsell,
		      double tempinf, double templeth, double tempvis, double tempheat,
		      double tempcold, double tempwet, double tempdry, double tempmed,
		      double tempwater, double tempair, ArrayList<Integer> prev, ArrayList<Integer> next){

    this._id = ID;
	this._name = tempname;
	this._description = tempdesc;
    this._cost = tempcost;
    this._sellPrice = tempsell;
    this._infectivityChange = tempinf;
    this._lethalityChange = templeth;
    this._visibilityChange = tempvis;
    this._heatResChange = tempheat;
    this._coldResChange = tempcold;
    this._wetResChange = tempwet;
    this._dryResChange = tempdry;
    this._medResChange = tempmed;
    this._waterTransChange = tempwater;
    this._airTransChange = tempair;
    this._prevPerks = prev;
    this._nextPerks = next;

  }
  
  /**
   * sets the availability of of this perk 
   * @param avail     availability of this perk
   */
  public void setAvailability(boolean avail){
    
    this._availability = avail;
    
  }
  
  /**
   * sets whether this perk is owned by its disease
   * @param owned        whether this perk is owned by its disease
   */
  public void setOwned(boolean owned){
    
    this._owned = owned;
    
  }

  /**
   * sets the ID of this perk
   * @param tempid          the new id of this perk
   */
  public void setID(int tempid){
    
    this._id = tempid;
    
  }
  
  /**
   * sets the array of all perks
   * @param toSet          the new perk array of this perk
   */
  public void setPerksArray(Perk[] toSet){
      
      this._perks = toSet;
      
  }
  
  /**
   * sets this perk's sell price to negative because it is for a virus
   */
  public void makeVirusPerk(){
      
      this._sellPrice = this._sellPrice*-1;
      
  }
  
  /**
   * gets the String name of this perk
   * @return _name
   */
  public String getName(){
	  
	  return this._name;
	  
  }
  
  /**
   * gets the integer cost of this perk
   * @return _cost 
   */
  public int getCost(){
   
    return this._cost;
    
  }
  
  /**
   * gets the integer selling price (cost or refund) of this perk
   * if perks that rely on this perk to be available, they will be sold if
   * this perk is sold. Therefore, their selling prices are included in
   * this integer. This is the selling price shown to the user
   * @return the combined _sellprice's of all owned perks after this perk
   */
  public int getCumSellPrice(){
   
      int returnPrice = this._sellPrice;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnPrice += this._perks[p].getCumSellPrice();
      }
      return returnPrice;
  }

  /**
   * gets the sell price of this individual perk
   * @return _sellPrice
   */
  public int getSellPrice(){

      return this._sellPrice;

  }
  
  /**
   * gets the unique integer id of this perk
   * @return _id
   */
  public int getID(){
   
    return this._id;
    
  }
  
  /**
   * gets the String description of this perk
   * @return _description
   */
  public String getDescription(){
	  
	  return this._description;
	  
  }
  
  /**
   * Like getCumSellPrice, gets the cumulative infectivity for every
   * perk directly reliant on this perk
   * @return the combined _infectivityChange's of all owned perks after this perk
   */
  public double getCumInf(){
   
      double returnInf = this._infectivityChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnInf += this._perks[p].getCumInf();
      }
      return returnInf;
  }

  /**
   * gets the change in infectivity this perk gives to a disease
   * @return _infectivityChange
   */
  public double getInf(){
   
    return this._infectivityChange;
    
  }

  /**
   * Like getCumSellPrice, gets the cumulative lethality for every
   * perk directly reliant on this perk
   * @return the combined _lethalityChange's of all owned perks after this perk
   */
  public double getCumLeth(){

      double returnLeth = this._lethalityChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnLeth += this._perks[p].getCumLeth();
      }
      return returnLeth;
  }

  /**
   * gets the change in lethality this perk gives to a disease
   * @return _lethalityChange
   */
  public double getLeth(){
   
    return this._lethalityChange;
    
  }

  /**
   * Like getCumSellPrice, gets the cumulative visibility for every
   * perk directly reliant on this perk
   * @return the combined _visibilityChange's of all owned perks after this perk
   */
  public double getCumVis(){

      double returnVis = this._visibilityChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnVis += this._perks[p].getCumVis();
      }
      return returnVis;
  }

  /**
   * gets the change in visibility this perk gives to a disease
   * @return _visibilityChange
   */
  public double getVis(){
   
    return this._visibilityChange;
    
  }

  /**
   * Like getCumSellPrice, gets the cumulative heat resistance for every
   * perk directly reliant on this perk
   * @return the combined _heatResChange's of all owned perks after this perk
   */
  public double getCumHeatRes(){

      double returnHeatRes = this._heatResChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnHeatRes += this._perks[p].getCumHeatRes();
      }
      return returnHeatRes;
  }

  /**
   * gets the change in heat resistance this perk gives to a disease
   * @return _heatResChange
   */
  public double getHeatRes(){
   
    return this._heatResChange;
    
  }

  /**
   * Like getCumSellPrice, gets the cumulative cold resistivity for every
   * perk directly reliant on this perk
   * @return the combined _coldResChange's of all owned perks after this perk
   */
  public double getCumColdRes(){

      double returnColdRes = this._coldResChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnColdRes += this._perks[p].getCumColdRes();
      }
      return returnColdRes;
  }

  /**
   * gets the change in cold resistance this perk gives to a disease
   * @return _coldResChange
   */
  public double getColdRes(){
   
    return this._coldResChange;
    
  }

  /**
   * Like getCumSellPrice, gets the cumulative wet resistivity for every
   * perk directly reliant on this perk
   * @return the combined _wetResChange's of all owned perks after this perk
   */
  public double getCumWetRes(){

      double returnWetRes = this._wetResChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnWetRes += this._perks[p].getCumWetRes();
      }
      return returnWetRes;
  }

  /**
   * gets the change in wetness resistance this perk gives to a disease
   * @return _wetResChange
   */
  public double getWetRes(){
   
    return this._wetResChange;
    
  }

  /**
   * Like getCumSellPrice, gets the cumulative dry resistivity for every
   * perk directly reliant on this perk
   * @return the combined _dryResChange's of all owned perks after this perk
   */
  public double getCumDryRes(){

      double returnDryRes = this._dryResChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnDryRes += this._perks[p].getCumDryRes();
      }
      return returnDryRes;
  }

  /**
   * gets the change in dryness resistance this perk gives to a disease
   * @return _dryResChange
   */
  public double getDryRes(){
   
    return this._dryResChange;
    
  }

  /**
   * Like getCumSellPrice, gets the cumulative medical resistivity for every
   * perk directly reliant on this perk
   * @return the combined _medResChange's of all owned perks after this perk
   */
  public double getCumMedRes(){

      double returnMedRes = this._medResChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnMedRes += this._perks[p].getCumMedRes();
      }
      return returnMedRes;
  }

  /**
   * gets the change in resistance to medicine this perk gives to a disease
   * @return _medResChange
   */
  public double getMedRes(){
   
    return this._medResChange;
    
  }

  /**
   * Like getCumSellPrice, gets the cumulative water transmissibilityfor every
   * perk directly reliant on this perk
   * @return the combined _waterTransChange's of all owned perks after this perk
   */
  public double getCumWaterTrans(){

      double returnWaterTrans = this._waterTransChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnWaterTrans += this._perks[p].getCumWaterTrans();
      }
      return returnWaterTrans;
  }

  /**
   * gets the change in water transmissibility this perk gives to a disease
   * @return   _waterTransChange
   */
  public double getWaterTrans(){

      return this._waterTransChange;

  }

  /**
   * Like getCumSellPrice, gets the cumulative air transmissibility for every
   * perk directly reliant on this perk
   * @return the combined _airTransChange's of all owned perks after this perk
   */
  public double getCumAirTrans(){

      double returnAirTrans = this._airTransChange;
      for(Integer p : this._nextPerks){
          if(this._perks[p].isOwned() && this._perks[p].isOnlyOwnedPrev(this))
              returnAirTrans += this._perks[p].getCumAirTrans();
      }
      return returnAirTrans;
  }

  /**
   * gets the change in air transmissibility this perk gives to a disease
   * @return   _airTransChange
   */
  public double getAirTrans(){

      return this._airTransChange;

  }
 
  /**
   * gets the boolean availability of this perk
   * @return _availability
   */
  public boolean isAvail(){
   
    return this._availability;
    
  }
  
  /**
   * gets the boolean saying if this perk is owned by the disease it is a part of
   * @return _owned
   */
  public boolean isOwned(){
   
    return this._owned;
    
  }

  /**
   * gets the ArrayList of Perks that make this perk available
   * @return _prevPerks
   */
  public List<Integer> getPrev(){

      return this._prevPerks;

  }

  /**
   * gets the ArrayList of Perks that make this perk available
   * @return _prevPerks
   */
  public List<Integer> getNext(){

      return this._nextPerks;

  }

  /**
   * gets the ArrayList of Perks that make this perk available
   * @return _prevPerks
   */
  public Perk[] getPerksArray(){

      return this._perks;

  }

  /**
   * gets all the perks that will be sold if this perk is sold
   * @return    a List<Perk> of every perk that will be sold if this is sold
   */
  public List<Perk> getWillBeSold(){
      List<Perk> ans = new ArrayList<Perk>();
      for(Integer i : this._nextPerks)
          if(this._perks[i].isOwned() && this._perks[i].isOnlyOwnedPrev(this)){
              ans.add(this._perks[i]);
              ans.addAll(this._perks[i].getWillBeSold());
          }
      return ans;
  }

  /**
   * gets all the perks that will be made available if this perk is bought
   * @return    a List<Perk> of every perk that will be available if this perk is
   *            bought
   */
  public List<Perk> getWillBeAvailable(){
      List<Perk> ans = new ArrayList<Perk>();
      for(Integer i : this._nextPerks)
          if(!this._perks[i].isOwned() && !this._perks[i].isAvail()){
              ans.add(this._perks[i]);
              ans.addAll(this._perks[i].getWillBeSold());
          }
      return ans;
  }

  /**
   * returns true if the perk's only owned predecessor is parent
   * return false if either the parent is not owned yet or some other perk
   * besides the parent is owned.
   * @param parent                        a perk
   * @returns                             true if the perk is the only owned
   *                                      perk previous to this perk
   */
  public boolean isOnlyOwnedPrev(Perk parent){

      if(!parent.isOwned()) return false;
      else
          for(Integer p : this._prevPerks){

              if(p != parent.getID() && this._perks[p].isOwned()) return false;

          }
     return true;

  }
  
  @Override
  public String toString() {
	  return _name;
  }
}
