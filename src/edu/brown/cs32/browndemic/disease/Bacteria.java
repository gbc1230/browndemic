package edu.brown.cs32.browndemic.disease;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Extends Disease and has all the perks a Bacteria can get and the
 * ability to sell its perks cumulatively or individual and GAIN money
 * from those sales
 * @author bkoatz
 */
public class Bacteria extends Disease{

    final private double MAX_INFECTIVITY = 59;
    final private double MAX_LETHALITY = 217;
    final private double MAX_VISIBILITY = 279;
    final private String FILE_PATH = "Bacteria.csv";

    public Bacteria(String tempname){
    
        this._name = tempname;
        try {
          this._perks = Perks.getPerks(FILE_PATH);
        } catch (FileNotFoundException ex) {
            System.out.println("Bacteria file not found!");
        } catch (IOException ex) {
            System.out.println("Problem with bacteria file!");
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown filed in the bacteria file!!");
            ex.printStackTrace();
        }
        this._perks[0].setAvailability(true);
        this._perks[1].setAvailability(true);
        this._perks[2].setAvailability(true);
        this._perks[3].setAvailability(true);
        this._perks[4].setAvailability(true);
        this._perks[7].setAvailability(true);
        this._perks[20].setAvailability(true);
        this._perks[23].setAvailability(true);
        this._perks[26].setAvailability(true);
        this._perks[29].setAvailability(true);
        this._perks[32].setAvailability(true);
        this._perks[35].setAvailability(true);
        this._perks[38].setAvailability(true);
        this._perks[41].setAvailability(true);
        this._perks[44].setAvailability(true);
        this._infectivity = 2;
        this._visibility = 1;
        this._lethality = 3;
        
    }

    public Bacteria(String tempname, String filepath){

        this._name = tempname;
        try {
          this._perks = Perks.getPerks(filepath);
        } catch (FileNotFoundException ex) {
            System.out.println("Bacteria file not found!");
        } catch (IOException ex) {
            System.out.println("Problem with bacteria file!");
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown filed in the bacteria file!!");
            ex.printStackTrace();
        }
        this._perks[0].setAvailability(true);
        this._perks[1].setAvailability(true);
        this._perks[2].setAvailability(true);
        this._perks[3].setAvailability(true);
        this._perks[4].setAvailability(true);
        this._perks[7].setAvailability(true);
        this._perks[20].setAvailability(true);
        this._perks[23].setAvailability(true);
        this._perks[26].setAvailability(true);
        this._perks[29].setAvailability(true);
        this._perks[32].setAvailability(true);
        this._perks[35].setAvailability(true);
        this._perks[38].setAvailability(true);
        this._perks[41].setAvailability(true);
        this._perks[44].setAvailability(true);
        this._infectivity = 2;
        this._visibility = 1;

    }

    @Override
    public List<Perk> getSellablePerks() {

        List<Perk> ans = new ArrayList<Perk>();
        for(Perk p : this.getPerks()){
            if(p.isOwned()) ans.add(p);
        }
        return ans;

    }

    @Override
    public void buyRandomPerk() {}

    @Override
    public double getMaxInfectivity() {
        return this.MAX_INFECTIVITY;
    }

    @Override
    public double getMaxLethality() {
        return this.MAX_LETHALITY;
    }

    @Override
    public double getMaxVisibility() {
        return this.MAX_VISIBILITY;
    }

    @Override
    public void sellCumPerk(int perkID) throws IllegalAccessException {
        if(!this._perks[perkID].isOwned()){

            throw new IllegalAccessException();

        }

        this._perks[perkID].setOwned(false);
        Perk soldPerk = this._perks[perkID];

        for(Integer p: soldPerk.getNext()){

            if(this._perks[p].isOnlyOwnedPrev(this._perks[perkID])){
                this._perks[p].setAvailability(false);
                if(this._perks[p].isOwned())
                    this.sellCumPerk(p);
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
    }

    @Override
    public void sellPerk(int perkID) throws IllegalAccessException {
        if(!this._perks[perkID].isOwned()){

            throw new IllegalAccessException();

       }
       for(Integer p : this._perks[perkID].getNext()){

            if(this._perks[p].isOwned()) throw new IllegalAccessException();

       }
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
    }
    
}
