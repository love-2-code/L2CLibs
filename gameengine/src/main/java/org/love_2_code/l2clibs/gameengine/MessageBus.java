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

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>[internal] Originally the MessageBus was a central part of the game engine's design, but
 * it hasn't proven terribly useful and may be going away.</h1>
 */

public final class MessageBus {
    private List<IMessageClient> mClients;

    public MessageBus() {
        mClients = new ArrayList<>();
    }

    public void addClient(IMessageClient client) {
        mClients.add(client);
    }

    public void postMessage(Message msg) {
        if (!msg.type.equals("update_redraw")) {
            Log.d("msg", msg.type + " (" + msg.x + "," + msg.y + ")");
        }
        for (IMessageClient client : mClients) {
            client.handleMessage(msg);
        }
    }
}
