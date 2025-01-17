/*
 * Copyright (c) 2019 - 2023  Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.signal;

import com.github.psambit9791.jdsp.misc.UtilMethods;

/**
 * <h2>Cross-Correlation</h2>
 * The Cross-Correlation class implements
 * correlation as provided in numpy <a href="https://docs.scipy.org/doc/numpy/reference/generated/numpy.correlate.html">correlate()</a>
 * function.
 *  
 *
 * @author  Sambit Paul
 * @version 1.1
 */

public class CrossCorrelation {

    private double[] signal;
    private double[] kernel;
    private double[] output;
    private boolean autocorr;

    /**
     * This constructor initialises the prerequisites required to perform cross-correlation.
     * @param signal Signal to be convolved
     * @param window Kernel for convolution
     */
    public CrossCorrelation(double[] signal, double[] window) {
        this.signal = signal;
        this.kernel = window;
        this.autocorr = false;
    }

    /**
     * This constructor initialises the prerequisites required to perform autocorrelation
     * @param s Signal to be convolved
     */
    public CrossCorrelation(double[] s) {
        this.signal = s;
        this.kernel = s;
        this.autocorr = true;
    }

    /**
     * This is the default cross-correlation procedure which works in "valid" mode.
     * @return double[] The result of correlation.
     */
    public double[] crossCorrelate() {
        //Works in "valid"/"full" mode depending on auto-correlation state
        String mode = "valid";
        if (autocorr) {
            mode = "full";
        }
        return this.crossCorrelate(mode);
    }

    /**
     * This is the discrete linear convolution procedure which works in the specified mode.
     * @param mode Mode in which correlation will work. Can be 'full', 'same' or 'valid'
     * @return double[] Result of cross-correlation.
     */
    public double[] crossCorrelate(String mode) {
        this.kernel = UtilMethods.reverse(this.kernel);
        Convolution c1 = new Convolution(this.signal, this.kernel);
        this.output = c1.convolve(mode);
        return this.output;
    }

    /**
     * Performs fast cross-correlation of the signal and kernel using the FFT method with the default mode 'full'.
     *
     * @return double[] Result of cross-correlation.
     */
    public double[] fastCrossCorrelate() {
        //Works in "valid"/"full" mode depending on auto-correlation state
        String mode = "valid";
        if (autocorr) {
            mode = "full";
        }
        return this.fastCrossCorrelate(mode);
    }

    /**
     * Performs fast cross-correlation of the signal and kernel using the FFT method in the specified mode.
     *
     * @param mode Mode in which cross-correlation will work. Can be 'full', 'same' or 'valid'.
     * @return double[] Result of cross-correlation.
     */
    public double[] fastCrossCorrelate(String mode) {
        this.kernel = UtilMethods.reverse(this.kernel);
        Convolution c1 = new Convolution(this.signal, this.kernel);
        this.output = c1.fastConvolve(mode);
        return this.output;
    }
}
