package org.usfirst.frc.team5190.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;

public class RobotMap {
	// arm
	public static final int ARM_TALONSRX_LEFT_CAN_ID = 11;
	public static final int ARM_TALONSRX_RIGHT_CAN_ID = 12;

	// cherry picker
	public static final int CHERRY_PICKER_TALON_PORT = 3;
	public static final int CHERRY_PICKER_MIN_LIMIT_SWITCH_PORT = 3;
	public static final int CHERRY_PICKER_MAX_LIMIT_SWITCH_PORT = 2;

	// drive train
	public static final int FRONTLEFT = 1;
	public static final int BACKLEFT = 2;
	public static final int FRONTRIGHT = 3;
	public static final int BACKRIGHT = 4;

	// navigation
	public static final I2C.Port LIDAR_PORT = I2C.Port.kMXP;
	public static final I2C.Port RANGE_FINDER_LEFT_PORT = I2C.Port.kMXP;
	public static final I2C.Port RANGE_FINDER_RIGHT_PORT = I2C.Port.kOnboard;
	public static final SerialPort.Port NAVX_PORT = SerialPort.Port.kMXP;

	// pawl
	public static final int PAWL_JAGUAR_PORT = 1;
	public static final int PAWL_POTENTIMETER_PORT = 4;
	public static final int PAWL_ENCODER_A_PORT = 10;
	public static final int PAWL_ENCODER_B_PORT = 11;
	public static final int PAWL_CLUTCH_ENAGED_SWITCH_PORT = 4;

	// lights
	public static final int CHASSIS_LIGHTS_RELAY_PORT = 0;
}
