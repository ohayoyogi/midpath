/*
 * MIDPath - Copyright (C) 2006-2008 Guillaume Legris, Mathieu Legris
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
 */
package org.thenesis.microbackend.ui.libretro;

import org.thenesis.microbackend.ui.GlyphSource;

import org.ohayoyogi.libretro.LibRetroBridge;

/**
 * Characters from whatever font the machine running the core has.
 *
 * Two native calls, and no logic: which font that is, and how a glyph is rasterised, is
 * the libretro core's business. A core without such a font answers "no glyph", and MIDPath
 * carries on with its own table.
 *
 * The drawing is done natively, straight into MIDPath's surface array. Handing the bitmap
 * up and blending it here would put a per-pixel loop back into Java.
 */
public class LibRetroGlyphs implements GlyphSource {

    public int advance(int code, int pixelSize) {
        return LibRetroBridge.glyphAdvanceNative(code, pixelSize);
    }

    public int draw(int[] surface, int stride, int penX, int baselineY,
                    int code, int pixelSize, int colour,
                    int clipXmin, int clipYmin, int clipXmax, int clipYmax) {
        return LibRetroBridge.drawGlyphNative(surface, stride, penX, baselineY,
                code, pixelSize, colour,
                clipXmin, clipYmin, clipXmax, clipYmax);
    }
}
