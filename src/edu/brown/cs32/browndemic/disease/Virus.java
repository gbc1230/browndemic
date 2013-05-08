package edu.brown.cs32.browndemic.disease;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Extends the disease class, and has all the perks a virus can get
 * and the ability to sell its perks cumulatively or individually, and
 * LOSE money from that sale
 * 
 * Viruses start off more infective, more visible and less deadly.
 * 
 * @author bkoatz
 */
public class Virus extends Disease{

    //SerialVersionUID for this disease
    private static final long serialVersionUID = -1644028364764513072L;
    //Maximum infectivity
    final private double MAX_INFECTIVITY = 134;
    //Maximum lethality
    final private double MAX_LETHALITY = 218;
    //Maximum visibility
    final private double MAX_VISIBILITY = 288;
    //Starting infectivity
    final private double START_INFECTIVITY = 5;
    //Starting lethality
    final private double START_LETHALITY = 1;
    //Starting visibility
    final private double START_VISIBILITY = 10;
    //the size of this disease's perk array
    final private int PERK_ARRAY_SIZE = 47;
    //The hardcoded file path for this disease's perks
    final private String FILE_PATH = "Virus.csv";

    //Constructor using built in file path for the virus perks
    public Virus(String tempname){
    
        this._name = tempname;
        try {
          this._perks = PerkMaker.getPerks(FILE_PATH, PERK_ARRAY_SIZE);
        } catch (FileNotFoundException ex) {
            System.out.println("Virus file not found!!");
        } catch (IOException ex) {
            System.out.println("Problem with virus file!!");
            ex.printStackTrace();
        }catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown field in the virus file!!");
            ex.printStackTrace();
        }
        //these perks are for a virus
        for(Perk p : this._perks) p.makeVirusPerk();
        //Sets the appropriate perks to initially available
        int[] availablePerks = {0, 1, 2, 3, 4, 7, 20, 23, 26, 29, 32, 35, 38,
                                41, 44};
        for(Integer i : availablePerks) this._perks[i].setAvailability(true);
        this._infectivity = START_INFECTIVITY;
        this._lethality = START_LETHALITY;
        this._visibility = START_VISIBILITY;
        
    }

    //Constructor with an inputted filepath
    public Virus(String tempname, String filepath){

        this._name = tempname;
        try {
          this._perks = PerkMaker.getPerks(filepath, PERK_ARRAY_SIZE);
        } catch (FileNotFoundException ex) {
            System.out.println("Virus file not found!!");
        } catch (IOException ex) {
            System.out.println("Problem with virus file!!");
            ex.printStackTrace();
        }catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown filed in the virus file!!");
            ex.printStackTrace();
        }
        //these perks are for a virus
        for(Perk p : this._perks) p.makeVirusPerk();
        //Sets the appropriate perks to initially available
        int[] availablePerks = {0, 1, 2, 3, 4, 7, 20, 23, 26, 29, 32, 35, 38,
                                41, 44};
        for(Integer i : availablePerks) this._perks[i].setAvailability(true);
        this._infectivity = START_INFECTIVITY;
        this._lethality = START_LETHALITY;
        this._visibility = START_VISIBILITY;

    }

    /**
     * Gets the perks that this virus can afford to sell
     * @return       the list of perks available to be sold by this virus
     */
    @Override
    public List<Perk> getSellablePerks(){

        List<Perk> ans = new ArrayList<Perk>();
        for (Perk p : _perks){
            if (p.isOwned() && p.getCumSellPrice() <= this._points)
                ans.add(p);
        }
        return ans;

    }

    /**
     * Has a non-zero chance to buy a randomly available perk for this virus
     * and give it points.
     */
    @Override
    public void randomPerkEvents(){
        if((int)Math.random()*540 == 432)
            try {
                this.buyPerkWithoutPay(this.getAvailablePerks().get(0).getID());
            } catch (IllegalAccessException ex) {}
        if((int)Math.random()*540 == 432){
        	this._numRandomPointsGot+=3;
            this.addPoints(3);
        }
    }

    //Gets the maximum infectivity for this disease
    @Override
    public double getMaxInfectivity() {
        return this.MAX_INFECTIVITY;
    }

    //Gets the maximum lethality for this disease
    @Override
    public double getMaxLethality() {
        return this.MAX_LETHALITY;
    }

    //Gets the maximum visibility for this disease
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
     * Sells this perk and all owned perks directly reliant on it
     * @param perkID                 the id of the first perk to be sold
     * @throws IllegalAccessException  if this perk isn't owned or
     *                                 the cost of selling this perk and all reliant
     *                                 perks is more than you have.
     */
    @Override
    public void sellCumPerk(int perkID) throws IllegalAccessException {
        
    	if(!this._perks[perkID].isOwned() ||
                this._perks[perkID].getCumSellPrice()*-1 > this._points){

            throw new IllegalAccessException();

        }
    	
        Perk soldPerk = this._perks[perkID];
        
        for(Integer p: soldPerk.getNext()){

            if(this._perks[p].isOnlyOwnedPrev(this._perks[perkID])){
                this._perks[p].setAvailability(false);
                if(this._perks[p].isOwned())
                    this.sellCumPerk(p);
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
        this._numPointsUsed+=-1*soldPerk.getSellPrice();
        this._numPerksSold++;
    }

    /**
     * Sells this perk
     * 
     * @param perkID                  the id of the perk to be sold
     * @throws IllegalAccessException if this perk isn't owned, if it costs more
     *                                to sell it than you have, or if perks after
     *                                this perk are owned and solely reliant on 
     *                                this perk
     */
    @Override
    public void sellPerk(int perkID) throws IllegalAccessException {
        if(!this._perks[perkID].isOwned() ||
                this._perks[perkID].getSellPrice()*-1 > this._points){

            throw new IllegalAccessException();

       }
       for(Integer p : this._perks[perkID].getNext()){

            if(this._perks[p].isOnlyOwnedPrev(this._perks[perkID]) && 
                   this._perks[p].isOwned()) throw new IllegalAccessException();

       }
       this._numPerksSold++;
       this._perks[perkID].setOwned(false);
       Perk soldPerk = this._perks[perkID];
       for(Integer p: soldPerk.getNext()){

            if(this._perks[p].isOnlyOwnedPrev(this._perks[perkID])){
                this._perks[p].setAvailability(false);
            }

        }
        this._infectivity -= soldPerk.getInf();
        this._lethality -= soldPerk.getLeth();
        this._visibility -= soldPerk.getVis();
        this._heatResistance -= soldPerk.getHeatRes();
        this._coldResistance -= soldPerk.getColdRes();
        this._wetResistance -= soldPerk.getWetRes();
        this._dryResistance -= soldPerk.getDryRes();
        this._medResistance -= soldPerk.getMedRes();
        this._points += soldPerk.getSellPrice();
        this._numPointsUsed+=-1*soldPerk.getSellPrice();
    }
}
