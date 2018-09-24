package classes;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Tree extends RecursiveTask<Float> {


    private float[] canopyArea;
    private int lo, hi;
    private static int SEQUENTIAL_CUTOFF = 5000;

    /**
     * This private constructor is solely used in the compute() method of this object. This creates a new Tree object
     * with the parameterized values as the new object's attributes. This constructor plays a role in the parallelising
     * of the sum calculation of all elements in the canopyArea array.
     * @param canopyArea array of hours of sunlight that is exposed to this tree
     * @param lo low value cutoff for parallelism
     * @param hi high value cutoff for parallelism
     */
    private Tree(float[] canopyArea, int lo, int hi){
        this.lo = lo;
        this.hi = hi;
        this.canopyArea = canopyArea;
    }

    /**
     * This public constructor is used to create new Tree objects that can be used
     *
     * @param x1 top-left x value of the Tree object, this parameter isn't stored
     *           in the object's memory. Solely used for the mapping of values in the
     *           canopyArea array.
     * @param y1 top-left y value of the Tree object, this parameter isn't stored
     *           in the object's memory. Solely used for mapping of values in the
     *           canopyArea array.
     * @param canopy length and width of tree object. This parameter isn't stored
     *               in memory and is used to set the size of the canopyArea array
     *               and also aids in mapping of values in canopyArea array.
     * @param Terrain 2D array that stores sunlight exposure values at specific points
     *                in the map.
     */
    public Tree(int x1, int y1, int canopy, float[][] Terrain){
        canopyArea = new float[canopy*canopy];
        int arrpos = 0;
        for(int x = x1; x < x1 + canopy; x++){
            if(x > Terrain.length - 1){
                break;
            }
            else{
                for(int y = y1; y < y1 + canopy; y++) {
                if(y > Terrain[x].length - 1)
                    break;
                else{
                    float a = Terrain[x][y];
                    canopyArea[arrpos] = a;
                    arrpos++;
                }
                }
            }
        }
        hi = canopyArea.length;
    }

    /**
     * This method overrides the compute method from this object's parent class, the RecursiveTask<Float> class.
     * This is a method that uses the Java Fork/Join Framework to compute the sum of all elements in this object's
     * canopyArea array.
     * @return Float which represents the total sun exposure (in hours) that has fell upon this Tree object.
     */
    @Override
    protected Float compute(){
        // Base case where Upper bound and Lower bound are less than the SEQUENTIAL_CUTOFF. Sequentially adds subset of canopyArea to ans and returns ans
        if(hi - lo <= SEQUENTIAL_CUTOFF){
            float ans = 0;
            for(int i = lo; i < hi; i++){
                ans += canopyArea[i];
            }
            return ans;
        }
        else{
            // Recursive step
            Tree Treeleft = new Tree(canopyArea, lo, (hi + lo)/2);
            Tree Treeright = new Tree(canopyArea, (hi + lo)/2, hi);

            Treeleft.fork();
            float rightAns = Treeright.compute();
            float leftAns = Treeleft.join();

            return leftAns + rightAns;
        }
    }

    /**
     * This method uses the parameterized val integer to determine which kind of sum operation is ran
     * @param val Value which is used to determine which kind of sum operation is ran (0 for serial operation, 1 for parallel operation, returns -1 for other vals)
     * @return Returns a float after specified operation is ran
     */
    public float sum(int val){
        float returnval;
        if(val == 0)
            returnval = seriesSum();
        else if(val == 1)
            returnval = new ForkJoinPool().invoke(this);
        else
            returnval = -1.0f;
        return returnval;
    }

    /**
     * This private method runs a serial algorithm that sums all values within the canopyArea and returns the sum.
     * @return Returns sum of all values in canopyArea.
     */
    private float seriesSum(){
        float sum = 0;

        for(int i = 0; i < canopyArea.length; i++){
            sum += canopyArea[i];
        }
        return sum;
    }
}
