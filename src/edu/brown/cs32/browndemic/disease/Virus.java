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
 * @author bkoatz
 */
public class Virus extends Disease{

    final private double MAX_INFECTIVITY = 62;
    final private double MAX_LETHALITY = 217;
    final private double MAX_VISIBILITY = 281;
    final private String FILE_PATH = "Virus.csv";

    public Virus(String tempname){
    
        this._name = tempname;
        try {
          this._perks = Perks.getPerks(FILE_PATH);
        } catch (FileNotFoundException ex) {
            System.out.println("Virus file not found!!");
        } catch (IOException ex) {
            System.out.println("Problem with virus file!!");
            ex.printStackTrace();
        }catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown filed in the virus file!!");
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
        this._infectivity = 5;
        this._visibility = 3;
        this._lethality = 1;
        
    }

    public Virus(String tempname, String filepath){

        this._name = tempname;
        try {
          this._perks = Perks.getPerks(filepath);
        } catch (FileNotFoundException ex) {
            System.out.println("Virus file not found!!");
        } catch (IOException ex) {
            System.out.println("Problem with virus file!!");
            ex.printStackTrace();
        }catch (NoSuchFieldException ex) {
            System.out.println("Missing/Unknown filed in the virus file!!");
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
        this._infectivity = 5;
        this._visibility = 3;

    }

    @Override
    public List<Perk> getSellablePerks(){

        List<Perk> ans = new ArrayList<Perk>();
        for (Perk p : _perks){
            if (p.isOwned() && p.getSellPrice() <= this._points)
                ans.add(p);
        }
        return ans;

    }

    @Override
    public void buyRandomPerk(){
        if((int)Math.random()*540 == 432)
            try {
            this.buyPerkWithoutPay(this.getAvailablePerks().get(0).getID());
        } catch (IllegalAccessException ex) {}
    }

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
        if(!this._perks[perkID].isOwned() ||
                this._perks[perkID].getCumSellPrice() > this._points){

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
        this._points -= soldPerk.getSellPrice();
    }

    @Override
    public void sellPerk(int perkID) throws IllegalAccessException {
        if(!this._perks[perkID].isOwned() ||
                this._perks[perkID].getSellPrice() > this._points){

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
        this._points -= soldPerk.getSellPrice();
    }
}
