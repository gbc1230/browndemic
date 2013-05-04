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

    final private double MAX_INFECTIVITY = 59;
    final private double MAX_LETHALITY = 217;
    final private double MAX_VISIBILITY = 279;
    final private String FILE_PATH = "Parasite.csv";

    public Parasite(String tempname){

        this._name = tempname;
        try {
          this._perks = PerkMaker.getPerks(FILE_PATH);
        } catch (FileNotFoundException ex) {
            System.out.println("Parasite file not found!");
        } catch (IOException ex) {
            System.out.println("Problem with parasite file!");
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown filed in the parasite file!!");
            ex.printStackTrace();
        }
        int[] availablePerks = {0, 1, 2, 3, 4, 7, 20, 23, 26, 29, 32, 35, 38,
                                41, 44};
        for(Integer i : availablePerks) this._perks[i].setAvailability(true);
        this._infectivity = 1;
        this._visibility = 1;
        this._lethality = 5;

    }

    public Parasite(String tempname, String filepath){

        this._name = tempname;
        try {
          this._perks = PerkMaker.getPerks(filepath);
        } catch (FileNotFoundException ex) {
            System.out.println("Parasite file not found!");
        } catch (IOException ex) {
            System.out.println("Problem with parasite file!");
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown filed in the parasite file!!");
            ex.printStackTrace();
        }
        int[] availablePerks = {0, 1, 2, 3, 4, 7, 20, 23, 26, 29, 32, 35, 38,
                                41, 44};
        for(Integer i : availablePerks) this._perks[i].setAvailability(true);
        this._infectivity = 1;
        this._visibility = 1;
        this._lethality = 5;

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

            if(this._perks[p].isOnlyOwnedPrev(this._perks[perkID]) &&
                   this._perks[p].isOwned()) throw new IllegalAccessException();

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

    @Override
    public List<Perk> getSellablePerks() {
         List<Perk> ans = new ArrayList<Perk>();
        for(Perk p : this.getPerks()){
            if(p.isOwned()) ans.add(p);
        }
        return ans;

    }

}
