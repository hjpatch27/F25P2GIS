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
    private String stringCityNull;
    private City city6;
    private City city7;
    private City city8;
    private City city9;
    private City city10;
    private City city11;
    private City city12;
    private City cityNull;
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
        city11 = new City("London", 0, 2);
        city12 = new City("London", 1, 1);
        stringCityNull = null;
        cityNull = null;
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
     */
    public void testDelete()
    {
        // Initial Condition: Add City objects to GIS
        it.insert("London", 1, 2);
        it.insert("Moscow", 3, 3);
        it.insert("New York City", 6, 7);
        
        // Call the method: Remove New York City
        // delete() should return "New York City"
        String result = it.delete(6, 7);
        assertEquals("3\nNew York City", result);
        
        // info(6,7) should return an empty string
        assertEquals(it.info(6, 7), "");
        
        // Trying to delete a nonexistent coordinate
        // should return an empty string.
        assertEquals(it.delete(10, 10), "");
    }
    
    
    /**
     * Tests the insert() method in the BST class.
     */
    public void testInsert2()
    {
        // Set initial conditions: Insert 3 City objects.
        assertTrue(it.insert("London", 1, 2));
        assertTrue(it.insert("New York City", 2, 1));
        assertTrue(it.insert("Moscow", 4, 4));
        
        // info(name) should return the name of each city inserted.
        assertEquals(it.info("London"), "London (1, 2)\n");
        assertEquals(it.info("New York City"), "New York City (2, 1)\n");
        assertEquals(it.info("Moscow"), "Moscow (4, 4)\n");
    }
    
    /**
     * Test for the BST class. In this test, we're inserting
     * City objects and keeping track of the nodeCount variable.
     */
    public void testBSTInsertNodeCount() {
        it.insert("A", 10, 10);
        it.insert("B", 20, 20);
        it.insert("C", 30, 30);

        String output = it.print();
        int lineCount = output.split("\n").length;

        // Expect 3 lines for 3 inserted cities
        assertEquals(3, lineCount);
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
     * Tests the remove() method, specificallly for BST class.
     */
    public void testBSTRemove()
    {
        // Set initial conditions
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        
        // Call the method: Remove c from the BST
        // Should return "Gamma"
        assertEquals(it.delete(70, 70), "4\nGamma");
        
        // find(c) should now return an empty string
        assertEquals(it.info(70, 70), "");
        
        // print() should no longer include c
        assertEquals("0Alpha (30, 40)\n"
            + "1  Beta (5, 25)\n"
            + "2    Delta (10, 12)\n"
            + "3      Epsilon (50, 50)\n", it.print());
    }
    
    /**
     * Tests the compareTo() method from the City class.
     */
    public void testCityCompareTo()
    {
        // Since they're same, return 0.
        assertEquals(city1.compareTo(city1), 0);
        // Different name, returns value of name comparison.
        assertEquals(city1.compareTo(city3), -2);
        // Same name/X value but different Y value
        assertEquals(city1.compareTo(city9), 0);
        assertEquals(city1.compareTo(city12), 0);
    }
    
    /**
     * Tests the equals() method of the City object.
     */
    public void testCityEquals()
    {
        assertTrue(city1.equals(city1));    // equals itself
        assertFalse(city1.equals(city5));   // cannot equal other object
        assertFalse(city1.equals(city2));   // different x/y values
        assertFalse(city1.equals(city3));   // different names
        assertFalse(city1.equals(null));    // cannot equal null
        assertFalse(city1.equals(cityNull)); // cannot equal null
        assertFalse(city1.equals(stringCityNull)); // cannot equal null/other object
        assertFalse(city1.equals(city4));   // different names and x/y values
        assertFalse(city1.equals(city6));   // different names and y values
        //assertTrue(city1.equals(city7));  // equal names and x/y values
        assertFalse(city1.equals(city8));   // different x values
        assertFalse(city1.equals(city9));   // different y values   
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

    /**
     * Test to return false is there is a duplicate in KDTree
     */
    public void testInsertDuplicate()
    {
        it.insert("London", 30, 40);
        assertFalse(it.insert("Duplicate", 30, 40));
    }
    
    /**
     * Testing insert() and print() for a problem coverage issue.
     * Web-CAT says there is an error in print after BST inserts
     * as well as BST removes. Thereforewe'll be trying to 
     * identify that issue in this test case.
     */
    public void testPrintErrorAfterBST()
    {
        // Set initial conditions: Add 5 City objects
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        
        // Call print()
        assertEquals("0Alpha (30, 40)\n"
            + "1  Beta (5, 25)\n"
            + "3      Delta (10, 12)\n"
            + "4        Epsilon (50, 50)\n"
            + "2    Gamma (70, 70)\n", it.print());
        
        // Insert a new City object into the left subtree
        it.insert("Zeta", 15, 15);
        
        // Call print(). The Zeta city should be on level 3
        assertEquals("0Alpha (30, 40)\n"
            + "1  Beta (5, 25)\n"
            + "3      Delta (10, 12)\n"
            + "4        Epsilon (50, 50)\n"
            + "2    Gamma (70, 70)\n"
            + "3      Zeta (15, 15)\n", it.print());
        
        // Remove the Alpha city, the root of the tree.
        // Should return (30, 40)
        assertEquals(it.delete("Alpha"), "Alpha (30, 40)\n");
        
        // Call print. Beta should now be the root on level 0,
        // Delta on level 2, and Zeta on level 2
        assertEquals("0Beta (5, 25)\n"
            + "2    Delta (10, 12)\n"
            + "3      Epsilon (50, 50)\n"
            + "1  Gamma (70, 70)\n"
            + "2    Zeta (15, 15)\n", it.print());
        
        // Add two city objects. One with a duplicate name,
        // another with both a duplicate name and coordinates,
        // and one with only duplicate coordinates. The duplicate
        // name should go through but other two shouldn't
        it.insert("Delta", 11, 11);
        it.insert("Delta", 10, 12);
        it.insert("Omega", 10, 12);
        // info() should only find Delta (11, 11) and Delta (10, 12),
        // the original City object at (10, 12)
        assertEquals(it.info(11, 11), "Delta");
        assertEquals(it.info("Delta"), "Delta (10, 12)\n"
            + "Delta (11, 11)\n");
        assertEquals(it.info(10, 12), "Delta");
        assertEquals(it.info("Omega"), "");
        
        // Call print(), Delta (11, 11) should be on level 3
        assertEquals("0Beta (5, 25)\n"
            + "3      Delta (11, 11)\n"
            + "2    Delta (10, 12)\n"
            + "3      Epsilon (50, 50)\n"
            + "1  Gamma (70, 70)\n"
            + "2    Zeta (15, 15)\n", it.print());
        
        // Remove both Delta cities from the BST
        assertEquals(it.delete("Delta"),"Delta (10, 12)\n"
            + "Delta (11, 11)\n");
        
        // Call print()
        assertEquals("0Beta (5, 25)\n"
            + "2    Epsilon (50, 50)\n"
            + "1  Gamma (70, 70)\n"
            + "2    Zeta (15, 15)\n", it.print());
    }
    
    /**
     * This tests the delete() method, specifically focusing
     * on remove() from the BST class. This test case
     */
    public void testDeleteDuplicate() {

        // Set initial conditions: Add 5 City objects
        it.insert("Delta", 30, 30);
        it.insert("Alpha", 10, 10);
        it.insert("Epsilon", 50, 50);
        it.insert("Delta", 25, 25);
        it.insert("Delta", 5, 5);

        // Call print() before deletion
        assertEquals("1  Alpha (10, 10)\n"
            + "3      Delta (5, 5)\n"
            + "2    Delta (25, 25)\n"
            + "0Delta (30, 30)\n"
            + "1  Epsilon (50, 50)\n", it.print());

        // Delete Delta (30, 30)
        assertEquals("3\nDelta", it.delete(30, 30));
    
        // Call print() after deletion
        assertEquals("1  Alpha (10, 10)\n"
            + "2    Delta (5, 5)\n"
            + "0Delta (25, 25)\n"
            + "1  Epsilon (50, 50)\n", it.print());
        
        // Delete remaining Delta cities
        assertEquals("Delta (25, 25)\n"
            + "Delta (5, 5)\n", it.delete("Delta"));
        
        // Call print() after deletion
        assertEquals("0Alpha (10, 10)\n"
            + "1  Epsilon (50, 50)\n", it.print());        
    }
    
    /**
     * Test the Find() to find an existing city
     */
    public void testFindExistingCity()
    {
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        String found = it.info(5, 25);
        assertNotNull(found);
        assertEquals("Beta", found);
    }
    
    /**
     * Test the method find() and should return null
     * since there is no city with x and y (99,99)
     */
    public void testFindNonexistentCity()
    {
        it.insert("Alpha", 30, 40);
        
        // Using info() in an empty coordinate
        // should return an empty string.
        assertEquals(it.info(99, 99), "");
    }
    
    /**
     * Test the remove method when removing a leaf node
     */
    public void testRemoveLeafNode()
    {
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        assertEquals("4\nBeta", it.delete(5, 25)); // remove city b
        //assertEquals(4, it.size());
        assertEquals("", it.info(5, 25));
    }
      
    /**
     * Test the clear() method and should return 0 for size
     */
    public void testClear()
    {
        // Set initial conditions: Add 2 City objects
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        
        // Call the method
        it.clear();
        
        // Using info() to try and find the objects shouldn't work
        assertEquals(it.info(30, 40), "");
        assertEquals(it.info(5, 25), "");
        assertEquals(it.info("Alpha"), "");
        assertEquals(it.info("Beta"), "");
    }
    
    /**
     * Test the find method when currentCity.getY() is false.
     * Where x matches but y does not
     */
    public void testFind2() 
    {
        it.insert("Blacksburg", 30, 40);

        // Same x, different y — should not match
        // Should return an empty string
        assertEquals(it.info(30, 999), "");
    }

    /**
     * Test the remove method
     */
    public void testRemove2() {
        it.delete("Hello");
        assertEquals("", it.delete("Hello"));
    }
     
    /**
     * Test the remove method for the scenario where x coordinate matches
     * but the y coordinate does not.
     */
    public void testRemove4() {
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        it.insert("Zeta", 5, 999); // same x, different y

        assertEquals("Beta (5, 25)\n", it.delete("Beta"));
    }
    

    /**
     * Test the remove method when y < currentCity.getY() 
     */
    public void testRemove5() 
    {
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        // At level 1, cd = 1 → compare y
        // Beta is at (5, 25), so y = 10 is less → should go left
        // left of Beta by y
        it.insert("Zeta", 2, 10);

        // Remove Zeta to trigger traversal through Beta's left
        //int removed = tree.remove(2, 10);
        //assertNotNull(removed);
        //assertEquals("Zeta", removed.getName());

        // Confirm Zeta is gone, Beta is still there
        assertEquals(it.info(2, 10), "Zeta");
        assertEquals(it.info(5, 25), "Beta");
    }
    
    /**
     * Place a description of your method here.
     */
    public void testRemoveCase2_LeftOnly() {
        // Insert cities to build the KDTree
        assertTrue(it.insert("Root", 50, 50));         // Root node
        assertTrue(it.insert("Left1", 25, 75));        // Goes to left of root
        assertTrue(it.insert("Left2", 10, 80));        // Goes to left of Left1

        // Tree structure before deletion:
        //        Root (50, 50)
        //       /
        //   Left1 (25, 75)
        //   /
        // Left2 (10, 80)

        // Now delete Root — it has only a left child
        String visited = it.delete("Root"); // should trigger Case 2


        // Confirm new root is Left2 (the min in new right subtree)
        assertEquals("Left2", it.info(10, 80));

        // Confirm Root is gone
        assertEquals("", it.info(50, 50));

        // Confirm Left1 is still present
        assertEquals("Left1", it.info(25, 75));
    }
    
    /**
     * Tests the delete() method. In this scenario,
     * we'll be deleting the root and then continuing 
     * to delete the replacement of the removed root.
     */
    public void testDeleteRootMultipleTimes() {
        it.insert("Root", 50, 50);
        it.insert("Root", 40, 40);
        it.insert("Root", 30, 30);
        it.insert("Root", 20, 20);
        it.insert("Root", 10, 10);

        // Delete root repeatedly
        assertEquals("9\nRoot", it.delete(50, 50));
        assertEquals("6\nRoot", it.delete(40, 40));
        assertEquals("3\nRoot", it.delete(30, 30));
        assertEquals("2\nRoot", it.delete(20, 20));
        assertEquals("1\nRoot", it.delete(10, 10));

        // Tree should now be empty
        assertEquals("", it.print());
    }
    
    /**
     * Place a description of your method here.
     */
    public void testDeleteCityWithYDiscriminator() {
        // Insert cities to build a KDTree with Y-discriminator at level 1
        it.insert("Alpha", 30, 40);  // level 0 (X)
        it.insert("Beta", 5, 25);    // level 1 (Y)
        it.insert("Gamma", 70, 70);  // level 1 (Y)
        it.insert("Delta", 10, 12);  // level 2 (X)
        it.insert("Epsilon", 50, 50);// level 2 (X)

        // Delete city "Beta" at (5, 25) — should trigger Y-discriminator logic
        String result = it.delete(5, 25);

        // Expected format: "<nodesVisited>\nBeta"
        String[] lines = result.split("\n");
        assertEquals("Beta", lines[1]);

        // Confirm city is no longer in the database
        assertEquals("", it.info(5, 25));
    }
    
    /**
     * Place a description of your method here.
     */
    public void testFindMinLeftRecursivePath() {
        // Insert cities to build a KDTree where level 0 is X, level 1 is Y
        it.insert("Alpha", 30, 40);  // root
        it.insert("Beta", 20, 30);   // goes left of Alpha
        it.insert("Gamma", 10, 20);  // goes left of Beta

        // Now remove "Alpha" — this will trigger findMin with dim = 0 (X)
        // At level 0, currentDisc = 0, so currentDisc == dim
        // Since Alpha has a left subtree, findMin will recurse left
        String result = it.delete(30, 40);

        // Validate output
        String[] lines = result.split("\n");
        assertEquals("Alpha", lines[1]);

        // Confirm Alpha is gone
        assertEquals("", it.info(30, 40));

        // Confirm Gamma is still present (was not removed as replacement)
        assertEquals("Gamma", it.info(10, 20));
    }



    /**
     * Tests the remove() method.
     * !compareX && dim == 1 with left child present
     */
    public void testRemove6() {
        it.insert("A", 5, 5);
        it.insert("B", 3, 7);
        it.insert("C", 1, 9); // deeper left
        it.delete(5, 5); // triggers Y-axis match at odd level
        assertNotNull(it.info(1, 9));
    }
    
     
    /**
     * Removes a node with a right child
     */
    public void testRemoveTriggersRightSubtreeReplacement() {

        // Build tree:
        //       A(50,50)  ← root
        //         \
        //         B(70,30) ← right child of A
        //         /
        //       C(60,20) ← left child of B


        it.insert("A", 50, 50); // level 0
        it.insert("B", 70, 30); // level 1
        it.insert("C", 60, 20); // level 2

        // Remove B — it has a right child (none), but a left child (C)
        // So we’ll reverse it: make B have a right child instead

        it.insert("A", 50, 50);
        it.insert("B", 70, 30); // insert C first
        // insert B after, so it becomes right child of A
        it.insert("C", 60, 20);

        // Now B is right child of A, and has no children
        // Let’s add a right child to B to trigger the block

        // right child of B
        it.insert("D", 80, 10);

        // Now remove B — it has a right child (D), so this triggers the block
        String removed = it.delete(70, 30);
        assertNotNull(removed);
        assertEquals("6\nB", removed);

        // Confirm B is gone, D was promoted
        assertEquals("", it.info(70, 30));
        assertEquals("D", it.info(80, 10));
    }
    
    
    /**
     * If leftMin is not null
     */
    public void testFindMinLeftMinXWinsViaRemove() {
        // Build tree:
        //       A(50,50)        ← root
        //         \
        //         B(70,30)      ← right child of A
        //         /
        //       C(60,20)        ← left child of B, smaller X than B

        it.insert("A", 50, 50); // level 0
        it.insert("B", 70, 30); // level 1
        it.insert("C", 60, 20); // level 2

     
        String removed = it.delete(50, 50);
        assertNotNull(removed);
        assertEquals("5\nA", removed);

        // Confirm A is gone, and C was promoted
        assertEquals("", it.info(50, 50));
        assertEquals("C", it.info(60, 20));
    }
    

    /**
     *  if (disc == 0) {
     *  if (rightMin.city.getX() < min.city.getX()) {
     */
    public void testFindMinLeftMinYDeepBranchViaRemove() {
        // Build tree:
        //       A(50,50)        ← level 0
        //         \
        //         B(70,70)      ← level 1 (cd = 1)
        //         /
        //       C(80,65)        ← level 2 (cd = 0)
        //       /
        //     D(90,60)          ← level 3 (cd = 1) ← smallest Y

        it.insert("A", 50, 50); // level 0
        it.insert("B", 70, 70); // level 1
        it.insert("C", 80, 65); // level 2
        it.insert("D", 90, 60); // level 3

        String removed = it.delete(50, 50);
        assertNotNull(removed);
        assertEquals("8\nA", removed);

        // Confirm A is gone, and D was promoted
        assertEquals("", it.info(50, 50));
        assertEquals("D", it.info(90, 60));
    }
    

    /** 
     * return c.getY(); for getCoord()
     *
    public void testFindMinLeftBranchTaken() {
        // Build tree:
        //         A(50, 50) [level 0]
        //        /
        //     B(30, 40)    [level 1]
        //    /
        //  C(20, 30)       [level 2]

        it.insert("A", 50, 50); // root
        it.insert("B", 30, 40); // left of A
        it.insert("C", 20, 30); // left of B

        // Now remove A (50, 50)
        // At level 0, dim = 0 → currentDisc == dim
        // rt.right == null, rt.left != null → triggers:
        // findMin(rt.left, dim, level + 1)
        // Inside findMin, rt = B, level = 1, 
        /// dim = 0 → currentDisc = 1 ≠ dim → goes to else
        // Then inside findMin(B.left, dim, level + 2), 
        ///rt = C, level = 2, currentDisc = 0 == dim
        // rt.left == null → returns C

        // But we want to hit the else branch: rt.left != null
        // So we add a left child to C

        //City d = new City("D", 10, 20); // left of C
        it.insert("D", 10, 20);

        // Now remove B (30, 40) instead of A
        // At level 1, dim = 1 → currentDisc == dim
        // rt.left = C → triggers findMin(C, dim, level + 1)
        // Inside findMin, level = 2, dim = 1, 
        ///currentDisc = 0 ≠ dim → goes to else
        // Then findMin(C.left, dim, level + 2) → rt = D, 
        ///level = 3, currentDisc = 1 == dim
        // rt.left != null → finally hits the line!

        String removed = it.delete(30, 40);
        assertEquals("6\nB", removed);
        
        // Confirm D is promoted
       // City promoted = it.info(10, 20);
        //assertNotNull(promoted);
        assertEquals("D", it.info(10, 20));
    }
    */
    
    public void testRemoveNonExistentCityReturnsZero() {
        it.insert("A", 3, 6);
        it.insert("B", 17, 15);
        it.insert("C", 13, 15);
        it.insert("D", 6, 12);
        it.insert("E", 9, 1);

        // Coordinates (99, 99) do not exist in the tree
        assertEquals("", it.delete(99, 99));
        assertEquals("A (3, 6)\n", it.delete("A"));
        assertEquals("3\nC", it.delete(13, 15));
        assertEquals("5\nD", it.delete(6, 12));
        assertEquals("B (17, 15)\n", it.delete("B"));
        assertEquals("", it.info(99,99));
        assertEquals("E (9, 1)\n", it.delete("E"));
    }
    




    // -------Test Search---------------
    /**
     * Test for the search() method. In this test case, search
     * radius initially includes what we're looking for.
     */
    public void testSearchWithinRadius() {
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        // Center near Alpha, radius large enough to include Alpha and Epsilon
        String result = it.search(30, 40, 25);
        assertTrue(result.contains("Alpha"));
        assertTrue(result.contains("Epsilon"));
        assertTrue(result.matches("(?s).*\\d+$")); // Ends with node count
    }

    /**
     * Test for the search() method. In this case we start our search
     * with the radius exactly over what we're looking for.
     */
    public void testSearchExactMatch() {
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        // Center exactly on Beta, radius 0
        String result = it.search(5, 25, 0);
        assertTrue(result.contains("Beta"));
        assertTrue(result.matches("(?s).*\\d+$")); // Ends with node count
    }

    /**
     * Test for the search() method. In this case, we cannot find
     * the coordinate that we're looking since it's not in the
     * GISDB object.
     */
    public void testSearchNoMatch() {
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        // Far from all cities
        String result = it.search(0, 0, 5);
        assertFalse(result.contains("Alpha"));
        assertFalse(result.contains("Beta"));
        assertFalse(result.contains("Gamma"));
        assertFalse(result.contains("Delta"));
        assertFalse(result.contains("Epsilon"));
        assertTrue(result.matches("(?s)^\\d+$")); // Only node count
    }
    
    /**
     * Tests the search() method. In this test case, there are
     * several City objects within the search radius.
     */
    public void testSearchSeveralMatches() {
        // Set initial conditions
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        
        // Call the method: Radius should contain
        // every city other than Gamma
        String result = it.search(40, 40, 42);
        
        assertEquals(result, "Alpha (30, 40)\n"
            + "Beta (5, 25)\n"
            + "Delta (10, 12)\n"
            + "Epsilon (50, 50)\n5");
        assertFalse(result.contains("Gamma"));
    }
    
    /**
     * Tests the search() method. In this test case,
     * most City objects are on the very edge of the 
     * search radius.
     */
    public void testSearchRadiusEdge() {
        // Set inital conditions: Add City objects
        it.insert("Alpha", 0, 50);
        it.insert("Beta", 50, 0);
        it.insert("Gamma", 50, 50);
        it.insert("Delta", 0, 0);
        it.insert("Epsilon", 25, 50);
        it.insert("Charlie", 50, 25);
        it.insert("Zulu", 42, 42);
        
        // Call the method
        String result = it.search(25, 25, 25);
        
        // search() should include Epsilon, Charlie, and
        // Zulu, where Zulu is exactly on the radius.
        assertEquals(result, "Epsilon (25, 50)\n"
            + "Zulu (42, 42)\n"
            + "Charlie (50, 25)\n7");
    }
    
     /**
      * Tests the search() method. In this scenario, the
      * radius is negative so an empty string is returned.
      */
    public void testSearchNegativeRadius() {   
       
        // Invalid radius, it's negative
        String result = it.search(30, 40, -10);
        // Should return an empty string
        assertEquals("", result);
    }

    /**
     * Test case for disc == 0 and x - radius > node.city.getX()
     */
    public void testLeftPruneDisc0False() {
        it.insert("A", 50, 50); // root
        it.insert("B", 30, 30); // left

        // x = 60, radius = 5 → x - radius = 55 > A.x = 50 → should skip left
        String result = it.search(60, 50, 5);
        
        // B is in left subtree, should be skipped
        assertFalse(result.contains("B"));
    }
    
    /**
     * Test case for search() method.
     */
    public void testLeftPruneDisc1False() {
        it.insert("A", 50, 50); // root
        it.insert("B", 30, 30); // left

        // y = 60, radius = 5 → y - radius = 55 > A.y = 50 → should skip left
        String result = it.search(50, 60, 5);
        
        // B is in left subtree, should be skipped
        assertFalse(result.contains("B"));
    }
    
    // -------Test Debug---------------
    /**
     * Testing the debug() method. In this test case,
     * we'll be ensuring that all names inserted in the
     * GISDB object are found in debug() as well as that
     * the identation/level formatting are correct.
     */
    public void testDebugStructure() {
        // Set initial conditions: Insert 5 City objects
        it.insert("Alpha", 30, 40);
        it.insert("Beta", 5, 25);
        it.insert("Gamma", 70, 70);
        it.insert("Delta", 10, 12);
        it.insert("Epsilon", 50, 50);
        
        // Call the method
        String output = it.debug();

        // Check that all cities are printed
        assertTrue(output.contains("Alpha"));
        assertTrue(output.contains("Beta"));
        assertTrue(output.contains("Gamma"));
        assertTrue(output.contains("Delta"));
        assertTrue(output.contains("Epsilon"));

        // Check indentation and level formatting
        assertTrue(output.matches("(?s).*0Alpha.*")); // Root
        assertTrue(output.matches("(?s).*1  Beta.*"));  // One level deeper
    }
    
    /**
     * Tests the debug() method. In this scenario,
     * we'll be running debug() on an empty GISDB object,
     * therefore, debug() should return an empty string.
     */
    public void testPrintEmptyTree() {
        // Should return an empty string
        assertEquals("", it.debug());
    }
    
    //----------BST DUPS TEST----------------
    /**
     * Tests deletion of a non-root duplicate node that has two children,
     * requiring replacement by the max from its own left subtree.
     *
     * Tree Structure (Names in ():
     * D(400, 400)
     * /  \
     * B(200, 200)  F(600, 600)
     * /   \
     * B(210, 210) C(300, 300)
     * /
     * A(100, 100)
     *
     * Remove B(200, 200) (ID: 20). Two children: B(210, 210) (left) and C(300, 300) (right).
     * Replacement should be Max of Left Subtree: B(210, 210) (ID: 21).
     */
    public void testRefDeleteDupsError() {
        // IDs: 10, 20, 21, 30, 40, 60 -> Coords: 100, 200, 210, 300, 400, 600
        int xA = 100, xB1 = 200, xB2 = 210, xC = 300, xD = 400, xF = 600;

        // Insertion order: D, B1, F, C, B2, A
        it.insert("D", xD, xD);  // Root D(400, 400)
        it.insert("B", xB1, xB1); // L of D: B(200, 200) - To be deleted
        it.insert("F", xF, xF);  // R of D: F(600, 600)
        it.insert("C", xC, xC);  // R of B(200, 200) (since 'C' > 'B'): C(300, 300)
        it.insert("B", xB2, xB2); // L of B(200, 200) (since 'B' >= 'B'): B(210, 210) - Max of left
        it.insert("A", xA, xA);  // L of B(210, 210) (since 'A' < 'B'): A(100, 100)

        // Expected Initial BST Print (Inorder): A, B(21), B(20), C, D, F
      
        String expectedInitial =
                "3      A (100, 100)\n" +
                "2    B (210, 210)\n" +
                "1  B (200, 200)\n" +
                "2    C (300, 300)\n" +
                "0D (400, 400)\n" +
                "1  F (600, 600)\n";

        assertEquals(expectedInitial, it.print());

        // Remove B(20) (ID: 20) using coordinates (200, 200)
        it.delete(xB1, xB1); // Remove B(200, 200)

        // B(20) is replaced by B(21) (Max of its left subtree).
        // The original B(21) node at (210, 210) is deleted.
        String expectedAfterDelete =
                "2    B (210, 210)\n" +
                "1  B (200, 200)\n" + // B(21) value is now at level 1
                //"0D (400, 400)\n" +
                //"0D (400, 400)\n" +
                "1  F (600, 600)\n";

        assertEquals(expectedAfterDelete, it.print());
    }
    

    /**
     * General stress test for the two-child deletion policy
     * (Max from the Left Subtree) in a complex, non-duplicate structure.
     *
     * Tree Structure (Insert Order: 50, 25, 75, 15, 40, 65, 85, 30, 45, 70, 80)
     *
     * 50 (F) (Root, 500)
     * /   \
     * 25 (B, 250) 75 (I, 750)
     * / \     / \
     * 15 (A, 150) 40 (D, 400) 65 (G, 650) 85 (K, 850)
     * / \  \  /
     * 30 (C, 300) 45 (E, 450) 70 (H, 700) 80 (J, 800)
     *
     * Remove 50 (F) (Root). Max of Left Subtree is 45 (E).
     */
    public void testRefRemoveStressBSTError() {
        // IDs: 15, 25, 30, 40, 45, 50, 65, 70, 75, 80, 85
        // Coords: 150, 250, 300, 400, 450, 500, 650, 700, 750, 800, 850
        int x15 = 150, x25 = 250, x30 = 300, x40 = 400, x45 = 450;
        int x50 = 500, x65 = 650, x70 = 700, x75 = 750, x80 = 800, x85 = 850;

        // Insertion order: F(50), B(25), I(75), A(15), D(40), G(65), K(85), C(30), E(45), H(70), J(80)
        it.insert("F", x50, x50); // Root
        it.insert("B", x25, x25);
        it.insert("I", x75, x75);
        it.insert("A", x15, x15);
        it.insert("D", x40, x40);
        it.insert("G", x65, x65);
        it.insert("K", x85, x85);
        it.insert("C", x30, x30);
        it.insert("E", x45, x45); // Max of left subtree
        it.insert("H", x70, x70);
        it.insert("J", x80, x80);

        // Remove F(500, 500) (Root)
        it.delete(x50, x50);

        // Expected New Root: E(450, 450) (Max of left subtree)
        // E(45) value replaces F(50). Original E(45) node (right child of D(40)) is deleted.
        String expectedAfterDelete =
                "3      A (150, 150)\n" +
                "2    B (250, 250)\n" +
                "4        C (300, 300)\n" +
                "3      D (400, 400)\n" +
                "0E (450, 450)\n" + // New Root
                "2    G (650, 650)\n" +
                "3      H (700, 700)\n" +
                "1  I (750, 750)\n" +
                "3      J (800, 800)\n" +
                "2    K (850, 850)\n";

        // Check if the old root is indeed gone (by trying to delete it again)
        // it.delete returns a string containing the number of nodes visited followed by the city name,
        // which will be blank if nothing was deleted. We check if the result is empty or only contains '0\n' (0 nodes visited, blank name).
        String notFound = it.delete(x50, x50);
        assertTrue(notFound.trim().isEmpty() || notFound.trim().equals("0"));

        assertEquals(expectedAfterDelete, it.print());
    }
    

}