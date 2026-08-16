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
        /* CHAR_UNDEFINED, not zero: GenericEventMapper returns the character when there
         * is one and the key code otherwise, so (char)0 turned every key it has no rule
         * for into 0. These events come from buttons, not a keyboard. */
        char c = KeyConstants.CHAR_UNDEFINED;
        int modifiers = 0;
        listener.keyPressed(convertKeyCode(keycode), c, modifiers);
    }

    // @Override
    public void onKeyReleased(int keycode) {
        /* CHAR_UNDEFINED, as in onKeyPressed(). */
        char c = KeyConstants.CHAR_UNDEFINED;
        int modifiers = 0;
        listener.keyReleased(convertKeyCode(keycode), c, modifiers);
    }

    // @Override
    public void onUnload() {
        listener.windowClosed();
    }

    /**
     * The libretro core already sends KeyConstants.VK_* codes, so nothing is translated here.
     *
     * Which physical button produces which key is decided by the core, which owns the
     * hardware and also drives any on-screen keypad; a touched key and a pressed button
     * arrive here as the same kind of value. Turning a VK code into a MIDP key code, a game
     * action or a soft button stays with GenericEventMapper and configuration.cfg.
     */
    public static int convertKeyCode(int keyCode) {
        return keyCode;
    }
}
