/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version: 1.3.22
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package sdljava.x.swig;

public class SDL_JoyButtonEvent {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected SDL_JoyButtonEvent(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SDL_JoyButtonEvent obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public void delete() {
    if(swigCPtr != 0 && swigCMemOwn) {
      swigCMemOwn = false;
      SWIG_SDLEventJNI.delete_SDL_JoyButtonEvent(swigCPtr);
    }
    swigCPtr = 0;
  }

  public void setType(short type) {
    SWIG_SDLEventJNI.set_SDL_JoyButtonEvent_type(swigCPtr, type);
  }

  public short getType() {
    return SWIG_SDLEventJNI.get_SDL_JoyButtonEvent_type(swigCPtr);
  }

  public void setWhich(short which) {
    SWIG_SDLEventJNI.set_SDL_JoyButtonEvent_which(swigCPtr, which);
  }

  public short getWhich() {
    return SWIG_SDLEventJNI.get_SDL_JoyButtonEvent_which(swigCPtr);
  }

  public void setButton(short button) {
    SWIG_SDLEventJNI.set_SDL_JoyButtonEvent_button(swigCPtr, button);
  }

  public short getButton() {
    return SWIG_SDLEventJNI.get_SDL_JoyButtonEvent_button(swigCPtr);
  }

  public void setState(short state) {
    SWIG_SDLEventJNI.set_SDL_JoyButtonEvent_state(swigCPtr, state);
  }

  public short getState() {
    return SWIG_SDLEventJNI.get_SDL_JoyButtonEvent_state(swigCPtr);
  }

  public SDL_JoyButtonEvent() {
    this(SWIG_SDLEventJNI.new_SDL_JoyButtonEvent(), true);
  }

}
