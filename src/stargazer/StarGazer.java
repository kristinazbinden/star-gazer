/**
 *  Description: Stargazer program built in Java consuming star catalog data files and plotting the location of stars and constellations using GObjects.
 *  Author:  Kristina Zbinden
 *  Due Date: 12/17/21
 *  Pledged: All work contained here is my own and was created without collaboration.
 *
 */
package stargazer;

import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Math;

/**
 * @author
 */
public class StarGazer extends GraphicsProgram {

    /**
     * Width/height of the display (all coordinates are in pixels)
     */
    public static final int WINDOW_SIZE = 800;

    /**
     * ArrayList of the stars in the data file
     */
    private ArrayList<Star> stars;

    /**
     * ArrayList of the constellations in the various data files
     */
    private ArrayList<Constellation> constellations;

    /**
     * Leave this as it is; you will work in the run() method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] sizeArgs = {"width=" + WINDOW_SIZE, "height=" + WINDOW_SIZE};
        new StarGazer().start(sizeArgs);
    }

    /**
     * Change coordinate locations in star data to pixel locations
     *
     * @param coordX
     * @param coordY
     */
    public GPoint coordsToPixel(double coordX, double coordY, int testWindowSize) {
        double pixelX;
        double pixelY;
        double pixelMax = testWindowSize / 2;
        GPoint newPoint;

        // Calculate pixel location front coords
        if (coordX > 0) {
            pixelX = pixelMax + (pixelMax * coordX);
        } else if (coordX < 0) {
            pixelX = pixelMax + (pixelMax * coordX);
        } else {
            pixelX = pixelMax;
        }

        if (coordY > 0) {
            pixelY = pixelMax - (pixelMax * coordY);
        } else if (coordY < 0) {
            pixelY = pixelMax + (pixelMax * Math.abs(coordY));
        } else {
            pixelY = pixelMax;
        }

        // Create point at which star is displayed
        newPoint = new GPoint(pixelX, pixelY);
        return newPoint;

    }

    /**
     * Draw stars on display
     *
     * @param squareCoords
     * @param sideLength
     * @param squareColor
     */
    public void plotSquare(GPoint squareCoords, int sideLength, Color squareColor) {

        GRect square = new GRect(squareCoords.getX(), squareCoords.getY(), sideLength, sideLength);
        square.setColor(squareColor);
        square.setFilled(true);
        add(square);
    }

