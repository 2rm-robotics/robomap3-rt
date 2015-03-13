/**
 * @file
 * Real-Time Driver Model for Xenomai, i2c device profile header
 *
 * @note Copyright (C) 2011 Guillaume Sanahuja <gsanahuj@hds.utc.fr>
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
 *
 * @ingroup rtradio
 */

/*!
 * @ingroup profiles
 * @defgroup rtradio Radio Devices
 *
 * This is the common interface a RTDM-compliant radio device has to provide.
 *
 * @b Profile @b Revision: 1
 * @n
 * @n
 * @par Device Characteristics
 * @ref rtdm_device.device_flags "Device Flags": @c RTDM_NAMED_DEVICE, @c RTDM_EXCLUSIVE @n
 * @n
 * @ref rtdm_device.device_name "Device Name": @c "rtradio", @n
 * @n
 * @ref rtdm_device.device_class "Device Class": @c RTDM_CLASS_SERIAL @n
 * @n
 *
 * @par Supported Operations
 * @b Open @n
 * Environments: non-RT (RT optional, deprecated)@n
 * Specific return values: none @n
 * @n
 * @b Close @n
 * Environments: non-RT (RT optional, deprecated)@n
 * Specific return values: none @n
 * @n
 * @b IOCTL @n
 * Mandatory Environments: see @ref RADIOIOCTLs "below" @n
 * Specific return values: see @ref RADIOIOCTLs "below" @n
 * @n
 * @{
 */

#ifndef _RTRADIO_H
#define _RTRADIO_H

#include <rtdm/rtdm.h>

#define RTRADIO_PROFILE_VER		1

#define RTIOC_TYPE_RADIO		RTDM_CLASS_SERIAL

#define NB_CHANNELS 8

/*!
 * @anchor RADIOIOCTLs @name IOCTLs
 * RADIO device IOCTLs
 * @{ */

/**
 * Get radio channels
 *
 * @param[out] arg Pointer to configuration buffer (struct rti2c_config)
 *
 * @return 0 on success, otherwise negative error code
 *
 * Environments:
 *
 * This service can be called from:
 *
 * - Kernel module initialization/cleanup code
 * - Kernel-based task
 * - User-space task (RT, non-RT)
 *
 * Rescheduling: never.
 */
#define RTRADIO_RTIOC_GET_CHANNELS	\
	_IOR(RTIOC_TYPE_RADIO, 0x00, unsigned int[NB_CHANNELS])


/** @} */


#endif /* _RTRADIO_H */
