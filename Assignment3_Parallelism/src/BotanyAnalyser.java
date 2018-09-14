import classes.Calculator;
import classes.Tree;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class BotanyAnalyser {


    private static Scanner inp = new Scanner(System.in);


    private static void calculation(){

    }

    private static void SeriesCalculation(Tree[] trees){
        Calculator calculator = new Calculator(trees);
    }

    private static void ParallelCalculation(Tree[] trees){
        Calculator calculator = new Calculator(trees);
    }
    private static ForkJoinPool fjPool = new ForkJoinPool();

    public static void main(String[] args){
        float[][] Terrain = {};
        Tree[] trees = {};
        try {
            System.out.println("Good day! Please enter the name of the file that will be used for the botany\n simulation");
            String filename = inp.nextLine();
            int stepinc = 0;
            FileReader file = new FileReader(filename);
            BufferedReader input = new BufferedReader(file);
            String line = "";
            String[] linedets;
            while((line = input.readLine()) != null){
                if(stepinc == 0){
                    linedets = line.split(" ");
                    Terrain = new float[Integer.parseInt(linedets[0])][Integer.parseInt(linedets[1])];
                    stepinc++;
                }
                if(stepinc == 1){
                    linedets = line.split(" ");
                    int arrpos = 0;
                    for(int i = 0; i < Terrain.length; i++){
                        for(int j = 0; j < Terrain[i].length; i++){
                            Terrain[i][j] = Float.parseFloat(linedets[arrpos++]);
                            System.out.println(Terrain[i][j]);
                        }
                    }
                }
                if(stepinc == 2){
                    trees = new Tree[Integer.parseInt(line)];
                }

            }

            System.out.println("How would you like to run this progam?\n1.) In Series\n2.) In Parallel 3.) Exit");
            int programruntype = inp.nextInt();
            switch(programruntype){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Incorrect input, program will now exit");
                    System.exit(1);
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
