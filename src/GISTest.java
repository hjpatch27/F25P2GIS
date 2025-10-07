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
    private KDTree tree;
    private City a;
    private City b;
    private City c;
    private City d;
    private City e;
    
    

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
        tree = new KDTree();
        a = new City("Alpha", 30, 40);
        b = new City("Beta", 5, 25);
        c = new City("Gamma", 70, 70);
        d = new City("Delta", 10, 12);
        e = new City("Epsilon", 50, 50);
    }
    
    /**
     * Tests the insert() method in GISDB. In this test case,
     * We run scenario's where the City object has coordinates
     * out of bounds, there is a City with duplicate coordinates,
     * and a successful insertion of a City object.
     */
    public void testInsert()
    {
        
        // Successfully insert first City object
        assertTrue(it.insert("London", 1, 2));
        // Inserting city with same name but different
        // coordinates (in-bounds) should return true.
        assertTrue(it.insert("London", 2, 1));
        // Trying to insert a city with duplicate 
        // coordinates should return false.
        assertFalse(it.insert("New York City", 1, 2));
        // Trying to insert city with coordinates
        // out of bounds should return false.
        assertFalse(it.insert("Moscow", 1, -1)); // y < 0
        assertFalse(it.insert("Moscow", -1, 1)); // x < 0
        assertFalse(it.insert("Moscow", 1, 100000)); // y > MAXCOORD
        assertFalse(it.insert("Moscow", 1, 100000)); // x > MAXCOORD
    }
    
    /**
     * All remove()/delete() methods commented out for 
     * Milestone 2 Mutation Coverage (75%)
     *
     * Tests the delete() method in GISDB. In this test case,
     * we check scenarios where the City object is successfully
     * removed as well as cases where no deletion occurs.
     *
    public void testDelete()
    {
        // Initial Condition: Add City objects to GIS
        it.insert("London", 1, 2);
        it.insert("Moscow", 3, 3);
        it.insert("New York City", 6, 7);
        
        // Call the method: Remove New York City
        // delete() should return "New York City"
        String result = it.delete(6, 7);
        assertEquals("New York City", result);
        
        // info(6,7) should return an empty string
        assertEquals(it.info(6, 7), "");
        
        // Trying to delete a nonexistent coordinate
        // should return an empty string.
        assertEquals(it.delete(10, 10), "");
    }
    */
    
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
     * Tests the find() method in the BST class.
     */
    public void testBSTFind()
    {
        // Set initial conditions: Insert City objects.
        bst.insert(a);
        bst.insert(b);
        bst.insert(c);
        bst.insert(d);
        bst.insert(e);
        
        // Searching for a nonexistent City will return null.
        assertNull(bst.find(city10));
        
        // Searching for an existing City will return the 
        // City object.
        assertEquals(bst.find(a), a);
        
        // Scenario where find() should traverse to the right.
        assertEquals(bst.find(e), e);
    }
    
    /**
     * All remove()/delete() methods commented out for 
     * Milestone 2 Mutation Coverage (75%)
     * 
     * Tests the remove() method in the BST class.
     *
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
    */

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
        assertEquals("Chicago", it.info(100, 150));
        assertFalse(it.insert("X", 100, 150));
        assertTrue(it.insert("L", 101, 150));
        assertEquals("", it.info(11, 500));
        assertTrue(it.insert("L", 11, 500));
        assertFuzzyEquals("1  Atlanta (10, 500)\n"
            + "2    Baltimore (0, 300)\n"
            + "0Chicago (100, 150)\n"
            + "3      L (11, 500)\n"
            + "2    L (101, 150)\n"
            + "1  Tacoma (1000, 100)\n"
            + "2    Washington (5, 350)\n", it.print());
        /**
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
        */
    }   
    // ----------------Test KDTREE--------------------------------------------
    /**
     * Test the method insert() and size() for KDTree
     */
    public void testKDInsert()
    {
        assertTrue(tree.insert(a));
        assertTrue(tree.insert(b));
        assertTrue(tree.insert(c));
        assertEquals(3, tree.size());
    }
    
    /**
     * Test to return false is there is a duplicate in KDTree
     */
    public void testInsertDuplicate()
    {
        tree.insert(a);
        assertFalse(tree.insert(new City("Duplicate", 30, 40)));
        assertEquals(1, tree.size());
    }
    
    /**
     * Test the Find() to find an existing city
     */
    public void testFindExistingCity()
    {
        tree.insert(a);
        tree.insert(b);
        City found = tree.find(5, 25);
        assertNotNull(found);
        assertEquals("Beta", found.getName());
    }
    
    /**
     * Test the mehtod find() and should return null
     * since there is no city with x and y (99,99)
     */
    public void testFindNonexistentCity()
    {
        tree.insert(a);
        assertNull(tree.find(99, 99));
    }
    
    /**
     * Test the remove method when removing a leaf node
     */
    public void testRemoveLeafNode()
    {
        tree.insert(a);
        tree.insert(b);
        assertNotNull(tree.remove(5, 25)); // remove city b
        assertEquals(1, tree.size());
        assertNull(tree.find(5, 25));
    }
    
    /**
     * Test the remove() when there is node with children
     */
    public void testRemoveNodeWithChildren()
    {
        tree.insert(a);
        tree.insert(b);
        tree.insert(c);
        tree.insert(d);
        tree.insert(e);
        assertNotNull(tree.remove(30, 40));
        assertEquals(4, tree.size());
        assertNull(tree.find(30, 40));
    }
    
    /**
     * Test the clear() method and should return 0 for size
     */
    public void testClearTree()
    {
        tree.insert(a);
        tree.insert(b);
        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.find(30, 40));
    }
    
    /**
     * Test the find method when currentCity.getY() is false.
     * Where x matches but y does not
     */
    public void testFind2() 
    {
        City blacksburg = new City("Blacksburg", 30, 40);
        tree.insert(blacksburg);

        // Same x, different y — should not match
        City result = tree.find(30, 999);
        assertNull(result);
    }

    /**
     * Test the remove method
     */
    public void testRemove2() {
        KDTree emptyTree = new KDTree();
        City result = emptyTree.remove(999, 999);
        assertNull(result);
    }
    
    /**
     * Remove a node that has no left or right child
     */
    public void testRemove3() {
        tree.insert(a);
        tree.insert(b);
        tree.insert(c);
        tree.insert(d);
        tree.insert(e);
        // Beta has no right child, but has left child (Delta)
        City removed = tree.remove(5, 25);
        assertNotNull(removed);
        assertEquals("Beta", removed.getName());

        // Beta should be replaced by Delta (min in left subtree)
        City replacement = tree.find(10, 12);
        assertNotNull(replacement);
        assertEquals("Delta", replacement.getName());

        // Beta should no longer be found
        assertNull(tree.find(5, 25));
    }
    
    /**
     * Test the remove method for the scenario where x coordinate matches
     * but the y coordinate does not.
     */
    public void testRemove4() {
        tree.insert(a);
        tree.insert(b);
        tree.insert(c);
        tree.insert(d);
        tree.insert(e);

        City f = new City("Zeta", 5, 999); // same x, different y
        tree.insert(f);

        City result = tree.remove(5, 999); // x matches Zeta, y matches → should remove Zeta
        assertNotNull(result);
        assertEquals("Zeta", result.getName());

        // Beta should still be in the tree
        City stillThere = tree.find(5, 25);
        assertNotNull(stillThere);
        assertEquals("Beta", stillThere.getName());
    }

    /**
     * Test the remove method when y < currentCity.getY() 
     */
    public void testRemove5() 
    {
        tree.insert(a);
        tree.insert(b);
        tree.insert(c);
        tree.insert(d);
        tree.insert(e);
        // At level 1, cd = 1 → compare y
        // Beta is at (5, 25), so y = 10 is less → should go left
        City f = new City("Zeta", 2, 10); // left of Beta by y
        tree.insert(f);

        // Remove Zeta to trigger traversal through Beta's left
        City removed = tree.remove(2, 10);
        assertNotNull(removed);
        assertEquals("Zeta", removed.getName());

        // Confirm Zeta is gone, Beta is still there
        assertNull(tree.find(2, 10));
        assertNotNull(tree.find(5, 25));
    }



}
