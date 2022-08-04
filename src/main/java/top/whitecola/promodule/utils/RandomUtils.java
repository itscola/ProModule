package top.whitecola.promodule.utils;

import java.security.SecureRandom;

public class RandomUtils {
    private static final SecureRandom random = new SecureRandom();

    public static double nextDouble(double min,double max){
        return  max + (min - max) * random.nextDouble();
    }
}
