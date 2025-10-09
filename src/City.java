/**
 * The City class represent the object we will be using
 * to store data within the BST and KDTree classes.
 * 
 * @author Henry Patch (hjpatch27)
 * @author Nyssa Loeu (nysaal23)
 * @version 10.5.2025
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
     * Getter method for x.
     * @return x
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Getter method for y.
     * @return y
     */
    public int getY()
    {
        return y;
    }
    

    
    /**
     * Compares two City objects to each other based on their name, determining
     * whether they are equal or if one is greater than or less
     * than the other.
     * @Override
     * @param o is the City object we'll be comparing with the 
     * current City object.
     * @return 0 is they are equal, 1 if the current City object is 
     * greater than the City object, and -1 if it's less than the other.
     */
    public int compareTo(City o) {
        return this.name.compareTo(o.getName());
    }

    /**
     * Compares two City objects with each other, returning
     * true or false if they are the same or not.
     * @param obj is the City object we'll be comparing with
     * the current City object.
     * @return True if the objects are equal and false if not.
     * @Override
     */
    public boolean equals(Object obj) {
        // If comparing the same object to itself, return true
        if (this == obj) {
            return true;
        }
        // If the other object isn't a City, return false
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        // If the other object has an identical name and coordinates,
        // return true. If not, return false.
        City other = (City) obj;
        return name.equals(other.getName()) && x == other.getX() 
            && y == other.getY();
    }
    
    /**
     * Convert the City object and its parameters into a String format.
     * @Override
     * @return name + "(" + x + ", " + y + ")" which displays
     * the name, x-coordinate, and y-coordinate of the City object.
     */
    public String toString() {
        return getName().trim() + " (" + getX() + ", " + getY() + ")";
    } 
}