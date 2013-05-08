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
        addEarthAirports(w, "resources/EarthAirports.csv");
        addEarthRegions(w, "resources/EarthRegions.csv");
        w.setPopulation();
        return w;
    }
    
    public static ServerWorld makeNewEarthServer() throws IOException{
        ServerWorld w = new ServerWorld();
        addEarthAirports(w, "resources/EarthAirports.csv");
        addEarthRegions(w, "resources/EarthRegions.csv");
        w.setPopulation();
        return w;
    }
    
    private static List<NaturalDisaster> getNaturalDisasters(String filename) throws IOException{
    	BufferedReader f = new BufferedReader(new FileReader(filename));
    	ArrayList<NaturalDisaster> ans = new ArrayList<>();
    	f.readLine();
    	while (f.ready()){
    		String l = f.readLine();
    		String[] line = l.split(",");
    		int id = Integer.parseInt(line[0]);
    		String name = line[1];
    		double wealth, wet, dry, heat, cold;
    		wealth = Double.parseDouble(line[2]);
    		wet = Double.parseDouble(line[3]);
    		dry = Double.parseDouble(line[4]);
    		heat = Double.parseDouble(line[5]);
    		cold = Double.parseDouble(line[6]);
    		NaturalDisaster temp = new NaturalDisaster(id, name, wealth, wet, 
    				dry, heat, cold);
    		ans.add(temp);
    	}
    	f.close();
    	return ans;
    }
    
    private static void addEarthAirports(MainWorld w, String filename) throws IOException{
    	BufferedReader f = new BufferedReader(new FileReader(filename));
    	f.readLine();
    	while (f.ready()){
    		String l = f.readLine();
    		String[] line = l.split(",");
    		int id = Integer.parseInt(line[0]);
    		int x = Integer.parseInt(line[1]);
    		int y = Integer.parseInt(line[2]);
    		int region = Integer.parseInt(line[3]);
    		String des = line[4];
    		Airport a = new Airport(id, region, x, y, des);
    		w.addAirport(a);
    	}
    	f.close();
    }
    
    private static void addEarthRegions(MainWorld w, String filename) throws IOException{
        BufferedReader f = new BufferedReader(new FileReader(filename));
        HashMap<Integer, Region> regionHash = new HashMap<>();
        List<NaturalDisaster> disasters = getNaturalDisasters("resources/NaturalDisasters.csv");
        List<Airport> worldAirports = w.getAirports();
        f.readLine();
        while (f.ready()){
            String line = f.readLine();
            String[] data = line.split(",");
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            long population = Long.parseLong(data[2]);
            String[] ap = data[3].split(" ");
            List<Airport> airports = new ArrayList<>();
            for (String s : ap){
            	if (!s.equals("")){
            		int pos = Integer.parseInt(s);
            		airports.add(worldAirports.get(pos));
            	}
            }
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
            String[] posDisasters = data[13].split(" ");
            List<Integer> possibles = new ArrayList<>();
            for (String s : posDisasters){
            	possibles.add(Integer.parseInt(s));
            }
            Region r = new Region(id, name, population, landNeighbors, seaNeighbors, 
                    regionHash, airports, seaports, wealth, wet, dry, heat, 
                    cold, medicine, disasters, possibles);
            regionHash.put(id, r);
            w.addRegion(r);
        }
        f.close();
    }
    
    
    public static void main (String [] args) throws IOException{
        //making sure we don't have any errors by just parsing the file
        makeNewEarthSP();
    }
   
}