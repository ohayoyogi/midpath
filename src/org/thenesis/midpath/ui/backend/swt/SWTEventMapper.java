/*
 * MIDPath - Copyright (C) 2006 Guillaume Legris, Mathieu Legris
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation. 
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details. 
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA 
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa
 * Clara, CA 95054 or visit www.sun.com if you need additional
 * information or have any questions. 
 */
package org.thenesis.midpath.ui.backend.swt;

import java.awt.event.KeyEvent;

import javax.microedition.lcdui.Canvas;

import com.sun.midp.configurator.Constants;
import com.sun.midp.events.EventMapper;
import com.sun.midp.lcdui.EventConstants;

public class SWTEventMapper implements EventMapper {

	public int getGameAction(int keyCode) {
		switch (keyCode) {
		case Constants.KEYCODE_DOWN:
			return Canvas.DOWN;
		case Constants.KEYCODE_SELECT:
			return Canvas.FIRE;
		case KeyEvent.VK_A:
			return Canvas.GAME_A;
		case KeyEvent.VK_B:
			return Canvas.GAME_B;
		case KeyEvent.VK_C:
			return Canvas.GAME_C;
		case KeyEvent.VK_D:
			return Canvas.GAME_D;
		case Constants.KEYCODE_LEFT:
			return Canvas.LEFT;
		case Constants.KEYCODE_RIGHT:
			return Canvas.RIGHT;
		case Constants.KEYCODE_UP:
			return Canvas.UP;
		default:
			return -1;
		}
	}

	public int getKeyCode(int gameAction) {
		switch (gameAction) {
		case Canvas.DOWN:
			return Constants.KEYCODE_DOWN;
		case Canvas.FIRE:
			return Constants.KEYCODE_SELECT;
		case Canvas.GAME_A:
			return KeyEvent.VK_A;
		case Canvas.GAME_B:
			return KeyEvent.VK_B;
		case Canvas.GAME_C:
			return KeyEvent.VK_C;
		case Canvas.GAME_D:
			return KeyEvent.VK_D;
		case Canvas.LEFT:
			return Constants.KEYCODE_LEFT;
		case Canvas.RIGHT:
			return Constants.KEYCODE_RIGHT;
		case Canvas.UP:
			return Constants.KEYCODE_UP;
		default:
			return 0;
		}
	}

	public String getKeyName(int keyCode) {
		return KeyEvent.getKeyText(keyCode);
	}

	public int getSystemKey(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_BACK_SPACE:
		case KeyEvent.VK_DELETE:
			return EventConstants.SYSTEM_KEY_CLEAR;
		case KeyEvent.VK_END:
			return EventConstants.SYSTEM_KEY_END;
		case KeyEvent.VK_F12:
			return EventConstants.SYSTEM_KEY_POWER;
		case KeyEvent.VK_ENTER:
			return EventConstants.SYSTEM_KEY_SEND;
		default:
			return 0;
		}
	}

	static int mapToInternalEvent(int keyCode, char c) {
		switch (keyCode) {
		case KeyEvent.VK_DOWN:
			return Constants.KEYCODE_DOWN;
		case KeyEvent.VK_LEFT:
			return Constants.KEYCODE_LEFT;
		case KeyEvent.VK_RIGHT:
			return Constants.KEYCODE_RIGHT;
		case KeyEvent.VK_ENTER:
			return Constants.KEYCODE_SELECT;
		case KeyEvent.VK_UP:
			return Constants.KEYCODE_UP;
		case KeyEvent.VK_F1:
			return EventConstants.SOFT_BUTTON1;
		case KeyEvent.VK_F2:
			return EventConstants.SOFT_BUTTON2;

		case KeyEvent.VK_1:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM1;
			}
			return 0;
		case KeyEvent.VK_2:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM2;
			}
			return 0;
		case KeyEvent.VK_3:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM3;
			}
			return 0;
		case KeyEvent.VK_4:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM4;
			}
			return 0;
		case KeyEvent.VK_5:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM5;
			}
			return 0;
		case KeyEvent.VK_6:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM6;
			}
			return 0;
		case KeyEvent.VK_7:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM7;
			}
			return 0;
		case KeyEvent.VK_8:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM8;
			}
			return 0;
		case KeyEvent.VK_9:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM9;
			}
			return 0;
		case KeyEvent.VK_0:
			if (Character.isDigit(c)) {
				return Canvas.KEY_NUM0;
			}
			return 0;
		case KeyEvent.VK_MULTIPLY:
		case KeyEvent.VK_ASTERISK:
			return Canvas.KEY_STAR;
		case KeyEvent.VK_NUMBER_SIGN:
			return Canvas.KEY_POUND;
		case KeyEvent.VK_BACK_SPACE:
		case KeyEvent.VK_DELETE:
			return KeyEvent.VK_DELETE;
		case KeyEvent.VK_END:
			return KeyEvent.VK_END;
		case KeyEvent.VK_F12:
			return KeyEvent.VK_F12;
		default:
			return 0;
			//return keyCode;
		}
	}

}