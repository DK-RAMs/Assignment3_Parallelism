package classes;

import main.BotanyAnalyser;

import java.util.concurrent.RecursiveTask;

public class Tree extends RecursiveTask<Float> {


    private float[] canopyArea;
    //private int x1, y1, canopy;
    private int lo, hi;
    private static int SEQUENTIAL_CUTOFF = 20;
    public Calculator calculator;

/*
    private Tree(int x1, int y1, int canopy, int lo, int hi, Calculator calculator){
        //this.x1 = x1;
        //this.y1 = y1;
        //this.canopy = canopy;
        this.lo = lo;
        this.hi = hi;
        this.calculator = calculator;
        //SEQUENTIAL_CUTOFF = canopy;
    }
*/
    private Tree(float[] canopyArea, int lo, int hi, Calculator calculator){
        this.lo = lo;
        this.hi = hi;
        this.canopyArea = canopyArea;
        this.calculator = calculator;
        //SEQUENTIAL_CUTOFF = (int)Math.pow(canopyArea.length, 0.5);
    }

    public Tree(int x1, int y1, int canopy, float[][] Terrain){
        //this.x1 = x1;
        //this.y1 = y1;
        //this.canopy = canopy;
        //lo = 0;
        calculator = new Calculator();
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

    public Tree(int x1, int y1, int canopy, Calculator calculator){
        //this.x1 = x1;
        //this.y1 = y1;
        //this.canopy = canopy;
        //lo = 0;
        //hi = canopy;
        this.calculator = calculator;
    }

    @Override
    protected Float compute() { //No need to time the parallel compute, timing only done in Calculator class
        if(hi - lo < SEQUENTIAL_CUTOFF){
            float ans = 0;
            /*
            for(int i = lo; i < hi; i++){
                if(i > BotanyAnalyser.Terrain.length - 1)
                    break;
                else{
                    for(int j = lo; j < hi; j++){
                        if(j > BotanyAnalyser.Terrain.length - 1)
                            break;
                        else {
                            ans += calculator.Terrain[i][j];
                        }
                    }
                }
            }*/
            for(int i = lo; i < hi; i++){
                ans += canopyArea[i];
            }
            return ans;
        }
        else{
            Tree Treeleft = new Tree(/*x1, y1,*/canopyArea, lo, (hi + lo)/2, calculator);
            Tree Treeright = new Tree(canopyArea, (hi + lo)/2, hi, calculator);

            Treeleft.fork();
            float rightAns = Treeright.compute();
            float leftAns = Treeleft.join();

            return leftAns + rightAns;
        }
    }

    public float sum(int val){
        float returnval;
        if(val == 0)
            returnval = seriesSum();
        else if(val == 1)
            returnval = -1.0f;
        else
            returnval = -1.0f;
        return returnval;
    }

    private float seriesSum(){
        float sum = 0;/*
        for(int x = x1; x < x1 + canopy; x++){
            if(x > Terrain.length - 1)
               break;
            else{
               for(int y = y1; y < y1 + canopy; y++){
                   if(y > Terrain[x].length - 1){
                       break;
                   }
                   else
                       sum += Terrain[x][y];
               }
            }
        }*/

        for(int i = 0; i < canopyArea.length; i++){
            sum += canopyArea[i];
        }

        return sum;
    }
}
