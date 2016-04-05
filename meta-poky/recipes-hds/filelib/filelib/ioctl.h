///////////////////
// file : ioctl.h
// date : 20/05/02
///////////////////
#ifdef __cplusplus
extern "C" {
#endif

#ifndef __IOCTL_H_
#define __IOCTL_H_

typedef enum {
	IMAGE_WIDTH,
	IMAGE_HEIGHT,
	DATA_SIZE,
	HEADER,
	HEADER_AUX,
	HEADER_AUX_SIZE,
	DATA_TYPE,
} ioctl_type;

typedef enum {
	READ_MODE,//0
	WRITE_MODE //1
} mode;


#endif
#ifdef __cplusplus
}
#endif
