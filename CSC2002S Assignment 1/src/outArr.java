/**
@author Cameron Rebelo RBLCAM001
output array class that keeps track of an float array object
*/
public class outArr {
    private float[] output;
/**
@param float[] arr = arr to store output
constructor
*/
    public outArr(float[] arr) {
        this.output = new float[arr.length];
    }
    
/**
getter method
@return float array of class
*/
    public float[] getOutput() {
        return this.output;
    }
/**
@param int index = index of element to be changed
@param float value = value of element to be changed
setter method
*/
    public void setOutput(int index, float value) {
        this.output[index] = value;
    }
}
