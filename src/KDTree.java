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
    private static final int DIMENSIONS = 2; // for (x,y)
    private int nodesVisited; // The number of nodes visited in remove()

    // ----------------------------------------------------------
    /**
     * Create a new KDTree object.
     */
    public KDTree() {
        root = null;
    }


    /**
     * Clears the entire tree.
     */
    public void clear() {
        root = null;
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
        // If the tree is empty, the city becomes the root node.
        if (root == null) {
            root = new KDTreeNode(city);
            return true;
        }

        // Otherwise, call the recursive insertion helper starting at root.
        boolean inserted = insertHelp(root, city, 0);

        // Return whether the insert succeeded or failed due to a duplicate.
        return inserted;
    }


    /**
     * Recursively inserts a new city into KDTree.
     * Alternates between comparing x and y at each level.
     * 
     * @param rt
     *            is the current node
     * @param newCity
     *            is the city to insert
     * @param level
     *            is the current depth
     * @return rt the updated root of the subtree
     */
    private boolean insertHelp(KDTreeNode rt, City newCity, int level) {
        int disc = level & 1; // 0 = x, 1 = y

        // Compare coordinates based on discriminator
        int cityCoord;
        int nodeCoord;
        if (disc == 0) {
            cityCoord = newCity.getX();
            nodeCoord = rt.city.getX();
        }
        else {
            cityCoord = newCity.getY();
            nodeCoord = rt.city.getY();
        }

        // Check for duplicate coordinates
        /**
         * not needed yet
         * if (newCity.getX() == rt.city.getX())
         * {
         * if (newCity.getY() == rt.city.getY())
         * {
         * return false; // Duplicate
         * }
         * }
         */

        // Go left if smaller, right if greater or equal
        if (cityCoord < nodeCoord) {
            if (rt.left == null) {
                rt.left = new KDTreeNode(newCity);
                return true;
            }
            return insertHelp(rt.left, newCity, level + 1);
        }
        if (rt.right == null) {
            rt.right = new KDTreeNode(newCity);
            return true;
        }
        return insertHelp(rt.right, newCity, level + 1);
    }


    /**
     * Find a city's coordinates
     * 
     * @param x
     *            is the x coordinate for the target city
     * @param y
     *            is the x coordinate for the target city
     * @return The City object if found and null otherwise
     */
    public City find(int x, int y) {
        return findHelp(root, x, y, 0);
    }


    /**
     * Recursively searches for a city by its coordinates (x,y).
     * It will alternate searching between x and y at each depth/level.
     * 
     * @param rt
     *            is the current node
     * @param x
     *            is the target x coordinate
     * @param y
     *            is the target y coordinate
     * @param level
     *            is the current depth
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
            return findHelp(rt.left, x, y, level + 1); // Go left
        }
        return findHelp(rt.right, x, y, level + 1); // Go right
    }

    /**
     * Finds the node with the minimum value in the given dimension.
     * * @param rt The root of the subtree to search.
     * @param dim The dimension to compare: 0 for x, 1 for y.
     * @param level is the current level (used to determine which 
     * coordinate to compare).
     * @return The node with the minimum value in that dimension.
     */
    private KDTreeNode findMin(KDTreeNode rt, int dim, int level) {
        if (rt == null) {
            return null;
        }

        // Count this node as visited when findMin is called during removal
        // so the removal nodesVisited matches the reference solution's
        // expectations (they include nodes visited during replacement
        // selection).
        nodesVisited++;

        int currentDisc = level & 1;

        // If current level compares the same dimension as we're searching for
        if (currentDisc == dim) {
            // The minimum must be in the left subtree (if it exists)
            if (rt.left == null) {
                return rt;
            }
            return findMin(rt.left, dim, level + 1);
        }
        // Otherwise, compare current node, left min, and right min
        KDTreeNode leftMin = findMin(rt.left, dim, level + 1);
        KDTreeNode rightMin = findMin(rt.right, dim, level + 1);
        KDTreeNode min = rt;

        // Compare with left min
        if (leftMin != null) {
            if (getCoord(leftMin.city, dim) < getCoord(min.city, dim)) {
                min = leftMin;
            }
        }
        // Compare with right min
        if (rightMin != null) {
            if (getCoord(rightMin.city, dim) < getCoord(min.city, dim)) {
                min = rightMin;
            }
        }
        return min;
    }


    /**
     * Removes a city from the KD-Tree at the given coordinates.
     * * @param x X-coordinate of the city to remove.
     * @param y Y-coordinate of the city to remove.
     * @return The City that was removed, or null if not found.
     */
    public int remove(int x, int y) {
        // Reset the node counter for the initial search traversal.
        nodesVisited = 0;

        // Holder for removed city's record
        KDTreeNode removed = new KDTreeNode(null);

        // Call the recursive helper. The initial call counts nodesVisited.
        root = removeHelp(root, x, y, 0, removed, true);

        if (removed.city != null) {
            return this.nodesVisited;
        }

        // City not found
        return 0;
    }


    /**
     * Helper recursive remove method
     * @param rt The current node.
     * @param x The target x-coordinate.
     * @param y The target y-coordinate.
     * @param level The current depth (discriminator is level & 1).
     * @param removed Holder for the removed city record.
     * @return The updated root of the subtree.
     */
    // Added boolean 'count' so we can avoid counting nodesVisited for internal
    // cleanup recursion (when removing the replacement node). Only the initial
    // search should increment the counter to match reference expectations.
    private KDTreeNode removeHelp(KDTreeNode rt, int x, int y,
        int level, KDTreeNode removed, boolean count) {
        if (rt == null) {
            return null;
        }

        if (count) {
            nodesVisited++; // Count only when requested
        }

        int disc = level & 1;

        // --- 1. CHECK FOR MATCH (do this before recursing) ---
        if (rt.city.getX() == x && rt.city.getY() == y 
            && removed.city == null) {
            // Found the node to remove
            removed.city = rt.city;

            // If right subtree exists, replace with min from right
            if (rt.right != null) {
                KDTreeNode minNode = findMin(rt.right, disc, level + 1);
                rt.city = minNode.city;
                // Remove replacement node from right subtree without counting
                rt.right = removeHelp(rt.right, minNode.city.getX(),
                    minNode.city.getY(), level + 1, new KDTreeNode(null), true);
                return rt;
            }

            // If right is null but left exists, follow OpenDSA approach:
            // move left subtree to right, then find min in the new right
            // subtree and remove that replacement node from the right.
            if (rt.left != null) {
                // Move left to right to preserve discriminators
                rt.right = rt.left;
                rt.left = null;

                KDTreeNode minNode = findMin(rt.right, disc, level + 1);
                rt.city = minNode.city;
                // Remove the replacement node from right subtree (counting)
                rt.right = removeHelp(rt.right, minNode.city.getX(),
                    minNode.city.getY(), level + 1, new KDTreeNode(null), true);
                return rt;
            }

            // Leaf node: remove it
            return null;
        }

        // --- 2. TRAVERSE DOWN ---
        int targetCoord = (disc == 0) ? x : y;
        int nodeCoord = (disc == 0) ? rt.city.getX() : rt.city.getY();

        if (targetCoord < nodeCoord) {
            rt.left = removeHelp(rt.left, x, y, level + 1, removed, count);
        } 
        else {
            rt.right = removeHelp(rt.right, x, y, level + 1, removed, count);
        }

        return rt;
    }


    /**
     * Returns the coordinate value (x or y) based on the discriminator.
     * 
     * @param c
     *            City object.
     * @param disc
     *            0 for x-coordinate, 1 for y-coordinate.
     * @return Corresponding coordinate value.
     */
    private int getCoord(City c, int disc) {
        if (disc == 0) {
            return c.getX();
        }
        return c.getY();
    }


    /**
     * Region search: find all cities within radius of (x,y)
     * 
     * @param x is the center x
     * @param y is the center y
     * @param radius is the search radius
     * @return formatted string of cities found + nodes visited
     */
    public String search(int x, int y, int radius) {
        if (radius < 0) {
            return ""; // Bad radius
        }

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
    private void regionSearchHelp(KDTreeNode node, int x, int y, int radius, 
        int level, StringBuilder sb, int[] count) {
        if (node == null) // Check
        {
            return; // Base: null node
        }
        count[0]++; // <--- Only increments here

        // Compute Euclidean distance
        double dx = node.city.getX() - x;
        double dy = node.city.getY() - y;
        // NOTE: comparing squared distance (distSq) is often faster as it
        // avoids Math.sqrt,
        // but the current code using dist works if done correctly.
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= radius) { // Within circle
            sb.append(node.city.toString()).append("\n");
        }

        int disc = level & 1;

        // Check left subtree if circle overlaps (center-radius <= split
        // coordinate)
        if ((disc == 0 && x - radius < node.city.getX()) || (disc == 1 && y
            - radius < node.city.getY())) {
            // CORRECT: Pass level + 1
            regionSearchHelp(node.left, x, y, radius, level + 1, sb, count);
        }

        // Check right subtree if circle overlaps (center+radius >= split
        // coordinate)
        if ((disc == 0 && x + radius >= node.city.getX()) || (disc == 1 && y
            + radius >= node.city.getY())) {
            // CORRECT: Pass level + 1
            regionSearchHelp(node.right, x, y, radius, level + 1, sb, count);
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
        if (node == null) {
            return; // Base: nothing to print
        }
        printHelp(node.left, level + 1, sb); // Left subtree
        // Indentation: 2 spaces per level
        String space = "";
        for (int i = 0; i < level * 2; i++) {
            space += " ";
        }
        sb.append(level).append(space).append(node.city.toString()).append(
            "\n"); // Print node

        printHelp(node.right, level + 1, sb); // Right subtree
    }
}