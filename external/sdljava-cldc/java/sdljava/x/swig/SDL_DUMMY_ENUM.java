/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version: 1.3.22
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package sdljava.x.swig;

public final class SDL_DUMMY_ENUM {
  public final static SDL_DUMMY_ENUM DUMMY_ENUM_VALUE = new SDL_DUMMY_ENUM("DUMMY_ENUM_VALUE");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static SDL_DUMMY_ENUM swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + SDL_DUMMY_ENUM.class + " with value " + swigValue);
  }

  private SDL_DUMMY_ENUM(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private SDL_DUMMY_ENUM(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private static SDL_DUMMY_ENUM[] swigValues = { DUMMY_ENUM_VALUE };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}


