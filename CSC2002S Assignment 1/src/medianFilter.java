/**
@author Cameron Rebelo RBLCAM001
main class for medianFilter. Accepts arguements from the terminal for an input file, filter size and output file.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class medianFilter {
    static long startTime = 0;
    static final ForkJoinPool fjPool = new ForkJoinPool();
    public static int filter = 3;
    private static outArr oArr;
    private static String outputFile;

    /**
     * @param input from the terminal in the form of <inputfilename.txt><filtersize><outfilename.txt> 
     * main method of the class
     */
    public static void main(String[] args) {
        Scanner inputLine = new Scanner(args[0]).useDelimiter(" ");
        String inputFile = inputLine.next();
        int filter = inputLine.nextInt();
        String outputFile = inputLine.next();
        // for (int i = 5; i < 6; i++) {
        // System.out.println("\nFile " + i);
        // fileName = ((int) (100 * Math.pow(10, i))) + "";
        try {
            Scanner sc = new Scanner(new File(inputFile));
            // Scanner sc = new Scanner(new File(
            // "C:/Users/camer/Desktop/Uni/2nd Year/2nd
            // Semester/CSC2002S/Projects/Assignment1/data/sampleInput"
            // + fileName + ".txt"));
            int numberOfLines = Integer.parseInt(sc.nextLine());
            float[] inputArr = new float[numberOfLines];
            for (int j = 0; j < numberOfLines; j++) {
                Scanner line = new Scanner(sc.nextLine()).useDelimiter(" ");
                String lineNumber = line.next();
                String value = (line.next()).replace(",", ".");
                inputArr[j] = Float.parseFloat(value);
                line.close();
            }
            sc.close();
            oArr = new outArr(inputArr);
            float[] print = prune(inputArr, filter, outputFile);

            // for (int l = 3; l <= 21; l += 2) {
            // System.out.println("Filter size: " + l);
            // for (int m = 0; m < 20; m++) {
            // }
            // }

        } catch (FileNotFoundException fe) {
            System.out.println("error: " + fe);
        }

    }
    /**
     * start a counter
     */ 
    private static void tick() {
        startTime = System.nanoTime();
    }
    /**
     * method to calulate how much time has passed since tick()
     * @return float with time passed
     */
       
    private static float tock() {
        return (System.nanoTime() - startTime) / 100000.0f;
    }
    /**
     * @param input  arr to be parsed to median filter
     * @param filter size of the median filter
     * @param oFile  name of the file for output to be written to 
     * method to sort and time the parallel exection of a median filter on a data set
     * @return float[] with median values
     */

    public static float[] prune(float[] input, int filter, String oFile) {
        System.gc();
        medianThread mThread = new medianThread(input, 0, input.length, filter);
        tick();
        fjPool.invoke(mThread);
        float time = tock();
        // System.out.println(time);
        float[] result = oArr.getOutput();
        try {
            PrintStream originalOut = System.out;
            PrintStream out = new PrintStream(new FileOutputStream("../data/" + oFile));
            System.setOut(out);
            // System.out.println(time);
            System.out.println(formatOutput(result));
            System.setOut(originalOut);
        } catch (FileNotFoundException fe) {
            System.out.println(fe);
        }
        return result;
    }
    /**
     * @param index the index of the element
     * @param value the value of the element 
     * method to set the value of the median in the output array object
     */
    public void setOutputArrayElement(int index, float value) {
        oArr.setOutput(index, value);
    }
    
    /**
     * @param output array of floats to be foramtted 
     * method to foramt array of floats into format for output file
     * @return String containing foramatted data to be written
     */
    public static String formatOutput(float[] output) {
        String temp = output.length + "";
        for (int i = 0; i < output.length; i++) {
            temp += "\n" + i + " " + String.format("%.5f", output[i]);
        }
        return temp;
    }
    
}