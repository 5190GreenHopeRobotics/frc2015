package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.DriveWithArcadeCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author sdai the drive train subsystem
 */
public class DriveTrainSubsystem extends Subsystem implements Displayable {
	private static DriveTrainSubsystem instance;

	private static final double DRIVE_SET_DISTANCE_P = 0.4;
	private static final double DRIVE_SET_DISTANCE_I = 0;
	private static final double DRIVE_SET_DISTANCE_D = 0;
	private static final double DRIVE_SET_DISTANCE_TOLERANCE = 2.0;

	/**
	 * The maximum power for driving under PID control for going a specific
	 * distance
	 */
	public static final double[] DRIVE_SET_DISTANCE_OUTPUT_RANGE = { -1000,
			1000 };

	public static final double TALON_CLOSED_LOOP_RAMP_SPEED = 48.0;

	private boolean disable = false;
	private CANTalon frontLeft, backLeft, frontRight, backRight;
	private Preferences prefs = Preferences.getInstance();

	private class AveragedEncoderTicksPIDSource implements PIDSource {

		@Override
		public double pidGet() {
			return frontLeft.getEncPosition() + frontRight.getEncPosition() / 2;
		}

	}

	private class DriveStraightPIDOutput implements PIDOutput {
		private NavigationSubsystem navigationSubsystem = NavigationSubsystem
				.getInstance();

		private static final double kP = 100;

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
			System.out.println("Output: " + output);
			double angle = navigationSubsystem.getYaw(); // get current heading
			frontLeft.set(output + (-angle * kP));
			frontRight.set(output + (angle * kP));
		}
	}

	public class DriveSetDistance {
		PIDController pidController;

		private DriveSetDistance() {
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
			pidController = new PIDController(prefs.getDouble("dt.distance.p",
					DRIVE_SET_DISTANCE_P), prefs.getDouble("dt.distance.i",
					DRIVE_SET_DISTANCE_I), prefs.getDouble("dt.distance.d",
					DRIVE_SET_DISTANCE_D), averagedEncoder,
					new DriveStraightPIDOutput());
			pidController.setAbsoluteTolerance(prefs.getDouble(
					"dt.distance.tolerance", DRIVE_SET_DISTANCE_TOLERANCE));
			pidController.setOutputRange(DRIVE_SET_DISTANCE_OUTPUT_RANGE[0],
					DRIVE_SET_DISTANCE_OUTPUT_RANGE[1]);
			double startPoint = averagedEncoder.pidGet();
			pidController.setSetpoint(startPoint + inchesToTicks(distance));
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
			return inches * 100;
		}
	}

	/**
	 * Init the drive train at default port, in RobotMap
	 */
	private DriveTrainSubsystem() {
		// init the motors
		initializeMotors();
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
		frontLeft.reverseSensor(true);
		frontLeft.setCloseLoopRampRate(48);
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
		frontRight.changeControlMode(ControlMode.Speed);
		frontRight.setCloseLoopRampRate(48);
		frontRight.setSafetyEnabled(false);
		frontRight.set(0);

		// back right
		backRight = new CANTalon(RobotMap.BACKRIGHT);
		backRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		backRight.changeControlMode(ControlMode.Follower);
		backRight.setSafetyEnabled(false);
		backRight.set(frontRight.getDeviceID());
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
			if (moveValue > 0.0) {
				if (rotateValue > 0.0) {
					leftMotorSpeed = moveValue - rotateValue;
					rightMotorSpeed = Math.max(moveValue, rotateValue);
				} else {
					leftMotorSpeed = Math.max(moveValue, -rotateValue);
					rightMotorSpeed = moveValue + rotateValue;
				}
			} else {
				if (rotateValue > 0.0) {
					leftMotorSpeed = -Math.max(-moveValue, rotateValue);
					rightMotorSpeed = moveValue + rotateValue;
				} else {
					leftMotorSpeed = moveValue - rotateValue;
					rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
				}
			}
			// Scale up to rpm
			leftMotorSpeed *= 1000;
			rightMotorSpeed *= 1000;

			frontLeft.set(leftMotorSpeed);
			frontRight.set(rightMotorSpeed);
		}
	}

	public void tankDrive(double leftPower, double rightPower) {
		if (!disable) {
			frontLeft.set(leftPower * 1000);
			frontRight.set(rightPower * 1000);
		}
	}

	public double testDrive() {
		return frontLeft.getPosition();
	}

	public void displayValues(Display display) {
		display.putNumber("Left Speed", frontLeft.getSpeed());
		display.putNumber("Right Speed", frontRight.getSpeed());
		display.putNumber("Left Position", frontLeft.getPosition());
		display.putNumber("Right Position", frontRight.getPosition());
	}
}
