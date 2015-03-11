#ifndef _I2C_OMAP_H
#define _I2C_OMAP_H

#include <rtdm/rtdm.h>

#define I2C_INTERNAL_SAMPLING_CLK	12000000
#define I2C_FASTSPEED_SCLL_TRIM		7
#define I2C_FASTSPEED_SCLH_TRIM		5

#define I2C1_IRQ	56
#define I2C2_IRQ	57
#define I2C3_IRQ	61
#define I2C1_MEM	0x48070000
#define I2C2_MEM	0x48072000
#define I2C3_MEM	0x48060000

//core cm register
#define CORE_CM 0x48004A00
//core cm register offset
#define CM_FCLKEN1_CORE 0x0
#define CM_ICLKEN1_CORE 0x10
//core CM_FCLKEN1_CORE and CM_ICLKEN1_CORE bits
#define EN_I2C1 0x008000
#define EN_I2C2 0x010000
#define EN_I2C3 0x020000

//i2c registers offsets
#define I2C_IE 0x04
#define I2C_STAT 0x08
#define I2C_SYSS 0x10
#define I2C_BUF 0x14
#define I2C_CNT 0x18
#define I2C_DATA 0x1c
#define I2C_SYSC 0x20
#define I2C_CON	0x24
#define I2C_OA0 0x28
#define I2C_SA 0x2c
#define I2C_PSC	0x30
#define I2C_SCLL 0x34
#define I2C_SCLH 0x38
#define I2C_SYSTEST 0x3c
#define I2C_BUFSTAT 0x40

//I2C_CON bits
#define STT 0x0001
#define STP 0x0002
#define TRX	0x0200
#define MST 	0x0400
#define I2C_EN 	0x8000

//I2C_STAT and I2C_IE bits
#define AL 0x0001
#define NACK 0x0002
#define ARDY 0x0004
#define RRDY 0x0008
#define XRDY 0X0010
#define AERR 0X0080
#define BF 0x0100
#define BB 0x1000
#define XDR 0x4000

//I2C_SYSTEST bits
#define ST_EN 0x8000
#define FREE 0x4000
#define TMODE_CLK 0x2000
#define SCL_O 0x0004

//I2C_BUFSTAT bits
#define TXSTAT 0x3F

//I2C_SYSC bits
#define SRST 0x0002

//I2C_SYSS bits
#define RDONE 0x0001

//I2C_BUF bits
#define TXFIFO_CLR 0x0040
#define RXFIFO_CLR 0x4000

#endif /* _I2C_OMAP_H */
