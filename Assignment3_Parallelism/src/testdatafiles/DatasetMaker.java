package main;

import java.io.*;
import java.util.*;


// This program creates a subset of data based on the sample_input.txt file. This makes it easier to run tests on data
public class DatasetMaker {

    private static Scanner inp = new Scanner(System.in);


    public static void main(String[] args) throws Exception{

        System.out.println("Enter the filename that will be used to save the data items");
        String filename = inp.nextLine();
        //Scanner input = new Scanner("C:\\Development\\CSC2002S Assignments\\Assignment3_Parallelism\\src\\main\\sample_input.txt");
        FileReader readfile = new FileReader(new File("C:\\Development\\CSC2002S Assignments\\Assignment3_Parallelism\\src\\main\\sample_input.txt"));
        BufferedReader input = new BufferedReader(readfile);
        FileWriter writefile = new FileWriter(filename);
        BufferedWriter writer = new BufferedWriter(writefile);
        String a = input.readLine();
        String b = input.readLine();
        writer.write(a + "\n" + b);
        System.out.println("Enter 2 numbers between 0 and 1 000 000");
        String numbers = inp.nextLine();
        int[] vals = {Integer.parseInt(numbers.split(" ")[0]), Integer.parseInt(numbers.split(" ")[1])};
        writer.write("\n" + (vals[1] - vals[0]) + "\n");
        for(int i = 0; i < vals[0]; i++){
            input.readLine();
        }
        for(int i = vals[0]; i < vals[1]; i++){
            writer.write(input.readLine() + "\n");
        }
        writer.close();
        System.out.println("dataset of subset " + vals[0] + " - " + vals[1] + " of size " + (vals[1] - vals[0]) + " has been created in file " + filename);

    }
}
