/**
 * The BST class creates a Binary Search Tree.
 * 
 * @author Henry Patch (hjpatch27)
 * @author Nyssa Loeu (nysaal23)
 * @version 10.5.2025
 * 
 * @param <E> for the Comparable object.
 */
public class BST<E extends Comparable<E>> {
    
    // Binary tree node implementation: supports comparable objects
    private class BSTNode {
        private E element;                 // Element for this node
        private BSTNode left;              // Pointer to left child
        private BSTNode right;             // Pointer to right child

        // Constructors
        /**
         * Constructor initializes left and right as null.
         */
        BSTNode() { 
            left = null;
            right = null; 
        }
        
        /**
         * Constructor initializes left and right as null
         * and set element to a value.
         * @param val is the value element is to be set to.
         */
        BSTNode(E val) { 
            left = null;
            right = null; 
            element = val; 
        }
        
        /**
         * Set left, right, and element to values
         * @param val is the value element is to be set to.
         * @param l is the value left is to be set to.
         * @param r is the value right is to be set to.
         */
        BSTNode(E val, BSTNode l, BSTNode r) { 
            left = l; 
            right = r; 
            element = val; 
        }

        /**
         * Getter method for value.
         * @return element
         */
        public E value() { 
            return element; 
        }
        
        /**
         * Setter method for value.
         * @param v is what the element is to be changed to.
         */
        public void setValue(E v) { 
            element = v; 
        }

        /**
         * Getter method for left.
         * @return left
         */
        public BSTNode left() { 
            return left; 
        }
        
        /**
         * Setter method for left
         * @param p is the value left is to be changed to.
         */
        public void setLeft(BSTNode p) { 
            left = p; 
        }

        /**
         * Getter method for right.
         * @return right
         */
        public BSTNode right() { 
            return right; 
        }
        
        /**
         * Setter method for right.
         * @param p is the value right is to be changed to.
         */
        public void setRight(BSTNode p) { 
            right = p; 
        }

    }
    
    private BSTNode root; // Root of the BST

    /**
     * Constructor for BST. Sets root to null 
     * and nodeCount to 0.
     */
    BST() { 
        root = null; 

    }

    /**
     * Reinitialize tree, setting root to null
     * and nodeCount to 0.
     */
    public void clear() { 
        root = null; 
    }
 
    /**
     * Insert a record into the tree.
     * @param e is the record to insert.
     */
    public void insert(E e) {
        root = insertHelp(root, e);
    }
  
    /**
     * Remove a record from the tree
     * @param key is the key value to remove.
     * @return the record removed, null if there is none.
     */
    public E remove(E key) {
        E temp = findHelp(root, key); // First find the record
        if (temp != null) {
            root = removeHelp(root, key); // Now remove the record
        }
        return temp;
    }
    

    /**
     * Find the record with a specific key value.
     * @param key is the key value to find.
     * @return the record with key value k, null if none exists.
     */
    public E find(E key) { 
        return findHelp(root, key); 
    }
    
    /**
     * Find the record of all objects with a given City name
     * and return it in a string format.
     * @param name is the name of the City object that
     * we're looking for.
     * @return the list of records with the given name.
     */
    public String findAll(String name) {
        return findAllHelp(root, name);
    }
    
    /**
     * Print a listing of the BST in alphabetical order (inorder traversal)
     * on the names.
     * Each city should be printed on a separate line. Each line should start
     * with the level of the current node, then be indented by 2 * level spaces
     * for a node at a given level, counting the root as level 0.
     * @return String listing the cities as specified.
     */
    public String print() {
        return printHelp(root, 0);
    }
    
    /**
     * Helper method for find().
     * @param rt
     * @param key
     * @return the record with key value k, null if none exists.
     */
    private E findHelp(BSTNode rt, E key) {
        if (rt == null) {
            return null;
        }
        if (rt.value().compareTo(key) > 0) {
            return findHelp(rt.left(), key);
        }
        else if (rt.value().compareTo(key) == 0) {
            return rt.value();
        }
        else {
            return findHelp(rt.right(), key);
        }
    }
    
    /**
     * Helper method for findAll().
     * @param rt
     * @param name
     * @return the list of records with the given name.
     */
    private String findAllHelp(BSTNode rt, String name) {
        // Return an empty string if the node is null.
        if (rt == null) {
            return "";
        }
        
        String result = "";
        // Check current node first (preorder traversal)
        if (rt.value() instanceof City) {
            City city = (City) rt.value();
            if (city.getName().equals(name)) {
                result += city.toString() + "\n";
            }
        }

        // Then traverse left and right
        result += findAllHelp(rt.left(), name);
        result += findAllHelp(rt.right(), name);

        // Return the final string
        return result;
    }
    
    /**
     * Helper method for insert().
     * @param rt
     * @param e
     * @return
     */
    private BSTNode insertHelp(BSTNode rt, E e) {
        if (rt == null) {
            return new BSTNode(e);
        }
        if (rt.value().compareTo(e) >= 0) {
            rt.setLeft(insertHelp(rt.left(), e));
        }
        else {
            rt.setRight(insertHelp(rt.right(), e));
        }
        return rt;
    }

    /**
     * Helper method for remove().
     * @param rt
     * @param key
     * @return
     */
    private BSTNode removeHelp(BSTNode rt, E key) {
        if (rt == null) {
            return null;
        }
        if (rt.value().compareTo(key) > 0) {
            rt.setLeft(removeHelp(rt.left(), key));
        }
        else if (rt.value().compareTo(key) < 0) {
            rt.setRight(removeHelp(rt.right(), key));
        }
        else { // Found it
            if (rt.left() == null) {
                return rt.right();
            }
            else if (rt.right() == null) {
                return rt.left();
            }
            else { // Two Children
                BSTNode temp = getMax(rt.left());
                rt.setValue(temp.value());
                rt.setLeft(deleteMax(rt.left()));
            }
        }
        return rt;
    }
    
    
    /**
     * Get the maximum valued element in a subtree.
     * @param rt
     * @return
     */
    private BSTNode getMax(BSTNode rt) {
        if (rt.right() == null) { 
            return rt; 
        }
        return getMax(rt.right());
    }
        
    /**
     * Delete the maximum valued element in a subtree.
     * @param rt
     * @return
     */
    private BSTNode deleteMax(BSTNode rt) {
        if (rt.right() == null) {
            return rt.left();
        }
        rt.setRight(deleteMax(rt.right()));
        return rt;
    }
      
    /**
     * Helper method for print().
     * @param node is the node the method is currently on.
     * @param level is the level the tree is currently on.
     * @return String listing the cities as specified.
     */
    private String printHelp(BSTNode node, int level) {
        if (node == null) {
            return "";
        }
        // Create StringBuilder object
        StringBuilder sb = new StringBuilder();
        
        // Use inorder traversal to move through tree.
        // Traverse left subtree
        sb.append(printHelp(node.left(), level + 1));

        // Print current node with correct indentation
        // Append level of the node
        sb.append(level)
          .append(" ".repeat(2 * level))  // Indent by 2 * level spaces
          // Append name and coordinates of the city object.
          .append(node.value().toString()).append("\n");

        // Traverse right subtree
        sb.append(printHelp(node.right(), level + 1));

        return sb.toString();
    }
}