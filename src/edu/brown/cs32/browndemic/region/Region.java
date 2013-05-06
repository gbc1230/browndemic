/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.region;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.brown.cs32.browndemic.disease.Disease;

/**
 *
 * @author ckilfoyl
 */
public class Region implements Serializable{
	private static final long serialVersionUID = 4373669006142652684L;

    //ArrayList of all land neighboring Regions by String name
	private ArrayList<Integer> _landNeighbors;

    //ArrayList of all sea neighboring Regions by String name
    private ArrayList<Integer> _waterNeighbors;

    //Hashmap of all Regions by ID
    private HashMap<Integer, Region> _regions;

    //ArrayList of diseases in this Region
    private Disease[] _diseases;

    private double _awareMax;
    
    private double[] _infDoubleTime;
    private static final int _INFTIMESCALE = 120;
    private static final double _INFSCALE = 3;
    
    private double[] _lethDoubleTime;
    private static final int _LETHTIMESCALE = 720;
    private static final double _LETHSCALE = 3;

    private static final int _PLANEFREQ = 240;
    private static final int _SHIPFREQ = 240;
    private static final int _LANDFREQ = 40;

    //number of diseases in game
    private int _numDiseases;
    
    //Random number generator for this region
    private Random _rand;

    //Custom HashMap to keep track of overlapping infected populations
    private PopHash _hash;

    //Total Region Population
    private long _population;

    //ArrayList of dead, order corresponds to the diseases in _disease
    private long[] _dead;

    //ArrayList of boolean isCure, order corresponds to diseases in _disease
    private boolean[] _hasCure;

    //ArrayList of double awaresness for each disease
    private double[] _awareness;

    //ArrayList of double cure progress for each disease
    private long[] _cureProgress;

    //Unique Region name
    //emphasis on the unique, some code in here runs on that assumption (hash, equals, etc.)
    private String _name;

    //Unique Int ID of this region
    private int _ID;

    //number of seaports and airports open in this Region
    private int _sea;
    private int _air;
    
    //wealth of this Region (reflects infrastructure, productivity, actual wealth, etc.)
    private double _wealth,  _wet,  _dry,  _heat,  _cold, _med;
    //Lists of transmissions to other regions and news
    private ArrayList<RegionTransmission> _transmissions;
    private ArrayList<String> _news;
    
    private ArrayList<Integer> _disIDs;
    private ArrayList<NaturalDisaster> _disasters;
    
    private double _remInf;

    /**
     * constructs a new Region with the given info
     * @param name The unique String name
     * @param population the initial population count
     * @param neighbors the names of all bordering Regions
     * @param seaports if this Region has open seaports
     * @param airports if this Region has open airports
     */
    public Region(int ID, String name, long population, List<Integer> landNeighbors,
            List<Integer> waterNeighbors, HashMap<Integer, Region> hash,
            int airports, int seaports, double wealth, double wet, double dry,
            double heat, double cold, double med, List<NaturalDisaster> disasters, 
            List<Integer> disIDs) {
        _name = name;
        _ID = ID;
        _population = population;
        _landNeighbors = new ArrayList<>(landNeighbors);
        _waterNeighbors = new ArrayList<>(waterNeighbors);
        _regions = hash;
        _sea = seaports;
        _air = airports;
        _wealth = wealth;
        _wet = wet;
        _dry = dry;
        _heat = heat;
        _cold = cold;
        _med = med;
        _transmissions = new ArrayList<>();
        _news = new ArrayList<>();
        _rand = new Random();
        _remInf = 0;
        _disIDs = new ArrayList<>(disIDs);
        _disasters = new ArrayList<>(disasters);
    }

    /**
     * The update method for this region
     */
    public void update() {
        updateCures();
        naturalDisaster();
        for (Disease d : _diseases) {
            if (null != d) {
                updateAwareness(d,getAwareIncrement(d));
                if(_hasCure[d.getID()])
                    cure(d);
                kill(d);
                infect(d);
                transmitSeaAndAir(d);
                transmitToLandNeighbors(d);
                transmitToWaterNeighbors(d);
            }
        }
    }
    
