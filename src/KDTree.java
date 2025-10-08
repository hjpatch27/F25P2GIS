/**
 * The KDTree class.
 * 
 * @author Henry Patch (hjpatch27)
 * @author Nyssa Loeu (nysaal23)
 * @version 10.5.2025
 */
public class KDTree {

    /**
     * A private inner class that represents a node in the KD Tree.
     * It stores city records and pointers to its children.
     */
    private class KDTreeNode {
        private City cityRecord;
        private KDTreeNode left;
        private KDTreeNode right;

        /**
         * The constructor for KDTreeNode.
         * 
         * @param city is the City object to be stored in the node
         */
        public KDTreeNode(City city) {
            this.cityRecord = city;
            this.left = null;
            this.right = null;
        }


        /**
         * Gets the city.
         * 
         * @return cityRecord is the city
         */
        public City getCity() {
            return cityRecord;
        }
        
        /**
         * Replaces the city record stored in this node with a new City object.
         * 
         * @param other the City to store in this node
         */
        public void setCity(City other)
        {
            this.cityRecord = new City(other.getName(), other.getX(), other.getY());

        }
        

        /**
         * Gets the left KDTreeNode.
         * 
         * @return left is the left node
         */
        public KDTreeNode getLeft() {
            return left;
        }


        /**
         * Gets the right KDTreeNode
         * 
         * @return right is the right node
         */
        public KDTreeNode getRight() {
            return right;
        }


        /**
         * Setter method for KDTreeNode left
         * 
         * @param left is the element to be changed too
         */
        public void setLeft(KDTreeNode left) {
            this.left = left;
        }


        /**
         * Setter method for KDTreeNode right
         * 
         * @param right is the element to be changed too
         */
        public void setRight(KDTreeNode right) {
            this.right = right;
        }
    }

    private KDTreeNode root; // The root of the KDTree
    private int nodeCount; // The number of nodes in the KDTree
    private static final int DIMENSIONS = 2; // for (x,y)

    // ----------------------------------------------------------
    /**
     * Create a new KDTree object.
     */
    public KDTree() {
        root = null;
        nodeCount = 0;
    }


    /**
     * Clears the entire tree.
     */
    public void clear() {
        root = null;
        nodeCount = 0;
    }


    /**
     * Gets the size of the tree
     * 
     * @return nodeCount is the number of nodes in the KDTree
     */
    public int size() {
        return nodeCount;
    }


