package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.dashboard.SmartDashBoardDisplayer;
import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.DriveWithArcadeCommand;
import org.usfirst.frc.team5190.robot.config.Configurable;
import org.usfirst.frc.team5190.robot.config.ConfigurationManager;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Preferences.IncompatibleTypeException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;

/**
 * @author sdai the drive train subsystem
 */
public class DriveTrainSubsystem extends LifecycleSubsystem implements
		Displayable, Configurable {
	private static DriveTrainSubsystem instance;

	private static final double DRIVE_VELOCITY_P = 0.5;
	private static final double DRIVE_VELOCITY_I = 0;
	private static final double DRIVE_VELOCITY_D = 1000;
	private static final double DRIVE_VELOCITY_RAMP_RATE = 24;
	private static final int DRIVE_VELOCITY_IZONE = 0;
	private static final int DRIVE_VELOCITY_PROFILE = 0;
	private static final double DRIVE_VELOCITY_RANGE = 500;

	private static final double DRIVE_SET_DISTANCE_P = 1.5;
	private static final double DRIVE_SET_DISTANCE_I = 0;
	private static final double DRIVE_SET_DISTANCE_D = 0;
	private static final double DRIVE_SET_DISTANCE_TOLERANCE = 1.0;
	private static final double TICKS_IN_INCH = 52;

	/**
	 * The maximum power for driving under PID control for going a specific
	 * distance
	 */
	public static final double[] DRIVE_SET_DISTANCE_OUTPUT_RANGE = { -150, 150 };

	public static final double TALON_CLOSED_LOOP_RAMP_SPEED = 48.0;

	private boolean disable = false;
	private CANTalon frontLeft, backLeft, frontRight, backRight;
	private double velocityRange;
	private Preferences prefs = Preferences.getInstance();

	private boolean moarPowah;

	private final ArmSubsystem armSubsystem = ArmSubsystem.getInstance();
	
	private class AveragedEncoderTicksPIDSource implements PIDSource {

		@Override
		public double pidGet() {
			return (frontLeft.getPosition() + frontRight.getPosition()) / 2;
		}

	}

	private class DriveStraightPIDOutput implements PIDOutput {
		private NavigationSubsystem navigationSubsystem = NavigationSubsystem
				.getInstance();

		private static final double kP = 10;

		public DriveStraightPIDOutput() {
			// Ideally I think this shouldn't zero the yaw value. It should get
			// the current initial angle then in the pidWrite it should use
			// difference between the inital angle and the current angle. That
			// way the yaw value doesn't get messed up if something else is
			// using it. The problem though is that then it would have to handle
			// the transition from -180 to 180. Rather than deal with that we're
			// doing this simplification.
			navigationSubsystem.zeroYaw();
		}

		@Override
		public void pidWrite(double output) {
			double angle = navigationSubsystem.getYaw(); // get current heading
			frontLeft.set(output + (-angle * kP));
			frontRight.set(output + (angle * kP));
		}
	}

	public class DriveSetDistance {
		PIDController pidController;
		private double ticksInInch;

		private DriveSetDistance() {
			ticksInInch = prefs.getDouble("dt.ticks.in.inch", TICKS_IN_INCH);
		}

		/**
		 * Drive a specific distance
		 * 
		 * @param distance
		 *            in inches to drive to It is the Encoder preset distance
		 *            added to the actual distance you want to go.
		 */
		public void start(double distance) {
			AveragedEncoderTicksPIDSource averagedEncoder = new AveragedEncoderTicksPIDSource();
			double p = prefs.getDouble("dt.distance.p", DRIVE_SET_DISTANCE_P);
			double i = prefs.getDouble("dt.distance.i", DRIVE_SET_DISTANCE_I);
			double d = prefs.getDouble("dt.distance.d", DRIVE_SET_DISTANCE_D);
			pidController = new PIDController(p, i, d, averagedEncoder,
					new DriveStraightPIDOutput(), 0.01);
			double tolerance = inchesToTicks(prefs.getDouble(
					"dt.distance.tolerance", DRIVE_SET_DISTANCE_TOLERANCE));
			pidController.setAbsoluteTolerance(tolerance);
			pidController.setOutputRange(DRIVE_SET_DISTANCE_OUTPUT_RANGE[0],
					DRIVE_SET_DISTANCE_OUTPUT_RANGE[1]);
			double startPoint = averagedEncoder.pidGet();
			double endPoint = startPoint + inchesToTicks(distance);
			pidController.setSetpoint(endPoint);
			pidController.enable();

		}

		/**
		 * stop the pid
		 */
		public void end() {
			pidController.disable();
		}

		// This asks to see if it has gotten to the distance.
		public boolean drivenDistance() {
			return pidController.onTarget();
		}

		private double inchesToTicks(double inches) {
			return inches * ticksInInch;
		}
	}

	/**
	 * Init the drive train at default port, in RobotMap
	 */
	private DriveTrainSubsystem() {
		super("DriveTrainSubsystem");
		SmartDashBoardDisplayer.getInstance().addDisplayable(this);
		ConfigurationManager.getInstance().addConfigurable(this);
		initializeMotors();
		updateConfiguration();
	}

	public static DriveTrainSubsystem getInstance() {
		if (instance == null) {
			try {
				instance = new DriveTrainSubsystem();
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
		return instance;
	}

	private void initializeMotors() {

		/*********************************************************************
		 * 
		 * DO NOT CHANGE CODE IN THIS SECTION WITHOUT PERMISSION FROM A MENTOR
		 * 
		 ********************************************************************/

		// front left
		frontLeft = new CANTalon(RobotMap.FRONTLEFT);
		frontLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		frontLeft.setSafetyEnabled(false);
		frontLeft.changeControlMode(ControlMode.Speed);
		frontLeft.set(0);

		// back left
		backLeft = new CANTalon(RobotMap.BACKLEFT);
		backLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		backLeft.reverseSensor(true);
		backLeft.setSafetyEnabled(false);
		backLeft.changeControlMode(ControlMode.Follower);
		backLeft.set(frontLeft.getDeviceID());

		// front right
		frontRight = new CANTalon(RobotMap.FRONTRIGHT);
		frontRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		frontRight.reverseOutput(true);
		frontRight.reverseSensor(true);
		frontRight.setSafetyEnabled(false);
		frontRight.changeControlMode(ControlMode.Speed);
		frontRight.set(0);

		// back right
		backRight = new CANTalon(RobotMap.BACKRIGHT);
		backRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		backRight.reverseOutput(false);
		backRight.reverseSensor(true);
		backRight.setSafetyEnabled(false);
		backRight.changeControlMode(ControlMode.Follower);
		backRight.set(frontRight.getDeviceID());

		// configureVelocityPID();
	}

	private void configureVelocityPID() {
		try {
			double p = prefs.getDouble("dt.velocity.p", DRIVE_VELOCITY_P);
			double i = prefs.getDouble("dt.velocity.i", DRIVE_VELOCITY_I);
			double d = prefs.getDouble("dt.velocity.d", DRIVE_VELOCITY_D);
			double rampRate = prefs.getDouble("dt.velocity.ramp.rate",
					DRIVE_VELOCITY_RAMP_RATE);
			int izone = prefs.getInt("dt.velocity.izone", DRIVE_VELOCITY_IZONE);
			int profile = prefs.getInt("dt.velocity.profile",
					DRIVE_VELOCITY_PROFILE);
			frontLeft.setPID(p, i, d, 0, izone, rampRate, profile);
			frontRight.setPID(p, i, d, 0, izone, rampRate, profile);

			SmartDashboard.putString("Drive PID", "Drive Velocity P: " + p
					+ " I: " + i + " D: " + d + " rampRate: " + rampRate
					+ " izone: " + izone + " profile: " + profile);
		} catch (IncompatibleTypeException e) {
			System.out.println("Failed to set configuration for drive train. "
					+ e.getMessage());
		}
	}

	/**
	 * disable or enable the drive train
	 * 
	 * @param flag
	 *            true for disable, false for enable
	 */
	public void setDisable(boolean flag) {
		disable = flag;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithArcadeCommand());
	}

	public DriveSetDistance driveSetDistance() {
		return new DriveSetDistance();
	}

	public TurnPIDOutput createTurnPIDOutput() {
		return new TurnPIDOutput();
	}

	public DriveStraightPIDOutput createDriveStraightPIDOutput() {
		return new DriveStraightPIDOutput();
	}

	public void arcadeDrive(double moveValue, double rotateValue) {
		if (!disable) {
			double leftMotorSpeed = 0.0;
			double rightMotorSpeed = 0.0;
			double tempThrottle = 1.0;
			double loadFactor = 1.0;
			
//This is one way to adjust drive power, with the trigger button
//and the throttle slider on the joystick....gotta have moarPowah!
			
			//Axis Z is the throttle slider on the joystick
			tempThrottle = Robot.oi.getFlightStickSpeed();
			
			//Here is another add....using the current/voltage to calculate hold power of lift motors 
			//This is directly proportional to the load on the arm (geometry has to be taken into account)
			rotateValue *= armSubsystem.getArmLoadFactor();
			
			if(!moarPowah){				//back 'er down unless you pull the trigger
				moveValue *= tempThrottle;
				rotateValue *= tempThrottle;
			}

			//simplified speed calculation
			leftMotorSpeed = moveValue - rotateValue;
			rightMotorSpeed = moveValue + rotateValue;
//End - this is one way....
			
			
			
			//End - arm power gain method for turning
			
			
//This is the drive power function we ran at the 2015 Regionals
//			if (moveValue > 0.0) {
//				if (rotateValue > 0.0) {
//					leftMotorSpeed = moveValue - rotateValue;
//					rightMotorSpeed = Math.max(moveValue, rotateValue);
//				} else {
//					leftMotorSpeed = Math.max(moveValue, -rotateValue);
//					rightMotorSpeed = moveValue + rotateValue;
//				}
//			} else {
//				if (rotateValue > 0.0) {
//					leftMotorSpeed = -Math.max(-moveValue, rotateValue);
//					rightMotorSpeed = moveValue + rotateValue;
//				} else {
//					leftMotorSpeed = moveValue - rotateValue;
//					rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
//				}
//			}
			//
			// if (moarPowah) {
			// frontLeft.setF(leftMotorSpeed * 100);
			// frontRight.setF(rightMotorSpeed * 100);
			// } else {
			// frontLeft.setF(0);
			// frontRight.setF(0);
			// }

			// Scale up to rpm
			leftMotorSpeed *= velocityRange;
			rightMotorSpeed *= velocityRange;

			SmartDashboard.putNumber("Velocity Range", velocityRange);

			frontLeft.set(leftMotorSpeed);
			frontRight.set(rightMotorSpeed);
		}
	}

	public void tankDrive(double leftPower, double rightPower) {
		if (!disable) {
			frontLeft.set(leftPower * velocityRange);
			frontRight.set(rightPower * velocityRange);
		}
	}

	public void moarPowah(boolean moarPlease) {
		moarPowah = moarPlease;
	}

	public void displayValues(Display display) {
		display.putNumber("Left Speed", frontLeft.getSpeed());
		display.putNumber("Right Speed", frontRight.getSpeed());
		display.putNumber("Left Position", frontLeft.getPosition());
		display.putNumber("Right Position", frontRight.getPosition());
	}

	@Override
	protected void init() {
		// configure PID again in case the values were updated while the robot
		// as disabled
		// configureVelocityPID();
	}

	@Override
	public void updateConfiguration() {
		try {
			velocityRange = prefs.getDouble("dt.velocity.range",
					DRIVE_VELOCITY_RANGE);
		} catch (IncompatibleTypeException e) {
			System.out.println("Failed to set configuration for drive train. "
					+ e.getMessage());
		}
		System.out.println("Velocity Range: " + velocityRange);
	}
}