    /**
     * calculates the number of pop to be infected
     * @param d the index of the disease
     * @param pop the population to infect
     * @return how many to infect
     */
    public long getTotNumInfected(int d) {
        double wetResFactor = 1.0;
        double dryResFactor = 1.0;
        double heatResFactor = 1.0;
        double coldResFactor = 1.0;
        double medResFactor = 1.0;
        if(_diseases[d].getWetRes() < _wet)
            wetResFactor = _diseases[d].getWetRes()/_wet;
        if(_diseases[d].getDryRes() < _dry)
            dryResFactor = _diseases[d].getDryRes()/_dry;
        if(_diseases[d].getHeatRes() < _heat)
            heatResFactor = _diseases[d].getHeatRes()/_heat;
        if(_diseases[d].getColdRes() < _cold)
            coldResFactor = _diseases[d].getColdRes()/_cold;
        if(_diseases[d].getMedRes() < _med)
            medResFactor = _diseases[d].getMedRes()/_med;
        double maxInf = _diseases[d].getMaxInfectivity();
        double infectivity = _diseases[d].getInfectivity();
        double inf = getInfected().get(d);
        double growthFactor =  1 + (infectivity + maxInf/_INFSCALE) / (maxInf/_INFSCALE) *
                ( wetResFactor + dryResFactor + heatResFactor + coldResFactor + medResFactor)/5;
        System.out.println("Inf growth factor: " + growthFactor);
        double number = inf*(Math.pow(growthFactor,_infDoubleTime[d]/_INFTIMESCALE) - 1);
        System.out.println("rate: " + (Math.pow(growthFactor,_infDoubleTime[d]/_INFTIMESCALE) - 1));
        System.out.println("infect total: " + number);
        if(_remInf >= 1){
            number++;
            _remInf--;
        }
        _remInf += number % 1;
        if(_rand.nextInt(_INFTIMESCALE) == 0){
            number = Math.ceil(number);
        }
        else
            number = Math.floor(number);
        return (long) number;
    }

    /**
     * infect(Disease) updates the infected for the given disease
     * @param disease the disease to update for
     **/
    public void infect(Disease disease) {
        int index = disease.getID();
        double totNumber = getTotNumInfected(index);
        double uninf = 0;
        for(InfWrapper inf : _hash.getAllOfType(index,0))
            uninf += inf.getInf();
        for(InfWrapper inf : _hash.getAllOfType(index,0)){
            double ratio = inf.getInf()/uninf;
            long number = (long) Math.ceil(totNumber*ratio);
            System.out.println("infect: " + number);
            String infID = inf.getID().substring(0,index) + "1" + inf.getID().substring(index + 1);
            if (inf.getInf() < number){
                _hash.put(new InfWrapper(inf.getID(), 0L));
                _hash.put(new InfWrapper(infID, _hash.get(infID).getInf() + inf.getInf()));
            } else {
                _hash.put(new InfWrapper(inf.getID(), inf.getInf() - number));
                _hash.put(new InfWrapper(infID, _hash.get(infID).getInf() + number));
            }
        }
    }

    /**
     * infect(Disease, int) updates the dead for a given disease
     * @param disease the disease to update dead for
     **/
    public void kill(Disease disease) {
        int index = disease.getID();
        double leth = disease.getLethality();
        double max = disease.getMaxLethality();
        for (InfWrapper inf : _hash.getAllOfType(index,1)) {
            double rate = 1 - (leth + max/_LETHSCALE)/(max/_LETHSCALE);
//            System.out.println("Kill growth factor: " + rate);
            double number = (1 - Math.pow(rate, _lethDoubleTime[disease.getID()]/_LETHTIMESCALE)) * inf.getInf();
//            System.out.println("Kill: " + number);
            number = number/4;
            number -= 10000;
            if(leth / max > .1)
                number = Math.ceil(number);
            else number = 0;
            if(number < 1)
                number = 0;
            if (inf.getInf() < number) {
                _dead[index] = _dead[index] + inf.getInf();
                _hash.put(new InfWrapper(inf.getID(), 0L));
            } else {
                _dead[index] =  _dead[index] + (long)number;
                _hash.put(new InfWrapper(inf.getID(), inf.getInf() - (long)number));
            }
        }
    }

    /**
     * calculates the number of people to cure in a given population
     * @param d
     * @param pop
     * @return
     */
    public double getTotNumCured(int d) {
        //TODO right now cured just cures 5% of total pop per tick
        double number = Math.ceil(0.005 * _population);
        return number;
    }

