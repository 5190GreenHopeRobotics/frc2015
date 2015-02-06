package org.usfirst.frc.team5190.robot;

public class RobotMap {
	// arm
	public static final int ARMLENGTH_TALONSRX_PORT = 2;
	public static final int ARMANGLE_TALONSRX_PORT = 3;
	public static final int ARM_RAISE_LIMIT_SWITCH_PORT = 6; // Get a real value
	public static final int ARM_LOWER_LIMIT_SWITCH_PORT = 7; // Get a real value
	// armextender Change these when you get a real value
	public static final int ARM_EXTEND_TALONSRX_PORT = 15;
	public static final int ARM_EXTEND_LIMIT_SWITCH_PORT = 16;
	public static final int ARM_RETRACT_LIMIT_SWITCH_PORT = 17;
	// forklift
	public static final int FORKLIFT_TALONSRX_PORT = 4;
	public static final int FORKLIFTRAISE_VICTOR_PORT = 5;
	// drive train
	public static final int FRONTLEFT = 0;
	public static final int BACKLEFT = 1;
	public static final int FRONTRIGHT = 2;
	public static final int BACKRIGHT = 3;
	// sensors
	public static final int GYRO_PORT = 0;
	public static final int DRIVE_TRAIN_LIMIT_SWITCH = 0;
	public static final int ULTRASONIC_PING = 2;
	public static final int ULTRASONIC_RECIEVE = 1;
	// encoder
	public static final int ENCODER_RIGHT_CHANNEL_A = 5;
	public static final int ENCODER_RIGHT_CHANNEL_B = 6;
	public static final int ENCODER_LEFT_CHANNEL_A = 3;
	public static final int ENCODER_LEFT_CHANNEL_B = 4;

	public static final int SOLENOID_PORT = 0;
}
