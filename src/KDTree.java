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
     *  A private inner class that represents a node in the KD Tree.
     *  It stores city records and pointers to its children.
     */
    private class KDTreeNode
    {
        private City cityRecord;
        private KDTreeNode left;
        private KDTreeNode right;
        
        /**
         * The constructor for KDTreeNode.
         * 
         * @param city is the City object to be stored in the node
         */
        public KDTreeNode(City city)
        {
            this.cityRecord = city;
            this.left = null;
            this.right = null;
        }
        
        /**
         * Gets the city.
         * 
         * @return cityRecord is the city
         */
        public City getCity()
        {
            return cityRecord;
        }
        
        /**
         * Gets the left KDTreeNode.
         * 
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
     * Clears the entire tree.
     */
    public void clear()
    {
        root = null;
        nodeCount = 0;
    }
    
    /**
     * Gets the size of the tree
     * 
     * @return nodeCount is the number of nodes in the KDTree
     */
    public int size()
    {
        return nodeCount;
    }
    
    /**
     * Insert a new city into the KD Tree. It will check
     * for any duplicates before inserting the city.
     * 
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
     * Recursively inserts a new city into KDTree.
     * Alternates between comparing x and y at each level.
     * 
     * @param rt is the current node
     * @param newCity is the city to insert
     * @param level is the current depth
     * @return rt the updated root of the subtree
     */
    private KDTreeNode insertHelp(KDTreeNode rt, City newCity, int level)
    {
        if (rt == null)
        {
            return new KDTreeNode(newCity);
        }
        
        // Determine which dimension to compare: 0 for x, 1 for y
        int cd = level % DIMENSIONS;
        
        // Compare based on the current dimension (cd)
        if (cd == 0) // Compare x coordinate
        {
            if (newCity.getX() < rt.getCity().getX())
            {
                // Go left since newCity x is smaller
                rt.setLeft(insertHelp(rt.getLeft(), newCity, level + 1));
            }
            else
            {
                // Go right since newCity x is bigger
                rt.setRight(insertHelp(rt.getRight(), newCity, level + 1));
            }
        }
        else
        {
            // Compare y coordinate
            if (newCity.getY() < rt.getCity().getY())
            {
                // Go left since newCity y is smaller
                rt.setLeft(insertHelp(rt.getLeft(), newCity, level + 1));
            }
            else
            {
                // Go right since newCity y is bigger
                rt.setRight(insertHelp(rt.getRight(), newCity, level + 1));
            }
        }
        return rt;
    }
    
    /**
     * Find a city's coordinates
     * 
     * @param x is the x coordinate for the target city
     * @param y is the x coordinate for the target city
     * @return The City object if found and null otherwise
     */
    public City find(int x, int y)
    {
        return findHelp(root, x, y, 0);
    }
    
    /**
     * Recursively searches for a city by its coordinates (x,y).
     * It will alternate searching between x and y at each depth/level.
     * 
     * @param rt is the current node
     * @param x is the target x coordinate
     * @param y is the target y coordinate
     * @param level is the current depth
     * @return the City object if found, null otherwise
     */
    private City findHelp(KDTreeNode rt, int x, int y, int level)
    {
        if (rt == null)
        {
            return null;
        }
        // Get the city record 
        City currentCity = rt.getCity();
        // Check if this city matches the targets coordinates
        if (currentCity.getX() == x)
        {
            if (currentCity.getY() == y)
            {
                return currentCity;
            }
        }
        
        // Determine which dimension to compare: 0 for x, 1 for y
        int cd = level % DIMENSIONS;
        // Decide which subtree to search based on current dimension (cd)
        if (cd == 0) // Compare x coordinates
        {
            if (x < currentCity.getX()) // If target x is less than currentCity x
            {
                return findHelp(rt.getLeft(), x, y, level + 1);
            }
            return findHelp(rt.getRight(), x, y, level + 1); // when target x is greater than currentCity x
        }
        if (y < currentCity.getY()) // Compare y coordinates: If target y is less than currentCity y
        {
            return findHelp(rt.getLeft(), x, y, level + 1);
        }
        return findHelp(rt.getRight(), x, y, level + 1); // when target y is greater than currentCity y
    }
    
    /**
     * Finds the node with the minimum value along the specified dimension (0 = x, 1 = y).
     * 
     * @param rt is the current node
     * @param dim is the dimension (0 or 1)
     * @param level is the current depth of the tree
     * @return minNode the KDTreeNode that contains the minimum value for the dimension
     */
    private KDTreeNode findMinHelp(KDTreeNode rt, int dim, int level)
    {
        if (rt == null)
        {
            return null;
        }
        // Current node dimension
        int cd = level % DIMENSIONS;
        
        if (cd == dim)
        {   // Only need to search the left of the tree since cd == dim
            if (rt.getLeft() == null)
            {
                return rt;
            }
            return findMinHelp(rt.getLeft(), dim, level + 1);
        }
        
        // The min could be anywhere subtree or current node.
        KDTreeNode leftMin = findMinHelp(rt.getLeft(), dim, level + 1);
        KDTreeNode rightMin = findMinHelp(rt.getRight(), dim, level + 1);
        
        // Assume current node is the min
        KDTreeNode minNode = rt;
        
        // Compare with left subtree
        if (leftMin != null)
        {
            if (dim == 0 && leftMin.getCity().getX() < minNode.getCity().getX())
            {
                minNode = leftMin;
            }
            else if(dim == 1 && leftMin.getCity().getY() < minNode.getCity().getY())
            {
                minNode = leftMin;
            }
        }
        // Compare with right subtree
        if (rightMin != null)
        {
            if (dim == 0 && rightMin.getCity().getX() < minNode.getCity().getX())
            {
                minNode = rightMin;
            }
            else if(dim == 1 && rightMin.getCity().getY() < minNode.getCity().getY())
            {
                minNode = rightMin;
            }
        }
        
        return minNode;
    }
    
    /**
     * Removes the node with the minimum value for the specified dimension.
     * 
     * @param rt is the current node\
     * @param dim is the dimension (0 or 1)
     * @param level is the current depth of the tree
     * @return rt is the updated root of the subtree after removed
     */
    private KDTreeNode removeMinHelp(KDTreeNode rt, int dim, int level)
    {
        if (rt == null)
        {
            return null;
        }
        int cd = level % DIMENSIONS;
        
        if (cd == dim)
        {
            // If current dim matches the target, the min is in the left subtree
            if (rt.getLeft() == null)
            {
                // Remove node by returning its right child
                return rt.getRight();
            }
            // Remove the min by recursing into left subtree
            rt.setLeft(removeMinHelp(rt.getLeft(), dim, level + 1));
        }
        else
        {   // If current dim does not match, min could be anywhere
            // Much check both subtree
            rt.setLeft(removeMinHelp(rt.getLeft(), dim, level + 1));
            rt.setRight(removeMinHelp(rt.getRight(), dim, level + 1));
        }
        return rt;
    }
    
    /**
     * Finds and removes a City by x and y coordinates.
     * If the node to remove is found, replace by the minimum node
     * 
     * @param rt is the current node
     * @param x is the city's x coordinate
     * @param y is the city's y coordinate
     * @param level is the current depth 
     * @return rt is the updated root of the subtree
     */
    private KDTreeNode removeHelp(KDTreeNode rt, int x, int y, int level)
    {
        if (rt == null) 
        {
            return null;
        }
        
        int cd = level % DIMENSIONS;
        City currentCity = rt.getCity();
        
        // Find node to remove
        if (currentCity.getX() == x)
        {
            if (currentCity.getY() == y)
            {
             // Node has right subtree -> replace with min from right
                if (rt.getRight() != null)
                {
                    KDTreeNode minNode = findMinHelp(rt.getRight(), cd, level + 1);
                    rt.cityRecord = minNode.getCity(); // Replace current nodes city
                    rt.setRight(removeMinHelp(rt.getRight(), cd, level + 1)); // Remove the min node
                }
                // No right but has left subtree -> replace with min from left
                else if (rt.getLeft() != null)
                {
                    KDTreeNode minNode = findMinHelp(rt.getLeft(), cd, level + 1);
                    rt.cityRecord = minNode.getCity();
                    // Delete the min node from the left subtree
                    rt.setLeft(removeMinHelp(rt.getLeft(), cd, level + 1));
                    rt.setRight(rt.getLeft()); // Move the ramaining left subtree to the right
                    rt.setLeft(null); // Clear left since it was removed
                }
                else
                {
                    // Leaf node so just remove
                    return null;
                }
                return rt;
            }
        }

       
        // Recurse into left or right subtree based on current dimension (cd)
        if (cd == 0) 
        {
            if (x < currentCity.getX()) 
            {
                rt.setLeft(removeHelp(rt.getLeft(), x, y, level + 1));
            } 
            else 
            {
                rt.setRight(removeHelp(rt.getRight(), x, y, level + 1));
            }
        } 
        else 
        {   // cd == 1
            if (y < currentCity.getY()) 
            {
                rt.setLeft(removeHelp(rt.getLeft(), x, y, level + 1));
            } 
            else 
            {
                rt.setRight(removeHelp(rt.getRight(), x, y, level + 1));
            }
        }
        return rt; 
    }
    
    /**
     * Remove a city by its coordinates
     * 
     * @param x is the target x coordinate
     * @param y is the target y coordinate
     * @return temp is the City object that was removed and null if not found
     */
    public City remove(int x, int y)
    {
        City temp = find(x, y); // Check if the coordinates exists
        if (temp != null)
        {
            root = removeHelp(root, x, y, 0);
            nodeCount--;
        }
        return temp;
    }
    /**
     * methods to add
     * search(int x, int y, int radius)
     * toStringIndented()
     */
}
