/**
 * The BST class creates a Binary Search Tree.
 * 
 * @author Henry Patch (hjpatch27), Nyssa Loeu (nysaal23)
 * @version 10.2.2025
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

          
        /**
         * Determine whether the node is a leaf node.
         * @return True if a leaf node, false otherwise
         */
         
        public boolean isLeaf() { 
            return (left == null) && (right == null); 
        }
    }
    
    private BSTNode root; // Root of the BST
    private int nodeCount; // Number of nodes in the BST

    /**
     * Constructor for BST. Sets root to null 
     * and nodeCount to 0.
     */
    BST() { 
        root = null; 
        nodeCount = 0; 
    }

    /**
     * Reinitialize tree, setting root to null
     * and nodeCount to 0.
     */
    public void clear() { 
        root = null; 
        nodeCount = 0; 
    }
 
    /**
     * Insert a record into the tree.
     * @param e is the record to insert.
     */
    public void insert(E e) {
        root = insertHelp(root, e);
        nodeCount++;
    }

    /**
     * Remove a record from the tree
     * @param key is the key value to remove.
     * @return the record removed, null if there is none.
     */
    public E remove(E key) {
        E temp = findHelp(root, key); // First find it
        if (temp != null) {
            root = removeHelp(root, key); // Now remove it
            nodeCount--;
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
     * Return the number of records in the dictionary.
     * @return nodeCount which is the number of nodes in the BST.
     */
    public int size() { 
        return nodeCount; 
    }
    
    /**
     * 
     * @param rt
     * @param key
     * @return
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
     * 
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
     * 
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
}