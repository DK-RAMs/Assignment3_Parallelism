package classes;

import java.util.concurrent.RecursiveTask;

public class Tree extends RecursiveTask<Float> {


    private float[][] canopyArea;
    

    public Tree(float[][] Terrain, int x1, int y1, int canopy){
        canopyArea = new float[canopy][canopy];
        int xincrement = 0;
        for(int x = x1; x < canopy + x1; x++){
            int yincrement = 0;
            for(int y = y1; y < canopy + y1; y++){
                if(x < Terrain.length && y < Terrain[x].length && xincrement < canopy && yincrement < canopy){
                    float terval = Terrain[x][y];
                    canopyArea[xincrement][yincrement] = terval;
                    yincrement++;
                }
                else{
                    canopyArea[xincrement][yincrement] = 0.0f;
                }
            }
            xincrement++;
        }
    }

    @Override
    protected Float compute() {
        return null;
    }

    public float sum(int val){
        float returnval;
        if(val == 0)
            returnval = seriesSum();
        else if(val == 1)
            returnval = parallelSum();
        else
            returnval = -1.0f;
        return returnval;
    }

    private float seriesSum(){
        int numvals = 0;
        float sum = 0;
        for(int i = 0; i < canopyArea.length; i++){
            for(int j = 0; j < canopyArea[i].length; j++){
                if(canopyArea[i][j] >= 0.0f){
                    sum += canopyArea[i][j];
                    numvals++;
                }
            }
        }
        return sum;
    }

    private float parallelSum(){
        // this will compute the average of all the canopy area's tree space in a parallel format using a Divide-and-Conquer strategy
        return 1.0f;
    }

}
