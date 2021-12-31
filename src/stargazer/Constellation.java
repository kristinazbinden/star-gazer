/**
 *  Description: Constellation stores basic Constellation data from the data file.
 *  You can use this as a guide when you are writing your Star.java file.
 *  Author:  Kristina Zbinden
 *  Date: 12/17/21
 *  Pledged: All work contained here is my own and was created without collaboration.
 *
 */
package stargazer;

import java.util.ArrayList; // so we can store a list of Strings for the star pairs

/**
 *
 * @author Kristina Zbinden
 */
public class Constellation {

    private String name;  // "Ursa Major", for example
    private ArrayList<String> starPairs; // straight from the data file

    /**
     * Initialize a new Constellation with its name and list of star pairs
     *
     * @param name - name of Constellation
     * @param starPairs - list of comma-separated star name tuples
     */
    public Constellation(String name, ArrayList<String> starPairs) {
        this.name = name;
        this.starPairs = starPairs;
    }

    /**
     * This is marked private because it should never be called.
     */
    private Constellation() {
    }

    /**
     * toString()
     *
     * @return a formatted String of star pairs. This is for testing purposes
     * only.
     */
    @Override
    public String toString() {
        String outputStr = name + ":\n";
        // loop through the star pairs and add each one to the output string
        // You could also use a classic for loop here, and use starPairs.get(i)
        // to fetch each String, but the enhanced for loop is more efficient
        // when working with an ArrayList.  You'll understand why in CSCI 290.  :-)
        for (String sp : starPairs) {
            outputStr += "   " + sp + "\n";
        }

        return outputStr;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public ArrayList<String> getStarPairs() {
        return starPairs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStarPairs(ArrayList<String> starPairs) {
        this.starPairs = starPairs;
    }

}
