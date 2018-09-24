
import java.util.Scanner;
import java.io.*;

class aclass{
    
    public static void main(String[] args) throws Exception{
        File file1 = new File("C:\\Development\\CSC2002S Assignments\\Assignment3_Parallelism\\src\\testoutput");
        File file2 = new File("C:\\Development\\CSC2002S Assignments\\sample_output.txt");
        
        FileReader reader1 = new FileReader(file1);
        FileReader reader2 = new FileReader(file2);
        
        BufferedReader in1 = new BufferedReader(reader1);
        BufferedReader in2 = new BufferedReader(reader2);
        
        String line = "";
        int check = 0;
        while((line = in1.readLine()) != null){
            if(Float.parseFloat(line) != Float.parseFloat(in2.readLine())){
               System.out.println("Anomaly detected");
               check++;
            }
        }
        if(check == 0)
            System.out.println("Algorithm works perfectly");
    }
}