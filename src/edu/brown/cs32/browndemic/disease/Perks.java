package edu.brown.cs32.browndemic.disease;
import java.util.ArrayList;

/**
 * The interface containing all the perks that diseases can get
 *@author bkoatz 
 */
public interface Perks{

  //cost, sell, inf, leth, vis, heat, cold, wet, dry, med, water, air, next, prev

  //These methods just create new perks with the traits of the CAPITALIZED_
  //PART_OF_THEIR_NAME. This solves the issues of different diseases pointing
  //to the same perk
  //The VIRUS or BACTERIA part of say whether the perks are for viruses or bacteria

  public static Perk newVOMITING_VIRUS(){
      return new Perk(2, 3, -1.0, 2.0, 6.0, 0, 0, 0, 0, 0, 0, 0, new ArrayList<Perk>(), new ArrayList<Perk>());
  }
  
  public static Perk newVOMITING_BACTERIA(){

       return new Perk(2, 1, -1.0, 2.0, 6.0, 0, 0, 0, 0, 0, 0, 0, new ArrayList<Perk>(), new ArrayList<Perk>());

  }
  
}
