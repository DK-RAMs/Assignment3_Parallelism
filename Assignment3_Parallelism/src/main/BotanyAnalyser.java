package main;

import classes.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;


public class BotanyAnalyser {


    private static Scanner inp = new Scanner(System.in);

    private static Tree[] prepData(String a, String b){
        Tree[] trees = new Tree[5];

        return trees;
    }

    private static void calculation(){

    }

    private static float SeriesCalculation(Tree[] trees, String outputfile){
        Calculator calculator = new Calculator(trees);
        return calculator.seriesCalculate(outputfile);
    }

    private static float ParallelCalculation(Tree[] trees, String outputfile){
        Calculator calculator = new Calculator(trees);
        return calculator.parallelCalculate(outputfile);
    }
    private static ForkJoinPool fjPool = new ForkJoinPool();

    public static void main(String[] args){
        float[][] Terrain;
        Tree[] trees;
        int numtrees = 0;
        String line;
        try{
            System.out.println("Good day! Please enter the name of the files that will be used for the botany analysis:");
            String filename = "testdata testoutput";
            String[] filenames = filename.split(" ");
            String inputfile = filename.split(" ")[0];
            String outputfile = filename.split(" ")[1];
            File file = new File("C:\\Development\\CSC2002S Assignments\\Assignment3_Parallelism\\src\\main\\testoutput");
            //FileReader file = new FileReader(inputfile);
            //BufferedReader input = new BufferedReader(file);
            Scanner input = new Scanner(file);
            //while((line = input.readLine()) != null){
            //switch (stepinc){
              //  case 0:
            line = input.nextLine();
            Terrain = new float[Integer.parseInt(line.split(" ")[0])][Integer.parseInt(line.split(" ")[1])];
                //    break;
                //case 1:
            for(int i = 0; i < Terrain.length; i++){
                for(int j = 0; j < Terrain[i].length; j++){
                    Terrain[i][j] = input.nextFloat();
                }
            }
                  //  break;
                //case 2:
            System.gc();
            trees = new Tree[input.nextInt()];
                  //  break;
                //case 3:
            for(int i = 0; i < numtrees; i++){
                trees[i] = new Tree(Terrain, input.nextInt(), input.nextInt(), input.nextInt());
            }

            System.out.println("How would you like to run this progam?\n1.) In Series\n2.) In Parallel\n3.) Exit");
            int programruntype = -1;
            float timer = -1;
            while(programruntype != 3) {
                programruntype = inp.nextInt();
                switch (programruntype) {
                    case 1:
                        timer = SeriesCalculation(trees, outputfile);
                        break;
                    case 2:
                        System.out.println("This region works");
                        timer = ParallelCalculation(trees, outputfile);
                        break;
                    case 3:
                        System.out.println("Thank you for using this Botany Analyzer program. The program will now exit");
                        System.exit(-1);
                        break;
                    default:
                        System.out.println("Incorrect input, program will now exit");
                        System.exit(1);
                        break;
                }
                System.out.println("Output of program has been written to " + outputfile + ". The program took " + timer + " seconds to complete.\n Would you like to run this program again?\n1.) Yes\n2.) No");
                int lastinp = inp.nextInt();
                switch(lastinp){
                    case 1:
                        System.out.println("How would you like to run this progam?\n1.) In Series\n2.) In Parallel\n3.) Exit");
                        break;
                    case 2:
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
    }
}
