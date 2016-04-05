///////////////////
// file : err.cc
// date : 02/01/02
///////////////////

#include <stdio.h>
#include "err.h"

int road_err = 0;

static char *error_txt[] = {
	"success",
	"not a camera",
	"no 1394 card detected",
	"dynamic allocation error",
	"no informations available",
	"camera initialization failure",
	"camera setup capture failure",
	"iso 1394 start failure",
	"camera capture failure",
	"hdfile read error",
	"hdfile write error",
	"unable to open file",
	"unable to create file",
	"bad magic number",
	"malloc problem",
	"not a road file",
	"test road file ok !",
	"operation not allowed",
	"type unknown",
	"hdfile end of file",
	"unable to open can bus",
	"can bus read error",
	"can bus write error",
	"data size unknown",
	"no place left",
	"unable to open shm",
	"unable to attach shm",
	"unable to create semaphore",
	"shared memory read error",
	"shared memory write error",
	"raw1394 error",
	"capture data not initialized",
	"operating system problem ?",
	"error !!",
	"shared memory init problem",
	"unable to open telemeter",
	"telemeter read error",
	"ioctl error",
	"fifo eof",
	"fifo read error",
	"fifo write error"
};

void road_perror(char *s) {
	printf("%s : %s\n", s, error_txt[road_err]);
}
