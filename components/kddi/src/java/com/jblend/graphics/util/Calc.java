package com.jblend.graphics.util;

public final class Calc {
    /**
     * Calculate sqrt.
     * @param x 4096 * X
     * @return 4096 * sqrt(X) = 64 * sqrt(4096 * X)
     */
    public static final int sqrt(int x) {
        return (int)Math.sqrt((double)x) << 6;
    }

    public static final int sin(int a) {
        double rad = (2.0 * Math.PI * a) / 4096;
        return (int)(Math.sin(rad)) << 12;
    }

    public static final int cos(int a) {
        double rad = (2.0 * Math.PI * a) / 4096;
        return (int)(Math.cos(rad)) << 12;
    }
}
