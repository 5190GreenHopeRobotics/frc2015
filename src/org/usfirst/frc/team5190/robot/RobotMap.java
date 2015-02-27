package org.usfirst.frc.team5190.robot;

import edu.wpi.first.wpilibj.I2C.Port;

public class RobotMap {
	// arm
	public static final int ARM_POTENTIOMETER_PORT = 1;
	public static final int ARM_TALONSRX_LEFT_CAN_ID = 11;
	public static final int ARM_TALONSRX_RIGHT_CAN_ID = 12;

	// cherry picker
	public static final int CHERRY_PICKER_TALON_PORT = 2;
	public static final int CHERRY_PICKER_MIN_LIMIT_SWITCH_PORT = 3;
	public static final int CHERRY_PICKER_MAX_LIMIT_SWITCH_PORT = 2;

	// drive train
	public static final int FRONTLEFT = 1;
	public static final int BACKLEFT = 2;
	public static final int FRONTRIGHT = 3;
	public static final int BACKRIGHT = 4;

	// navigation
	public static final Port LIDAR_PORT = Port.kMXP;
}
