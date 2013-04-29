package edu.brown.cs32.browndemic.disease;
/**
 * Extends the disease class, and has all the perks a virus can get
 * and the ability to sell its perks cumulatively or individually, and
 * LOSE money from that sale
 * 
 * @author bkoatz
 */
public class Virus extends Disease{

    public Virus(String tempname){
    
        this._name = tempname;
        this._perks = Perks.getVirusPerks();
        
    }

    @Override
    public void sellPerk(int perkID) throws IllegalAccessException{

       if(!this._perks[perkID].isOwned()){

            throw new IllegalAccessException();

       }
       this._perks[perkID].setOwned(false);
       Perk soldPerk = this._perks[perkID];
       for(Perk p : this._perks[perkID].getNext()){

            if(p.isOwned()) throw new IllegalAccessException();

       }
       for(Perk p: soldPerk.getNext()){

            if(p.isOnlyOwnedPrev(this._perks[perkID])){
                this._perks[p.getID()].setAvailability(false);
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

    @Override
    public void sellCumPerk(int perkID) throws IllegalAccessException{

        if(!this._perks[perkID].isOwned()){
            
            throw new IllegalAccessException();
            
        }
        
        this._perks[perkID].setOwned(false);
        Perk soldPerk = this._perks[perkID];
        
        for(Perk p: soldPerk.getNext()){

            if(p.isOnlyOwnedPrev(this._perks[perkID])){
                this._perks[p.getID()].setAvailability(false);
                if(this._perks[p.getID()].isOwned())
                    this.sellCumPerk(p.getID());
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
