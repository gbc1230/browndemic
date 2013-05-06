package edu.brown.cs32.browndemic.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.brown.cs32.browndemic.disease.*;
import edu.brown.cs32.browndemic.region.*;
import edu.brown.cs32.browndemic.world.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests basic world run functionality, specifically for ending games
 * @author gcarling
 */
public class WorldTests {

	private static WorldSP _world;
	
    public WorldTests() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    	WorldSP world = WorldMaker.makeNewEarthSP();
    	HashMap<Integer, Region> hash = new HashMap<>();
    	Region r1 = new Region(0, "Region 1", 20L, Arrays.asList(1), new ArrayList<Integer>(), 
    			hash, 0, 0, 20.0, 2.0, 2.0, 2.0, 2.0, 2.0, new ArrayList<NaturalDisaster>(),
    			new ArrayList<Integer>());
    	Region r2 = new Region(1, "Region 2", 5L, Arrays.asList(0), new ArrayList<Integer>(), 
    			hash, 0, 0, 20.0, 2.0, 2.0, 2.0, 2.0, 2.0, new ArrayList<NaturalDisaster>(),
    			new ArrayList<Integer>());
    	hash.put(0, r1);
    	hash.put(0, r2);
    	world.addRegion(r1);
    	world.addRegion(r2);
    	Disease d = new Virus("Disease");
    	world.addDisease(d);
    	_world = world;
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    //used to make a disease strong for testing purposes
    public void addAllPerks(Disease d){
    	d.addPoints(1000);
    	while(true){
    		List<Perk> perks = d.getAvailablePerks();
    		if (perks.size() == 0)
    			break;
    		for (Perk p : perks){
    			try{
    				d.buyPerk(p.getID());
    			}
    			catch(IllegalAccessException e){
    				
    			}
    		}
    	}
    }
    
    @Test
    public void correctWinnerTest1(){
    	_world.start();
    	_world.introduceDisease(0, 0);
    	_world.setSpeed(3);
    	Disease d = _world.getDisease(0);
    	addAllPerks(d);
    	while (!_world.isGameOver()){
    		
    	}
    	List<Integer> win = _world.getWinners();
    	assert(win.size() == 1);
    	assert(win.get(0) == 0);
    }
    
    @Test
    public void correctWinnerTest2(){
    	Disease d2 = new Parasite("Test2");
    	_world.addDisease(d2);
    	addAllPerks(_world.getDisease(0));
    	_world.start();
    	_world.introduceDisease(0, 0);
    	_world.setSpeed(3);
    	while (!_world.isGameOver()){
    		
    	}
    	List<Integer> win = _world.getWinners();
    	assert(win.size() == 1);
    	assert(win.get(0) == 0);
    }
    
    @Test
    public void cureWorksTest(){
    	List<Disease> diseases = _world.getDiseases();
    	System.out.println(diseases);
    	for (int i = 0; i < diseases.size(); i++){
    		diseases.get(i).die();
    		_world.sendCures(i);
    	}
    	while (!_world.isGameOver()){
    		
    	}
    	assert(_world.getWinners().size() == 0);
    }
}