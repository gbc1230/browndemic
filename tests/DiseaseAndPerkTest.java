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

    public DiseaseAndPerkTest() {
    }

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

        Virus v = new Virus("Swag");
        assert(v.getAvailablePerks().size() == 15);
        assert(v.getPoints() == 0);
        v.addPoints(1000);
        assert(v.getPoints() == 1000);
        try {
            v.buyPerk(8);
            assert(false);
        } catch (IllegalAccessException ex) {
            assert(true);
        }
        try {
            v.buyPerk(0);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        assert(v.getPoints() == 1000 - v.getPerks()[0].getCost());
        assert(v.getAvailablePerks().size() == 15);
        assert(v.getOwnedPerks().size() == 1);
        try {
            v.buyPerk(1);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        assert(v.getPoints() == 1000 - v.getPerks()[0].getCost() -
                v.getPerks()[1].getCost());
        assert(v.getAvailablePerks().size() == 15);
        assert(v.getOwnedPerks().size() == 2);
        assert(v.getPerks()[0].getCumSellPrice() == v.getPerks()[0].getSellPrice());
        try {
            v.buyPerk(8);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        assert(v.getPerks()[0].getCumSellPrice() != v.getPerks()[0].getSellPrice());
        assert(v.getPoints() == 1000 - v.getPerks()[0].getCost() -
                v.getPerks()[1].getCost() - v.getPerks()[8].getCost());
        try {
            v.sellPerk(0);
            assert(false);
        } catch (IllegalAccessException ex) {
            assert(true);
        }
        try {
            v.sellCumPerk(0);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        assert(v.getAvailablePerks().size() == 15);
        assert(v.getOwnedPerks().size() == 1);
        assert(v.getPoints() == 1000 - v.getPerks()[0].getCost() -
                v.getPerks()[1].getCost() - v.getPerks()[8].getCost() -
                v.getPerks()[0].getSellPrice() - v.getPerks()[8].getSellPrice());

    }

    @Test
    public void parasitePerkTests(){

        Parasite p = new Parasite("Swag");
        assert(p.getAvailablePerks().size() == 14);
        assert(p.getPoints() == 0);
        p.addPoints(1000);
        assert(p.getPoints() == 1000);
        try {
            p.buyPerk(8);
            assert(false);
        } catch (IllegalAccessException ex) {
            assert(true);
        }
        try {
            p.buyPerk(1);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        assert(p.getPoints() == 1000 - p.getPerks()[1].getCost());
        assert(p.getAvailablePerks().size() == 15);
        assert(p.getOwnedPerks().size() == 1);
        try {
            p.buyPerk(0);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        assert(p.getPoints() == 1000 - p.getPerks()[1].getCost() -
                p.getPerks()[0].getCost());
        assert(p.getAvailablePerks().size() == 15);
        assert(p.getOwnedPerks().size() == 2);
        assert(p.getPerks()[0].getCumSellPrice() == p.getPerks()[0].getSellPrice());
        try {
            p.buyPerk(4);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        assert(p.getPerks()[0].getCumSellPrice() != p.getPerks()[0].getSellPrice());
        double saveSellPrice = p.getPerks()[0].getCumSellPrice();
        try {
            p.buyPerk(2);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        assert(p.getPerks()[0].getCumSellPrice() != saveSellPrice);
        assert(p.getPoints() == 1000 - p.getPerks()[2].getCost() -
                p.getPerks()[4].getCost() - p.getPerks()[0].getCost()
                - p.getPerks()[1].getCost());
        try {
            p.sellPerk(0);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        try {
            p.sellPerk(2);
            assert(false);
        } catch (IllegalAccessException ex) {
            assert(true);
        }
        try {
            p.sellCumPerk(2);
            assert(true);
        } catch (IllegalAccessException ex) {
            assert(false);
        }
        assert(p.getAvailablePerks().size() == 15);
        assert(p.getOwnedPerks().size() == 1);

    }

}