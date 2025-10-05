/**
 * The KDTree class.
 * 
 * @author Henry Patch (hjpatch27), Nyssa Loeu (nysaal23)
 * @version 10.2.2025
 */
public class KDTree 
{

    /**
     *  A private inner class that represents a node in the KD Tree
     *  It stores city records and pointers to its children.
     */
    private class KDTreeNode
    {
        private City cityRecord;
        private KDTreeNode left;
        private KDTreeNode right;
        
        /**
         * The constructor for KDTreeNode
         * @param city is the City object to be stored in the node
         */
        public KDTreeNode(City city)
        {
            this.cityRecord = city;
            this.left = null;
            this.right = null;
        }
        
        /**
         * Gets the city
         * @return cityRecord is the city
         */
        public City getCity()
        {
            return cityRecord;
        }
        
        /**
         * Gets the left KDTreeNode
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
}
