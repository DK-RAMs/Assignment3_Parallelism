package main;

import classes.*;
import sun.rmi.server.InactiveGroupException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Driver class for entire program.
 */

public class BotanyAnalyser {

    private static long startTime = 0;
    private static final Scanner inp = new Scanner(System.in);

    private static float[][] Terrain;
    
    private static ForkJoinPool fjPool = new ForkJoinPool();

    /**
     * This method captures the current system time in milliseconds and is used to initialize the timing
     * process.
     */

    private static void tick(){
         startTime = System.currentTimeMillis();
    }

    /**
     * This method captures the current system time in milliseconds and returns the difference
     * between the current time and the start time of the program.
     * @return Returns the difference between current time and start time in seconds.
     */
    private static float tock(){
        return (System.currentTimeMillis() - startTime);
    }

    /**
     * This method takes an array of tree objects and a 2D array of floats (which act as
     * terrain), creates a new calculator object, initializes the timer and returns an arraylist of float objects.
     * @param trees An array of tree objects
     */
    private static ArrayList<Float> ParallelCalculation(Tree[] trees){
        Calculator calculator = new Calculator(trees, 0, trees.length);
        tick();
        return fjPool.invoke(calculator);
    }

    /**
     * This method creates a new calculator, starts the timer and runs the series calculation of total sun exposure for each individual tree in the parameterized trees array
     * @param trees An array of tree objects
     * @return An ArrayList<Float> containing resultants of all calculations made on individual tree objects in the trees array
     */
    private static ArrayList<Float> SeriesCalculation(Tree[] trees){
        Calculator calculator = new Calculator(trees, 0, trees.length);
        tick();
        return calculator.seriesCalculate();
    }



    public static void main(String[] args){
        // Attributes for terrain and tree creation declared
        Tree[] trees;
        String line;

        //Loads parameterized arg strings and generates files from them
        try{
            String outputfile = args[1];
            File file = new File(args[0]);
            Scanner input = new Scanner(file);
            tick();
            line = input.nextLine();

            //Terrain details are generated. input.nextFloat() used to dodge heap space issues as reading the entire line will cause Java to run out of space
            Terrain = new float[Integer.parseInt(line.split(" ")[0])][Integer.parseInt(line.split(" ")[1])];
            for(int i = 0; i < Terrain.length; i++){
                for(int j = 0; j < Terrain[i].length; j++){
                    Terrain[i][j] = input.nextFloat();
                }
            }
            
            //creates new array of trees with size equal to the next value
            trees = new Tree[input.nextInt()];
            for(int i = 0; i < trees.length; i++){
                trees[i] = new Tree(input.nextInt(), input.nextInt(), input.nextInt(), Terrain);
            }

            //close file and removes it from memory
            input.close();
            input = null;
            file = null;

            //User is informed about the amount of time it took for the file to load. User can now interact with the program
            System.out.println("File took " + tock() + " seconds to load." );
            System.out.println("How would you like to run this progam?\n1.) In Series\n2.) In Parallel\n3.) Exit");

            //Initializes user IO items
            int programruntype = -1;
            float timer;
            while(programruntype != 3){
                ArrayList<Float> finalvals;
                timer = 0;
                float timer2 = -1;
                FileWriter writefile = new FileWriter(outputfile);
                BufferedWriter writer = new BufferedWriter(writefile);
                programruntype = inp.nextInt();
                SumTree sumTree;
                switch (programruntype){
                    case 1:
                        //Case where user uses serial implementation of program
                        finalvals = SeriesCalculation(trees); // Start time set here, used for timer
                        timer += tock(); //Adds tock() value to timer, this is phase 1 of calculations completed

                        float average = 0;

                        tick();
                        for(float vals: finalvals){
                            average += vals;
                        }
                        average = average / finalvals.size();
                        timer += tock(); //Adds tock() value to timer, this is phase 2 of calculations completed
            
                        tick(); //Begins timing the write operations
                        writer.write(String.format("%.6f", average) + "\n" + finalvals.size() + "\n");
                        for(float val: finalvals){
                            writer.write(String.format("%.6f", val) + "\n");
                        }
                        writer.close();
                        timer2 = tock(); // Ends timing the write operations and stores the resultant in a separate value to the initial vals.
                        writer = null;
                        writefile = null;
                        break;
                    case 2:
                        // Case where user uses parallel implementation of program
                        finalvals = ParallelCalculation(trees); // Starts timer for phase 1 calculations
                        timer += tock(); //Adds tock() value to timer, this is phase 1 of calculations complete
                        float total = 0;
                        for(float val: finalvals){
                            total += val;
                        }
                        sumTree = new SumTree(finalvals, 0, finalvals.size());
                        tick(); // Starts timer for phase 2 calculations
                        average = fjPool.invoke(sumTree) / 1000000;
                        System.out.println(average);
                        timer += tock();//Adds tock() value to timer, this signifies the completion of phase 2 calculations
                        
                        tick(); // Begins timing the write operations
                        writer.write(String.format("%.6f", average) + "\n" + finalvals.size() + "\n");
                        for(float val: finalvals){
                            writer.write(String.format("%.6f", val) + "\n");
                        }
                        writer.close();
                        timer2 = tock(); // Ends timing of the write operations.
                        writer = null;
                        writefile = null;

                        break;
                    case 3:
                        //Case where user decides to stop using program.
                        System.out.println("Thank you for using this Botany Analyzer program. The program will now exit");
                        System.exit(-1);
                        break;
                    case 4:
                        ArrayList<Float> serlist = SeriesCalculation(trees);
                        ArrayList<Float> parlist = ParallelCalculation(trees);
                        for(int i = 0; i < serlist.size(); i++){
                            if(!serlist.get(i).equals(parlist.get(i))){
                                System.out.println((double)serlist.get(i) + " " + (double)parlist.get(i));
                            }
                        }
                        break;
                    default:
                        // Informs user of flawed input and closes the program.
                        System.out.println("Incorrect input, program will now exit");
                        System.exit(1);
                        break;
                }
                // User informed of success of operation and how long it took for file to be written
                System.out.println("Output of program has been written to " + outputfile + ". The program took " + timer + " seconds to complete all calculations and " + timer2 + " seconds to \nwrite all output to " + outputfile + ".\nWould you like to run this program again?\n1.) Yes\n2.) No");
                finalvals = null;

                // Call to Garbage collector to remove all null pointers from memory.
                System.gc();
                int lastinp = inp.nextInt();
                switch(lastinp){
                    case 1:
                        //Repeats process of user IO from start with current items in memory.
                        System.out.println("How would you like to run this progam?\n1.) In Series\n2.) In Parallel\n3.) Exit");
                        break;
                    case 2:
                        //Case where user decides to stop using program
                        System.out.println("Thank you for using this Botany Analyzer program. The program will now exit");
                        programruntype = 3;
                        break;
                    default:
                        System.out.println("Incorrect input, program will now exit");
                        System.exit(1);
                        break;
                }
            }
        }
        catch(Exception ioe){
            ioe.printStackTrace();
        }
        catch(OutOfMemoryError oom){
            System.out.println("Insufficient memory to process request, the program will now exit");
            System.exit(0);
        }
    }
}