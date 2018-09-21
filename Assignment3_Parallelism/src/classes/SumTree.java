package classes;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumTree extends RecursiveTask<Float> {

    static int SEQUENTIAL_CUTOFF = 1000;
    int lo;
    int hi;
    ArrayList<Float> treearr;

    private ForkJoinPool fjPool = new ForkJoinPool();

    public SumTree(ArrayList treearr, int lo, int hi){
        this.treearr = treearr;
        this.lo = lo;
        this.hi = hi;
    }

    public float getSumArr(int type){
        float total = 0;
        if(type == 0)
            total = seriesSum();
        else if(type == 1)
            total = parallelSum();
        return total;
    }

    private float parallelSum(){
        return this.invoke();
    }

    private float seriesSum(){
        float sum = 0;
        for(float tree: treearr){
            sum += tree;
        }
        return sum;
    }

    @Override
    protected Float compute() {
        if(hi - lo < SEQUENTIAL_CUTOFF){
            float sum = 0;
            for(int i = lo; i < hi; i++){
                sum += treearr.get(i);
            }
            return sum;
        }
        else{

            SumTree leftSumTree = new SumTree(treearr, lo, (hi+lo)/2);
            SumTree rightSumTree = new SumTree(treearr, (lo+hi)/2, hi);

            leftSumTree.fork();
            float rightAns = rightSumTree.compute();
            float leftAns = leftSumTree.join();

            return leftAns + rightAns;
        }
    }
}
