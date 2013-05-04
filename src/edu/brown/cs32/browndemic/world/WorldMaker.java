/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import edu.brown.cs32.browndemic.region.Region;
import edu.brown.cs32.browndemic.region.NaturalDisaster;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * For generating various types of Worlds
 * @author Graham
 */
public class WorldMaker{
    
    public static WorldSP makeNewEarthSP() throws IOException{
        WorldSP w = new WorldSP();
        addEarthRegions(w, "EarthRegions.csv");
        return w;
    }
    
    public static ServerWorld makeNewEarthServer() throws IOException{
        ServerWorld w = new ServerWorld();
        addEarthRegions(w, "EarthRegions.csv");
        return w;
    }
    
    private static List<NaturalDisaster> getNaturalDisasters(String filename) throws IOException{
    	BufferedReader f = new BufferedReader(new FileReader(filename));
    	ArrayList<NaturalDisaster> ans = new ArrayList<>();
    	f.readLine();
    	while (f.ready()){
    		String l = f.readLine();
    		String[] line = l.split(",");
    		String name = line[0];
    		double wealth, wet, dry, heat, cold;
    		wealth = Double.parseDouble(line[1]);
    		wet = Double.parseDouble(line[2]);
    		dry = Double.parseDouble(line[3]);
    		heat = Double.parseDouble(line[4]);
    		cold = Double.parseDouble(line[5]);
    		NaturalDisaster temp = new NaturalDisaster(name, wealth, wet, dry, heat, cold);
    		ans.add(temp);
    	}
    	return ans;
    }
    
    private static void addEarthRegions(MainWorld w, String filename) throws IOException{
        BufferedReader f = new BufferedReader(new FileReader(filename));
        HashMap<Integer, Region> regionHash = new HashMap<>();
        List<NaturalDisaster> disasters = getNaturalDisasters("NaturalDisasters.csv");
        f.readLine();
        while (f.ready()){
            String line = f.readLine();
            String[] data = line.split(",");
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            long population = Long.parseLong(data[2]);
            int airports = Integer.parseInt(data[3]);
            int seaports = Integer.parseInt(data[4]);
            List<Integer> landNeighbors = new ArrayList<>();
            if (!data[5].equals("")){
                String[] landNeigh = data[5].split(" ");
                for (String s : landNeigh)
                    landNeighbors.add(Integer.parseInt(s));
            }
            List<Integer> seaNeighbors = new ArrayList<>();
            if (!data[6].equals("")){
                String[] seaNeigh = data[6].split(" ");
                for (String s: seaNeigh)
                    seaNeighbors.add(Integer.parseInt(s));
            }
            int wealth = Integer.parseInt(data[7]);
            int wet, dry, heat, cold, medicine;
            wet = Integer.parseInt(data[8]);
            dry = Integer.parseInt(data[9]);
            heat = Integer.parseInt(data[10]);
            cold = Integer.parseInt(data[11]);
            medicine = Integer.parseInt(data[12]);
            Region r = new Region(id, name, population, landNeighbors, seaNeighbors, 
                    regionHash, airports, seaports, wealth, wet, dry, heat, 
                    cold, medicine, disasters);
            regionHash.put(id, r);
            w.addRegion(r);
        }
    }
    
    
    public static void main (String [] args) throws IOException{
        //making sure we don't have any errors by just parsing the file
        makeNewEarthSP();
    }
   
}