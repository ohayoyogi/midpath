JNI_INCLUDE = -I/usr/include/classpath

CFLAGS = $(JNI_INCLUDE)
LDFLAGS =

SOURCES = jcl.c javaio.c org_thenesis_midpath_io_backend_cldc_File.c \
org_thenesis_midpath_io_backend_cldc_RandomAccessFile.c
		  

OBJECTS=$(patsubst %.c, %.o, $(SOURCES))

all: libmidpathfile.so

%.o : %.c
	$(CC) $(CFLAGS) -fPIC -g -c -Wall -o $(patsubst %.c, %.o, $<) $<

%.so: $(OBJECTS)
	$(CC) $(LDFLAGS) -shared -Wl,-soname,$@ -lc -o $@ $^

clean:
	rm -f *.so *.o

