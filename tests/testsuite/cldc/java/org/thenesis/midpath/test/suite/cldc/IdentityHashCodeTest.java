/* 
 * MIDPath - Copyright (C) 2006-2008 Guillaume Legris, Mathieu Legris
 *
 * Copyright (C) 2002 Free Software Foundation, Inc.
 * Written by Mark Wielaard (mark@klomp.org)
 * 
 * This file is part of Mauve.
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
package org.thenesis.midpath.test.suite.cldc;

import org.thenesis.midpath.test.suite.TestHarness;
import org.thenesis.midpath.test.suite.Testlet;

public class IdentityHashCodeTest implements Testlet {
	public int hashCode() {
		return 42;
	}

	private int origHashCode() {
		return super.hashCode();
	}

	public void test(TestHarness harness) {
		
		harness.checkPoint("System.identityHashCode");
		
		// Returns the same as hash code for any object that does not override it.
		Object o = new Object();
		harness.check(System.identityHashCode(o), o.hashCode());

		// Exception also does not override hashCode
		o = new Exception();
		harness.check(System.identityHashCode(o), o.hashCode());

		// When a class overrides it you can still get the original
		IdentityHashCodeTest ihc = new IdentityHashCodeTest();
		harness.check(System.identityHashCode(ihc), ihc.origHashCode());

		// null has identityHashCode zero
		harness.check(System.identityHashCode(null), 0);
	}
}