    /**
     * cure(Disease) updates the number of cured for this disease
     * @param d the disease to update cured for
     */
    public void cure(Disease d) {
        int index = d.getID();
        _hasCure[index] = true;
        if (_diseases[index] != null) {
            ArrayList<InfWrapper> infected = _hash.getAllOfType(index, 1);
            double totCured = getTotNumCured(index);
            double numInfected = getInfected().get(index);
            for (InfWrapper inf : infected) {
                double popRatio = inf.getInf() / numInfected;
                String cureID = inf.getID().substring(0, index) + "2" + inf.getID().substring(index + 1);
                long number = (long) Math.ceil(totCured * popRatio);
                if (inf.getInf() < number) {
                    _hash.put(new InfWrapper(cureID, _hash.get(cureID).getInf() + inf.getInf()));
                    _hash.put(new InfWrapper(inf.getID(), 0L));
                } else {
                    _hash.put(new InfWrapper(inf.getID(), inf.getInf() - number));
                    _hash.put(new InfWrapper(cureID, _hash.get(cureID).getInf() + number));
                }
            }
        }
    }

    /**
     * updates cureProgress for each disease
     */
    public void updateCures(){
        double weightedPop = _hash.getZero().getInf();
        ArrayList<Long> inf = getInfected();
        for(int i = 0; i < _numDiseases; i++){
            if(_diseases[i] != null){
                double max = _diseases[i].getMaxLethality();
                weightedPop += inf.get(i) * max/(max + _diseases[i].getLethality());
            }
        }
        for(int j = 0; j < _numDiseases; j++){
            if(_awareness[j] > _awareMax/4 && _diseases[j] != null){
                double medFactor = _med/_diseases[j].getMedRes();
                if(medFactor > 1)
                    medFactor = 1;
                _cureProgress[j] = _cureProgress[j] + (long) (_wealth*weightedPop*_med/_diseases[j].getMedRes());
            }
        }
    }

    /**
     * returns an ArrayList of cureProgress so far
     * @return
     */
    public ArrayList<Long> getCures(){
        ArrayList<Long> cures = new ArrayList<>();
        for(int i = 0; i < _numDiseases; i++){
            if(_diseases[i] != null)
                cures.add(_cureProgress[i]);
            else cures.add(0L);
        }
        return cures;
    }
    
    /**
     * calculates how much to increment awareness by
     * @param d
     * @return 
     */
    public double getAwareIncrement(Disease d){
        int index = d.getID();
        double maxVis = d.getMaxVisibility();
        return (d.getVisibility() + maxVis)/maxVis * (getInfected().get(index) + 2*_dead[index]);
    }

    /**
     * updates Awareness and checks to notify neighbors/close ports
     * @param d the disease
     * @param aware the increment to awareness
     */
    public void updateAwareness(Disease d, double aware) {
        int index = d.getID();
        double tot = _awareness[index] + aware;
        if(_awareness[index] < _awareMax/6 && tot > _awareMax/6){
            if(this.hasDisease(d))
                _news.add("An infection has been spotted in " + _name + "!");
            _awareness[index] = tot;
            notifyNeighbors(d);
        }
        else if(_awareness[index] < _awareMax/4 && tot > _awareMax/4){
            _news.add(_name + " has begun work on a cure for " + d.getName() + ".");
            _awareness[index] = tot;
            notifyNeighbors(d);
        }
        else if(_awareness[index] < _awareMax && tot > _awareMax){
            _awareness[index] = tot;
            notifyNeighbors(d);
            if(_air != 0 || _sea != 0)
                _news.add(_name + " has closed its sea and airports.");
        }
    }
    
    public void notifyNeighbors(Disease d) {
        for (Integer ind : _waterNeighbors) {
            Region r = _regions.get(ind);
            r.updateAwareness(d, _awareness[d.getID()] / 5);
        }
        for (Integer ind : _landNeighbors) {
            Region r = _regions.get(ind);
            r.updateAwareness(d, _awareness[d.getID()] / 5);
        }
    }

    /**
     * introduces a disease to this region, initializes necessary lists
     * @param d hte disease to introduce
     */
    public void introduceDisease(Disease d) {
        if(_diseases[d.getID()] != null){
            System.out.println("Introduced disease that existed already");
            return;
        }
        int index = d.getID();
        String ID = "";
        for (int i = 0; i < _numDiseases; i++) {
            if (i == index) {
                ID += "1";
            } else {
                ID += "0";
            }
        }
        InfWrapper inf = _hash.get(ID);
        _hash.put(new InfWrapper(ID, inf.getInf() + 1));
        _hash.addZero(_hash.getZero().getInf() - 1);
        d.addPoints(2);
        _diseases[index] = d;
        _news.add(d.getName() + " has infected " + _name + ".");
        double startInf = d.getStartInfectivity();
        double maxInf = d.getMaxInfectivity();
        double startLeth = d.getStartLethality();
        double maxLeth = d.getMaxLethality();
        double infLow = Math.log(2)/Math.log((startInf + maxInf/_INFSCALE)/(maxInf/_INFSCALE));
        double infHigh = Math.log(2)/Math.log(1 + _INFSCALE);
        _infDoubleTime[index] = (infLow + infHigh)/2;
        double lethLow = Math.log(.5)/Math.log(1 - (startLeth + maxLeth/_LETHSCALE)/(maxLeth/_LETHSCALE));
        double lethHigh = Math.log(.5)/Math.log(1 + _LETHSCALE);
        _lethDoubleTime[index] = (lethLow + lethHigh)/2;
    }

