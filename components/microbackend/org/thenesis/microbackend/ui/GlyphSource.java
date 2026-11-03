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
package org.thenesis.microbackend.ui;

/**
 * Somewhere to get the characters this toolkit's own font does not have.
 *
 * MIDPath's RawFontPeer draws from a table indexed by character code that stops at 256
 * entries, so everything above it, Japanese for one, draws as nothing. A device may have
 * those glyphs in a font of its own.
 *
 * The font peer finds an implementation by name, so nothing here depends on any particular
 * backend, and an absent implementation simply leaves the old behaviour in place.
 */
public interface GlyphSource {

    /**
     * Width the pen would advance for this character at this size, or 0 for no glyph.
     */
    int advance(int code, int pixelSize);

    /**
     * Draw one character and return the advance, or 0 without drawing if there is no
     * glyph.
     *
     * baselineY is the text baseline, since that is what a glyph's bearing is measured
     * from. Clipping is the implementation's job, so a caller need not clip first.
     */
    int draw(int[] surface, int stride, int penX, int baselineY,
             int code, int pixelSize, int colour,
             int clipXmin, int clipYmin, int clipXmax, int clipYmax);
}
