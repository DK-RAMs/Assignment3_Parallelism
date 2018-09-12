import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class BotanyAnalyser {

    private static Scanner Inp = new Scanner(System.in);


    public static void main(String[] args){
        int[][] Terrain;
        try {
            System.out.println("Good day! Please enter the name of the file that will be used for the botany\n simulation");
            String filename = Inp.nextLine();
            int stepinc = 0;
            FileReader file = new FileReader(filename);
            BufferedReader fileinput = new BufferedReader(file);
            String line = "";
            while((line = fileinput.readLine()) != null){
                if(stepinc == 0){
                    String[] linedets = line.split(" ");
                    Terrain = new int[Integer.parseInt(linedets[0])][Integer.parseInt(linedets[1])];
                    stepinc++;
                }
                if(stepinc == 1){

                }

            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
