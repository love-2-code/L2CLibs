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

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * A very basic image cache so that when we create 100 frogs or cycle through an animation
 * we only have one shared copy of the bitmap.  No upper cache size for now.
 */

public final class Images {
    private static Map<Integer, Bitmap> mCache = new HashMap<>();
    private static Resources mResources;
    private static int mDefaultId;

    public static void setResources(Resources r) {
        mResources = r;
    }

    public static void setDefaultImage(int defaultId) {
        mDefaultId = defaultId;
    }

    public static Bitmap get(int resourceId) {
        Bitmap bmp = mCache.get(resourceId);
        if (bmp == null) {
            try {
                bmp = BitmapFactory.decodeResource(mResources, resourceId);
            } catch (Exception e) {
                bmp = null;
                Log.d("Images", "decodeResource: " + e.toString());
            }
            if (bmp == null) {
                // Handle exception case or case where decodeResources returns null on its own
                Log.d("Images", "Error decoding id " + resourceId);
                bmp = BitmapFactory.decodeResource(mResources, mDefaultId);
            }
            mCache.put(resourceId, bmp);
        }

        return bmp;
    }
}
