#ifndef _CAPTURE_OMAP_H
#define _CAPTURE_OMAP_H

#include <rtdm/rtdm.h>

#define GPT8_IRQ	44
#define GPT9_IRQ	45
#define GPT10_IRQ	46
#define GPT11_IRQ	47
#define GPT8_MEM	0x4903E000
#define GPT9_MEM	0x49040000
#define GPT10_MEM	0x48086000
#define GPT11_MEM	0x48088000 


//core cm register
#define CORE_CM		0x48004A00

//core cm register offset
#define CM_FCLKEN1_CORE	0x0
#define CM_ICLKEN1_CORE 0x10
#define CM_CLKSEL_CORE	0x40
//CM_FCLKEN1_CORE and CM_ICLKEN1_CORE bits
#define EN_GPT10	0x0800
#define EN_GPT11	0x1000
//CM_CLKSEL_CORE bits
#define CLKSEL_GPT10	0x40
#define CLKSEL_GPT11	0x80

//timer registers offsets
#define TIOCP_CFG	0x10
#define TISTAT		0x14
#define TISR		0x18
#define TIER		0x1c
#define TCLR		0x24
#define TCRR		0x28
#define TCAR1		0x3c
#define TSICR		0x40
#define TCAR2		0x44

//TIOCP_CFG bits
#define SOFTRESET	0x02

//TISTAT bits
#define RESETDONE 	0x01

//TISR bits
#define TCAR_IT_FLAG	0x04
#define OVF_IT_FLAG	0x02
#define MAT_IT_FLAG	0x01

//TIER bits
#define TCAR_IT_ENA	0x04
#define OVF_IT_ENA	0x02
#define MAT_IT_ENA	0x01

//TCLR bits
#define GPO_CFG 	0x4000
#define CAPT_MODE	0x2000
#define TCM_NO		0
#define TCM_RISING	0x100
#define TCM_FALLING	0x200
#define TCM_BOTH	0x300
#define AR		0x02
#define ST		0x01

//TSICR bits


#endif /* _CAPTURE_OMAP_H */
