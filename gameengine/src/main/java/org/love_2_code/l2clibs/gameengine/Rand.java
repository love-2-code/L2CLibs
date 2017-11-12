//   MIT License
//
//   Copyright (c) 2017 Love-2-Code.org
//
//   Permission is hereby granted, free of charge, to any person obtaining a copy
//   of this software and associated documentation files (the "Software"), to deal
//   in the Software without restriction, including without limitation the rights
//   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//   copies of the Software, and to permit persons to whom the Software is
//   furnished to do so, subject to the following conditions:
//
//   The above copyright notice and this permission notice shall be included in all
//   copies or substantial portions of the Software.
//
//   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//   SOFTWARE.

package org.love_2_code.l2clibs.gameengine;

import java.util.Random;

/**
 * <h1>Helper functions for using random numbers in your game</h1>
 */

final public class Rand {
    final private static String TAG = "Rand";
    final private static int FPS = 60;

    private static Random mRand = new Random();

    /**
     * Returns a random number between {@code min} and {@code max}
     * <p>
     * Note that min and max can be negative, but max must be greater than min.
     * </p>
     *
     * @param min the minimum number to return
     * @param max the maximum number to return
     * @return integer in the range min..max
     */
    public static int between(int min, int max) {
        return min + mRand.nextInt(max - min + 1);
    }

    /**
     * Randomly returns true approximately once every {@code seconds} seconds.
     *
     * @param seconds the average time between events returning true
     * @return usually false, but true randomly every {@code seconds}
     */
    public static boolean onceEvery(float seconds) {
        int ticks = (int) (seconds * FPS);
        int prob = mRand.nextInt(ticks);
        return prob == 0;
    }
}