    /**
     * Read a star's data and add a new star to an ArrayList. Given a file name
     * and an ArrayList, read star data from the file, create Star objects, and
     * add them to the ArrayList.
     *
     * @param fileName
     * @param stars
     */
    public void readStars(String fileName, ArrayList<Star> stars) {
        try {
            // create the new file input stream and attach a Scanner to it.
            FileInputStream fileStream = new FileInputStream(fileName);
            Scanner s = new Scanner(fileStream);
            double starX = 0.0;
            double starY = 0.0;
            int henryID = 0;
            double magnitude = 0.0;
            String starNames = "";
            Scanner inSS;

            // While there is a new line of data, extract it
            while (s.hasNext()) {

                inSS = new Scanner(s.nextLine());
                // Convert string values to doubles and integers
                starX = Double.valueOf(inSS.next());
                starY = Double.valueOf(inSS.next());
                inSS.nextDouble();
                henryID = Integer.parseInt(inSS.next());
                magnitude = Double.valueOf(inSS.next());
                inSS.nextDouble();

                while (inSS.hasNext()) {
                    starNames += inSS.next() + ' ';
                }
                if (starNames.length() > 1) {
                    starNames = starNames.substring(0, starNames.length() - 1);
                }

                Star newStar = new Star(starX, starY, henryID, magnitude, starNames);
                stars.add(newStar);

                starNames = "";

            }

            // close the file stream
            fileStream.close();

        } catch (IOException ex) {
            Logger.getLogger(StarGazer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Read a constellation's data and add a new Constellation to the ArrayList.
     * The name of the constellation is not part of the data file, so it is
     * given here as a parameter.
     *
     * @param filename - file with star pairs, such as "Cas_lines.txt"
     * @param constellationName - for example, "Cassiopeia"
     * @param constellations - ArrayList to add the Constellation object to
     */
    public void readConstellation(String filename, String constellationName, ArrayList<Constellation> constellations) {
        try {
            // create the new file input stream and attach a Scanner to it.
            FileInputStream fis = new FileInputStream(filename);
            Scanner s = new Scanner(fis);
            // make an ArrayList to hold the Strings with the star pairs (see data file)
            // We have to make an ArrayList here because the Constellation class
            // has an ArrayList as a property, and requires that we pass it
            // to the constructor when creating a new Constellation.
            ArrayList<String> starPairs = new ArrayList();
            // keep reading until we get to the end of the file
            while (s.hasNext()) {
                String oneLine = s.nextLine();
                // trim off whitespace and newlines
                oneLine = oneLine.trim();
                // add to the list of star pairs
                starPairs.add(oneLine);
            }

            // make a Constellation and add it to the list of constellations
            // passed in as a parameter.
            Constellation c = new Constellation(constellationName, starPairs);
            constellations.add(c);

            // close the file stream now that we're done
            fis.close();

        } catch (IOException ex) {
            Logger.getLogger(StarGazer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Draw starts according to data in stars ArrayList
     *
     * @param stars
     */
    public void drawStars(ArrayList<Star> stars) {
        for (Star star : stars) {
            int starSize = (int) (5.0 / (star.getMagnitude() + 2));
            GPoint starPoint = coordsToPixel(star.getStarX(), star.getStarY(), WINDOW_SIZE);
            plotSquare(starPoint, starSize, Color.white);
        }
    }

    /**
     * Search list of stars and extract coordinates for each pair in
     * Constellation
     *
     * @param starName
     * @return
     */
    public String findStar(String starName) {
        String thisStar = "";

        for (Star star : stars) {
            if (star.getStarNames().contains(starName)) {
                thisStar = star.getStarX() + "," + star.getStarY();
            }

        }
        return thisStar;
    }

    /**
     * Draw constellations on display
     *
     * @param constellations
     */
    public void drawConstellations(ArrayList<Constellation> constellations) {
        String starOne = "";
        String starTwo = "";
        String starOneCoords = "";
        String starTwoCoords = "";
        GPoint starOnePoint;
        GPoint starTwoPoint;
        GLine line = new GLine(2, 2, 2, 2);

        for (Constellation cons : constellations) {
            for (String starName : cons.getStarPairs()) {
                starOne = starName.split(",")[0];
                starTwo = starName.split(",")[1];

                starOneCoords = findStar(starOne);
                starTwoCoords = findStar(starTwo);

                double starOneX = Double.parseDouble(starOneCoords.split(",")[0]);
                double starOneY = Double.parseDouble(starOneCoords.split(",")[1]);

                double starTwoX = Double.parseDouble(starTwoCoords.split(",")[0]);
                double starTwoY = Double.parseDouble(starTwoCoords.split(",")[1]);

                starOnePoint = coordsToPixel(starOneX, starOneY, WINDOW_SIZE);
                starTwoPoint = coordsToPixel(starTwoX, starTwoY, WINDOW_SIZE);

                GLine consLine = new GLine(starOnePoint.getX(), starOnePoint.getY(), starTwoPoint.getX(), starTwoPoint.getY());
                consLine.setColor(Color.YELLOW);
                consLine.isVisible();
                boolean intersects = consLine.getBounds().intersects(line.getBounds());

                add(consLine);

                line = new GLine(starOnePoint.getX(), starOnePoint.getY(), starTwoPoint.getX(), starTwoPoint.getY());

                // TO DO: delete once cygnus is drawing correctly
                System.out.println(starOne + " is at " + starOnePoint);
                System.out.println(starTwo + " is at " + starTwoPoint);
                System.out.println();

            }
        }

    }

    /**
     * Called by the system once the app is started.
     */
    @Override
    public void run() {
        // black background
        setBackground(Color.BLACK);

        // initialize the ArrayLists for Star and Constellation objects
        stars = new ArrayList();
        constellations = new ArrayList();

        // read constellations
        readConstellation("BigDipper_lines.txt", "Big Dipper", constellations);
        readConstellation("Bootes_lines.txt", "Bootes", constellations);
        readConstellation("Cas_lines.txt", "Cassiopeia", constellations);
        readConstellation("Cyg_lines.txt", "Cygnus", constellations);
        readConstellation("Gemini_lines.txt", "Gemini", constellations);
        readConstellation("Hydra_lines.txt", "Hydra", constellations);
        readConstellation("UrsaMajor_lines.txt", "Ursa Major", constellations);
        readConstellation("UrsaMinor_lines.txt", "Ursa Minor", constellations);

        readStars("stars.txt", stars);
        drawStars(stars);
        drawConstellations(constellations);

    }
}
