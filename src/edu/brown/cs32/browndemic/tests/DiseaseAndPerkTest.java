package edu.brown.cs32.browndemic.tests;

import static org.junit.Assert.*;
import edu.brown.cs32.browndemic.disease.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests disease and perk functionality
 * @author bkoatz
 */   
public class DiseaseAndPerkTest {

    public DiseaseAndPerkTest() {}

    @BeforeClass
    public static void setUpClass() throws Exception {
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

    @Test
    public void virusPerkTests(){

        Virus v = new Virus("Test");
        assert(v.getAvailablePerks().size() == 15);
        assertTrue(v.getPoints() == 0);
        v.addPoints(1000);
        assertTrue(v.getPoints() == 1000);
        try {
            v.buyPerk(8);
            assertTrue(false);
        } catch (IllegalAccessException ex) {
            assertTrue(true);
        }
        try {
            v.buyPerk(0);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        assertTrue(v.getPoints() == 1000 - v.getPerks()[0].getCost());
        assertTrue(v.getAvailablePerks().size() == 15);
        assertTrue(v.getOwnedPerks().size() == 1);
        try {
            v.buyPerk(1);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        assertTrue(v.getPoints() == 1000 - v.getPerks()[0].getCost() -
                v.getPerks()[1].getCost());
        assertTrue(v.getAvailablePerks().size() == 15);
        assertTrue(v.getOwnedPerks().size() == 2);
        assertTrue(v.getPerks()[0].getCumSellPrice() == v.getPerks()[0].getSellPrice());
        try {
            v.buyPerk(8);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        assertTrue(v.getPerks()[0].getCumSellPrice() != v.getPerks()[0].getSellPrice());
        assertTrue(v.getPoints() == 1000 - v.getPerks()[0].getCost() -
                v.getPerks()[1].getCost() - v.getPerks()[8].getCost());
        try {
            v.sellPerk(0);
            assertTrue(false);
        } catch (IllegalAccessException ex) {
            assertTrue(true);
        }
        try {
            v.sellCumPerk(0);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        assertTrue(v.getAvailablePerks().size() == 15);
        assertTrue(v.getOwnedPerks().size() == 1);
        assertTrue(v.getPoints() == 1000 - v.getPerks()[0].getCost() -
                v.getPerks()[1].getCost() - v.getPerks()[8].getCost() +
                v.getPerks()[0].getSellPrice() + v.getPerks()[8].getSellPrice());

    }

    @Test
    public void parasitePerkTests(){

        Parasite p = new Parasite("Test");
        assertTrue(p.getAvailablePerks().size() == 14);
        assertTrue(p.getPoints() == 0);
        p.addPoints(1000);
        assertTrue(p.getPoints() == 1000);
        try {
            p.buyPerk(8);
            assertTrue(false);
        } catch (IllegalAccessException ex) {
            assertTrue(true);
        }
        try {
            p.buyPerk(1);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        assertTrue(p.getPoints() == 1000 - p.getPerks()[1].getCost());
        assertTrue(p.getAvailablePerks().size() == 15);
        assertTrue(p.getOwnedPerks().size() == 1);
        try {
            p.buyPerk(0);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        assertTrue(p.getPoints() == 1000 - p.getPerks()[1].getCost() -
                p.getPerks()[0].getCost());
        assertTrue(p.getAvailablePerks().size() == 15);
        assertTrue(p.getOwnedPerks().size() == 2);
        assertTrue(p.getPerks()[0].getCumSellPrice() == p.getPerks()[0].getSellPrice());
        try {
            p.buyPerk(4);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        assertTrue(p.getPerks()[0].getCumSellPrice() != p.getPerks()[0].getSellPrice());
        double saveSellPrice = p.getPerks()[0].getCumSellPrice();
        try {
            p.buyPerk(2);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        assertTrue(p.getPerks()[0].getCumSellPrice() != saveSellPrice);
        assertTrue(p.getPoints() == 1000 - p.getPerks()[2].getCost() -
                p.getPerks()[4].getCost() - p.getPerks()[0].getCost()
                - p.getPerks()[1].getCost());
        try {
            p.sellPerk(0);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        try {
            p.sellPerk(2);
            assertTrue(false);
        } catch (IllegalAccessException ex) {
            assertTrue(true);
        }
        try {
            p.sellCumPerk(2);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
        assertTrue(p.getAvailablePerks().size() == 15);
        assertTrue(p.getOwnedPerks().size() == 1);

    }

    @Test
    public void bacteriaPerkTest(){

        Bacteria b = new Bacteria("Test");
        assertTrue(b.getAvailablePerks().size() == 15);
        assertTrue(b.getPoints() == 0);
        b.addPoints(1000);
        assertTrue(b.getPoints() == 1000);
        try {
            b.buyPerk(8);
            assertTrue(false);
        } catch (IllegalAccessException ex) {
        	assertTrue(true);
        }
        try {
            b.buyPerk(4);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        assertTrue(b.getOwnedPerks().get(0).getImage().equals("skull.png"));
        assertTrue(b.getPoints() == 1000 - b.getPerks()[4].getCost());
        assertTrue(b.getAvailablePerks().size() == 15);
        assertTrue(b.getOwnedPerks().size() == 1);
        try {
            b.buyPerk(3);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        } 
        assertTrue(b.getPoints() == 1000 - b.getPerks()[4].getCost() -
                b.getPerks()[3].getCost());
        assertTrue(b.getAvailablePerks().size() == 16);
        assertTrue(b.getOwnedPerks().size() == 2);
        try {
            b.buyPerk(0);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        assertTrue(b.getPerks()[0].getCumSellPrice() == b.getPerks()[0].getSellPrice());
        try {
            b.buyPerk(7);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        assertTrue(b.getPerks()[0].getCumSellPrice() != b.getPerks()[0].getSellPrice());
        try {
            b.buyPerk(17);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        double saveSellPrice = b.getPerks()[7].getCumSellPrice();
        try {
            b.buyPerk(6);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        assertTrue(b.getPerks()[7].getCumSellPrice() != saveSellPrice);
        try {
            b.sellPerk(6);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        assertTrue(b.getPerks()[7].getCumSellPrice() == saveSellPrice);
        try {
            b.sellPerk(17);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        try {
            b.sellPerk(0);
            assertTrue(false);
        } catch (IllegalAccessException ex) {
        	assertTrue(true);
        }
        try {
            b.sellCumPerk(0);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        try {
            b.sellCumPerk(3);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        try {
            b.sellCumPerk(4);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
        	assertTrue(false);
        }
        assertTrue(b.getAvailablePerks().size() == 15);
        assertTrue(b.getOwnedPerks().size() == 0);
        try {
            b.buyPerk(20);
            assertTrue(true);
        } catch (IllegalAccessException ex) {
            assertTrue(false);
        }
    }

}