/**
 * The City class represent the object we will be using
 * to store data within the BST and KDTree classes.
 * 
 * @author Henry Patch (hjpatch27), Nyssa Loeu (nysaal23)
 * @version 10.2.2025
 */
public class City implements Comparable<City> {

    private String name;
    private int x;
    private int y;
    
    /**
     * Constructor for the City class.
     * @param name is the name of the city.
     * @param x is the x-coordinate of the city.
     * @param y is the y-coordinate of the city.
     */
    public City(String name, int x, int y)
    {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Getter method for name.
     * @return name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Getter method for x
     * @return x
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Getter method for y
     * @return y
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * @Override
     */
    public int compareTo(City o) {
        // TODO Auto-generated method stub
        return 0;
    }

    
}
