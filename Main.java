package com.company;

public class Main {

    public static double log(double num, double exp) {
        return Math.log(num) / Math.log(exp);
    }

    public static double[] getDispersion(double[] scale1, double[] scale2, double exp) {
        double dispersionExp = 0;
        double dispersionLog = 0;

        if(scale1.length == scale2.length)
            for (int i = 0; i < scale1.length - 2; i++) {
                double p1 =Math.pow(scale1[i], exp) / scale2[i];
                double p2 = Math.pow(scale1[i + 1], exp) / scale2[i + 1];

                dispersionExp += Math.abs(p1/p2 - 1);

                if(exp <= 0) continue;

                p1 = log(scale1[i], exp) / scale2[i];
                p2 = log(scale1[i + 1], exp) / scale2[i + 1];

                dispersionLog += Math.abs(p1/p2 - 1);

            }

        return new double[] {dispersionExp, dispersionLog};
    }

    public static void main(String[] args) {


        double min = -10;
        double max = 100;
        double inc = 0.0001;
        int pixels = 10000;
        double[] scale1 = {12, 64, 132, 184, 241, 309, 363, 423, 487, 536, 592};
        double[] scale2 = {0.0001, 0.0002, 0.0005, 0.001, 0.002, 0.005, 0.01, 0.02, 0.05, 0.1, 0.2};
        double[] dispersion = getDispersion(scale1, scale2, min);
        double bestExp[] = {min, min};
        int bestPixel[] = {0, 0};

        for (int addPixels = 0; addPixels < pixels; addPixels++) {

            System.out.print("\r" + (addPixels * 100 / pixels) + "%");

            for (int i = 0; i < scale1.length;i++)
                scale1[i] = scale1[i] + 1;

            for(double exp = min + inc; exp <= max; exp += inc) {
                double[] temp = getDispersion(scale1, scale2, exp);
                if(temp[0] < dispersion[0]) {
                    dispersion[0] = temp[0];
                    bestExp[0] = exp;
                    bestPixel[0] = addPixels;
                }
                if(temp[1] < dispersion[1]) {
                    dispersion[1] = temp[1];
                    bestExp[1] = exp;
                    bestPixel[1] = addPixels;
                }
            }
        }


        System.out.println(
                "\nDispersion: " + dispersion[0] + " " + dispersion[1] + "\n"
                + "Exp: " + bestExp[0] + " " + bestExp[1] + "\n"
                + "Added:" + bestPixel[0] + " " + bestPixel[1]
        );
    }



}
