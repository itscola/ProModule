package top.whitecola.promodule.utils;

import java.security.SecureRandom;

public class RandomUtils {
    private static final SecureRandom random = new SecureRandom();

    public static float nextFloat(float min,float max){
        return min+ random.nextFloat()*(max-min+1);
    }
}
