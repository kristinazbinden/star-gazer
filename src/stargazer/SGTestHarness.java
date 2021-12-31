/**
 *  Description: Use this to test the functionality of your StarGazer program.
 *  Author:  Valerie Green
 *  Date: 23 August 2020
 *  Pledged: I pledge that I heart CSCI!  :-)
 *
 */
package stargazer;

import acm.graphics.GPoint;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vgreen
 */
public class SGTestHarness {
    private static final String TEST_FOLDER_PREFIX = "TestFiles";
    private static final String CORRECT_OUTPUT_PREFIX = "correct_";
    private static final String TEST_OUTPUT_PREFIX = "test_";

    private StarGazer starGazer;

    /**
     * Helper method for printing out the stars and constellations
     * @param filename
     * @param list 
     */
    private void dumpListToFile(String filename, ArrayList list) {
        try {
            PrintWriter pw = new PrintWriter(filename);
            for (Object o:list) {
                pw.printf("%s%n", o);
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SGTestHarness.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Helper method for printing differences
     * @param filename1
     * @param filename2 
     */
    private void bareBonesDiff(String filename1, String filename2) {
        try {
            FileReader fr1 = new FileReader(filename1);
            FileReader fr2 = new FileReader(filename2);
            Scanner s1 = new Scanner(fr1);
            Scanner s2 = new Scanner(fr2);
            
            boolean filesIdentical = true;
            int lineCount = 0; // so you can tell where the first difference was
            while (s1.hasNext() && s2.hasNext() && filesIdentical) {
                lineCount++;
                String curLine1 = s1.nextLine();
                String curLine2 = s2.nextLine();
                
                // check each line to see if it's the same in both files.
                if (!curLine1.equals(curLine2)) {
                    filesIdentical = false;
                }
            }
            
            // if one file has more content, they're not the same.
            if (s1.hasNext() != s2.hasNext()) {
                filesIdentical = false;
            }
            
            if (!filesIdentical) {
                System.out.printf("FAILED: output doesn't match at line %d%n", lineCount);
            }
            else {
                System.out.printf("PASSED%n");
            }
            
            fr1.close();
            fr2.close();
        } catch (IOException ex) {
            Logger.getLogger(SGTestHarness.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void testConstellationInitialization() {
        System.out.print("Testing constellation file input and initialization...");
        ArrayList<Constellation> constellations = new ArrayList();
        // read in constellation data for all files.
        starGazer.readConstellation("BigDipper_lines.txt", "Big Dipper", constellations);
        starGazer.readConstellation("Bootes_lines.txt", "Bootes", constellations);
        starGazer.readConstellation("Cas_lines.txt", "Cassiopeia", constellations);
        starGazer.readConstellation("Cyg_lines.txt", "Cygnus", constellations);
        starGazer.readConstellation("Gemini_lines.txt", "Gemini", constellations);
        starGazer.readConstellation("Hydra_lines.txt", "Hydra", constellations);
        starGazer.readConstellation("UrsaMajor_lines.txt", "Ursa Major", constellations);
        starGazer.readConstellation("UrsaMinor_lines.txt", "Ursa Minor", constellations);
         

        // Test constellation reading by dumping it out to a file, and comparing
        // that to the contents of the test file given.
        dumpListToFile(TEST_FOLDER_PREFIX + File.separator + TEST_OUTPUT_PREFIX + "constellations.txt", constellations);
        System.out.println("DONE");
        bareBonesDiff(TEST_FOLDER_PREFIX + File.separator + CORRECT_OUTPUT_PREFIX + "constellations.txt", 
                TEST_FOLDER_PREFIX + File.separator + TEST_OUTPUT_PREFIX + "constellations.txt");

        
    }
    
    public void testStarInitialization() {
        System.out.println("Uncomment this code when you are ready to test readStars()");
        
        System.out.print("Testing star file input and initialization...");
        ArrayList<Star> stars = new ArrayList();
        
        starGazer.readStars("stars.txt", stars);
        dumpListToFile(TEST_FOLDER_PREFIX + File.separator + TEST_OUTPUT_PREFIX + "stars.txt", stars);
        System.out.println("DONE");
        bareBonesDiff(TEST_FOLDER_PREFIX + File.separator + CORRECT_OUTPUT_PREFIX + "stars.txt", 
                TEST_FOLDER_PREFIX + File.separator + TEST_OUTPUT_PREFIX + "stars.txt");
        
    }
    
    private void testSquarePlotting() {
        System.out.println("Uncomment this code when you are ready to test plotSquare()");
       
        System.out.print("Testing square plotting...");
        // You will check this by eye.  Think about what it's supposed to
        // be doing and judge whether your plotSquare() is correct.
        for (int i=0; i<10; i++) {
            starGazer.plotSquare(new GPoint(i*10, i*10), i+4, Color.GREEN);
        }
        String[] sizeArgs = {"width=" + StarGazer.WINDOW_SIZE, "height=" + StarGazer.WINDOW_SIZE};
        starGazer.start(sizeArgs);
        System.out.println("DONE");
        
    }
    
    private void testCoordsToPixel() {
        System.out.println("Uncomment this code when you are ready to test coordsToPixel()");
        
        System.out.println("Testing coords to pixel:");
        GPoint[] testPoints = {
            new GPoint(-1, -1),
            new GPoint(1,1),
            new GPoint(1,-1),
            new GPoint(-1,-1),
            new GPoint(0,0),
            new GPoint(0.5,0.5),
            new GPoint(-0.5,-0.5)    
        };
        
//         This will print out the converted points.  Verify that they are correct.
        for (GPoint p : testPoints) {
            System.out.println(starGazer.coordsToPixel(p.getX(), p.getY(), 1000));
        }
        System.out.println("DONE");
        
    }
    
    public void runTests() {
        // make sure the test files are there first.
        // just pick one...
        File f = new File(TEST_FOLDER_PREFIX + File.separator + CORRECT_OUTPUT_PREFIX + "constellations.txt");
        if (!f.exists()) {
            System.out.println("ERROR: please copy the TestFiles folder into your project folder.");
        }
        starGazer = new StarGazer();

        testCoordsToPixel();
        testSquarePlotting();
        testConstellationInitialization();  // this is already done for you
        testStarInitialization();
        
        
    }
    
    public static void main(String[] args) {
        SGTestHarness sgth = new SGTestHarness();
        sgth.runTests();
        
    }
    
}
