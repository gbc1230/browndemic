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

  public static Perk newSNEEZING_VIRUS(){
	  return new Perk("Sneezing", 2, 4, 3, 0, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCOUGHING_VIRUS(){
	  return new Perk("Coughing", 3, 5, 5, 0, 3, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newNAUSEA_VIRUS(){
	  return new Perk("Nausea", 2, 2, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newRASH_VIRUS(){
	  return new Perk("Rash", 3, 5, 2, 1, 4, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDEHYDRATION_VIRUS(){
	  return new Perk("Dehydration", 3, 3, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newVOMITING_VIRUS(){
      return new Perk("Vomiting", 6, 9, 2, 5, 5, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDIZZINESS_VIRUS(){
	  return new Perk("Dizziness", 5, 6, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newFATIGUE_VIRUS(){
	  return new Perk("Fatigue", 5, 6, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newFEVER_VIRUS(){
	  return new Perk("Fever", 6, 7, 2, 4, 3, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDIARRHEA_VIRUS(){
	  return new Perk("Diarrhea", 7, 9, 4, 6, 4, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newINSOMNIA_VIRUS(){
	  return new Perk("Insomnia", 6, 6, 2, 5, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newLESIONS_VIRUS(){
	  return new Perk("Lesions", 6, 9, 4, 6, 7, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCYSTS_VIRUS(){
	  return new Perk("Cysts", 6, 8, 4, 4, 6, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newPULMONARY_OBSTRUCTION_VIRUS(){
	  return new Perk("Pulmonary Obstruction", 8, 10, 6, 10, 15, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newINFLAMMATION_VIRUS(){
	  return new Perk("Inflammation", 10, 11, 6, 12, 9, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newPARALYSIS_VIRUS(){
	  return new Perk("Paralysis", 17, 20, -2, 20, 30, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCOMA_VIRUS(){
	  return new Perk("Coma", 30, 40, -15, 40, 50, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDELIRIUM_VIRUS(){
	  return new Perk("Delirium", 15, 17, -3, 15, 20, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newPULMONARY_EDEMA_VIRUS(){
	  return new Perk("Pulmonary Edema", 28, 35, 10, 25, 35, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newHYPOXIA_VIRUS(){
	  return new Perk("Hypoxia", 35, 37, 10, 50, 70, 0, 0, 0, 0, 0, 0, 0);
  }
  
  public static Perk newVOMITING_BACTERIA(){

       return new Perk("Vomiting", 2, 1, -1.0, 2.0, 6.0, 0, 0, 0, 0, 0, 0, 0);

  }
  
}
