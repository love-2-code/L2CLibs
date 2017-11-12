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

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Holden Matheson on 3/1/2017.
 * <p>
 * Basic text object
 */

public class Text extends GameObject {
    private String text = "";
    private Paint paint = new Paint();

    public Text(String name, RectF extent) {
        super(name, extent);
        this.paint.setARGB(255, 0, 0, 0);
        this.paint.setTextSize(100);
    }

    public Text(String name, String text, float centerX, float centerY, float width, float height) {
        super(name, new RectF(centerX - width / 2, centerY - height / 2,
                centerX + width / 2, centerY + height / 2));
        this.text = text;
        this.paint.setARGB(255, 0, 0, 0);
        this.paint.setTextSize(100);
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets the color of the text
     *
     * @param RGB Hex string for the color (eg. FF2100)
     **/
    public void setHexColor(String RGB) {
        int r, g, b;
        r = Integer.parseInt(RGB.substring(0, 2), 16);
        g = Integer.parseInt(RGB.substring(2, 4), 16);
        b = Integer.parseInt(RGB.substring(4, 6), 16);

        paint.setARGB(this.paint.getAlpha(), r, g, b);
    }

    /**
     * Sets the transparency of the test
     *
     * @param num integer from 0 (invisible) to 255 (solid)
     */
    public void setTransparency(int num) {
        this.paint.setAlpha(num);
    }

    /**
     * Sets the text size
     *
     * @param scale Size of the text (default is 100)
     */
    public void setSize(float scale) {
        this.paint.setTextSize(scale);
    }

    public void copyPaint(Text textToCopy) {
        this.paint = textToCopy.paint;
    }

    @Override
    public void draw(Canvas c, float xScale, float yScale) {
        c.drawText(this.text, boundingRect.left, boundingRect.bottom, paint);
    }
}
