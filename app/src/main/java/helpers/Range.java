package helpers;

import java.io.Serializable;

/**
 * Created by Ishibori on 18/09/2017.
 */

public class Range implements Serializable{
    public int Min;
    public int Max;

    public Range(int min, int max){
        this.Min = min;
        this.Max = max;
    }
}
