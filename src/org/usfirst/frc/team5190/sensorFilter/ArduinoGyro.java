package org.usfirst.frc.team5190.sensorFilter;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;

public class ArduinoGyro implements PIDSource {

	private I2C i2c;
	private byte[] AddressCheck;

	public static final byte L3GD20_ADDRESS = (0x6B), // 1101011
			L3GD20_POLL_TIMEOUT = (100), // Maximum number of read attempts
			L3GD20_ID = (byte) (0xD4), L3GD20H_ID = (byte) (0xD7);
	public static final double GYRO_SENSITIVITY_250DPS = (0.00875F), // Roughly
																		// 22/256
																		// for
																		// fixed
																		// point
																		// match
			GYRO_SENSITIVITY_500DPS = (0.0175F), // Roughly 45/256
			GYRO_SENSITIVITY_2000DPS = (0.070F);
	// DEFAULT TYPE
	private final int GYRO_REGISTER_WHO_AM_I = 0x0F, // 11010100 r
			GYRO_REGISTER_CTRL_REG1 = 0x20, // 00000111 rw
			GYRO_REGISTER_CTRL_REG2 = 0x21, // 00000000 rw
			GYRO_REGISTER_CTRL_REG3 = 0x22, // 00000000 rw
			GYRO_REGISTER_CTRL_REG4 = 0x23, // 00000000 rw
			GYRO_REGISTER_CTRL_REG5 = 0x24, // 00000000
											// rwGYRO_REGISTER_REFERENCE = 0x25,
											// // 00000000 rw
			GYRO_REGISTER_OUT_TEMP = 0x26, // r
			GYRO_REGISTER_STATUS_REG = 0x27, // r
			GYRO_REGISTER_OUT_X_L = 0x28, // r
			GYRO_REGISTER_OUT_X_H = 0x29, // r
			GYRO_REGISTER_OUT_Y_L = 0x2A, // r
			GYRO_REGISTER_OUT_Y_H = 0x2B, // r
			GYRO_REGISTER_OUT_Z_L = 0x2C, // r
			GYRO_REGISTER_OUT_Z_H = 0x2D, // r
			GYRO_REGISTER_FIFO_CTRL_REG = 0x2E, // 00000000 rw
			GYRO_REGISTER_FIFO_SRC_REG = 0x2F, // r
			GYRO_REGISTER_INT1_CFG = 0x30, // 00000000 rw
			GYRO_REGISTER_INT1_SRC = 0x31, // r
			GYRO_REGISTER_TSH_XH = 0x32, // 00000000 rw
			GYRO_REGISTER_TSH_XL = 0x33, // 00000000 rw
			GYRO_REGISTER_TSH_YH = 0x34, // 00000000 rw
			GYRO_REGISTER_TSH_YL = 0x35, // 00000000 rw
			GYRO_REGISTER_TSH_ZH = 0x36, // 00000000 rw
			GYRO_REGISTER_TSH_ZL = 0x37, // 00000000 rw
			GYRO_REGISTER_INT1_DURATION = 0x38; // 00000000 rw

	private final int GYRO_RANGE_250DPS = 250, GYRO_RANGE_500DPS = 500,
			GYRO_RANGE_2000DPS = 2000;

	public ArduinoGyro(Port port) {
		i2c = new I2C(port, L3GD20_ADDRESS);
		AddressCheck = new byte[0];
	}

	public boolean check(int register, byte[] reg, int count) {
		byte[] test = new byte[6];
		i2c.read(register, 6, test);
		for (int i = 0; i < count; i++) {
			if (test[i] == 0) {
				return false;
			}
		}

		i2c.read(register, 6, reg);

		return true;
	}

	// read 24 bytes(2 doubles)
	public boolean begin() {

		if (check(L3GD20_ADDRESS, AddressCheck, 1) == false) {
			return false;
		}

		i2c.read(L3GD20_ADDRESS, 1, AddressCheck);

		if (AddressCheck[0] != L3GD20_ID && AddressCheck[0] != L3GD20H_ID) {
			return false;
		}

		i2c.write(GYRO_REGISTER_CTRL_REG1, 0x00);
		i2c.write(GYRO_REGISTER_CTRL_REG1, 0x0F);

		// Range = 500DPS
		i2c.write(GYRO_REGISTER_CTRL_REG4, 0x10);

		return true;
	}

	public double pidGet() {
		// TODO Auto-generated method stub
		return 0;
	}

}
