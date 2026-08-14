/*
 * Copyright 2021 ohayoyogi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kddi.system;

import com.sun.midp.events.EventQueue;
import com.sun.midp.events.EventTypes;
import com.sun.midp.events.NativeEvent;
import com.sun.midp.lcdui.EventConstants;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.UIToolkit;

import org.thenesis.midpath.ui.toolkit.sdl.SDLToolkit;
import org.thenesis.midpath.ui.toolkit.virtual.VirtualToolkit;

import com.sun.midp.configurator.Constants;
import com.sun.midp.events.Event;
import com.sun.midp.events.EventListener;
import com.sun.midp.events.EventMapper;

public class PhoneSystem implements EventListener {

    static {
        EventQueue eventQueue = EventQueue.getEventQueue();
        new PhoneSystem(eventQueue);
    }

    static int keyState = 0;
    EventMapper mapper;

    private PhoneSystem(EventQueue eventQueue) {
        UIToolkit toolkit = UIToolkit.getToolkit();
        if (toolkit instanceof SDLToolkit) {
            throw new IllegalArgumentException("SDLToolkit is not supported");
        }
        mapper = ((VirtualToolkit) toolkit).getEventMapper();
        eventQueue.registerEventListener(EventTypes.KEY_EVENT, this);
    }

    // @Override
    public boolean preprocess(Event event, Event waitingEvent) {
        return true;
    }

    // @Override
    public void process(Event event) {
        NativeEvent nativeEvent = (NativeEvent) event;
        int bit = -1;
        switch (event.getType()) {
            case EventTypes.KEY_EVENT:

                // TODO: handle CLR, RightUp, LeftUp, RightDown, LeftDown
                switch (nativeEvent.intParam2) {
                    case Constants.KEYCODE_UP:
                        bit = 12;
                        break;
                    case Constants.KEYCODE_LEFT:
                        bit = 13;
                        break;
                    case Constants.KEYCODE_RIGHT:
                        bit = 14;
                        break;
                    case Constants.KEYCODE_DOWN:
                        bit = 15;
                        break;
                    case Constants.KEYCODE_SELECT:
                        bit = 16;
                        break;
                    case Canvas.KEY_NUM0:
                    case Canvas.KEY_NUM1:
                    case Canvas.KEY_NUM2:
                    case Canvas.KEY_NUM3:
                    case Canvas.KEY_NUM4:
                    case Canvas.KEY_NUM5:
                    case Canvas.KEY_NUM6:
                    case Canvas.KEY_NUM7:
                    case Canvas.KEY_NUM8:
                    case Canvas.KEY_NUM9:
                        bit = nativeEvent.intParam2 - Canvas.KEY_NUM0;
                        break;
                    case Canvas.KEY_STAR:
                        bit = 10;
                        break;
                    case Canvas.KEY_POUND:
                        bit = 11;
                        break;
                    case EventConstants.SOFT_BUTTON1:
                        bit = 17;
                        break;
                    case EventConstants.SOFT_BUTTON2:
                        bit = 18;
                        break;
                }
                System.out.println("Pushed key: " + Integer.toBinaryString(keyState) + "  " + nativeEvent.intParam2);

                if (bit != -1) {
                    if (nativeEvent.intParam1 == EventConstants.PRESSED) {
                        keyState |= 1 << bit;
                    } else {
                        keyState &= ~(1 << bit);
                    }
                }
                return;
        }

    }

    public static final int NO_SUPPORT = 0x20;
    public static final int ATTR_ONOFF = 0x21;
    public static final int ATTR_PATTERN = 0x22;
    public static final int ATTR_COLOR = 0x23;

    public static final int PATTERN1 = 0x11;
    public static final int PATTERN2 = 0x12;
    public static final int PATTERN3 = 0x13;
    public static final int PATTERN4 = 0x14;
    public static final int PATTERN5 = 0x15;
    public static final int PATTERN6 = 0x16;
    public static final int PATTERN7 = 0x17;
    public static final int PATTERN8 = 0x18;
    public static final int MAX_TIMEOUT = 60000;

    public static void onVibration(int pattern, int timeout) {
        if (timeout < 0) offVibration();
        if (timeout > MAX_TIMEOUT) {

        }
    }

    public static void onVibration(int timeout) {
        onVibration(PATTERN1, timeout);
    }

    public static void offVibration() {
    }

    public static int getVibrationStatus(int attr) {
        return NO_SUPPORT;
    }

    public static int getKeyState(boolean eightDirections) {
        return keyState;
    }

    public static String getID() {
        return "AABB"; // dummy
    }

}