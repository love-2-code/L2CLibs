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

import android.graphics.RectF;

/**
 * Shapes class for use with the collisions subsystem.
 * <p>
 * Created by Holden Matheson on 6/7/2017.
 */

public class CollisionShape {
    public CollisionVertex[] verticies;
    private CollisionVector[] edges;
    private CollisionVector[] normals;
    private CollisionVector[] axes;
    private int countAxes;
    private boolean goodNormals;
    private boolean goodAxes;


    public CollisionShape(CollisionVertex[] verts) {
        verticies = verts;
        goodNormals = false;
        goodAxes = false;
        calculateEdges();
        calculateNormals();
        calculateAxes();
    }

    /**
     * Builds a CollisionShape from a RectF. Used to test collisions between normal
     * and complex shapes.
     */
    public CollisionShape(RectF boundingRect) {
        double yOffset = boundingRect.height() / 2.0;
        double xOffset = boundingRect.width() / 2.0;
        verticies = new CollisionVertex[]{
                new CollisionVertex(-xOffset, -yOffset),
                new CollisionVertex(xOffset, -yOffset),
                new CollisionVertex(xOffset, yOffset),
                new CollisionVertex(-xOffset, yOffset)};
        goodNormals = false;
        goodAxes = false;
        calculateEdges();
        calculateNormals();
        calculateAxes();
    }

    private void calculateEdges() {
        edges = new CollisionVector[verticies.length];
        for (int i = 0; i < verticies.length - 1; i++) {
            edges[i] = new CollisionVector(verticies[i], verticies[i + 1]);
        }
        edges[verticies.length - 1] = new CollisionVector(verticies[verticies.length - 1], verticies[0]);
    }

    public boolean collideWith(CollisionShape other, double x, double y, double other_x, double other_y) {
        CollisionVector overlap_axis;
        double overlap = 1000000;  // overlap in pixels
        CollisionProjection projection, other_projection;
//        Log.d("SAT", "#####Projecting onto host axes...");
        for (CollisionVector axis : axes) {
            projection = projectOnto(axis, x, y);
            other_projection = other.projectOnto(axis, other_x, other_y);
            if (!projection.overlaps(other_projection)) {
                return false;
            }
//            else{
//                double o = projection.getOverlap(other_projection);
//                if (o < overlap){
//                    overlap_axis = axis;
//                    overlap = o;
//                }
//            }
        }
//        Log.d("SAT", "#####Projecting onto other axes...");
        for (CollisionVector axis : other.axes) {
            projection = projectOnto(axis, x, y);
            other_projection = other.projectOnto(axis, other_x, other_y);
            if (!projection.overlaps(other_projection)) {
                return false;
            }
        }
        return true;
    }

    // Currently returns perpendiculars, not true normals
    private void calculateNormals() {
        if (!goodNormals) {
            normals = new CollisionVector[edges.length];
            for (int i = 0; i < edges.length; i++) {
                normals[i] = edges[i].perpendicular();
            }

            goodNormals = true;
        }
    }

    private void calculateAxes() {
        boolean duplicate;
        if (!goodAxes) {
            axes = new CollisionVector[normals.length];
            countAxes = 0;
            for (int i = 0; i < normals.length; i++) {
                duplicate = false;
                for (int j = 0; j < countAxes; j++) {
                    if (normals[i].isParallel(axes[j])) {
                        duplicate = true;
                    }
                }
                if (!duplicate) {
                    axes[countAxes] = normals[i];
                    countAxes++;
                }
            }
            goodAxes = true;
        }
    }

    public CollisionProjection projectOnto(CollisionVector axis, double offset_x, double offset_y) {
        double min, max;
        min = max = verticies[0].projectOnto(axis, offset_x, offset_y);
        for (CollisionVertex vert : verticies) {
            double p = vert.projectOnto(axis, offset_x, offset_y);
            if (p < min) {
                min = p;
            } else if (p > max) {
                max = p;
            }
        }
        return new CollisionProjection(min, max);
    }
}
