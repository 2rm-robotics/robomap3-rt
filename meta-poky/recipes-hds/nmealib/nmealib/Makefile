.PHONY : clean

CFLAGS= -fPIC -g
LDFLAGS= -shared
INCS = -I include 

SOURCES = $(shell echo src/*.c)
OBJECTS=$(SOURCES:.cpp=.o)

TARGET=libnmea.so.1.0

all: $(TARGET)

clean:
	rm -f $(OBJECTS) $(TARGET)

$(TARGET) : $(OBJECTS)
	$(CC) $(INCS) $(CFLAGS) $(OBJECTS) -o $@ $(LDFLAGS)

install:
	install -d $(DESTDIR)/$(libdir)
	install -m 0777 libnmea.so.1.0 $(DESTDIR)/$(libdir)
	ln -sf libnmea.so.1.0 $(DESTDIR)/$(libdir)/libnmea.so.1
	ln -sf libnmea.so.1.0 $(DESTDIR)/$(libdir)/libnmea.so
	install -d $(DESTDIR)/$(includedir)/nmea
	install -m 0622 include/nmea/*.h $(DESTDIR)/$(includedir)/nmea
