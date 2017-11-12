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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * <h1>A scene consisting of a single fixed background image</h1>
 * <p>
 * If you want your game to have a background more interesting than a {@link SolidColorScene},
 * create a new instance of this class with your desired image and pass that to the
 * {@link GameObjectManager#setScene(Scene)} method.
 * </p>
 */

public class BackgroundImageScene extends Scene {
    private Bitmap mImage;
    private GameObjectManager mManager;

    /**
     * Create a new BackgroundImageScene with the given image.
     *
     * @param manager pointer to the current game object manager
     * @param id      resource ID of the image (eg {@code R.raw.background_level1})
     */
    public BackgroundImageScene(GameObjectManager manager, int id) {
        super();
        mManager = manager;
        setImage(id);
    }

    /**
     * Change the image associated with this scene.
     * <p>
     * <p>
     * This can be used sparingly
     * (for example, if you want the "lights" to turn off on a scene so it gets darker at
     * some point) but probably isn't high-preformance enough to use it to make an animated
     * background (ie, don't call setImage every single game update() loop!
     * </p>
     *
     * @param id resource ID of the image (eg {@code R.raw.background_level2})
     */
    public void setImage(int id) {
        mImage = Images.get(id);
    }

    /**
     * Used by the game engine to draw the image on the screen.  Do not call this directly.
     *
     * @param c the canvas object for the current screen
     */
    @Override
    public void draw(Canvas c) {
        Rect src = new Rect(0, 0, mImage.getWidth(), mImage.getHeight());
        Rect cRect = new Rect(0, 0, c.getWidth(), c.getHeight());
        c.drawBitmap(mImage, src, cRect, null);
    }
}
