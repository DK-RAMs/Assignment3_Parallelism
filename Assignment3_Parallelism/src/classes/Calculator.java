package classes;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

public class Calculator extends RecursiveTask<Float>{

    private float startTime;
    private Tree[] trees;
    private float avg;
    private float[] treesavg;

    private static ForkJoinPool fjPool = new ForkJoinPool();

    private static float sumTreeHrs(Tree tree){
        return fjPool.invoke(tree);
    }




    public Calculator(Tree[] trees){
        this.trees = trees;
        treesavg = new float[trees.length];
    }

    private void startTimer(){
        startTime = System.currentTimeMillis();
    }


    public float seriesCalculate(String filename){
            String finalcommit = "";
            float averageall = 0;
            startTimer();
            for (int i = 0; i < trees.length; i++){
                averageall += trees[i].sum(0);
                finalcommit = finalcommit + trees[i].sum(0) + "\n";
            }
            float endTime = System.currentTimeMillis();
            averageall = averageall / trees.length;
        try{
            String firstall = averageall + "\n" + trees.length + "\n";
            FileWriter writefile = new FileWriter(filename, true);
            writefile.write(firstall);
            writefile.write(finalcommit);
            writefile.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            return endTime - startTime / 1000.0f;
        }
    }

    public float parallelCalculate(String filename){
        String finalcommit = "";
        float averageall = 0;
        startTime = System.currentTimeMillis();
        compute();

        try{
            FileWriter writer = new FileWriter(filename);

        }
        catch(IOException ioe){

        }
        finally {
            float endTime = System.currentTimeMillis();
            return endTime - startTime / 1000.0f;
        }
    }

    @Override
    protected Float compute() {

        return null;
    }
}
