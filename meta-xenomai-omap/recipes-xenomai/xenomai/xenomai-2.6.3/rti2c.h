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
 * @ingroup rti2c
 */

/*!
 * @ingroup profiles
 * @defgroup rti2c I2C Devices
 *
 * This is the common interface a RTDM-compliant i2c device has to provide.
 *
 * @b Profile @b Revision: 1
 * @n
 * @n
 * @par Device Characteristics
 * @ref rtdm_device.device_flags "Device Flags": @c RTDM_NAMED_DEVICE, @c RTDM_EXCLUSIVE @n
 * @n
 * @ref rtdm_device.device_name "Device Name": @c "rti2c<N>", N >= 0 @n
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
 * Mandatory Environments: see @ref I2CIOCTLs "below" @n
 * Specific return values: see @ref I2CIOCTLs "below" @n
 * @n
 * @b Read @n
 * Environments: RT (non-RT optional)@n
 * Specific return values:
 * - -ETIMEDOUT
 * - -EINTR (interrupted explicitly or by signal)
 * - -EAGAIN (no data available in non-blocking mode)
 * - -EBADF (device has been closed while reading)
 * - -EIO (hardware error or broken bit stream)
 * .
 * @n
 * @b Write @n
 * Environments: RT (non-RT optional)@n
 * Specific return values:
 * - -ETIMEDOUT
 * - -EINTR (interrupted explicitly or by signal)
 * - -EAGAIN (no data written in non-blocking mode)
 * - -EBADF (device has been closed while writing)
 *
 * @{
 */

#ifndef _RTI2C_H
#define _RTi2C_H

#include <rtdm/rtdm.h>

#define RTI2C_PROFILE_VER		1


/*!
 * @anchor RTI2C_xxx_BAUD   @name RTI2C_xx_BAUD
 * Common baud rate values
 * @{ */
#define RTI2C_STANDARD_BAUD		100000
#define RTI2C_FAST_MODE_BAUD		400000
#define RTI2C_HIGH_SPEED_BAUD		3400000
/** @} */

/*!
 * @anchor RTI2C_DEF_BAUD   @name RTI2C_DEF_BAUD
 * Default baud rate
 * @{ */
#define RTI2C_DEF_BAUD			RTI2C_STANDARD_BAUD
/** @} */


/*!
 * @anchor RTI2C_TIMEOUT_xxx   @name RTI2C_TIMEOUT_xxx
 * Special timeout values, see also @ref RTDM_TIMEOUT_xxx
 * @{ */
#define RTI2C_TIMEOUT_INFINITE		RTDM_TIMEOUT_INFINITE
#define RTI2C_TIMEOUT_NONE		RTDM_TIMEOUT_NONE
#define RTI2C_DEF_TIMEOUT		RTDM_TIMEOUT_INFINITE
/** @} */



/*!
 * @anchor RTI2C_SET_xxx   @name RTI2C_SET_xxx
 * Configuration mask bits
 * @{ */
#define RTI2C_SET_BAUD			0x0001
#define RTI2C_SET_TIMEOUT_RX		0x0100
#define RTI2C_SET_TIMEOUT_TX		0x0200
/** @} */


/**
 * Serial device configuration
 */
typedef struct rti2c_config {
	/** mask specifying valid fields, see @ref RTI2C_SET_xxx */
	int		config_mask;

	/** baud rate, default @ref RTI2C_DEF_BAUD */
	int		baud_rate;

	/** reception timeout, see @ref RTI2C_TIMEOUT_xxx for special
	 *  values */
	nanosecs_rel_t	rx_timeout;

	/** transmission timeout, see @ref RTI2C_TIMEOUT_xxx for special
	 *  values */
	nanosecs_rel_t	tx_timeout;

} rti2c_config_t;



#define RTIOC_TYPE_I2C		RTDM_CLASS_SERIAL


/*!
 * @anchor I2CIOCTLs @name IOCTLs
 * I2C device IOCTLs
 * @{ */

/**
 * Get i2c device configuration
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
#define RTI2C_RTIOC_GET_CONFIG	\
	_IOR(RTIOC_TYPE_I2C, 0x00, struct rti2c_config)

/**
 * Set i2c device configuration
 *
 * @param[in] arg Pointer to configuration buffer (struct rti2c_config)
 *
 * @return 0 on success, otherwise:
 *
 * - -EPERM is returned if the caller's context is invalid, see note below.
 *
 * - -ENOMEM is returned if a new history buffer for timestamps cannot be
 * allocated.
 *
 * Environments:
 *
 * This service can be called from:
 *
 * - Kernel module initialization/cleanup code
 * - Kernel-based task
 * - User-space task (RT, non-RT)
 *
 * @note If rtser_config contains a valid timestamp_history and the
 * addressed device has been opened in non-real-time context, this IOCTL must
 * be issued in non-real-time context as well. Otherwise, this command will
 * fail.
 *
 * Rescheduling: never.
 */
#define RTI2C_RTIOC_SET_CONFIG	\
	_IOW(RTIOC_TYPE_I2C, 0x01, struct rti2c_config)

/**
 * set i2c slave address
 *
 * @param[in] arg Pointer slave address (unsigned short)
 *
 * @return 0 on success, otherwise negative error code
 *
 * Environments:
 *
 * This service can be called from:
 *
 */
#define RTI2C_RTIOC_SET_SLAVE	\
	_IOR(RTIOC_TYPE_I2C, 0x02, unsigned short)


/** @} */


#endif /* _RTI2C_H */
