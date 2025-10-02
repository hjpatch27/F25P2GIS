/**
 * @author Henry Patch (hjpatch27)
 * @version 10.2.2025
 */

/**
 * The BST class creates a Binary Search Tree.
 */
public class BST {
    private BSTNode root; // Root of the BST
    private int nodecount; // Number of nodes in the BST

    // constructor
    BST() { root = null; nodecount = 0; }

    // Reinitialize tree
    public void clear() { root = null; nodecount = 0; }

    // Insert a record into the tree.
    // Records can be anything, but they must be Comparable
    // e: The record to insert.
    public void insert(Comparable e) {
      root = inserthelp(root, e);
      nodecount++;
    }

    // Remove a record from the tree
    // key: The key value of record to remove
    // Returns the record removed, null if there is none.
    public Comparable remove(Comparable key) {
      Comparable temp = findhelp(root, key); // First find it
      if (temp != null) {
        root = removehelp(root, key); // Now remove it
        nodecount--;
      }
      return temp;
    }

    // Return the record with key value k, null if none exists
    // key: The key value to find
    public Comparable find(Comparable key) { return findhelp(root, key); }

    // Return the number of records in the dictionary
    public int size() { return nodecount; }
}
