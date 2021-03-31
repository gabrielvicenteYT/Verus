/*
 * Decompiled with CFR 0.150.
 */
package me.levansj01.verus.util.java;

public class Moment {
    private double variance;
    private int n;
    private double kurtosis;
    private double mean;
    private double std;
    private double skewness;

    public double getKurtosis() {
        return this.kurtosis;
    }

    public double getStd() {
        return this.std;
    }

    public double getSkewness() {
        return this.skewness;
    }

    public double getMean() {
        return this.mean;
    }

    public double getVariance() {
        return this.variance;
    }

    public Moment(Iterable<Number> iterable) {
        for (Number number : iterable) {
            ++this.n;
            this.mean += number.doubleValue();
        }
        double d = this.n;
        this.mean /= d;
        double d2 = 0.0;
        double d3 = 0.0;
        for (Number number : iterable) {
            this.variance += Math.pow(this.mean - number.doubleValue(), 2.0);
            d2 += Math.pow(this.mean - number.doubleValue(), 3.0);
            d3 += Math.pow(this.mean - number.doubleValue(), 4.0);
        }
        this.variance /= d;
        this.std = Math.sqrt(this.variance);
        if (d >= 2.0) {
            this.skewness = d / ((d - 1.0) * (d - 2.0)) * (d2 /= this.variance * this.std);
            if (d >= 3.0) {
                double d4 = d * (d + 1.0) / ((d - 1.0) * (d - 2.0) * (d - 3.0));
                double d5 = 3.0 * Math.pow(d - 1.0, 2.0) / ((d - 2.0) * (d - 3.0));
                this.kurtosis = d4 * (d3 /= Math.pow(this.variance, 2.0)) - d5;
            }
        }
    }

    public int getN() {
        return this.n;
    }
}

