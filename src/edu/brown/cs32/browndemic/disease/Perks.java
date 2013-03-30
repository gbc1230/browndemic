package edu.brown.cs32.browndemic.disease;
import java.util.ArrayList;

/**
 *@author bkoatz 
 */
public interface Perks{
 
  final Perk VOMITING_VIRUS = new Perk(2, 3, 0, 2.0, 6.0, 0, 0, 0, 0, 0,
              new ArrayList<Perk>());
  
  final Perk VOMITING_BACTERIA = new Perk(2, 1, 0, 2.0, 6.0, 0, 0, 0, 0, 0,
              new ArrayList<Perk>());
  
}
