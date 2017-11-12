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

import static java.lang.Math.abs;

/**
 * Vector class for use with the collisions subsystem.
 * <p>
 * Created by Holden Matheson on 6/7/2017.
 */

public class CollisionVector {
    private final double epsilon = 0.001;

    double x;
    double y;

    public CollisionVector(double i, double j) {
        x = i;
        y = j;
    }

    public CollisionVector(CollisionVertex i, CollisionVertex j) {
        x = j.x - i.x;
        y = j.y - i.y;
    }

    public CollisionVector perpendicular() {
        return new CollisionVector(y, -x);
    }

    public boolean isParallel(CollisionVector other) {
        double pz = x * other.y - y * other.x;
        return abs(pz) < epsilon;
    }
}