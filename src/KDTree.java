/**
 * The KDTree class.
 * 
 * @author Henry Patch (hjpatch27)
 * @author Nyssa Loeu (nysaal23)
 * @version 10.5.2025
 */
public class KDTree 
{

    /**
     *  A private inner class that represents a node in the KD Tree
     *  It stores city records and pointers to its children.
     */
    private class KDTreeNode
    {
        private City cityRecord;
        private KDTreeNode left;
        private KDTreeNode right;
        
        /**
         * The constructor for KDTreeNode
         * @param city is the City object to be stored in the node
         */
        public KDTreeNode(City city)
        {
            this.cityRecord = city;
            this.left = null;
            this.right = null;
        }
        
        /**
         * Gets the city
         * @return cityRecord is the city
         */
        public City getCity()
        {
            return cityRecord;
        }
        
        /**
         * Gets the left KDTreeNode
         * @return left is the left node
         */
        public KDTreeNode getLeft()
        {
            return left;
        }
        
        /**
         * Gets the right KDTreeNode
         * @return right is the right node
         */
        public KDTreeNode getRight()
        {
            return right;
        }
        
        /**
         * Setter method for KDTreeNode left
         * @param left is the element to be changed too
         */
        public void setLeft(KDTreeNode left)
        {
            this.left = left;
        }
        
        /**
         * Setter method for KDTreeNode right
         * @param right is the element to be changed too
         */
        public void setRight(KDTreeNode right)
        {
            this.right = right;
        }
    }
    
    private KDTreeNode root; // The root of the KDTree
    private int nodeCount; // The number of nodes in the KDTree
    private static final int DIMENSIONS = 2;  // for (x,y)
    
    // ----------------------------------------------------------
    /**
     * Create a new KDTree object.
     */
    public KDTree()
    {
        root = null;
        nodeCount = 0;
    }
    
    /**
     * Clears the entire tree
     */
    public void clear()
    {
        root = null;
        nodeCount = 0;
    }
    
    /**
     * Gets the size of the tree
     * @return nodeCount is the number of nodes in the KDTree
     */
    public int size()
    {
        return nodeCount;
    }
    
    /**
     * Insert a city into the KD Tree. It will check
     * for any duplicates before inserting the city
     * @param city is the City to insert
     * @return true if inserted, false otherwise
     */
    public boolean insert(City city)
    {
        if (find(city.getX(), city.getY()) != null)
        {
            return false;
        }
        root = insertHelp(root, city, 0);
        nodeCount++;
        return true;
    }
    
    /**
     * Find a city's coordinates
     * @param x is the x coordinate for the target city
     * @param y is the x coordinate for the target city
     * @return The City object if found and null otherwise
     */
    public City find(int x, int y)
    {
        return findHelp(root, x, y, 0);
    }
    
    /**
     * Helper method for insert()
     * @param rt is the current node
     * @param newCity is the city to insert
     * @param level is the current depth
     * @return rt the updated root of the subtree
     */
    private KDTreeNode insertHelp(KDTreeNode rt, City newCity, int level)
    {
        return rt;
        
    }
    
    /**
     * @param rt is the current node
     * @param x is the target x coordinate
     * @param y is the target y coordinate
     * @param level is the current depth
     * @return rt the City object if found, null otherwise
     */
    private City findHelp(KDTreeNode rt, int x, int y, int level)
    {
        if (rt == null)
        {
            return null;
        }
        // Get the city record 
        City currentCity = rt.getCity();
        return null;
        
    }
    
}
