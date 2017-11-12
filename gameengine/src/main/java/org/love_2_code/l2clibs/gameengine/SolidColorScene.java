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
import android.graphics.Color;
import android.graphics.Paint;

/**
 * <h1>Gives your level a background scene that is all one solid color</h1>
 */
public class SolidColorScene extends Scene {
    private Paint mPaint;

    /**
     * Creates a new SolidColorScene object with the specified color.
     * <p>
     * This object should be passed into the {@link GameObjectManager#setScene(Scene)}
     * method in your GameLevel's setup code.
     * </p>
     *
     * @param color_str a color string of the form "#f29833", for example from Google's color picker
     */
    public SolidColorScene(String color_str) {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor(color_str));
    }

    /**
     * Called by the game engine to draw this background onto the screen each frame.
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);
    }
}
