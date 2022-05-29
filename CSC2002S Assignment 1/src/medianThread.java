/**
@author Cameron Rebelo RBLCAM001
Thread class for the median filter that recursively splits an array into threads
*/

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class medianThread extends RecursiveAction{
    private int filter;
    private int lo;
    private int hi;
    private int width;
    private float[] arr;
    static final int SEQUENTIAL_CUTOFF = 100;
    private medianFilter mf = new medianFilter();
/**
@param float[] arr = array to be filtered 
@param int lo = start index of array
@param int hi = end index of array
@param int filter = size of filter
constructor for medianThread
*/
    medianThread(float[] arr, int lo, int hi, int filter) {
        this.filter = filter;
        this.arr = arr;
        this.hi = hi;
        this.lo = lo;
        //this.width = ((filter) - 1) / 2;
        this.width = (int) Math.ceil(filter/2);
        
    }
/**
compute method
*/
    protected void compute(){
        if((hi-lo)<SEQUENTIAL_CUTOFF)
        {
            for (int i = lo; i < hi; i++) {
                if(i < width || i > arr.length - width)
                {
                    mf.setOutputArrayElement(i, arr[i]);
                }
                else{
                    float[] temp = Arrays.copyOfRange(arr, i - width, i + 1 + width);
                    mf.setOutputArrayElement(i, median(sort(temp)));
                }
            }
        }
        else 
        {
            medianThread left = new medianThread(arr, lo, ((hi + lo) / 2), filter);
            medianThread right = new medianThread(arr, (hi + lo) / 2, hi, filter);
            left.fork();
            right.fork();
            left.join();
            right.join();
        }

    }
/**
@param float[] arr = array to be sorted
method that sorts an array
@return sorted array
*/
    public float[] sort(float[] arr) {
        Arrays.sort(arr);
        return arr;
    }
/**
@param float[] arr = array to find median off
method to find the median of an array
@return float that is the median value
*/
    public float median(float[] arr) {
        return arr[((filter + 1) / 2) - 1];
    }
}
