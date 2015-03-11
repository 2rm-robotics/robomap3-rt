/*
 * Copyright (C) 2007 Jan Kiszka <jan.kiszka@web.de>.
 *
 * Xenomai is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Xenomai is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Xenomai; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
/*
 * Changes by Guillaume Sanahuja to comply with OMAP UART
 */


#define UART1_IRQ	72
#define UART2_IRQ	73
#define UART3_IRQ	74
#define UART1_MEM	0x4806A000
#define UART2_MEM	0x4806C000
#define UART3_MEM	0x49020000

static unsigned long mem[MAX_DEVICES]={UART1_MEM,UART2_MEM,UART3_MEM};
static void *mapped_io[MAX_DEVICES];
static unsigned int irq[MAX_DEVICES]={UART1_IRQ,UART2_IRQ,UART3_IRQ};

#define RT_16550_IO_INLINE inline


static inline unsigned long rt_16550_base_addr(int dev_id)
{
	return (unsigned long)mapped_io[dev_id];
}

static inline void
rt_16550_init_io_ctx(int dev_id, struct rt_16550_context *ctx)
{
	ctx->base_addr = (unsigned long)mapped_io[dev_id];
}


static RT_16550_IO_INLINE u8
rt_16550_reg_in(unsigned long base, int off)
{
	return readb((void *)base + (off<<2));	
}

static RT_16550_IO_INLINE void
rt_16550_reg_out(unsigned long base, int off, u8 val)
{
	writeb(val, (void *)base + (off<<2));
		
}

static int rt_16550_init_io(int dev_id, char* name)
{
	mapped_io[dev_id] = ioremap(mem[dev_id], 8);
	if (!mapped_io[dev_id])
		return -EBUSY;
		
	return 0;
}

static void rt_16550_release_io(int dev_id)
{
	iounmap(mapped_io[dev_id]);	
}
