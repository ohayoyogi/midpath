/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.22
 * 
 * This file is not intended to be easily readable and contains a number of 
 * coding conventions designed to improve portability and efficiency. Do not make
 * changes to this file unless you know what you are doing--modify the SWIG 
 * interface file instead. 
 * ----------------------------------------------------------------------------- */


#if defined(__GNUC__)
    typedef long long __int64; /*For gcc on Windows */
#endif
#include <jni.h>
#include <stdlib.h>
#include <string.h>


/* Support for throwing Java exceptions */
typedef enum {
  SWIG_JavaOutOfMemoryError = 1, 
  SWIG_JavaIOException, 
  SWIG_JavaRuntimeException, 
  SWIG_JavaIndexOutOfBoundsException,
  SWIG_JavaArithmeticException,
  SWIG_JavaIllegalArgumentException,
  SWIG_JavaNullPointerException,
  SWIG_JavaDirectorPureVirtual,
  SWIG_JavaUnknownError
} SWIG_JavaExceptionCodes;

typedef struct {
  SWIG_JavaExceptionCodes code;
  const char *java_exception;
} SWIG_JavaExceptions_t;


static void SWIG_JavaThrowException(JNIEnv *jenv, SWIG_JavaExceptionCodes code, const char *msg) {
  jclass excep;
  static const SWIG_JavaExceptions_t java_exceptions[] = {
    { SWIG_JavaOutOfMemoryError, "java/lang/OutOfMemoryError" },
    { SWIG_JavaIOException, "java/io/IOException" },
    { SWIG_JavaRuntimeException, "java/lang/RuntimeException" },
    { SWIG_JavaIndexOutOfBoundsException, "java/lang/IndexOutOfBoundsException" },
    { SWIG_JavaArithmeticException, "java/lang/ArithmeticException" },
    { SWIG_JavaIllegalArgumentException, "java/lang/IllegalArgumentException" },
    { SWIG_JavaNullPointerException, "java/lang/NullPointerException" },
    { SWIG_JavaDirectorPureVirtual, "java/lang/RuntimeException" },
    { SWIG_JavaUnknownError,  "java/lang/UnknownError" },
    { (SWIG_JavaExceptionCodes)0,  "java/lang/UnknownError" } };
  const SWIG_JavaExceptions_t *except_ptr = java_exceptions;

  while (except_ptr->code != code && except_ptr->code)
    except_ptr++;

  (*jenv)->ExceptionClear(jenv);
  excep = (*jenv)->FindClass(jenv, except_ptr->java_exception);
  if (excep)
    (*jenv)->ThrowNew(jenv, excep, msg);
}


/* Contract support */

#define SWIG_contract_assert(nullreturn, expr, msg) if (!(expr)) {SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, msg); return nullreturn; } else


  #include "SDL_image.h"

  SDL_Surface * SWIG_IMG_Load_Buffer(void* buf, int size) {
    return IMG_Load_RW(SDL_RWFromMem(buf, size), 1);
  }

extern SDL_Surface *IMG_Load(char const *);
extern SDL_Surface *SWIG_IMG_Load_Buffer(void *,int);

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jlong JNICALL Java_sdljava_x_swig_SWIG_1SDLImageJNI_IMG_1Load(JNIEnv *jenv, jclass jcls, jstring jarg1) {
    jlong jresult = 0 ;
    char *arg1 ;
    SDL_Surface *result;
    
    (void)jenv;
    (void)jcls;
    {
        arg1 = 0;
        if (jarg1) {
            arg1 = (char *)(*jenv)->GetStringUTFChars(jenv, jarg1, 0);
            if (!arg1) return 0;
        }
    }
    result = (SDL_Surface *)IMG_Load((char const *)arg1);
    
    *(SDL_Surface **)&jresult = result; 
    {
        if (arg1) (*jenv)->ReleaseStringUTFChars(jenv, jarg1, arg1); 
    }
    return jresult;
}


JNIEXPORT jlong JNICALL Java_sdljava_x_swig_SWIG_1SDLImageJNI_SWIG_1IMG_1Load_1Buffer(JNIEnv *jenv, jclass jcls, jobject jarg1, jint jarg2) {
    jlong jresult = 0 ;
    void *arg1 = (void *) 0 ;
    int arg2 ;
    SDL_Surface *result;
    
    (void)jenv;
    (void)jcls;
    {
        void * buf = (*jenv)->GetDirectBufferAddress(jenv, jarg1);
        if (buf == NULL) {
            jclass clazz = (*jenv)->FindClass(jenv, "java/lang/NullPointerException");
            (*jenv)->ThrowNew(jenv, clazz, "null address returned from GetDirectBufferAddress() call.  Make sure the buffer is a _direct_ buffer.");
            return 0;
        }
        
        arg1 = buf;
    }
    arg2 = (int)jarg2; 
    result = (SDL_Surface *)SWIG_IMG_Load_Buffer(arg1,arg2);
    
    *(SDL_Surface **)&jresult = result; 
    return jresult;
}


#ifdef __cplusplus
}
#endif