    /**
     * Transmits the disease to all regions with open sea/airports if the transmission
     * conditions are met (probability)
     * @param d the disease to transmit
     */
    public void transmitSeaAndAir(Disease d) {
        //TODO revise this calc
        for (int j = 0; j < 10; j++) {
            int ind = _rand.nextInt(_regions.size()) + 1;
            Region region = _regions.get(ind);
            if (region.hasDisease(d)) {
                continue;
            }
            int air = region.getAir();
            int sea = region.getSea();
            if (air > 0 && _air > 0) {
                boolean transmit = false;
                for(int i = 0; i < _air; i++)
                    if(_rand.nextInt(_PLANEFREQ) == 0)
                        transmit = true;
                double inf = getInfected().get(d.getID());
                double trans = inf/_population;
                if(transmit)
                    transmit = trans > _rand.nextDouble();
                if (transmit) {
                    RegionTransmission rt = new RegionTransmission(_name, region.getName(), d.getID(), true);
                    _transmissions.add(rt);
                    region.introduceDisease(d);
//                    System.out.println("Plane Trans");
                    continue;
                }
            }
            if (sea > 0 && _sea > 0) {
                boolean transmit = false;
                for(int i = 0; i < _sea; i++)
                    if(_rand.nextInt(_SHIPFREQ) == 0)
                        transmit = true;
                double inf = getInfected().get(d.getID());
                double trans = inf/_population;
                if(transmit)
                    transmit = trans > _rand.nextDouble();
                if (transmit) {
                    RegionTransmission rt = new RegionTransmission(_name, region.getName(), d.getID(), true);
                    _transmissions.add(rt);
                    region.introduceDisease(d);
//                    System.out.println("Ship Trans");
                }
            }
        }
    }

    /**
     * transmits the disease to all Land Neighbors if the conditions are met
     * @param d the disease to transmit
     */
    public void transmitToLandNeighbors(Disease d) {
        for (Integer id : _landNeighbors) {
            Region region = _regions.get(id);
            if (region.hasDisease(d)) {
                continue;
            }
            boolean transmit = false;
            double inf = getInfected().get(d.getID());
            if(_rand.nextInt(_LANDFREQ) == 0)
                transmit = true;
            if(transmit){
                double max = d.getMaxInfectivity();
                double trans = inf /_population *(d.getAirTrans() + d.getInfectivity() +max)/max;
                transmit = _rand.nextDouble() < trans;
            }
            if (transmit) {
//                System.out.println("Land Trans");
                region.introduceDisease(d);
            }
        }
    }

    /**
     * transmits the disease to all Water Neighbors if the conditions are met
     * @param d the disease to transmit
     */
    public void transmitToWaterNeighbors(Disease d) {
        for (Integer id : _waterNeighbors) {
            Region region = _regions.get(id);
            if (region.hasDisease(d)) {
                continue;
            }
            double inf = getInfected().get(d.getID());
            double trans = inf/_population * (d.getAirTrans() + d.getWaterTrans() + 82)/82;
            boolean transmit = _rand.nextDouble() < trans;
            if (transmit) {
//                System.out.println("Water Trans");
                region.introduceDisease(d);
            }
        }
    }

    /**
     * prompts a natural disaster in this region
     * @param intensity
     */
    public void naturalDisaster() {
        if(_rand.nextInt(64800) == 0){
            NaturalDisaster dis = _disasters.get(_disIDs.get(_rand.nextInt(_disIDs.size())));
            _news.add(dis.getName() + " has hit " + _name + ".");
            _wealth *= dis.getWealthFactor();
            _wet += dis.getWetChange();
            _dry += dis.getDryChange();
            _heat += dis.getHeatChange();
            _cold += dis.getColdChange();
        }
    }

    /**
     * gets the ArrayList of all air/sea transmissions
     * @return _transmissions
     */
    public ArrayList<RegionTransmission> getTransmissions() {
        ArrayList<RegionTransmission> list = new ArrayList<>(_transmissions);
        _transmissions.clear();
        return list;
    }

