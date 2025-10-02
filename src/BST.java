/**
 * The BST class creates a Binary Search Tree.
 * 
 * @author Henry Patch (hjpatch27), Nyssa Loeu (nysaal23)
 * @version 10.2.2025
 */
public class BST <E extends Comparable<? super E>> {
    
    // Binary tree node implementation: supports comparable objects
    private class BSTNode {
        private E element;        // Element for this node
        private BSTNode left;     // Pointer to left child
        private BSTNode right;    // Pointer to right child

        // Constructors
        BSTNode() { 
            left = null; // Set left and right to null.
            right = null; 
        }
        BSTNode(E val) { 
            left = null;
            right = null; 
            element = val; 
        }
        BSTNode(E val, BSTNode l, BSTNode r) { 
            left = l; 
            right = r; 
            element = val; 
        }

      // Get and set the element value
      public E value() { 
          return element; 
      }
      public void setValue(E v) { 
          element = v; 
      }

      // Get and set the left child
      public BSTNode left() { 
          return left; 
      }
      public void setLeft(BSTNode p) { 
          left = p; 
      }

      // Get and set the right child
      public BSTNode right() { 
          return right; 
      }
      public void setRight(BSTNode p) { 
          right = p; 
      }

      // return TRUE if a leaf node, FALSE otherwise
          public boolean isLeaf() { 
          return (left == null) && (right == null); 
      }
    }
    
    private BSTNode root; // Root of the BST
    private int nodeCount; // Number of nodes in the BST

    // constructor
    BST() { 
        root = null; 
        nodeCount = 0; 
    }

    // Reinitialize tree
    public void clear() { 
        root = null; 
        nodeCount = 0; 
    }
    
    // Get the maximum valued element in a subtree
    private BSTNode getMax(BSTNode rt) {
        if (rt.right() == null) { 
            return rt; 
        }
        return getMax(rt.right());
    }
    
    private BSTNode deleteMax(BSTNode rt) {
        if (rt.right() == null) {
            return rt.left();
        }
        rt.setRight(deleteMax(rt.right()));
        return rt;
    }
    
    private Comparable<E> findHelp(BSTNode rt, Comparable<E> key) {
        if (rt == null) {
            return null;
        }
        if (rt.value().compareTo((E)key) > 0) {
            return findHelp(rt.left(), key);
        }
        else if (rt.value().compareTo((E)key) == 0) {
            return rt.value();
        }
        else {
            return findHelp(rt.right(), key);
        }
    }
    
    private BSTNode insertHelp(BSTNode rt, Comparable<E> e) {
        if (rt == null) {
            return new BSTNode((E)e);
        }
        if (rt.value().compareTo((E)e) >= 0) {
            rt.setLeft(insertHelp(rt.left(), e));
        }
        else {
            rt.setRight(insertHelp(rt.right(), e));
        }
        return rt;
    }
    
    private BSTNode removeHelp(BSTNode rt, Comparable<E> key) {
        if (rt == null) {
            return null;
        }
        if (rt.value().compareTo((E)key) > 0) {
            rt.setLeft(removeHelp(rt.left(), key));
        }
        else if (rt.value().compareTo((E)key) < 0) {
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

    // Insert a record into the tree.
    // Records can be anything, but they must be Comparable
    // e: The record to insert.
    public void insert(Comparable<E> e) {
        root = insertHelp(root, e);
        nodeCount++;
    }

    // Remove a record from the tree
    // key: The key value of record to remove
    // Returns the record removed, null if there is none.
    public Comparable<E> remove(Comparable<E> key) {
        Comparable<E> temp = findHelp(root, key); // First find it
        if (temp != null) {
            root = removeHelp(root, key); // Now remove it
            nodeCount--;
        }
        return temp;
    }

    // Return the record with key value k, null if none exists
    // key: The key value to find
    public Comparable<E> find(Comparable<E> key) { 
        return findHelp(root, key); 
    }

    // Return the number of records in the dictionary
    public int size() { 
        return nodeCount; 
    }
}