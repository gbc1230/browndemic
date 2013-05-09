package edu.brown.cs32.browndemic.disease;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Extends Disease and has all the perks a Parasite can get and the
 * ability to sell its perks cumulatively or individual and GAIN money
 * from those sales
 * 
 * Parasite start off about not visible and infective, but lethal.
 * 
 * @author bkoatz
 */
public class Parasite extends Disease{

    //SerialVersionUID for this disease
    private static final long serialVersionUID = -6836486974707322172L;
    //Maximum infectivity
    final private double MAX_INFECTIVITY = 139;
    //Maximum lethality
    final private double MAX_LETHALITY = 243;
    //Maximum visibility
    final private double MAX_VISIBILITY = 322;
    //Starting infectivity
    final private double START_INFECTIVITY = 1;
    //Starting lethality
    final private double START_LETHALITY = 5;
    //Starting visibility
    final private double START_VISIBILITY = 1;
    //the size of this disease's perk array
    final private int PERK_ARRAY_SIZE = 50;
    //The file path for this disease's perks
    final private String FILE_PATH = "resources/Parasite.csv";

    //Constructor using built in file path for the parasite perks
    public Parasite(String tempname){

        this._name = tempname;
        try {
          this._perks = PerkMaker.getPerks(FILE_PATH, PERK_ARRAY_SIZE);
        } catch (FileNotFoundException ex) {
            System.out.println("Parasite file not found!");
        } catch (IOException ex) {
            System.out.println("Problem with parasite file!");
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown field in the parasite file!!");
            ex.printStackTrace();
        }
        //Sets the appropriate perks to initially available
       int[] availablePerks = {0, 1, 2, 3, 5, 23, 26, 29, 32, 35, 38, 41, 44,
                                47};
        for(int i : availablePerks) this._perks[i].setAvailability(true);
        this._infectivity = 1;
        this._visibility = 1;
        this._lethality = 5;

    }

    //Constructor with an inputted filepath
    public Parasite(String tempname, String filepath){

        this._name = tempname;
        try {
          this._perks = PerkMaker.getPerks(filepath, PERK_ARRAY_SIZE);
        } catch (FileNotFoundException ex) {
            System.out.println("Parasite file not found!");
        } catch (IOException ex) {
            System.out.println("Problem with parasite file!");
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown filed in the parasite file!!");
            ex.printStackTrace();
        }
        //Sets the appropriate perks to initially available
        int[] availablePerks = {0, 1, 2, 3, 5, 23, 26, 29, 32, 35, 38, 41, 44,
                                47};
        for(Integer i : availablePerks) this._perks[i].setAvailability(true);
        this._infectivity = 1;
        this._visibility = 1;
        this._lethality = 5;

    }

    //Slight chance that this parasite gets random points
    @Override
    public void randomPerkEvents() {
    	
    	if(Math.random() < .00001111)
            try {
                this.buyPerkWithoutPay(this.getAvailablePerks().get((int)(Math.random()*this.getAvailablePerks().size())).getID());
            } catch (IllegalAccessException ex) {}
        if(Math.random() < .0001111){
        	this._numPointsEarned+=5;
            this.addPoints(5);

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
        this._numPointsEarned+=soldPerk.getSellPrice();
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
        this._numPointsEarned+=soldPerk.getSellPrice();
    }

    /**
     * Gets the perks that can be sold by this disease (all owned perks, for a
     * parasite)
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

}
