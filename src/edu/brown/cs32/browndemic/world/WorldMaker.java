/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import edu.brown.cs32.browndemic.region.Region;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * For generating various types of Worlds
 * @author Graham
 */
public class WorldMaker{
    
    private static void addRegions(MainWorld w, String filename) throws IOException{
        BufferedReader f = new BufferedReader(new FileReader(filename));
        HashMap<Integer, Region> regionHash = new HashMap<>();
        f.readLine();
        while (f.ready()){
            String line = f.readLine();
            System.out.println(line);
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
            int wet, dry, heat, cold;
            wet = Integer.parseInt(data[8]);
            dry = Integer.parseInt(data[9]);
            heat = Integer.parseInt(data[10]);
            cold = Integer.parseInt(data[11]);
            Region r = new Region(id, name, population, landNeighbors, seaNeighbors, 
                    regionHash, airports, seaports, wealth, wet, dry, heat, cold);
            regionHash.put(id, r);
            w.addRegion(r);
        }
    }
    
    public static MainWorld makeNewEarthSP() throws IOException{
        MainWorld w = new MainWorld();
        addRegions(w, "EarthRegions.csv");
        return w;
    }
    
    public static ServerWorld makeNewEarthServer() throws IOException{
        ServerWorld w = new ServerWorld();
        addRegions(w, "EarthRegions.csv");
        return w;
    }
    
    public static void main(String [] args) throws IOException{
        makeNewEarthSP();
        System.out.println("done");
    }
   
}