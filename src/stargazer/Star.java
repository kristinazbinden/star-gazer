/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stargazer;

import java.util.ArrayList;

/**
 *
 * @author kristinazbinden
 */
public class Star {

    private double starX;
    private double starY;
    private int henryID;
    private double magnitude;
    private String starNames;

    public Star() {
        starX = 0.0;
        starY = 0.0;
        henryID = 0;
        magnitude = 0.0;
        starNames = "";
    }

    /**
     * Initialize new Star
     *
     * @param starX - The star's X coordinate
     * @param starY - The star's Y coordinate
     * @param henryID - A unique identifier for each star, called the Henry
     * Draper number
     * @param magnitude - How bright the star shines in contrast to the others
     * @param starNames - One or many names the star may have
     */
    public Star(double starX, double starY, int henryID, double magnitude, String starNames) {
        this.starX = starX;
        this.starY = starY;
        this.henryID = henryID;
        this.magnitude = magnitude;
        this.starNames = starNames;
    }

    // getters and setters
    //Set star xCoord
    public void setStarX(double xCoord) {
        starX = xCoord;
    }

    //Get star xCoord
    public double getStarX() {
        return starX;
    }

    //Set star yCoord
    public void setStarY(double yCoord) {
        starY = yCoord;
    }

    //Get star yCoord
    public double getStarY() {
        return starY;
    }

    //Set Henry ID
    public void setHenryID(int ID) {
        henryID = ID;
    }

    //Get Henry ID 
    public int getHenryID() {
        return henryID;
    }

    //Set magnitude
    public void setMagnitude(double mag) {
        magnitude = mag;
    }

    //Get magnitude
    public double getMagnitude() {
        return magnitude;
    }

    //Set star names
    public void setStarNames(String names) {
        starNames = names;
    }

    //Get star names
    public String getStarNames() {
        return starNames;
    }

    /**
     * toString()
     *
     * @return a formatted String of star pairs. This is for testing purposes
     * only.
     */
    @Override
    public String toString() {
        String starInfo = starX + "," + starY + "," + henryID + "," + magnitude + "," + starNames;

        return starInfo;
    }
}
