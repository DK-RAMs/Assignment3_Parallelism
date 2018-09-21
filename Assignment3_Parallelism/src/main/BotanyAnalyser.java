package main;

import classes.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;


public class BotanyAnalyser {
    static long startTime = 0;

    public static float[][] Terrain;

    private static void tick(){
         startTime = System.currentTimeMillis();
    }
    
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }
    
    private static Scanner inp = new Scanner(System.in);

    private static ArrayList<Float> ParallelCalculation2(Tree[] trees, float[][] Terrain){
        Calculator calculator = new Calculator(trees, 0, trees.length, Terrain);
        tick();
        return fjPool.invoke(calculator);
    }

    private static ArrayList<Float> SeriesCalculation(Tree[] trees, String outputfile, float[][] Terrain){
        Calculator calculator = new Calculator(trees, 0, trees.length, Terrain);
        tick();
        return calculator.seriesCalculate();
    }

    private static float ParallelCalculation(Tree[] trees, String outputfile, float[][] Terrain){
        Calculator calculator = new Calculator(trees, 0, trees.length, Terrain);
        for(Tree tree: trees){
            tree.calculator = calculator;
        }
        tick();
        //return Avg(calculator);
        return -1;
    }
    private static ForkJoinPool fjPool = new ForkJoinPool();

//    private static float Avg(Calculator cal){
//        return fjPool.invoke(cal);
//    }

    public static void main(String[] args){
        Tree[] trees;
        String line;
        try{
            //String inputfile = args[0];
            String outputfile = "testoutput";
            File file = new File("C:\\Development\\CSC2002S Assignments\\Assignment3_Parallelism\\src\\main\\sample_input.txt");
            //FileReader file = new FileReader(inputfile);
            Scanner input = new Scanner(file);
            //while((line = input.readLine()) != null){
            //switch (stepinc){
              //  case 0:
            tick();
            line = input.nextLine();
            Terrain = new float[Integer.parseInt(line.split(" ")[0])][Integer.parseInt(line.split(" ")[1])];
            for(int i = 0; i < Terrain.length; i++){
                for(int j = 0; j < Terrain[i].length; j++){
                    Terrain[i][j] = input.nextFloat();
                }
            }
            trees = new Tree[input.nextInt()];
            for(int i = 0; i < trees.length; i++){
                trees[i] = new Tree(input.nextInt(), input.nextInt(), input.nextInt(), Terrain);
            }
            input.close();
            input = null;
            file = null;
            System.out.println("File took " + tock() + " seconds to load." );
            System.out.println("How would you like to run this progam?\n1.) In Series\n2.) In Parallel\n3.) Exit");
            int programruntype = -1;
            float timer = -1;
            float timer2 = -1;
            while(programruntype != 3){
                ArrayList<Float> finalvals;
                timer = 0;
                //programruntype = 2;
                programruntype = inp.nextInt();
                switch (programruntype){
                    case 1:
                        tick();
                        finalvals = SeriesCalculation(trees, outputfile, Terrain);
                        timer += tock();
                        System.out.println(finalvals.size());
                        for(float vals: finalvals){
                            System.out.println(vals);
                        }

                        /*
                        FileWriter writer = new FileWriter(outputfile);
                        writer.write(Float.toString(timer));
                        writer.close();*/

                        break;
                    case 2:
                        //timer = ParallelCalculation(trees, outputfile, Terrain);
                        tick();
                        finalvals = ParallelCalculation2(trees, Terrain);
                        timer += tock();
                        SumTree sumTree = new SumTree(finalvals, 0, finalvals.size());
                        tick();
                        float average = fjPool.invoke(sumTree) / finalvals.size();
                        timer += tock();
                        FileWriter writefile = new FileWriter(outputfile);
                        BufferedWriter writer = new BufferedWriter(writefile);
                        tick();
                        writer.write(String.format("%.6f", average) + "\n" + finalvals.size() + "\n");
                        for(float val: finalvals){
                            writer.write(String.format("%.6f", val) + "\n");
                        }
                        writer.close();
                        timer2 = tock();
                        writer = null;
                        writefile = null;
                        /*
                        for(float vals: finalvals){
                            System.out.println(vals);
                        }*/
                        //timer = tock();
                        //write occurs here
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
                System.out.println("Output of program has been written to " + outputfile + ". The program took " + timer + " seconds to complete all calculations and " + timer2 + " seconds to write all output to " + outputfile + ".\nWould you like to run this program again?\n1.) Yes\n2.) No");
                finalvals = null;

                System.gc();
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
        catch(OutOfMemoryError oom){
            System.out.println("Insufficient memory to process request, the program will now exit");
            System.exit(0);
        }
    }
}
