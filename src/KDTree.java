/**
 * The KDTree class, implements a 2D KD Tree for City objects.
 * 
 * @author Henry Patch (hjpatch27)
 * @author Nyssa Loeu (nysaal23)
 * @version 10.14.2025
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
         * @param newCity is the City object to be stored in the node
         */
        public KDTreeNode(City newCity) {
            this.city = newCity;
            this.left = null;
            this.right = null;
        }
    }

    private KDTreeNode root; // The root of the KDTree
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
     * for any duplicates before inserting the city. Inserted
     * based on alternating X and Y coordinates at each level.
     * 
     * @param city is the City to insert
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
     * Decides to go left or right based on comparing x
     * and y at each level (discriminator). Equal values will\
     * go to the right subtree.
     * 
     * @param rt is the current node
     * @param newCity is the city to insert
     * @param level is the current depth
     * @return true if the insertion was successful false
     */
    private boolean insertHelp(KDTreeNode rt, City newCity, int level) {
        int disc = level & 1; // Same as level % 2 :  0 = x, 1 = y 

        // Compare coordinates based on discriminator
        int cityCoord;
        int nodeCoord;
        if (disc == 0) 
        {   // Compare x coords
            cityCoord = newCity.getX();
            nodeCoord = rt.city.getX();
        }
        else 
        {   // Compare y coords
            cityCoord = newCity.getY();
            nodeCoord = rt.city.getY();
        }

        // Go left if smaller, right if greater or equal
        if (cityCoord < nodeCoord) 
        {
            if (rt.left == null) 
            {
                rt.left = new KDTreeNode(newCity);
                return true;
            }
            // Recurse left and increment level
            return insertHelp(rt.left, newCity, level + 1);
        }
        // Traverse right if new city coord is >=
        if (rt.right == null) 
        {
            rt.right = new KDTreeNode(newCity);
            return true;
        }
        // Recurse right and increment level
        return insertHelp(rt.right, newCity, level + 1);
    }


    /**
     * Find a city by its exact coordinates (x, y).
     * 
     * @param x is the x coordinate for the target city
     * @param y is the x coordinate for the target city
     * @return The City object if found and null otherwise
     */
    public City find(int x, int y) {
        return findHelp(root, x, y, 0);
    }


    /**
     * Recursively searches for a city by its coordinates (x, y).
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
            return null; // Tree is empty or path ended
        }
     
        // Check for a match at current node
        if (rt.city.getX() == x && rt.city.getY() == y) 
        {
            return rt.city;
        }

        int cd = level & 1; // 0 = x, 1 = y
        int depth = level;
        
        // Check the left branch: Go left if the is X and targetX < nodeX,
        // or is Y and targetY < node Y
        if ((cd == 0 && x < rt.city.getX()) || (cd == 1 && y < rt.city
            .getY())) 
        {
            return findHelp(rt.left, x, y, level + 1); // Go left
        }
        // Otherwise, go right 
        depth += 1;
        return findHelp(rt.right, x, y, depth); // Go right
    }


    /**
     * Finds the node with the minimum value in the given dimension.
     * Searches both subtrees when the current split dimension is different
     * from the target dimension (dim).
     * 
     * @param rt The root of the subtree to search.
     * @param dim is the dimension to compare: 0 for x, 1 for y.
     * @param level is the current level (used to determine which
     * coordinate to compare).
     * @return The node with the minimum value in that dimension.
     */
    private KDTreeNode findMin(KDTreeNode rt, int dim, int level) 
    {
        if (rt == null) 
        {
            return null;
        }

        // Count this node as visited when findMin is called during removal
        nodesVisited++;

        int currentDisc = level & 1;
        int depth = level;
        
        // If current split dimension matches target dimension
        if (currentDisc == dim) 
        {
            // The minimum must be in the left subtree
            if (rt.left == null)
            {
                return rt;
            }
            // Recurse left and ignore right subtree
            depth += 1;
            return findMin(rt.left, dim, depth);
        }
        
        // Current split dimension does not match the target dimension
        // Search both sides and compare min found (left and right subtrees)
        depth += 1;
        KDTreeNode leftMin = findMin(rt.left, dim, depth);
        KDTreeNode rightMin = findMin(rt.right, dim, depth);
        KDTreeNode min = rt; // Start comparison with current node

        // Compare with left min
        if (leftMin != null) 
        {
            if (getCoord(leftMin.city, dim) < getCoord(min.city, dim)) 
            {
                min = leftMin;
            }
        }
        // Compare with right min
        if (rightMin != null) 
        {
            if (getCoord(rightMin.city, dim) < getCoord(min.city, dim)) 
            {
                min = rightMin;
            }
        }
        // Return the overall min found in subtree
        return min;
    }


    /**
     * Removes a city from the KD-Tree at the given coordinates (x, y).
     * The node is replaced by the min value from the right subtree.
     * 
     * @param x is the X-coordinate of the city to remove.
     * @param y is the Y-coordinate of the city to remove.
     * @return The number of nodes visited to find and remove
     *         the City or 0 if the City was not found.
     */
    public int remove(int x, int y) 
    {
        // Reset the node counter before starting search
        nodesVisited = 0;
        // Placeholder to store removed city record
        KDTreeNode removed = new KDTreeNode(null); 

        // Call the recursive helper which counts nodesVisited
        root = removeHelp(root, x, y, 0, removed, true);

        // Return final count of nodes visited
        return this.nodesVisited;
    }


    /**
     * Helper recursive remove method.
     * 
     * @param rt is the current node.
     * @param x is the target x-coordinate.
     * @param y is the target y-coordinate.
     * @param level is the current depth (discriminator is level & 1).
     * @param removed is the holder for the removed city record.
     * @return The updated root of the subtree.
     */
    private KDTreeNode removeHelp(KDTreeNode rt, int x, int y, int level,
        KDTreeNode removed, boolean count) 
    {
        nodesVisited++; // Count only when requested

        int disc = level & 1;
        int depth = level;

        // Check for a match
        if (rt.city.getX() == x && rt.city.getY() == y) 
        {
            // Found the node to remove
            removed.city = rt.city;

            // If right subtree exists, replace with min from right
            if (rt.right != null) 
            {
                depth += 1;
                // Find min node in current disc dimension from right subtree
                KDTreeNode minNode = findMin(rt.right, disc, depth);
                rt.city = minNode.city; 
                // Remove replacement node from right subtree without counting
                rt.right = removeHelp(rt.right, minNode.city.getX(),
                    minNode.city.getY(), depth, new KDTreeNode(null), true);
                return rt;
            }

            // If right is null but left exists move left subtree to right
            if (rt.left != null) 
            {
                // Move left to right to maintain discriminators for all children
                rt.right = rt.left;
                rt.left = null;
                
                // Find replacement min from the new right subtree
                KDTreeNode minNode = findMin(rt.right, disc, level + 1);
                rt.city = minNode.city;
                // Remove the replacement node from right subtree
                rt.right = removeHelp(rt.right, minNode.city.getX(),
                    minNode.city.getY(), level + 1, new KDTreeNode(null), true);
                return rt;
            }

            // remove if leaf node
            return null;
        }

        // traverse down
        // Determine coordinates for comparison based on disriminator
        int targetCoord = x;
        int nodeCoord = rt.city.getX();
        if (disc == 1) 
        {
            targetCoord = y;
            nodeCoord = rt.city.getY();
        }
        // Recurse left if target coordinate is smaller
        if (targetCoord < nodeCoord) 
        {
            depth += 1;
            rt.left = removeHelp(rt.left, x, y, depth, removed, count);
        }
        else 
        {   // Recurse right if target coordinate is >=
            depth += 1;
            rt.right = removeHelp(rt.right, x, y, depth, removed, count);
        }
        // Return current node if not modified
        return rt;
    }


    /**
     * Returns the coordinate value (x or y) based on the discriminator.
     * 
     * @param c is the City object.
     * @param disc 0 for x-coordinate, 1 for y-coordinate.
     * @return Corresponding coordinate value.
     */
    private int getCoord(City c, int disc) 
    {
        if (disc == 0) 
        {
            return c.getX();
        }
        return c.getY();
    }


    /**
     * Performs a region search to find all cities within
     * the specified radius of a center point (x, y).
     * 
     * @param x is the center x
     * @param y is the center y
     * @param radius is the search radius
     * @return formatted string of cities found + nodes visited
     */
    public String search(int x, int y, int radius) 
    {
        if (radius < 0) 
        {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        // Nodes visited count as array to pass by reference
        int[] count = new int[] {0};
        // Start recursive region search from the root (level 0)
        regionSearchHelp(root, x, y, radius, 0, sb, count); 
        sb.append(count[0]); // Append node visit count
        return sb.toString();
    }


    /**
     * Recursive helper for region search. Traverses through KDTree
     * to check nodes for proximity and pruning branches that don't
     * intersect the search circle.
     * 
     * @param x is the X coordinate of the search center
     * @param y is the Y coordinate of the search center
     * @param radius is the search radius
     * @param level is the current depth in the tree
     * @param sb is the StringBuilder so found cities are appended
     * @param count is an array to hold the count of nodes visited
     */
    private void regionSearchHelp(KDTreeNode node, int x, int y, int radius,
        int level, StringBuilder sb, int[] count) 
    {
        if (node == null)
        {
            return;
        }
        
        count[0]++; // Increment nodes visited count

        // Compute Euclidean distance
        double dx = node.city.getX() - x;
        double dy = node.city.getY() - y;

        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= radius) 
        {   // If the city is within the radius, add it to result
            sb.append(node.city.toString()).append("\n");
        }

        int disc = level & 1; // 0 = x, 1 =y

        // Check left subtree if circle overlaps the region (coordinate < split)
        // Circle overlaps if (center - radius) < split coordinates
        if ((disc == 0 && x - radius < node.city.getX()) || (disc == 1 && y
            - radius < node.city.getY())) 
        {
            regionSearchHelp(node.left, x, y, radius, level + 1, sb, count);
        }

        // Check right subtree if circle overlaps the region (coordinate >= split)
        // Circle overlaps if (center + radius) >= split coordinates
        if ((disc == 0 && x + radius >= node.city.getX()) || disc == 1 && y
            + radius >= node.city.getY()) 
        {

            regionSearchHelp(node.right, x, y, radius, level + 1, sb, count);
        }
    }


    /**
     * Print the KD Tree using inorder traversal. Each city
     * is printed on a separate line and each line starts
     * with level and indent = 2*level spaces.
     * 
     * @return String listing the cities
     */
    public String print() 
    {
        StringBuilder sb = new StringBuilder();
        printHelp(root, 0, sb); // Start recursion
        return sb.toString();
    }


    /**
     * Recursive helper for print.
     * 
     * @param node is the current node
     * @param level is the depth of the current node
     * @param sb is the StringBuilder to format output
     */
    private void printHelp(KDTreeNode node, int level, StringBuilder sb) 
    {
        if (node == null) 
        {
            return;
        }
        // Traverse left subtree
        printHelp(node.left, level + 1, sb);
        
        // Print current node, indent: 2 spaces per level 
        String space = "";
        for (int i = 0; i < level * 2; i++) 
        {
            space += " ";
        }
        
        sb.append(level).append(space).append(node.city.toString()).append(
            "\n"); // Print node
        
        // Traverse right subtree
        printHelp(node.right, level + 1, sb); 
    }
}