    /**
     * gets the news
     * @return
     */
    public ArrayList<String> getNews() {
        ArrayList<String> list = new ArrayList<>(_news);
        _news.clear();
        return list;
    }

    /**
     * hasDisease(Disease) returns true if this region has been infected by this disease
     * @param d
     * @return
     */
    public boolean hasDisease(Disease d) {
        return _diseases[d.getID()] != null;
    }

    public void setNumDiseases(int num) {
        _numDiseases = num;
        _diseases = new Disease[num];
        _dead = new long[num];
        _hasCure = new boolean[num];
        _awareness = new double[num];
        _cureProgress = new long[num];
        _hash = new PopHash(num);
        _hash.addZero(_population);
        _awareMax = 10 * _population;
        _infDoubleTime = new double[num];
        _lethDoubleTime = new double[num];
    }

    /**
     * getAir() gets a the number of open airports in this Region
     * @return _air;
     */
    public int getAir() {
        return _air;
    }

    /**
     * getSea() gets the number of open seaports in this Region
     * @return _sea
     */
    public int getSea() {
        return _sea;
    }

    /**
     * getWealth() returns the wealth of this region
     * @return _wealth
     */
    public double getWealth() {
        return _wealth;
    }

    /**
     * getNeighbors() gets the ids of all bordering Regions
     * @return _neighbors
     */
    public ArrayList<Integer> getLandNeighbors() {
        return _landNeighbors;
    }

    /**
     * getNeighbors() gets the ids of all bordering Regions (by Water)
     * @return _neighbors
     */
    public ArrayList<Integer> getWaterNeighbors() {
        return _waterNeighbors;
    }

    /**
     * getInfected() gets the ArrayList of infected people in this Region
     * @return _infected
     */
    public ArrayList<Long> getInfected() {
        ArrayList<Long> infected = new ArrayList<>();
        for (int i = 0; i < _numDiseases; i++) {
            long num = 0L;
            if (_diseases[i] != null)
                for(InfWrapper inf : _hash.getAllOfType(i,1))
                    num += inf.getInf();
            infected.add(num);
        }
        return infected;
    }

    /**
     * getTotalInfected() gets the total number of infected people in this Region
     * @return
     */
    public Long getTotalInfected() {
        long num = 0;
        for (int i = 0; i < _numDiseases; i++) {
            if (_diseases[i] != null) {
                for (InfWrapper inf : _hash.getAllOfType(i, 1)) {
                    num += inf.getInf();
                }
            }
        }
        return num;
    }

    /**
     * getKilled() gets the ArrayList of dead people in this Region
     * @return _dead;
     */
    public ArrayList<Long> getKilled() {
        ArrayList<Long> dead = new ArrayList<>();
        for(int i = 0; i < _numDiseases; i++){
            if(_diseases[i] != null)
                dead.add(_dead[i]);
            else dead.add(0L);
        }
        return dead;
    }

    /**
     * getCured() gets the ArrayList of cured people in this Region
     * @return _cured;
     **/
    public ArrayList<Long> getCured() {
        ArrayList<Long> list = new ArrayList<>();
        for (int i = 0; i < _numDiseases; i++) {
            long num = 0L;
            if (_diseases[i] != null) {
                for (InfWrapper inf : _hash.getAllOfType(i, 2)) {
                    num += inf.getInf();
                }
            }
            list.add(i, num);
        }
        return list;
    }

    /**
     * getAlive() gets the number of healthy people in this Region
     * @return _healthy
     */
    public long getHealthy() {
        return _hash.getZero().getInf();
    }

    /**
     * getName() gets the unique String name for this Region
     * @return _name
     */
    public String getName() {
        return _name;
    }

    /**
     * getID() gets the unique int ID for this region
     * @return _ID
     */
    public int getID() {
        return _ID;
    }

    //accessor for getting population
    public long getPopulation() {
        return _population;
    }

    /**
     * toString() returns a String with the name and population counts for this Region
     * @return
     */
    @Override
    public String toString() {
        return _name + ", healthy: " + _hash.getZero().getInf() + ", infected: " + getTotalInfected() +
                ", dead: " + _dead;
    }

    /**
     * equals(Object o) returns true if o is a Region with the same name,
     * false otherwise
     * @param o the object to compare to
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        } else {
            Region r = (Region) o;
            return _ID == r.getID();
        }
    }

    /**
     * hashCode() returns the hashCode for this Region's String name
     * @return _name.hashCode()
     */
    @Override
    public int hashCode() {
        return new Integer(_ID).hashCode();
    }
}
