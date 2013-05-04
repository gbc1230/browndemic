package edu.brown.cs32.browndemic.disease;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The static class which can take in a file and parse out the perks in that
 * file.
 * 
 *@author bkoatz 
 */
public class PerkMaker{

    /**
     * Takes in a file and produces an array of perks from that file.
     * 
     * @param filePath the path to the file 
     * @return         an array of perks with characteristics defined by the file
     * @throws FileNotFoundException     if filepath does not lead to a file
     * @throws IOException               if the file has an error in it
     * @throws NoSuchFieldException      if there is an unrecognized field in 
     *                                   the file
     */
    public static Perk[] getPerks(String filePath, int arraySize) throws FileNotFoundException,
                                              IOException, NoSuchFieldException{

        Perk[] toReturn = new Perk[arraySize];
        File f = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line = reader.readLine();
        String[] fields = line.split(",");
        int idInd=-1, nameInd=-1, descInd=-1, costInd=-1, sellInd=-1, infInd=-1,
            lethInd=-1, visInd=-1, heatInd=-1, coldInd=-1, wetInd=-1, dryInd=-1,
            medInd=-1, watInd=-1, airInd=-1, prevInd=-1,nextInd=-1;
        for(int i = 0; i < fields.length; i++){

            
            if(fields[i].equals("ID")) idInd = i;
            else if(fields[i].equals("Name")) nameInd = i;
            else if(fields[i].equals("Description")) descInd = i;
            else if(fields[i].equals("Cost")) costInd = i;
            else if(fields[i].equals("Sell")) sellInd = i;
            else if(fields[i].equals("Infectivity")) infInd = i;
            else if(fields[i].equals("Lethality")) lethInd = i;
            else if(fields[i].equals("Visibility")) visInd = i;
            else if(fields[i].equals("Heat Resistance")) heatInd = i;
            else if(fields[i].equals("Cold Resistance")) coldInd = i;
            else if(fields[i].equals("Wet Resistance")) wetInd = i;
            else if(fields[i].equals("Dry Resistance")) dryInd = i;
            else if(fields[i].equals("Medicinal Resistance")) medInd = i;
            else if(fields[i].equals("Water Transmission")) watInd = i;
            else if(fields[i].equals("Air Transmission")) airInd = i;
            else if(fields[i].equals("Previous")) prevInd = i;
            else if(fields[i].equals("Next")) nextInd = i;
            else throw new NoSuchFieldException();

        }

        line = reader.readLine();
        while(line != null){

            fields = line.split(",");
            int id = Integer.parseInt(fields[idInd]);
            int cost = Integer.parseInt(fields[costInd]);
            int sell = Integer.parseInt(fields[sellInd]);
            double inf = Double.parseDouble(fields[infInd]);
            double leth = Double.parseDouble(fields[lethInd]);
            double vis = Double.parseDouble(fields[visInd]);
            double heat = Double.parseDouble(fields[heatInd]);
            double cold = Double.parseDouble(fields[coldInd]);
            double wet = Double.parseDouble(fields[wetInd]);
            double dry = Double.parseDouble(fields[dryInd]);
            double med = Double.parseDouble(fields[medInd]);
            double water = Double.parseDouble(fields[watInd]);
            double air = Double.parseDouble(fields[airInd]);
            ArrayList<Integer> prev = new ArrayList<Integer>();
            if(!fields[prevInd].equals("")){
            String[] prevs = fields[prevInd].split("and");
            for(String s : prevs) prev.add(Integer.parseInt(s));
            }
            ArrayList<Integer> next = new ArrayList<Integer>();
            if(fields.length > 16  && !fields[nextInd].equals("")){
            String[] nexts = fields[nextInd].split("and");
            for(String s : nexts) next.add(Integer.parseInt(s));
            }
            Perk tempPerk = new Perk(id, fields[nameInd], fields[descInd], cost,
                                   sell, inf, leth, vis, heat, cold, wet, dry,
                                   med, water, air, prev, next);
            toReturn[id] = tempPerk;
            line = reader.readLine();

        }
        for(Perk p : toReturn) p.setPerksArray(toReturn);
        reader.close();
        return toReturn;

    }

}
