package classes;

import java.util.ArrayList;
import java.util.concurrent.*;

public class Calculator extends RecursiveTask<ArrayList>{

    int lo;
    int hi;
    public float startTime;
    static int SEQUENTIAL_CUTOFF = 1000000 / Runtime.getRuntime().availableProcessors();
    private static Tree[] trees;

    /**
     * Public constructor that creates a new Calculator object with parameterized values as it's attributes
     * @param trees an array of Tree objects
     * @param lo lower bound used for parallel algorithm
     * @param hi upper bound used for parallel algorithm
     */
    public Calculator(Tree[] trees, int lo, int hi){
        this.trees = trees;
        this.lo = lo;
        this.hi = hi;
    }


    /**
     * Uses a serial implementation to return an arraylist of sum of all data items
     * @return An ArrayList of all the sums of individual trees within this trees array
     */
    public ArrayList<Float> seriesCalculate(){
            ArrayList<Float> treeans = new ArrayList<>();
            // Loops through every array element in the trees array and adds the serialsum of all the elements in the tree's canopyArea array
            for(int i = 0; i < trees.length; i++){
                treeans.add(trees[i].sum(0));
            }
            treeans.trimToSize();
            return treeans;
    }


    /**
     * Uses the Fork/Join framework to return an array of float elements. These float elements are computed by
     * invoking tree objects with this calculator's ForkJoinPool within the stored tree array and runs the operations
     * in parallel.
     * @return An array of float values.
     */
    @Override
    protected ArrayList<Float> compute() {
        // Base case for solving the values. Invokes tree objects between lo and hi
        if(hi - lo <= SEQUENTIAL_CUTOFF){
            ArrayList<Float> treevals = new ArrayList<>();
            for(int i = lo; i < hi; i++){
                treevals.add(trees[i].sum(0));
            }
            treevals.trimToSize();
            return treevals;
        }
        else{
            // Recursive step of dividing the larger data into smaller bits and returns a combined resultant ArrayList
            Calculator calcright = new Calculator(trees, (lo + hi)/2, hi);
            Calculator calcleft = new Calculator(trees, lo, (lo+hi)/2);
            // Parallelization step
            calcleft.fork();
            ArrayList<Float> rightans = calcright.compute();
            ArrayList leftans = calcleft.join();
            /*
            // Both arrays are trimmed down to their size to save space
            rightans.trimToSize();
            leftans.trimToSize();
            // resultant arrays are combined into one net array that
            */
            leftans.addAll(rightans);

            leftans.trimToSize();
            return leftans;
        }

    }
}