    /**
     * Insert a new city into the KD Tree. It will check
     * for any duplicates before inserting the city.
     * 
     * @param city is the City to insert
     * @return true if inserted, false otherwise
     */
    public boolean insert(City city) {
        if (find(city.getX(), city.getY()) != null) {
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
    private KDTreeNode insertHelp(KDTreeNode rt, City newCity, int level) {
        if (rt == null) {
            return new KDTreeNode(newCity);
        }

        // Determine which dimension to compare: 0 for x, 1 for y
        int cd = level % DIMENSIONS;

        // Compare based on the current dimension (cd)
        if (cd == 0) // Compare x coordinate
        {
            if (newCity.getX() < rt.getCity().getX()) {
                // Go left since newCity x is smaller
                rt.setLeft(insertHelp(rt.getLeft(), newCity, level + 1));
            }
            else {
                // Go right since newCity x is bigger
                rt.setRight(insertHelp(rt.getRight(), newCity, level + 1));
            }
        }
        else {
            // Compare y coordinate
            if (newCity.getY() < rt.getCity().getY()) {
                // Go left since newCity y is smaller
                rt.setLeft(insertHelp(rt.getLeft(), newCity, level + 1));
            }
            else {
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
    public City find(int x, int y) {
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
    private City findHelp(KDTreeNode rt, int x, int y, int level) {
        if (rt == null) {
            return null;
        }
        
        City currentCity = rt.getCity();
        // 1. Check if we found the city
        if (currentCity.getX() == x && currentCity.getY() == y) {
            return currentCity;
        }

        int cd = level % DIMENSIONS;
        
        // 2. Decide which subtree to search
        if (cd == 0) // Compare x coordinates
        {
            // Traverse based on the target x coordinate
            if (x < currentCity.getX()) {
                return findHelp(rt.getLeft(), x, y, level + 1);
            }
            else { // Go right for x >= currentCity.getX(). This matches insertHelp.
                return findHelp(rt.getRight(), x, y, level + 1);
            }
        }
        else // cd == 1 (Compare y coordinates)
        {
            // Traverse based on the target y coordinate
            if (y < currentCity.getY()) {
                return findHelp(rt.getLeft(), x, y, level + 1);
            }
            else { // Go right for y >= currentCity.getY(). This matches insertHelp.
                return findHelp(rt.getRight(), x, y, level + 1);
            }
        }                                   // currentCity y
    }


    /**
     * Finds the node with the minimum value along the specified dimension (0 =
     * x, 1 = y).
     * 
     * @param rt is the current node
     * @param dim is the dimension (0 or 1)
     * @param level is the current depth of the tree
     * @return minNode the KDTreeNode that contains the minimum value for the
     *         dimension
     */    
    public KDTreeNode findMin(KDTreeNode rt, int dim, int level) {
        if (rt == null) {
            return null;
        }
        
        boolean compareX = (level % DIMENSIONS == 0);

       if (compareX && dim == 0)
       {
           if (rt.getLeft() == null)
           {
               return rt;
           }
           return findMin(rt.getLeft(), dim, level + 1);
       }
       else if (!compareX && dim == 1)
       {
           if (rt.getLeft() == null)
           {
               return rt;
           }
           return findMin(rt.getLeft(), dim, level + 1);
       }
       else
       {
           KDTreeNode leftMin = findMin(rt.getLeft(), dim, level + 1);
           KDTreeNode rightMin = findMin(rt.getRight(), dim, level + 1);
           KDTreeNode minNode = rt;
           
           if (leftMin != null)
           {
               if (dim == 0)
               {
                   if (leftMin.getCity().getX() < minNode.getCity().getX())
                   {
                       minNode = leftMin;
                   }
               }
               else
               {
                   if (leftMin.getCity().getY() < minNode.getCity().getY())
                   {
                       minNode = leftMin;
                   }
               }
           }
           if (rightMin != null)
           {
               if (dim == 0)
               {
                   if (rightMin.getCity().getX() < minNode.getCity().getX())
                   {
                       minNode = rightMin;
                   }
               }
               else
               {
                   if (rightMin.getCity().getY() < minNode.getCity().getY())
                   {
                       minNode = rightMin;
                   }
               }
           }
           return minNode;
       }

    }
    


    /**
     * Removes the node with the minimum value for the specified dimension.
     * 
     * @param rt
     *            is the current node\
     * @param dim
     *            is the dimension (0 or 1)
     * @param level
     *            is the current depth of the tree
     * @return rt is the updated root of the subtree after removed
     */
    private KDTreeNode removeMinHelp(KDTreeNode rt, int level) {
        if (rt == null) {
            return null;
        }

        boolean compareX = (level % DIMENSIONS == 0);

        // If this node has no left child, its right child replaces it
        if (rt.getLeft() == null)
        {
            return rt.getRight();
        }
        // Otherwise, recurse left to continue removing the min
        rt.setLeft(removeMinHelp(rt.getLeft(), level + 1));
        
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
    private KDTreeNode removeHelp(KDTreeNode rt, int x, int y, int level) {
        if (rt == null) {
            return null;
        }

        boolean compareX = (level % DIMENSIONS == 0);

        // Check if we found target city
        if (rt.getCity().getX() == x && rt.getCity().getY() == y)
        {
            // If the node has a right subtree
            if (rt.getRight() != null)
            {
                KDTreeNode minNode;
                if (compareX)
                {
                    // Find the min node in the right subtree based on x
                    minNode = findMin(rt.getRight(), 0, level + 1);
                }
                else
                {
                    // Find the min node in the right subtree based on y
                    minNode = findMin(rt.getRight(), 1, level + 1);
                }
                // Replace current node's city with min node
                rt.setCity(minNode.getCity());
                // Remove the min node from the right subtree
                rt.setRight(removeMinHelp(rt.getRight(), level + 1));
            }
            // Node has no right child but has a left child
            else if (rt.getLeft() != null)
            {
                KDTreeNode minNode;
                if(compareX)
                {
                    minNode = findMin(rt.getLeft(), 0, level + 1);
                }
                else
                {
                    minNode = findMin(rt.getLeft(), 1, level + 1);
                }
                
                rt.setCity(minNode.getCity());
                rt.setRight(removeMinHelp(rt.getLeft(), level + 1));
                rt.setLeft(null);
            }
            else
            {
                // Leaf Node
                return null;
            }
        }
        else
        {
            // Recurse into correct subtree
            if (compareX)
            {
                if (x < rt.getCity().getX())
                {
                    rt.setLeft(removeHelp(rt.getLeft(), x, y, level + 1));
                }
                else
                {
                    rt.setRight(removeHelp(rt.getRight(), x, y, level + 1));
                }
            }
            else
            {
                if (x < rt.getCity().getY())
                {
                    rt.setLeft(removeHelp(rt.getLeft(), x, y, level + 1));
                }
                else
                {
                    rt.setRight(removeHelp(rt.getRight(), x, y, level + 1));
                }
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
    public City remove(int x, int y) {
        City temp = find(x, y); // Check if the coordinates exists
        if (temp != null) {
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
