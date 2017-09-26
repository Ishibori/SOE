package utils;

import java.util.Random;

/**
 * Created by Ishibori on 07/09/2017.
 */

public class DiceUtils {
    public static Random RANDOM_GENERATOR = new Random();

    public static int getSingleDiceRoll(int minVal, int maxVal)
    {
        int val = RANDOM_GENERATOR.nextInt(maxVal+1);
        val = val < minVal? minVal : val;

        return val;
    }
}
