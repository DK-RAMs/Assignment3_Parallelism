package classes;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Calculator extends RecursiveTask<ArrayList>{

    int lo;
    int hi;
    static final int SEQUENTIAL_CUTOFF = 1000;
    private static Tree[] trees;
    public float[][] Terrain;
    private String outputfile;
    private float sumall;
    private String finalcommit;

    private static ForkJoinPool fjPool = new ForkJoinPool();



    public Calculator(){

    }

    public Calculator(Tree[] trees, int lo, int hi, float[][] Terrain){
        this.trees = trees;
        this.lo = lo;
        this.hi = hi;
        this.Terrain = Terrain;

    }



    public ArrayList<Float> seriesCalculate(){
            //String finalcommit = "";
            //float endTime;
            //float averageall = 0;
            ArrayList<Float> treeans = new ArrayList<>();
            //System.out.println(System.currentTimeMillis());
            for(int i = 0; i < trees.length; i++){
                //averageall += trees[i].sum(0, Terrain);
                treeans.add(trees[i].sum(0));
                treeans.trimToSize();
                //finalcommit = finalcommit + trees[i].sum(0, Terrain) + "\n";
            }
            /*endTime = System.currentTimeMillis();
            totalTime -= (endTime - startTime);
            averageall = averageall / trees.length;
        try{
            String firstall = averageall + "\n" + trees.length + "\n";
            File file = new File(outputfile);
            FileWriter writefile = new FileWriter(file, true);
            BufferedWriter written = new BufferedWriter(writefile);
            written.write(firstall);
            written.write(finalcommit);
            written.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{*/
            return treeans;
        //}
    }



    @Override
    protected ArrayList compute() {
        if(hi - lo <= SEQUENTIAL_CUTOFF){
            ArrayList treevals = new ArrayList();
            //long endTime = 0;
            //startTimer();
            for(int i = lo; i < hi; i++){
                treevals.add(fjPool.invoke(trees[i]));
                //finalcommit = finalcommit + trees[i].sum(0, Terrain) + "\n";
            }
            //endTime = System.currentTimeMillis();
            //totalTime = endTime;
            treevals.trimToSize();
            return treevals;
        }
        else{
            Calculator calcright = new Calculator(trees, (lo + hi)/2, hi, Terrain);
            Calculator calcleft = new Calculator(trees, lo, (lo+hi)/2, Terrain);

            calcleft.fork();
            ArrayList rightans = calcright.compute();
            ArrayList leftans = calcleft.join();
            rightans.trimToSize();
            leftans.trimToSize();

            leftans.addAll(rightans);

            leftans.trimToSize();
            /*
            try{
                FileWriter writefile = new FileWriter(outputfile, true);
                BufferedWriter writer = new BufferedWriter(writefile);
                if(calcleft.sumall + calcright.sumall > 0) {
                    writer.write(Float.toString(calcleft.sumall + calcright.sumall / trees.length) + "\n" + trees.length + "\n");
                    writer.write(calcleft.finalcommit + "\n" + calcright.finalcommit);
                }
                writer.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{*/
            return leftans;
            //}
        }

    }
}
