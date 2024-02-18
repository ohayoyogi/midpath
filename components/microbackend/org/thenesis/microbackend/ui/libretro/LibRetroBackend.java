/**
 * Copyright (C) 2024 ohayoyogi
 */
package org.thenesis.microbackend.ui.libretro;

import java.io.IOException;

import org.ohayoyogi.libretro.LibRetroBridge;
import org.ohayoyogi.libretro.LibRetroEventListener;
import org.thenesis.microbackend.ui.BackendEventListener;
import org.thenesis.microbackend.ui.Configuration;
import org.thenesis.microbackend.ui.KeyConstants;
import org.thenesis.microbackend.ui.Logging;
import org.thenesis.microbackend.ui.NullBackendEventListener;
import org.thenesis.microbackend.ui.UIBackend;

public class LibRetroBackend implements UIBackend, LibRetroEventListener {

    private LibRetroBridge libretro;
    private BackendEventListener listener = new NullBackendEventListener();

    private int canvasWidth;
    private int canvasHeight;


    public LibRetroBackend() {
        libretro = LibRetroBridge.getInstance();
    }

    // @Override
    public void configure(Configuration conf, int width, int height) {
        canvasWidth = width;
        canvasHeight = height;
    }

    // @Override
    public void setBackendEventListener(BackendEventListener listener) {
        this.listener = listener;
    }

    // @Override
    public void open() throws IOException {
        // initialize 
        libretro.setEventListener(this);
    }

    // @Override
    public void close() {
        // uninitialize
        libretro.unsetEventListener();
    }

    // @Override
    public void updateARGBPixels(int[] argbPixels, int x, int y, int widht, int heigth) {
        if (Logging.TRACE_ENABLED)
            System.out.println("[DEBUG] LibRetroBackend.updateARGBPixels: x=" + x + " y=" + y + " widht=" + widht + " heigth=" + heigth);

        libretro.refreshVideo(argbPixels, x, y, widht, heigth);
    }

    // @Override
    public int getWidth() {
        return canvasWidth;
    }

    // @Override
    public int getHeight() {
        return canvasHeight;
    }

    // @Override
    public void onKeyPressed(int keycode) {
        char c = (char)0;
        int modifiers = 0;
        listener.keyPressed(convertKeyCode(keycode), c, modifiers);
    }

    // @Override
    public void onKeyReleased(int keycode) {
        char c = (char)0;
        int modifiers = 0;
        listener.keyReleased(convertKeyCode(keycode), c, modifiers);
    }

    // @Override
    public void onUnload() {
        listener.windowClosed();
    }

    public static int convertKeyCode(int keyCode) {
        // FixME
        switch (keyCode) {
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_A:
                return KeyConstants.VK_1;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_B:
                return KeyConstants.VK_3;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_X:
                return KeyConstants.VK_9;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_Y:
                return KeyConstants.VK_6;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_UP:
                return KeyConstants.VK_UP;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_DOWN:
                return KeyConstants.VK_DOWN;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_LEFT:
                return KeyConstants.VK_LEFT;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_RIGHT:
                return KeyConstants.VK_RIGHT;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_R:
                return KeyConstants.VK_F2;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_L:
                return KeyConstants.VK_F1;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_START:
                return KeyConstants.VK_F2;
            case LibRetroConstants.RETRO_DEVICE_ID_JOYPAD_SELECT:
                return KeyConstants.VK_F1;
            default:
                return KeyConstants.VK_UNDEFINED;
        }
    }
}
