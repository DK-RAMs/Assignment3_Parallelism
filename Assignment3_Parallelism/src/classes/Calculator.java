package classes;

import java.util.concurrent.*;

public class Calculator extends RecursiveTask<Float>{

    private float startTime;
    private Tree[] trees;
    private float avg;
    private float[] treesavg;

    private static void startTimer(){

    }

    private static float endTimer(){

        return 1;
    }

    public Calculator(Tree[] trees){
        this.trees = trees;
        treesavg = new float[trees.length];
    }

    public float seriesCalculate(){
        startTime = System.currentTimeMillis();

        float endTime = System.currentTimeMillis();
        return endTime - startTime / 1000.0f;
    }

    public float parallelCalculate(){
        startTime = System.currentTimeMillis();

        float endTime = System.currentTimeMillis();
        return endTime - startTime / 1000.0f;
    }

    @Override
    protected Float compute() {
        return null;
    }
}
