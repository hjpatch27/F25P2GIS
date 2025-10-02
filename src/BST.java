/**
 * @author Henry Patch (hjpatch27), Nyssa Loeu (nysaal23)
 * @version 10.2.2025
 */

/**
 * The BST class creates a Binary Search Tree.
 */
public class BST <E extends Comparable<E>> {
    // Binary tree node implementation: supports comparable objects
    class BSTNode<E extends Comparable<? super E>> 
    {
        private E element;           // Element for this node
        private BSTNode<E> left;     // Pointer to left child
        private BSTNode<E> right;    // Pointer to right child

        // Constructors
        BSTNode() { 
            left = right = null; 
        }
        BSTNode(E val) { 
            left = right = null; element = val; 
        }
        BSTNode(E val, BSTNode<E> l, BSTNode<E> r) { 
            left = l; right = r; element = val; 
        }

      // Get and set the element value
      public E value() { 
          return element; 
      }
      public void setValue(E v) { 
          element = v; 
      }

      // Get and set the left child
      public BSTNode<E> left() { 
          return left; 
      }
      public void setLeft(BSTNode<E> p) { 
          left = p; 
      }

      // Get and set the right child
      public BSTNode<E> right() { 
          return right; 
      }
      public void setRight(BSTNode<E> p) { 
          right = p; 
      }

      // return TRUE if a leaf node, FALSE otherwise
          public boolean isLeaf() { 
          return (left == null) && (right == null); 
      }
    }
    
    private BSTNode<E> root; // Root of the BST
    private int nodecount; // Number of nodes in the BST

    // constructor
    BST() { root = null; nodecount = 0; }

    // Reinitialize tree
    public void clear() { 
        root = null; nodecount = 0; 
    }
    
    private Comparable<E> findHelp(BSTNode<E> rt, Comparable<E> key) {
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
    
    private BSTNode<E> insertHelp(BSTNode<E> rt, Comparable<E> e) {
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
    
    private BSTNode<E> removeHelp(BSTNode<E> rt, Comparable<E> key) {
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
                BSTNode<E> temp = getMax(rt.left());
                rt.setValue(temp.value());
                rt.setLeft(deleteMax(rt.left()));
            }
        }
        return rt;
    }

    // Insert a record into the tree.
    // Records can be anything, but they must be Comparable
    // e: The record to insert.
    public void insert(E e) {
        root = insertHelp(root, e);
        nodecount++;
    }

    // Remove a record from the tree
    // key: The key value of record to remove
    // Returns the record removed, null if there is none.
    public Comparable<E> remove(E key) {
        Comparable<E> temp = findHelp(root, key); // First find it
        if (temp != null) {
            root = removeHelp(root, key); // Now remove it
            nodecount--;
        }
        return temp;
    }

    // Return the record with key value k, null if none exists
    // key: The key value to find
    public Comparable<E> find(E key) { 
        return findHelp(root, key); 
    }

    // Return the number of records in the dictionary
    public int size() { 
        return nodecount; 
    }
}
