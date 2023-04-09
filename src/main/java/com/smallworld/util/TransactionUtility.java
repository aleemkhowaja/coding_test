package com.smallworld.util;

import java.text.DecimalFormat;

public class TransactionUtility {
    public static double formatDoubleValue(double value) {
        DecimalFormat df2 = new DecimalFormat("#.00");
        return Double.valueOf(df2.format(value));
    }
}
