package classes;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumTree extends RecursiveTask<Float> {

    static int SEQUENTIAL_CUTOFF = 1000000 / Runtime.getRuntime().availableProcessors();
    int lo;
    int hi;
    ArrayList<Float> treearr;

    /**
     * This constructor creates a new SumTree object, setting the parameterized types as the new SumTree object's attributes.
     * @param treearr an ArrayList of floats. Used to calculate the sum of all elements within the ArrayList
     * @param lo a lower bound parameter used for the parallel computation of the sum of all elements in the ArrayList
     * @param hi an upper bound parameter used for the parallel computation of the sum of all elements in the ArrayList
     */
    public SumTree(ArrayList<Float> treearr, int lo, int hi){
        this.treearr = treearr;
        this.lo = lo;
        this.hi = hi;
    }

    /**
     * This method overrides the compute method from this object's parent class, the RecursiveTask<Float> class.
     * This method uses the Java Fork/Join Framework to compute the sum of all elements within this object's ArrayList
     * and returns this sum as a float
     * @return A sum of all elements stored within this ArrayList object
     */
    @Override
    protected Float compute() {
        // Base case where upper bound and lower bound are less than the SEQUENTIAL_CUTOFF. Linearly adds values between lower bounds and upper bounds to the sum in this ArrayList
        // and returns that sum as a float
        if(hi - lo <= SEQUENTIAL_CUTOFF){
            float sum = 0;
            for(int i = lo; i < hi; i++){
                sum += treearr.get(i);
            }
            return sum;
        }
        else{
            // Recursive step which divides larger task into smaller bits and uses Fork/Join framework to calculate return value;
            SumTree leftSumTree = new SumTree(treearr, lo, (hi+lo)/2); // Upper bound decreased
            SumTree rightSumTree = new SumTree(treearr, (lo+hi)/2, hi); // Lower bound increased

            // Parallelization step
            leftSumTree.fork();
            float rightAns = rightSumTree.compute();
            float leftAns = leftSumTree.join();

            return leftAns + rightAns;
        }
    }
}
