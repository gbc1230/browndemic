package disease;
import java.util.HashMap;

/**
 *
 * @author bkoatz
 * @contributor ckilfoyl
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
    
    //A double reflecting how many points this disease has
    protected integer _points;
    
    /**
     * setID(int newID) sets the _id of this Disease to newID
     */
    public void setID(int newID){
     
      this._id = newID;
      
    }
    
    /**
     * setInfectivity(double newInf) sets the _infectivity of this
     * Disease to newInf
     */
    public void setInfectivity(double newInf){
      
      this._infectivity = newInf;
      
    }
    
    /**
     * setLethality(double newLeth) sets the _lethality of this
     * Disease to newLeth
     */
    public void setLethality(double newLeth){
      
      this._lethality = newLeth;
      
    }
    
    /**
     * setVisibility(double newVis) sets the _visibility of this
     * Disease to newVis
     */
    public void setVisibility(double newVis){
      
      this._visibility = newVis;
      
    }
    
    /**
     * addPoint() gives this Disease another point
     */
    public void addPoint(){
     
      this._points++;
      
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
     * getName() returns the unique String name for this disease
     * @return _name
     */
    public String getName(){
        return _name;
    }

    //IMPORTANT PLEASE READ
    //The following code relies on the uniqueness of the String name

    /**
     * toString gets a String of the unique name, the infectivity, the mortality
     * @return
     */
    @Override
    public String toString(){
        return _name + ", infectivity: " + _infectivity + ", mortality: " +
                _mortality;
    }

}
