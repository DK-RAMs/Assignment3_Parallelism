package classes;

import java.util.concurrent.RecursiveTask;

public class Tree extends RecursiveTask<Float> {
    private float[][] canopyArea;


    public Tree(){

    }

    @Override
    protected Float compute() {
        return null;
    }

    public float average(){

        return 1.0f;
    }

    public float[][] getCanopyArea(){
        return canopyArea;
    }

}
