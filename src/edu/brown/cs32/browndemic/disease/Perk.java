package disease;
import java.util.ArrayList;

/**
 *@author bkoatz 
 */
public class Perk{
 
  //the integer cost of this perk
  private int _cost;
  
  //the double change that this perk will provide to infectivity
  private double _infectivityChange;
  
  //the double change that this perk will provide to lethality
  private double _lethalityChange;
  
  //the double change that this perk will provide to visibility
  private double _visibilityChange;
  
  //the boolean availability of this perk to be purchased
  private boolean _availability = false;
  
  //the boolean saying whether or not this perk has been purchased
  private boolean _bought = false;
  
  //the ArrayList of perks that this perk's purchase makes available
  private ArrayList<Perk> _nextPerks;
  
  //constuctor: sets cost, change in infectivity/lethality/visibility and
  //the perks that, when this perk is bought, become available for purchase
  public Perk(int tempcost, double tempinf, double templeth, double tempvis,
              ArrayList<Perk> tempnext){
    
    this._cost = tempcost;
    this._infectivityChange = tempinf;
    this._lethalityChange = templeth;
    this._visibilityChange = tempvis;
    this._nextPerks = tempnext;
    
  }
  
  //sets the availability of of this perk
  public void setAvailability(boolean avail){
    
    this._availability = avail;
    
  }
  
  //sets whether this perk has been bought
  public void setBought(boolean bought){
    
    this._bought = bought;
    
  }
  
}
