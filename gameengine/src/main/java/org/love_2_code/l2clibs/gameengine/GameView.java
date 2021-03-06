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

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * <h1>[internal] Android User Interface widget that provides the space for the game to draw on</h1>
 * <p>
 * This widget is responsible for starting and stopping the game's background
 * worker thread when the view is visible and active, and for receiving, packaging up, and
 * queueing UI events for the worker thread.
 * </p>
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    final private String TAG = "GameView";
    private GestureDetectorCompat mDetector;
    private IRedrawService mRedrawService;
    private IGameLogicService mGameLogicService;
    private GameViewThread mGameViewThread;

    public interface IRedrawService {
        void draw(Canvas canvas);
    }

    public interface IGameLogicService extends IMessageClient {
        void onMotionEvent(UIEvent e);

        void update(int millis);
    }

    public GameView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        getHolder().addCallback(this);

        mDetector = new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                mGameViewThread.queueEvent(new UIEvent(UIEventType.Down, e));
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                mGameViewThread.queueEvent(new UIEvent(UIEventType.ShowPress, e));
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                mGameViewThread.queueEvent(new UIEvent(UIEventType.SingleTapUp, e));
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                mGameViewThread.queueEvent(new UIEvent(UIEventType.Scroll, e1, e2, distanceX, distanceY));
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                mGameViewThread.queueEvent(new UIEvent(UIEventType.LongPress, e));
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                mGameViewThread.queueEvent(new UIEvent(UIEventType.Fling, e1, e2, velocityX, velocityY));
                return true;
            }
        });
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) {
            mGameViewThread.queueEvent(new UIEvent(UIEventType.ButtonUp, event.getKeyCode()));
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) {
            mGameViewThread.queueEvent(new UIEvent(UIEventType.ButtonDown, event.getKeyCode()));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK) {
            mGameViewThread.queueEvent(new UIEvent(UIEventType.Joystick, event));
            return true;
        }
        return super.onGenericMotionEvent(event);
    }


    public void setRedrawService(IRedrawService rs) {
        mRedrawService = rs;
    }

    public void setGameLogicService(IGameLogicService gs) {
        mGameLogicService = gs;
    }


    public void onResume() {

    }

    public void onPause() {
        if (mGameViewThread != null) {
            mGameViewThread.gracefulStop();
            mGameViewThread = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Don't actually care about creation; only surfaceChanged when the surface is ready to go
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mGameViewThread != null) {
            mGameViewThread.gracefulStop();
            mGameViewThread = null;
        }
        if (holder != null) {
            Log.d(TAG, "surfaceChanged, launching thread");
            mGameViewThread = new GameViewThread(holder, mGameLogicService, mRedrawService);
            if (width == 0 || height == 0) {
                Log.e(TAG, "Illegal width/height: " + width + ", " + height);
            } else {
                mGameViewThread.setEventScalingFactors(1.0f / width, 1.0f / height);
            }
            mGameViewThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mGameViewThread != null) {
            mGameViewThread.gracefulStop();
            mGameViewThread = null;
        }
    }

    public enum UIEventType {
        Down, ShowPress, SingleTapUp, Scroll, LongPress, Fling, Joystick, ButtonDown, ButtonUp,
    }

    public class UIEvent {
        UIEvent(UIEventType type, MotionEvent event1) {
            this.type = type;
            this.event1 = event1;
        }

        UIEvent(UIEventType type, int keyCode) {
            this.type = type;
            this.keyCode = keyCode;
        }

        UIEvent(UIEventType type, MotionEvent event1, MotionEvent event2, float dx, float dy) {
            this.type = type;
            this.event1 = event1;
            this.event2 = event2;
            this.dx = dx;
            this.dy = dy;
        }

        UIEventType type;
        public MotionEvent event1, event2;
        public float dx, dy;        // Holds Velocity for flings, distance for scrolls
        int keyCode;
    }
}

