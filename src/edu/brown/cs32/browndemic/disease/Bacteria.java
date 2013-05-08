package edu.brown.cs32.browndemic.disease;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Extends Disease and has all the perks a Bacteria can get and the
 * ability to sell its perks cumulatively or individual and GAIN money
 * from those sales
 * 
 * Bacteria start off about evenly visible, lethal and infective
 * 
 * @author bkoatz
 */
public class Bacteria extends Disease{

    //SerialVersionUID for this disease
    private static final long serialVersionUID = 9086224726997457013L;
    //Maximum infectivity
    final private double MAX_INFECTIVITY = 133;
    //Maximum lethality
    final private double MAX_LETHALITY = 297;
    //Maxium visibility
    final private double MAX_VISIBILITY = 399;
    //Starting infectivity
    final private double START_INFECTIVITY = 2;
    //Starting lethality
    final private double START_LETHALITY = 1;
    //Starting visibility
    final private double START_VISIBILITY = 3;
    //the size of this disease's perk array
    final private int PERK_ARRAY_SIZE = 47;
    //The path to the file with perks for the bacteria
    final private String FILE_PATH = "Bacteria.csv";

    //Constructor using built in file path for the bacteria perks
    public Bacteria(String tempname){
    
        this._name = tempname;
        try {
          this._perks = PerkMaker.getPerks(FILE_PATH, PERK_ARRAY_SIZE);
        } catch (FileNotFoundException ex) {
            System.out.println("Bacteria file not found!");
        } catch (IOException ex) {
            System.out.println("Problem with bacteria file!");
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown field in the bacteria file!!");
            ex.printStackTrace();
        }
        //Sets the appropriate perks to initially available
        int[] availablePerks = {0, 1, 2, 3, 4, 5, 20, 23, 26, 29, 32, 35, 38,
                                41, 44};
        for(Integer i : availablePerks) this._perks[i].setAvailability(true);
        this._infectivity = this.START_INFECTIVITY;
        this._lethality = this.START_LETHALITY;
        this._visibility = this.START_VISIBILITY;
        
    }

    //Constructor with an inputted filepath
    public Bacteria(String tempname, String filepath){

        this._name = tempname;
        try {
          this._perks = PerkMaker.getPerks(filepath, PERK_ARRAY_SIZE);
        } catch (FileNotFoundException ex) {
            System.out.println("Bacteria file not found!");
        } catch (IOException ex) {
            System.out.println("Problem with bacteria file!");
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown filed in the bacteria file!!");
            ex.printStackTrace();
        }
        //Sets the appropriate perks to initially available
        int[] availablePerks = {0, 1, 2, 3, 4, 7, 20, 23, 26, 29, 32, 35, 38,
                                41, 44};
        for(Integer i : availablePerks) this._perks[i].setAvailability(true);
        this._infectivity = this.START_INFECTIVITY;
        this._lethality = this.START_LETHALITY;
        this._visibility = this.START_VISIBILITY;

    }

    /**
     * Gets the perks that can be sold by this disease (all owned perks, for a
     * bacteria)
     * @return the list of perks available to be sold by this disease
     */
    @Override
    public List<Perk> getSellablePerks() {

        List<Perk> ans = new ArrayList<Perk>();
        for(Perk p : this.getPerks()){
            if(p.isOwned()) ans.add(p);
        }
        return ans;

    }

    //Mild chance of getting a random point
    @Override
    public void randomPerkEvents() {

    	if(Math.random() < .00011111)
            try {
                this.buyPerkWithoutPay(this.getAvailablePerks().get(0).getID());
            } catch (IllegalAccessException ex) {}
        if(Math.random() < .0011111){

        	this._numRandomPointsGot+=3;
            this.addPoints(3);

        }
        
    }

    //Gets the max infectivity this disease can have
    @Override
    public double getMaxInfectivity() {
        return this.MAX_INFECTIVITY;
    }

    //Gets the max lethality this disease can have
    @Override
    public double getMaxLethality() {
        return this.MAX_LETHALITY;
    }

    //Gets the max visibility this disease can have
    @Override
    public double getMaxVisibility() {
        return this.MAX_VISIBILITY;
    }
    
    /**
     * gets the starting infectivity this disease has
     * @return START_INFECTIVITY
     */
    @Override
    public double getStartInfectivity(){
        return this.START_INFECTIVITY;
    }

    /**
     * gets the starting letahlity this disease has
     * @return START_LETHALITY
     */
    @Override
    public double getStartLethality(){
        return this.START_LETHALITY;
    }

    /**
     * gets the starting visibility this disease has
     * @return START_LETHALITY
     */
    @Override
    public double getStartVisibility(){
        return this.START_VISIBILITY;
    }

    /**
     * Sells this perk and all owned perks that directly rely on it.
     * @param perkID                   the id of the perk to be sold
     * @throws IllegalAccessException  if you try to sell an unowned perks.
     */
    @Override
    public void sellCumPerk(int perkID) throws IllegalAccessException {
        if(!this._perks[perkID].isOwned()){

            throw new IllegalAccessException();

        }

        this._numPerksSold++;
        Perk soldPerk = this._perks[perkID];

        for(Integer i: soldPerk.getNext()){

            if(this._perks[i].isOnlyOwnedPrev(this._perks[perkID])){
                this._perks[i].setAvailability(false);
                if(this._perks[i].isOwned())
                    this.sellCumPerk(i);
            }

        }

        this._perks[perkID].setOwned(false);
        this._infectivity -= soldPerk.getInf();
        this._lethality -= soldPerk.getLeth();
        this._visibility -= soldPerk.getVis();
        this._heatResistance -= soldPerk.getHeatRes();
        this._coldResistance -= soldPerk.getColdRes();
        this._wetResistance -= soldPerk.getWetRes();
        this._dryResistance -= soldPerk.getDryRes();
        this._medResistance -= soldPerk.getMedRes();
        this._points += soldPerk.getSellPrice();
    }

    /**
     * Sells this perk
     * @param perkID                   the id of the perk to be sold
     * @throws IllegalAccessException  thrown if the perk is not owned, or
     *                                 if perks after it are also owned and solely
     *                                 reliant on this perk to exist.
     */
    @Override
    public void sellPerk(int perkID) throws IllegalAccessException {
        if(!this._perks[perkID].isOwned()){

            throw new IllegalAccessException();

       }
       for(Integer i : this._perks[perkID].getNext()){

            if(this._perks[i].isOnlyOwnedPrev(this._perks[perkID]) &&
                   this._perks[i].isOwned()) throw new IllegalAccessException();

       }
   
       this._numPerksSold++;
       Perk soldPerk = this._perks[perkID];
       for(Integer i: soldPerk.getNext()){

            if(this._perks[i].isOnlyOwnedPrev(this._perks[perkID])){
                this._perks[i].setAvailability(false);
            }

        }
       this._perks[perkID].setOwned(false);
        this._infectivity -= soldPerk.getInf();
        this._lethality -= soldPerk.getLeth();
        this._visibility -= soldPerk.getVis();
        this._heatResistance -= soldPerk.getHeatRes();
        this._coldResistance -= soldPerk.getColdRes();
        this._wetResistance -= soldPerk.getWetRes();
        this._dryResistance -= soldPerk.getDryRes();
        this._medResistance -= soldPerk.getMedRes();
        this._points += soldPerk.getSellPrice();

    }
    
}
