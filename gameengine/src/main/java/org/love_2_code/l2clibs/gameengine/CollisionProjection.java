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

import android.util.Log;

/**
 * Projections class for use with the collisions subsystem.
 * <p>
 * Created by Holden Matheson on 6/8/2017.
 */

public class CollisionProjection {
    double min;
    double max;

    public CollisionProjection(double i, double j) {
        min = i;
        max = j;
    }

    public boolean overlaps(CollisionProjection other) {
        if (isBetween(min, other.min, other.max) ||
                isBetween(max, other.min, other.max) ||
                isBetween(other.min, min, max) ||
                isBetween(other.max, min, max)) {
            return true;
        }
        Log.d("SAT", "Separating axis found, no collision!");
        return false;
    }

    public static boolean isBetween(double value, double min, double max) {
        return ((value > min) && (value < max));
    }
}
