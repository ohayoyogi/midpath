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
 * A backend that wraps a widget belonging to some host toolkit.
 *
 * UIBackendFactory constructs these reflectively so that a device without the host
 * toolkit never loads their classes: on a VM that resolves a class's whole constant
 * pool when the class is initialised, a direct reference is enough to pull them in.
 * Reflection can only call a no-argument constructor, hence this two-step interface.
 */
public interface WrapperBackend extends UIBackend {

    /**
     * Hand over the host widget. Called immediately after construction, before any
     * other method. Implementations may assume target is of their own toolkit's type.
     */
    public void setTarget(Object target);
}
