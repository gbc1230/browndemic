package edu.brown.cs32.browndemic.disease;

/**
 *
 * @author bkoatz
 */
public class Bacteria extends Disease implements Perks{
    
    public Bacteria(String tempname){
    
        this._name = tempname;
        this._perks = new Perk[10];
        this._perks[0] = VOMITING_BACTERIA;
        this._perks[0].setID(0);
        
    }
    
    @Override
    public void sellPerk(int perkID) throws IllegalAccessException{

        if(!this._perks[perkID].isOwned()){
            
            throw new IllegalAccessException();
            
        }
        
        this._perks[perkID].setOwned(false);
        Perk soldPerk = this._perks[perkID];
        
        for(Perk p: soldPerk.getNext()){
            
            this._perks[p.getID()].setAvailability(false);
            
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
