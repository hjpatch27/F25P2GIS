/**
 * The KDTree class, implements a 2D KD Tree for City objects.
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
        private City city;
        private KDTreeNode left;
        private KDTreeNode right;

        /**
         * The constructor for KDTreeNode.
         * 
         * @param newCity
         *            is the City object to be stored in the node
         */
        public KDTreeNode(City newCity) {
            this.city = newCity;
            this.left = null;
            this.right = null;
        }

        /**
         * Gets the city.
         * 
         * @return cityRecord is the city
         */
        public City getCity() {
            return city;
        }

        /**
         * Replaces the city record stored in this node with a new City object.
         * 
         * @param other
         *            the City to store in this node
         */
        public void setCity(City other) {
            this.city = new City(other.getName(), other.getX(), other.getY());
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
         * @param left
         *            is the element to be changed too
         */
        public void setLeft(KDTreeNode left) {
            this.left = left;
        }

        /**
         * Setter method for KDTreeNode right
         * 
         * @param right
         *            is the element to be changed too
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
     * @param city
     *            is the City to insert
     * @return true if inserted, false otherwise
     */
    public boolean insert(City city) {
        if (find(city.getX(), city.getY()) != null) {
            return false;
        }
        root = insertHelp(root, city, 0);
        nodeCount += 1;
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
        int cd = level & 1;

        // Compare based on the current dimension (cd)
        if (cd == 0) // Compare x coordinate
        {
            level += 1;
            if (newCity.getX() < rt.getCity().getX()) {
                // Go left since newCity x is smaller
                rt.setLeft(insertHelp(rt.getLeft(), newCity, level));
            }
            else {
                // Go right since newCity x is bigger
                level += 1;
                rt.setRight(insertHelp(rt.getRight(), newCity, level));
            }
        }
        else {
            level += 1;
            // Compare y coordinate
            if (newCity.getY() < rt.getCity().getY()) {
                // Go left since newCity y is smaller
                rt.setLeft(insertHelp(rt.getLeft(), newCity, level));
            }
            else {
                // Go right since newCity y is bigger
                level += 1;
                rt.setRight(insertHelp(rt.getRight(), newCity, level));
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
        if (rt == null)
            return null; // Base case: not found

        if (rt.city.getX() == x && rt.city.getY() == y) {
            return rt.city; // Found the city
        }

        int cd = level & 1;

        if ((cd == 0 && x < rt.city.getX()) || (cd == 1 && y < rt.city
            .getY())) {
            level += 1;
            return findHelp(rt.left, x, y, level); // Go left
        }
        level += 1;
        return findHelp(rt.right, x, y, level); // Go right
    }

    /**
     * Remove a city at given coordinates
     * 
     * @param x is the x-coordinate
     * @param y is the y-coordinate
     * @return City object removed, null if not found
     */
    public City remove(int x, int y) 
    {
        RemoveResult result = removeHelp(root, x, y, 0); // Call recursive
                                                         // helper
        root = result.node; // Update root in case it changed
        if (result.removed != null)
            nodeCount--; // Decrement node count if removed
        return result.removed; // Return removed city or null
    }

    // Helper class to return both new node and removed city
    private class RemoveResult {
        KDTreeNode node; // Node after deletion
        City removed; // City removed

        RemoveResult(KDTreeNode n, City r) {
            node = n;
            removed = r;
        }
    }

    /**
     * Recursive helper for remove
     */
    private RemoveResult removeHelp(KDTreeNode node, int x, int y, int level) {
        if (node == null)
            return new RemoveResult(null, null); // Base: not found

        int cd = level & 1;
        City removedCity = null;

        if (node.city.getX() == x && node.city.getY() == y) {
            // Node to remove found
            removedCity = node.city;

            if (node.right != null) 
            {
                // Replace with min in right subtree for current discriminator
                KDTreeNode min = findMin(node.right, cd, level + 1);
                node.city = min.city; // Copy city
                RemoveResult rr = removeHelp(node.right, min.city.getX(),
                    min.city.getY(), level + 1);
                node.right = rr.node; // Update right subtree
            }
            else if (node.left != null) 
            {
                // Replace with min in left subtree
                level += 1;
                KDTreeNode min = findMin(node.left, cd, level);
                node.city = min.city; // Copy city
                RemoveResult rr = removeHelp(node.left, min.city.getX(),
                    min.city.getY(), level);
                node.left = rr.node; // Update left subtree
            }
            else {
                // Leaf node
                return new RemoveResult(null, removedCity);
            }

            return new RemoveResult(node, removedCity);
        }

        // Recurse left or right
        if ((cd == 0 && x < node.city.getX()) || (cd == 1 && y < node.city
            .getY())) {
            level += 1;
            RemoveResult rr = removeHelp(node.left, x, y, level);
            node.left = rr.node;
            if (removedCity == null)
            {
                removedCity = rr.removed;
            }
        }
        else {
            level += 1;
            RemoveResult rr = removeHelp(node.right, x, y, level);
            node.right = rr.node;
            if (removedCity == null)
            {
                removedCity = rr.removed;
            }
        }

        return new RemoveResult(node, removedCity);
    }

    /**
     * Find node with minimum coordinate in a subtree
     * 
     * @param node is the subtree root
     * @param disc is the discriminator to minimize (0=x,1=y)
     * @param level is the current level
     * @return node with minimum coordinate at disc
     */
    private KDTreeNode findMin(KDTreeNode node, int disc, int level) {
        if (node == null)
        {
            return null; // Empty subtree
        }
        int cd = level & 1;

        KDTreeNode leftMin = findMin(node.left, disc, level + 1); // Min from
                                                                  // left
        KDTreeNode rightMin = null;

        if (cd != disc) {
            level += 1;
            rightMin = findMin(node.right, disc, level); // Min from right
                                                             // if needed
        }

        // Determine smallest among node, leftMin, rightMin
        KDTreeNode min = node;

        if (leftMin != null) 
        {
            if (disc == 0) 
            {
                if (leftMin.city.getX() < min.city.getX()) 
                {
                    min = leftMin;
                }
            }
            else 
            {
                if (leftMin.city.getY() < min.city.getY()) 
                {
                    min = leftMin;
                }
            }
        }
        if (rightMin != null) {
            if (disc == 0) {
                if (rightMin.city.getX() < min.city.getX()) {
                    min = rightMin;
                }
            }
            else {
                if (rightMin.city.getY() < min.city.getY()) {
                    min = rightMin;
                }
            }
        }
        return min;
    }

    /**
     * Region search: find all cities within radius of (x,y)
     * 
     * @param x center x
     * @param y center y
     * @param radius search radius
     * @return formatted string of cities found + nodes visited
     */
    public String search(int x, int y, int radius) {
        if (radius < 0)
            return ""; // Bad radius
        StringBuilder sb = new StringBuilder();
        int[] count = new int[] { 0 }; // Nodes visited count as array to pass
                                       // by reference
        regionSearchHelp(root, x, y, radius, 0, sb, count); // Start recursion
        sb.append(count[0]); // Append node visit count
        return sb.toString();
    }

    /**
     * Recursive helper for region search
     */
    private void regionSearchHelp(KDTreeNode node, int x, int y,
        int radius, int level, StringBuilder sb, int[] count) 
    {
        if (node == null)
        {
            return; // Base: null node
        }
        count[0]++; // Increment nodes visited

        // Compute Euclidean distance
        double dx = node.city.getX() - x;
        double dy = node.city.getY() - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= radius) { // Within circle
            sb.append(node.city.toString()).append("\n");
        }

        int disc = level % DIMENSIONS;

        // Check left subtree if circle overlaps
        if ((disc == 0 && x - radius <= node.city.getX()) || (disc == 1 && y
            - radius <= node.city.getY())) 
        {
            level += 1;
            regionSearchHelp(node.left, x, y, radius, level, sb, count);
        }

        // Check right subtree if circle overlaps
        if ((disc == 0 && x + radius >= node.city.getX()) || (disc == 1 && y
            + radius >= node.city.getY())) 
        {
            level += 1;
            regionSearchHelp(node.right, x, y, radius, level, sb, count);
        }
    }

    /**
     * Print the KD Tree using inorder traversal
     * Each line starts with level and indent = 2*level spaces
     * 
     * @return formatted string
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        printHelp(root, 0, sb);
        return sb.toString();
    }

    /**
     * Recursive helper for print
     */
    private void printHelp(KDTreeNode node, int level, StringBuilder sb) {
        if (node == null)
        {
            return; // Base: nothing to print
        }
        printHelp(node.left, level + 1, sb); // Left subtree
        // Indentation: 2 spaces per level
        for (int i = 0; i < level * 2; i++) {
            sb.append(' ');
        }
        sb.append(level).append(node.city.toString()).append("\n"); // Print
        level += 1;                                                  // node
        printHelp(node.right, level, sb); // Right subtree
    }

    /**
     * methods to add
     * search(int x, int y, int radius)
     * toStringIndented()
     */
}