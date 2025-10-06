import java.io.IOException;
import student.TestCase;

/**
 * @author Henry Patch (hjpatch27)
 * @author Nyssa Loeu (nysaal23)
 * @version 10.5.2025
 */
public class GISTest extends TestCase {

    private GIS it;
    private BST<City> bst;
    private City city1;
    private City city2;
    private City city3;
    private City city4;
    private int city5;
    private City city6;
    private City city7;
    private City city8;
    private City city9;
    private City city10;
    private City noCity;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        it = new GISDB();
        this.bst = new BST<>();
        city1 = new City("London", 1, 2);
        city2 = new City("London", 2, 1);
        city3 = new City("New York City", 1, 2);
        city4 = new City("New York City", 2, 1);
        city5 = 10;
        city6 = new City("New York City", 1, 3);
        city7 = new City("London", 1, 2);
        city8 = new City("London", 3, 2);
        city9 = new City("London", 1, 3);
        city10 = new City("Moscow", 4, 4);
        noCity = null;
    }
    
    /**
     * Tests the insert() method in the BST class.
     */
    public void testBSTInsert()
    {
        // Set initial conditions: Insert 3 City objects.
        bst.insert(city1);
        bst.insert(city4);
        bst.insert(city10);
        
        // size() should return 3
        assertEquals(bst.size(), 3);
    }
    
    /**
     * Tests the remove() method in the BST class.
     */
    public void testBSTRemove()
    {
        // Set initial conditions: Insert 3 City objects.
        bst.insert(city1);
        bst.insert(city4);
        bst.insert(city10);
        
        // Call the method: Remove a City object
        bst.remove(city1);
        
        // size() should now return 2
        assertEquals(bst.size(), 2);
    }

    /**
     * Tests the equals() method of the City object.
     */
    public void testCityEquals()
    {
        assertTrue(city1.equals(city1));  // equals itself
        assertFalse(city1.equals(city5)); // cannot equal other object
        assertFalse(city1.equals(city2)); // different x/y values
        assertFalse(city1.equals(city3)); // different names
        assertFalse(city1.equals(city4)); // different names and x/y values
        assertFalse(city1.equals(city6)); // different names and y values
        assertTrue(city1.equals(city7)); // equal names and x/y values
        assertFalse(city1.equals(city8)); // different x values
        assertFalse(city1.equals(city9)); // different y values
        
    }
    
    /**
     * Test clearing on initial
     * @throws IOException
     */
    public void testRefClearInit()
        throws IOException
    {
        assertTrue(it.clear());
    }

    /**
     * Print testing for empty trees
     * @throws IOException
     */
    public void testRefEmptyPrints()
        throws IOException
    {
        assertFuzzyEquals("", it.print());
        assertFuzzyEquals("", it.debug());
        assertFuzzyEquals("", it.info("CityName"));
        assertFuzzyEquals("", it.info(5, 5));
        assertFuzzyEquals("", it.delete("CityName"));
        assertFuzzyEquals("", it.delete(5, 5));
    }

    /**
     * Print bad input checks
     * @throws IOException
     */
    public void testRefBadInput()
        throws IOException
    {
        assertFalse(it.insert("CityName", -1, 5));
        assertFalse(it.insert("CityName", 5, -1));
        assertFalse(it.insert("CityName", 100000, 5));
        assertFalse(it.insert("CityName", 5, 100000));
        assertFuzzyEquals("", it.search(-1, -1, -1));
    }

    /**
     * Insert some records and check output requirements for various commands
     * @throws IOException
     */
    public void testRefOutput()
        throws IOException
    {
        assertTrue(it.insert("Chicago", 100, 150));
        assertTrue(it.insert("Atlanta", 10, 500));
        assertTrue(it.insert("Tacoma", 1000, 100));
        assertTrue(it.insert("Baltimore", 0, 300));
        assertTrue(it.insert("Washington", 5, 350));
        assertFalse(it.insert("X", 100, 150));
        assertTrue(it.insert("L", 101, 150));
        assertTrue(it.insert("L", 11, 500));
        assertFuzzyEquals("1  Atlanta (10, 500)\n"
            + "2    Baltimore (0, 300)\n"
            + "0Chicago (100, 150)\n"
            + "3      L (11, 500)\n"
            + "2    L (101, 150)\n"
            + "1  Tacoma (1000, 100)\n"
            + "2    Washington (5, 350)\n", it.print());
        assertFuzzyEquals("2    Baltimore (0, 300)\n"
            + "3      Washington (5, 350)\n"
            + "1  Atlanta (10, 500)\n"
            + "2    L (11, 500)\n"
            + "0Chicago (100, 150)\n"
            + "1  Tacoma (1000, 100)\n"
            + "2    L (101, 150)\n", it.debug());
        assertFuzzyEquals("L (101, 150)\nL (11, 500)", it.info("L"));
        assertFuzzyEquals("L", it.info(101, 150));
        assertFuzzyEquals("Tacoma (1000, 100)", it.delete("Tacoma"));
        assertFuzzyEquals("3\nChicago", it.delete(100, 150));
        assertFuzzyEquals("L (101, 150)\n"
                + "Atlanta (10, 500)\n"
                + "Baltimore (0, 300)\n"
                + "Washington (5, 350)\n"
                + "L (11, 500)\n5", it.search(0, 0, 2000));
        assertFuzzyEquals("Baltimore (0, 300)\n4", it.search(0, 300, 0));
    }
}
