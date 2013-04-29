package edu.brown.cs32.browndemic.disease;
import java.util.ArrayList;

/**
 * The interface containing all the perks that diseases can get
 *@author bkoatz 
 */
public class Perks{

  //cost, sell, inf, leth, vis, heat, cold, wet, dry, med, water, air, next, prev

  //These methods just create new perks with the traits of the CAPITALIZED_
  //PART_OF_THEIR_NAME. This solves the issues of different diseases pointing
  //to the same perk
  //The VIRUS or BACTERIA part of say whether the perks are for viruses or bacteria

  public static Perk newSNEEZING_VIRUS(){
	  String sd = "Sneezing allows your disease to spread easier, but also brings it some attention.";
	  return new Perk("Sneezing", sd, 2, 4, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCOUGHING_VIRUS(){
	  String cd = "Coughing allows your disease to spread easier, but also brings it some attention.";
	  return new Perk("Coughing", cd, 3, 5, 5, 0, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newNAUSEA_VIRUS(){
	  String nd = "Nausea allows your disease to spread through mouth-to-mouth contact and draws some attention to it.";
	  return new Perk("Nausea", nd, 2, 2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newRASH_VIRUS(){
	  String rd = "People avoid the infected more, but they are more contagious. And more visible.";
	  return new Perk("Rash", rd, 3, 5, 3, 1, 4, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDEHYDRATION_VIRUS(){
	  String dd = "Dehydration can be deadly, and is noticeable.";
	  return new Perk("Dehydration", dd, 3, 3, 0, 3, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newVOMITING_VIRUS(){
	  String vd = "Vomiting brings a lot of attention but also can make the infected frail and weak.";
      return new Perk("Vomiting", vd, 6, 9, 2, 5, 5, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDIZZINESS_VIRUS(){
	  String dd = "Dizziness can lead to dangerous accidents!";
	  return new Perk("Dizziness", dd, 5, 6, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newFATIGUE_VIRUS(){
	  String fd = "Fatigue weakens the immune system of the infected.";
	  return new Perk("Fatigue", fd, 5, 6, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newFEVER_VIRUS(){
	  String fd = "Fevers can be deadly.";
	  return new Perk("Fever", fd, 6, 7, 2, 4, 3, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDIARRHEA_VIRUS(){
	  String dd = "Diarrhea, and its dehydrating effects, can be deadly and can spread infection to the water supply.";
	  return new Perk("Diarrhea", dd, 7, 9, 7, 6, 4, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newINSOMNIA_VIRUS(){
	  String id = "Insomnia weakens immune system responses.";
	  return new Perk("Insomnia", id, 6, 6, 2, 5, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newLESIONS_VIRUS(){
	  String ld = "Lesions can cause open wounds, leading to death and spread of infection.";
	  return new Perk("Lesions", ld, 6, 9, 6, 6, 7, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCYSTS_VIRUS(){
	  String cd = "Cysts hold pathogens, can burst and spread the disease.";
	  return new Perk("Cysts", cd, 6, 8, 4, 4, 6, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newPULMONARY_OBSTRUCTION_VIRUS(){
	  String pod = "This is, long-term, a very deadly symptom. Limited breathing and lung damage are very visible, however.";
	  return new Perk("Pulmonary Obstruction", pod, 8, 10, 6, 20, 25, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newINFLAMMATION_VIRUS(){
	  String id = "Inflammation is harmful, and disrupting for many bodily processes. Can be deadly";
	  return new Perk("Inflammation", id, 9, 11, 6, 10, 9, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newPARALYSIS_VIRUS(){
	  String pd = "Paralysis is deadly, but makes it much less likely for infected to infect others.";
	  return new Perk("Paralysis", pd, 17, 20, -2, 20, 30, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCOMA_VIRUS(){
	  String cd = "Comas are very deadly, and make it much less likely for infected to infect others.";
	  return new Perk("Coma", cd, 30, 40, -15, 40, 50, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDELIRIUM_VIRUS(){
	  String dd = "Delerium comes after not sleeping for way too long. It is very dangerous and very noticeable.";
	  return new Perk("Delirium", dd, 15, 17, -3, 15, 20, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newPULMONARY_EDEMA_VIRUS(){
	  String ped = "Blood in the lungs, or Pulmonary Edema, makes a disease more infective, deadly and visible.";
	  return new Perk("Pulmonary Edema", ped, 28, 35, 10, 25, 35, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newHYPOXIA_VIRUS(){
	  String hd = "Hypoxia, the death of organs, is extremely deadly and will lead to much notice.";
	  return new Perk("Hypoxia", hd, 35, 37, 0, 50, 70, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newHEAT_RESISTANCE_I(){
	  String hrId = "Resistance to low heat.";
	  return new Perk("Heat Resistance I", hrId, 10, 10, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newHEAT_RESISTANCE_II(){
	  String hrIId = "Resistance to medium heat.";
	  return new Perk("Heat Resistance II", hrIId, 15, 15, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newHEAT_RESISTANCE_III(){
	  String hrIIId = "Resistance to high heat.";
	  return new Perk("Heat Resistance III", hrIIId, 20, 20, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCOLD_RESISTANCE_I(){
	  String crId = "Resistance to low cold.";
	  return new Perk("Cold Resistance I", crId, 10, 10, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
  }
  public static Perk newCOLD_RESISTANCE_II(){
	  String crIId = "Resistance to medium cold.";
	  return new Perk("Cold Resistance II", crIId, 15, 15, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
  }
  public static Perk newCOLD_RESISTANCE_III(){
	  String crIIId = "Resistance to high cold.";
	  return new Perk("Cold Resistance III", crIIId, 20, 20, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
  }
  public static Perk newWET_RESISTANCE_I(){
	  String wrId = "Resistance to low wetness.";
	  return new Perk("Wet Resistance I", wrId, 10, 10, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
  }
  public static Perk newWET_RESISTANCE_II(){
	  String wrIId = "Resistance to medium wetness.";
	  return new Perk("Wet Resistance II", wrIId, 15, 15, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
  }
  public static Perk newWET_RESISTANCE_III(){
	  String wrIIId = "Resistance to high wetness.";
	  return new Perk("Wet Resistance III", wrIIId, 20, 20, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
  }
  public static Perk newDRY_RESISTANCE_I(){
	  String drId = "Resistance to low dryness.";
	  return new Perk("Dry Resistance I", drId, 10, 10, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
  }
  public static Perk newDRY_RESISTANCE_II(){
	  String drIId = "Resistance to medium dryness.";
	  return new Perk("Dry Resistance II", drIId, 15, 15, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
  }
  public static Perk newDRY_RESISTANCE_III(){
	  String drIIId = "Resistance to high dryness.";
	  return new Perk("Dry Resistance III", drIIId, 20, 20, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
  }
  public static Perk newMED_RESISTANCE_I(){
	  String mrId = "Resistance to low levels of medicine.";
	  return new Perk("Med Resistance I", mrId, 10, 10, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  }
  public static Perk newMED_RESISTANCE_II(){
	  String mrIId = "Resistance to medium levels of medicine.";
	  return new Perk("Med Resistance II", mrIId, 15, 15, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  }
  public static Perk newMED_RESISTANCE_III(){
	  String mrIIId = "Resistance to high levels of medicine.";
	  return new Perk("Med Resistance III", mrIIId, 20, 20, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  }
  public static Perk newWATER_TRANSMISSION_I(){
	  String wtId = "Low ability to be transmitted through water.";
	  return new Perk("Water Transmission I", wtId, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0);
  }
  public static Perk newWATER_TRANSMISSION_II(){
	  String wtIId = "Medium ability to be transmitted through water.";
	  return new Perk("Water Transmission II", wtIId, 15, 15, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0);
  }
  public static Perk newWATER_TRANSMISSION_III(){
	  String wtIIId = "High ability to be transmitted through water.";
	  return new Perk("Water Transmission III", wtIIId, 25, 25, 0, 0, 0, 0, 0, 0, 0, 0, 25, 0);
  }
  public static Perk newAIR_TRANSMISSION_I(){
	  String atId = "Low ability to be transmitted through air.";
	  return new Perk("Air Transmission I", atId, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
  }
  public static Perk newAIR_TRANSMISSION_II(){
	  String atIId = "Medium ability to be transmitted through air.";
	  return new Perk("Air Transmission II", atIId, 15, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3);
  }
  public static Perk newAIR_TRANSMISSION_III(){
	  String atIIId = "High ability to be transmitted through air.";
	  return new Perk("Air Transmission III", atIIId, 25, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7);
  }
  public static Perk newBIRD_TRANSMISSION_I(){
	  String btId = "Low ability to be transmitted through bird.";
	  return new Perk("Bird Transmission I", btId, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
  }
  public static Perk newBIRD_TRANSMISSION_II(){
	  String btIId = "Medium ability to be transmitted through bird.";
	  return new Perk("Bird Transmission II", btIId, 15, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3);
  }
  public static Perk newBIRD_TRANSMISSION_III(){
	  String btIIId = "High ability to be transmitted through bird.";
	  return new Perk("Bird Transmission III", btIIId, 25, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7);
  }
  public static Perk newINSECT_TRANSMISSION_I(){
	  String itId = "Low ability to be transmitted through insect.";
	  return new Perk("Insect Transmission I", itId, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
  }
  public static Perk newINSECT_TRANSMISSION_II(){
	  String itIId = "Medium ability to be transmitted through insect.";
	  return new Perk("Insect Transmission II", itIId, 15, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3);
  }
  public static Perk newINSECT_TRANSMISSION_III(){
	  String itIIId = "High ability to be transmitted through insect.";
	  return new Perk("Insect Transmission III", itIIId, 25, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7);
  }
  
  
  public static Perk[] getVirusPerks(){
	  
	  Perk sneeze = Perks.newSNEEZING_VIRUS();
      sneeze.setAvailability(true);
	  Perk cough = Perks.newCOUGHING_VIRUS();
      cough.setAvailability(true);
	  Perk nausea = Perks.newNAUSEA_VIRUS();
      nausea.setAvailability(true);
	  Perk rash = Perks.newRASH_VIRUS();
      rash.setAvailability(true);
	  Perk dehydration = Perks.newDEHYDRATION_VIRUS();
      dehydration.setAvailability(true);
	  Perk vomiting = Perks.newVOMITING_VIRUS();
	  Perk dizziness = Perks.newDIZZINESS_VIRUS();
	  Perk fatigue = Perks.newFATIGUE_VIRUS();
      fatigue.setAvailability(true);
	  Perk fever = Perks.newFEVER_VIRUS();
	  Perk diarrhea = Perks.newDIARRHEA_VIRUS();
	  Perk insomnia = Perks.newINSOMNIA_VIRUS();
	  Perk lesions = Perks.newLESIONS_VIRUS();
	  Perk cysts = Perks.newCYSTS_VIRUS();
	  Perk pulmonaryObstruction = Perks.newPULMONARY_OBSTRUCTION_VIRUS();
	  Perk inflammation = Perks.newINFLAMMATION_VIRUS();
	  Perk paralysis = Perks.newPARALYSIS_VIRUS();
	  Perk coma = Perks.newCOMA_VIRUS();
	  Perk delirium = Perks.newDELIRIUM_VIRUS();
	  Perk pulmonaryEdema = Perks.newPULMONARY_EDEMA_VIRUS();
	  Perk hypoxia = Perks.newHYPOXIA_VIRUS();
	  sneeze.addToNext(fever);
	  cough.addToNext(pulmonaryObstruction);
	  fever.addToPrev(sneeze);
	  pulmonaryObstruction.addToPrev(cough);
	  nausea.addToNext(vomiting);
	  vomiting.addToPrev(nausea);
	  rash.addToNext(lesions);
	  rash.addToNext(cysts);
	  lesions.addToPrev(rash);
	  cysts.addToPrev(rash);
	  dehydration.addToNext(dizziness);
	  dehydration.addToNext(diarrhea);
	  dizziness.addToPrev(dehydration);
	  diarrhea.addToPrev(dehydration);
	  fatigue.addToNext(insomnia);
	  insomnia.addToPrev(fatigue);
	  fever.addToNext(inflammation);
	  inflammation.addToPrev(fever);
	  pulmonaryObstruction.addToNext(pulmonaryEdema);
	  pulmonaryEdema.addToPrev(pulmonaryObstruction);
	  pulmonaryObstruction.addToNext(hypoxia);
	  hypoxia.addToPrev(pulmonaryObstruction);
	  insomnia.addToNext(delirium);
	  delirium.addToPrev(insomnia);
	  inflammation.addToNext(paralysis);
	  paralysis.addToNext(coma);
	  paralysis.addToPrev(inflammation);
	  coma.addToPrev(paralysis);
	  Perk[] toReturn = new Perk[44];
	  toReturn[0] = sneeze;
	  sneeze.setID(0);
	  toReturn[1] = cough;
	  cough.setID(1);
	  toReturn[2] = nausea;
	  nausea.setID(2);
	  toReturn[3] = rash;
	  rash.setID(3);
	  toReturn[4] = dehydration;
	  dehydration.setID(4);
	  toReturn[5] = vomiting;
	  vomiting.setID(5);
	  toReturn[6] = dizziness;
	  dizziness.setID(6);
	  toReturn[7] = fatigue;
	  fatigue.setID(7);
	  toReturn[8] = fever;
	  fever.setID(8);
	  toReturn[9] = diarrhea;
	  diarrhea.setID(9);
	  toReturn[10] = insomnia;
	  insomnia.setID(10);
	  toReturn[11] = lesions;
	  lesions.setID(11);
	  toReturn[12] = cysts;
	  cysts.setID(12);
	  toReturn[13] = pulmonaryObstruction;
	  pulmonaryObstruction.setID(13);
	  toReturn[14] = inflammation;
	  inflammation.setID(14);
	  toReturn[15] = paralysis;
	  paralysis.setID(15);
	  toReturn[16] = coma;
	  coma.setID(16);
	  toReturn[17] = delirium;
	  delirium.setID(17);
	  toReturn[18] = pulmonaryEdema;
	  pulmonaryEdema.setID(18);
	  toReturn[19] = hypoxia;
	  hypoxia.setID(19);
	  Perk heatResI = Perks.newHEAT_RESISTANCE_I();
      heatResI.setAvailability(true);
	  Perk heatResII = Perks.newHEAT_RESISTANCE_II();
	  Perk heatResIII = Perks.newHEAT_RESISTANCE_III();
	  Perk coldResI = Perks.newCOLD_RESISTANCE_I();
      coldResI.setAvailability(true);
	  Perk coldResII = Perks.newCOLD_RESISTANCE_II();
	  Perk coldResIII = Perks.newCOLD_RESISTANCE_III();
	  Perk wetResI = Perks.newWET_RESISTANCE_I();
      wetResI.setAvailability(true);
	  Perk wetResII = Perks.newWET_RESISTANCE_II();
	  Perk wetResIII = Perks.newWET_RESISTANCE_III();
	  Perk dryResI = Perks.newDRY_RESISTANCE_I();
      dryResI.setAvailability(true);
	  Perk dryResII = Perks.newDRY_RESISTANCE_II();
	  Perk dryResIII = Perks.newDRY_RESISTANCE_III();
	  Perk wetTransI = Perks.newWATER_TRANSMISSION_I();
      wetTransI.setAvailability(true);
	  Perk wetTransII = Perks.newWATER_TRANSMISSION_II();
	  Perk wetTransIII = Perks.newWATER_TRANSMISSION_III();
	  Perk airTransI = Perks.newAIR_TRANSMISSION_I();
      airTransI.setAvailability(true);
	  Perk airTransII = Perks.newAIR_TRANSMISSION_II();
	  Perk airTransIII = Perks.newAIR_TRANSMISSION_III();
	  Perk birdTransI = Perks.newBIRD_TRANSMISSION_I();
      birdTransI.setAvailability(true);
	  Perk birdTransII = Perks.newBIRD_TRANSMISSION_II();
	  Perk birdTransIII = Perks.newBIRD_TRANSMISSION_III();
	  Perk insectTransI = Perks.newINSECT_TRANSMISSION_I();
      insectTransI.setAvailability(true);
	  Perk insectTransII = Perks.newINSECT_TRANSMISSION_II();
	  Perk insectTransIII = Perks.newINSECT_TRANSMISSION_III();
	  heatResI.addToNext(heatResII);
	  heatResII.addToPrev(heatResI);
	  heatResII.addToNext(heatResIII);
	  heatResIII.addToPrev(heatResII);
	  coldResI.addToNext(coldResII);
	  coldResII.addToPrev(coldResI);
	  coldResII.addToNext(coldResIII);
	  coldResIII.addToPrev(coldResII);
	  wetResI.addToNext(wetResII);
	  wetResII.addToPrev(wetResI);
	  wetResII.addToNext(wetResIII);
	  wetResIII.addToPrev(wetResII);
	  dryResI.addToNext(dryResII);
	  dryResII.addToPrev(dryResI);
	  dryResII.addToNext(dryResIII);
	  dryResIII.addToPrev(dryResII);
	  wetTransI.addToNext(wetTransII);
	  wetTransII.addToPrev(wetTransI);
	  wetTransII.addToNext(wetTransIII);
	  wetTransIII.addToPrev(wetTransII);
	  airTransI.addToNext(airTransII);
	  airTransII.addToPrev(airTransI);
	  airTransII.addToNext(airTransIII);
	  airTransIII.addToPrev(airTransII);
	  birdTransI.addToNext(birdTransII);
	  birdTransII.addToPrev(birdTransI);
	  birdTransII.addToNext(birdTransIII);
	  birdTransIII.addToPrev(birdTransII);
	  insectTransI.addToNext(insectTransII);
	  insectTransII.addToPrev(insectTransI);
	  insectTransII.addToNext(insectTransIII);
	  insectTransIII.addToPrev(insectTransII);
	  toReturn[20] = heatResI;
	  heatResI.setID(20);
	  toReturn[21] = heatResII;
	  heatResII.setID(21);
	  toReturn[22] = heatResIII;
	  heatResIII.setID(22);
	  toReturn[23] = coldResI;
	  coldResI.setID(23);
	  toReturn[24] = coldResII;
	  coldResII.setID(24);
	  toReturn[25] = coldResIII;
	  coldResIII.setID(25);
	  toReturn[26] = wetResI;
	  wetResI.setID(26);
	  toReturn[27] = wetResII;
	  wetResII.setID(27);
	  toReturn[28] = wetResIII;
	  wetResIII.setID(28);
	  toReturn[29] = dryResI;
	  dryResI.setID(29);
	  toReturn[30] = dryResII;
	  dryResII.setID(30);
	  toReturn[31] = dryResIII;
	  dryResIII.setID(31);
	  toReturn[32] = wetTransI;
	  wetTransI.setID(32);
	  toReturn[33] = wetTransII;
	  wetTransII.setID(33);
	  toReturn[34] = wetTransIII;
	  wetTransIII.setID(34);
	  toReturn[35] = airTransI;
	  airTransI.setID(35);
	  toReturn[36] = airTransII;
	  airTransII.setID(36);
	  toReturn[37] = airTransIII;
	  airTransIII.setID(37);
	  toReturn[38] = birdTransI;
	  birdTransI.setID(38);
	  toReturn[39] = birdTransII;
	  birdTransII.setID(39);
	  toReturn[40] = birdTransIII;
	  birdTransIII.setID(40);
	  toReturn[41] = insectTransI;
	  insectTransI.setID(41);
	  toReturn[42] = insectTransII;
	  insectTransII.setID(42);
	  toReturn[43] = insectTransIII;
	  insectTransIII.setID(43);
	  
	  return toReturn;
	  
  }
  
  public static Perk newSNEEZING_BACTERIA(){
	  String sd = "Sneezing allows your disease to spread easier, but also brings it some attention.";
	  return new Perk("Sneezing", sd, 2, 1, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCOUGHING_BACTERIA(){
	  String cd = "Coughing allows your disease to spread easier, but also brings it some attention.";
	  return new Perk("Coughing", cd, 3, 1, 5, 0, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newNAUSEA_BACTERIA(){
	  String nd = "Nausea allows your disease to spread through mouth-to-mouth contact and draws some attention to it.";
	  return new Perk("Nausea", nd, 2, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newRASH_BACTERIA(){
	  String rd = "People avoid the infected more, but they are more contagious. And more visible.";
	  return new Perk("Rash", rd, 3, 2, 3, 1, 4, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDEHYDRATION_BACTERIA(){
	  String dd = "Dehydration can be deadly, and is noticeable.";
	  return new Perk("Dehydration", dd, 3, 1, 0, 3, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newVOMITING_BACTERIA(){
	  String vd = "Vomiting brings a lot of attention but also can make the infected frail and weak.";
      return new Perk("Vomiting", vd, 6, 3, 2, 5, 5, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDIZZINESS_BACTERIA(){
	  String dd = "Dizziness can lead to dangerous accidents!";
	  return new Perk("Dizziness", dd, 5, 2, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newFATIGUE_BACTERIA(){
	  String fd = "Fatigue weakens the immune system of the infected.";
	  return new Perk("Fatigue", fd, 5, 2, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newFEVER_BACTERIA(){
	  String fd = "Fevers can be deadly.";
	  return new Perk("Fever", fd, 6, 2, 2, 4, 3, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDIARRHEA_BACTERIA(){
	  String dd = "Diarrhea, and its dehydrating effects, can be deadly and can spread infection to the water supply.";
	  return new Perk("Diarrhea", dd, 7, 2, 7, 6, 4, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newINSOMNIA_BACTERIA(){
	  String id = "Insomnia weakens immune system responses.";
	  return new Perk("Insomnia", id, 6, 2, 2, 5, 2, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newLESIONS_BACTERIA(){
	  String ld = "Lesions can cause open wounds, leading to death and spread of infection.";
	  return new Perk("Lesions", ld, 6, 2, 6, 6, 7, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCYSTS_BACTERIA(){
	  String cd = "Cysts hold pathogens, can burst and spread the disease.";
	  return new Perk("Cysts", cd, 6, 2, 4, 4, 6, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newPULMONARY_OBSTRUCTION_BACTERIA(){
	  String pod = "This is, long-term, a very deadly symptom. Limited breathing and lung damage are very visible, however.";
	  return new Perk("Pulmonary Obstruction", pod, 8, 3, 6, 20, 25, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newINFLAMMATION_BACTERIA(){
	  String id = "Inflammation is harmful, and disrupting for many bodily processes. Can be deadly";
	  return new Perk("Inflammation", id, 9, 2, 6, 10, 9, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newPARALYSIS_BACTERIA(){
	  String pd = "Paralysis is deadly, but makes it much less likely for infected to infect others.";
	  return new Perk("Paralysis", pd, 17, 9, -2, 20, 30, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newCOMA_BACTERIA(){
	  String cd = "Comas are very deadly, and make it much less likely for infected to infect others.";
	  return new Perk("Coma", cd, 30, 17, -15, 40, 50, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newDELIRIUM_BACTERIA(){
	  String dd = "Delerium comes after not sleeping for way too long. It is very dangerous and very noticeable.";
	  return new Perk("Delirium", dd, 15, 3, -3, 15, 20, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newPULMONARY_EDEMA_BACTERIA(){
	  String ped = "Blood in the lungs, or Pulmonary Edema, makes a disease more infective, deadly and visible.";
	  return new Perk("Pulmonary Edema", ped, 28, 14, 10, 25, 35, 0, 0, 0, 0, 0, 0, 0);
  }
  public static Perk newHYPOXIA_BACTERIA(){
	  String hd = "Hypoxia, the death of organs, is extremely deadly and will lead to much notice.";
	  return new Perk("Hypoxia", hd, 35, 15, 0, 50, 70, 0, 0, 0, 0, 0, 0, 0);
  }


  public static Perk[] getBacteriaPerks(){

	  Perk sneeze = Perks.newSNEEZING_BACTERIA();
      sneeze.setAvailability(true);
	  Perk cough = Perks.newCOUGHING_BACTERIA();
      cough.setAvailability(true);
	  Perk nausea = Perks.newNAUSEA_BACTERIA();
      nausea.setAvailability(true);
	  Perk rash = Perks.newRASH_BACTERIA();
      rash.setAvailability(true);
	  Perk dehydration = Perks.newDEHYDRATION_BACTERIA();
      dehydration.setAvailability(true);
	  Perk vomiting = Perks.newVOMITING_BACTERIA();
	  Perk dizziness = Perks.newDIZZINESS_BACTERIA();
	  Perk fatigue = Perks.newFATIGUE_BACTERIA();
      fatigue.setAvailability(true);
	  Perk fever = Perks.newFEVER_BACTERIA();
	  Perk diarrhea = Perks.newDIARRHEA_BACTERIA();
	  Perk insomnia = Perks.newINSOMNIA_BACTERIA();
	  Perk lesions = Perks.newLESIONS_BACTERIA();
	  Perk cysts = Perks.newCYSTS_BACTERIA();
	  Perk pulmonaryObstruction = Perks.newPULMONARY_OBSTRUCTION_BACTERIA();
	  Perk inflammation = Perks.newINFLAMMATION_BACTERIA();
	  Perk paralysis = Perks.newPARALYSIS_BACTERIA();
	  Perk coma = Perks.newCOMA_BACTERIA();
	  Perk delirium = Perks.newDELIRIUM_BACTERIA();
	  Perk pulmonaryEdema = Perks.newPULMONARY_EDEMA_BACTERIA();
	  Perk hypoxia = Perks.newHYPOXIA_BACTERIA();
	  sneeze.addToNext(fever);
	  cough.addToNext(pulmonaryObstruction);
	  fever.addToPrev(sneeze);
	  pulmonaryObstruction.addToPrev(cough);
	  nausea.addToNext(vomiting);
	  vomiting.addToPrev(nausea);
	  rash.addToNext(lesions);
	  rash.addToNext(cysts);
	  lesions.addToPrev(rash);
	  cysts.addToPrev(rash);
	  dehydration.addToNext(dizziness);
	  dehydration.addToNext(diarrhea);
	  dizziness.addToPrev(dehydration);
	  diarrhea.addToPrev(dehydration);
	  fatigue.addToNext(insomnia);
	  insomnia.addToPrev(fatigue);
	  fever.addToNext(inflammation);
	  inflammation.addToPrev(fever);
	  pulmonaryObstruction.addToNext(pulmonaryEdema);
	  pulmonaryEdema.addToPrev(pulmonaryObstruction);
	  pulmonaryObstruction.addToNext(hypoxia);
	  hypoxia.addToPrev(pulmonaryObstruction);
	  insomnia.addToNext(delirium);
	  delirium.addToPrev(insomnia);
	  inflammation.addToNext(paralysis);
	  paralysis.addToNext(coma);
	  paralysis.addToPrev(inflammation);
	  coma.addToPrev(paralysis);
	  Perk[] toReturn = new Perk[44];
	  toReturn[0] = sneeze;
	  sneeze.setID(0);
	  toReturn[1] = cough;
	  cough.setID(1);
	  toReturn[2] = nausea;
	  nausea.setID(2);
	  toReturn[3] = rash;
	  rash.setID(3);
	  toReturn[4] = dehydration;
	  dehydration.setID(4);
	  toReturn[5] = vomiting;
	  vomiting.setID(5);
	  toReturn[6] = dizziness;
	  dizziness.setID(6);
	  toReturn[7] = fatigue;
	  fatigue.setID(7);
	  toReturn[8] = fever;
	  fever.setID(8);
	  toReturn[9] = diarrhea;
	  diarrhea.setID(9);
	  toReturn[10] = insomnia;
	  insomnia.setID(10);
	  toReturn[11] = lesions;
	  lesions.setID(11);
	  toReturn[12] = cysts;
	  cysts.setID(12);
	  toReturn[13] = pulmonaryObstruction;
	  pulmonaryObstruction.setID(13);
	  toReturn[14] = inflammation;
	  inflammation.setID(14);
	  toReturn[15] = paralysis;
	  paralysis.setID(15);
	  toReturn[16] = coma;
	  coma.setID(16);
	  toReturn[17] = delirium;
	  delirium.setID(17);
	  toReturn[18] = pulmonaryEdema;
	  pulmonaryEdema.setID(18);
	  toReturn[19] = hypoxia;
	  hypoxia.setID(19);
	  Perk heatResI = Perks.newHEAT_RESISTANCE_I();
      heatResI.setAvailability(true);
	  Perk heatResII = Perks.newHEAT_RESISTANCE_II();
	  Perk heatResIII = Perks.newHEAT_RESISTANCE_III();
	  Perk coldResI = Perks.newCOLD_RESISTANCE_I();
      coldResI.setAvailability(true);
	  Perk coldResII = Perks.newCOLD_RESISTANCE_II();
	  Perk coldResIII = Perks.newCOLD_RESISTANCE_III();
	  Perk wetResI = Perks.newWET_RESISTANCE_I();
      wetResI.setAvailability(true);
	  Perk wetResII = Perks.newWET_RESISTANCE_II();
	  Perk wetResIII = Perks.newWET_RESISTANCE_III();
	  Perk dryResI = Perks.newDRY_RESISTANCE_I();
      dryResI.setAvailability(true);
	  Perk dryResII = Perks.newDRY_RESISTANCE_II();
	  Perk dryResIII = Perks.newDRY_RESISTANCE_III();
	  Perk wetTransI = Perks.newWATER_TRANSMISSION_I();
      wetTransI.setAvailability(true);
	  Perk wetTransII = Perks.newWATER_TRANSMISSION_II();
	  Perk wetTransIII = Perks.newWATER_TRANSMISSION_III();
	  Perk airTransI = Perks.newAIR_TRANSMISSION_I();
      airTransI.setAvailability(true);
	  Perk airTransII = Perks.newAIR_TRANSMISSION_II();
	  Perk airTransIII = Perks.newAIR_TRANSMISSION_III();
	  Perk birdTransI = Perks.newBIRD_TRANSMISSION_I();
      birdTransI.setAvailability(true);
	  Perk birdTransII = Perks.newBIRD_TRANSMISSION_II();
	  Perk birdTransIII = Perks.newBIRD_TRANSMISSION_III();
	  Perk insectTransI = Perks.newINSECT_TRANSMISSION_I();
      insectTransI.setAvailability(true);
	  Perk insectTransII = Perks.newINSECT_TRANSMISSION_II();
	  Perk insectTransIII = Perks.newINSECT_TRANSMISSION_III();
	  heatResI.addToNext(heatResII);
	  heatResII.addToPrev(heatResI);
	  heatResII.addToNext(heatResIII);
	  heatResIII.addToPrev(heatResII);
	  coldResI.addToNext(coldResII);
	  coldResII.addToPrev(coldResI);
	  coldResII.addToNext(coldResIII);
	  coldResIII.addToPrev(coldResII);
	  wetResI.addToNext(wetResII);
	  wetResII.addToPrev(wetResI);
	  wetResII.addToNext(wetResIII);
	  wetResIII.addToPrev(wetResII);
	  dryResI.addToNext(dryResII);
	  dryResII.addToPrev(dryResI);
	  dryResII.addToNext(dryResIII);
	  dryResIII.addToPrev(dryResII);
	  wetTransI.addToNext(wetTransII);
	  wetTransII.addToPrev(wetTransI);
	  wetTransII.addToNext(wetTransIII);
	  wetTransIII.addToPrev(wetTransII);
	  airTransI.addToNext(airTransII);
	  airTransII.addToPrev(airTransI);
	  airTransII.addToNext(airTransIII);
	  airTransIII.addToPrev(airTransII);
	  birdTransI.addToNext(birdTransII);
	  birdTransII.addToPrev(birdTransI);
	  birdTransII.addToNext(birdTransIII);
	  birdTransIII.addToPrev(birdTransII);
	  insectTransI.addToNext(insectTransII);
	  insectTransII.addToPrev(insectTransI);
	  insectTransII.addToNext(insectTransIII);
	  insectTransIII.addToPrev(insectTransII);
	  toReturn[20] = heatResI;
	  heatResI.setID(20);
	  toReturn[21] = heatResII;
	  heatResII.setID(21);
	  toReturn[22] = heatResIII;
	  heatResIII.setID(22);
	  toReturn[23] = coldResI;
	  coldResI.setID(23);
	  toReturn[24] = coldResII;
	  coldResII.setID(24);
	  toReturn[25] = coldResIII;
	  coldResIII.setID(25);
	  toReturn[26] = wetResI;
	  wetResI.setID(26);
	  toReturn[27] = wetResII;
	  wetResII.setID(27);
	  toReturn[28] = wetResIII;
	  wetResIII.setID(28);
	  toReturn[29] = dryResI;
	  dryResI.setID(29);
	  toReturn[30] = dryResII;
	  dryResII.setID(30);
	  toReturn[31] = dryResIII;
	  dryResIII.setID(31);
	  toReturn[32] = wetTransI;
	  wetTransI.setID(32);
	  toReturn[33] = wetTransII;
	  wetTransII.setID(33);
	  toReturn[34] = wetTransIII;
	  wetTransIII.setID(34);
	  toReturn[35] = airTransI;
	  airTransI.setID(35);
	  toReturn[36] = airTransII;
	  airTransII.setID(36);
	  toReturn[37] = airTransIII;
	  airTransIII.setID(37);
	  toReturn[38] = birdTransI;
	  birdTransI.setID(38);
	  toReturn[39] = birdTransII;
	  birdTransII.setID(39);
	  toReturn[40] = birdTransIII;
	  birdTransIII.setID(40);
	  toReturn[41] = insectTransI;
	  insectTransI.setID(41);
	  toReturn[42] = insectTransII;
	  insectTransII.setID(42);
	  toReturn[43] = insectTransIII;
	  insectTransIII.setID(43);

	  return toReturn;

  }
  
}
