package org.usfirst.frc.team5190.robot.subsystems;

import java.util.Collection;
import java.util.LinkedList;

import org.usfirst.frc.team5190.robot.IndependentSensors;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.Direction;
import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * the drive train subsystem
 */
public class DriveTrainSubsystem extends Subsystem implements Displayable {

	/**
	 * The range +/- that is acceptable for driving a set distance.
	 */
	public static final double DRIVE_SET_DISTANCE_TOLERANCE = 2.0;

	/**
	 * The maximum power for driving under PID control for going a specific
	 * distance
	 */
	public static final double[] DRIVE_SET_DISTANCE_OUTPUT_RANGE = { -0.5, 0.5 };

	/**
	 * The range of degrees +/- that is acceptable for turning a requested
	 * amount.
	 */
	public static final double TURN_TOLERANCE = 2.0;

	/**
	 * The maximum power for driving under PID control for turning the robot
	 */
	public static final double[] TURN_OUTPUT_RANGE = { -0.3, 0.3 };

	public static final double kP = 0.03;

	private RobotDrive mDrive;
	private boolean disable = false;
	private Encoder right, left;
	private PIDEncoderDriveTrain enc;
	private Jaguar frontleft, backleft, frontright, backright;
	private DriveStraightRobotDrive driveStraightRobotDrive;
	private TurnRobotDrive turnRobotDrive;

	public class DriveSetDistance {
		PIDController pidController;

		private DriveSetDistance() {
			pidController = new PIDController(0.5, 0, 0.4, enc,
					driveStraightRobotDrive);
			pidController.setAbsoluteTolerance(DRIVE_SET_DISTANCE_TOLERANCE);
			pidController.setOutputRange(DRIVE_SET_DISTANCE_OUTPUT_RANGE[0],
					DRIVE_SET_DISTANCE_OUTPUT_RANGE[1]);
		}

		/**
		 * Drive a specific distance
		 * 
		 * @param distance
		 *            in inches to drive to It is the Encoder preset distance
		 *            added to the actual distance you want to go.
		 */
		public void start(double distance) {
			pidController.setSetpoint(enc.getDistance() + distance);
			pidController.enable();

		}

		public void end() {
			pidController.disable();
		}

		// This asks to see if it has gotten to the distance.
		public boolean drivenDistance() {
			return pidController.onTarget();
		}
	}

	public class Turn {
		private Direction mDir = Direction.LEFT;
		PIDController pidController;

		private Turn() {
			pidController = new PIDController(0.3, 0, 0.1,
					IndependentSensors.getGyro(), turnRobotDrive);
			pidController.setAbsoluteTolerance(TURN_TOLERANCE);
			pidController.setOutputRange(TURN_OUTPUT_RANGE[0],
					TURN_OUTPUT_RANGE[1]);
		}

		/**
		 * set direction to turn, via Direction enum
		 * 
		 * @param dir
		 *            the direction, RIGHT, LEFT
		 */
		public void setDirection(Direction dir) {
			mDir = dir;
		}

		public void start(double turnDegree) {
			pidController.setSetpoint(turnDegree);
			pidController.enable();

		}

		public void end() {
			pidController.disable();
		}

		// This asks to see if it has gotten to the distance.
		public boolean finishedTurn() {
			return pidController.onTarget();
		}
	}

	/**
	 * Init the drive train at default port, in RobotMap
	 */
	public DriveTrainSubsystem() {
		// init the motors
		frontleft = new Jaguar(RobotMap.FRONTLEFT);
		backleft = new Jaguar(RobotMap.BACKLEFT);
		frontright = new Jaguar(RobotMap.FRONTRIGHT);
		backright = new Jaguar(RobotMap.BACKRIGHT);
		// init drive
		mDrive = new RobotDrive(frontleft, backleft, frontright, backright);
		mDrive.setSafetyEnabled(false);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		driveStraightRobotDrive = new DriveStraightRobotDrive(mDrive);
		turnRobotDrive = new TurnRobotDrive(mDrive);

		// init the encoder
		enc = new PIDEncoderDriveTrain();

		// get the encoders
		right = enc.getRight();
		left = enc.getLeft();
	}

	/**
	 * set the output of the motor
	 * 
	 * @param power
	 *            the power, from 0 to 1
	 */
	public void setPower(double power) {
		mDrive.setMaxOutput(power);
	}

	/**
	 * reset all the encoder
	 */
	public void resetEncoder() {
		right.reset();
		left.reset();
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

	/**
	 * dummy
	 */
	@Override
	public void initDefaultCommand() {
	}

	public DriveSetDistance driveSetDistance() {
		return new DriveSetDistance();
	}

	public Turn turn() {
		return new Turn();
	}

	/**
	 * drive forward at the full speed
	 */

	public void driveForward() {
		if (!disable) {
			mDrive.drive(1, 0);
		}
	}

	/**
	 * drive at a speed, forward is 0 - 1, back is 0 - -1
	 * 
	 * @param speed
	 *            the speed robot will go at, -1 to 1, 1 goes forward
	 */

	public void drive(double speed) {
		if (!disable) {
			driveStraightRobotDrive.drive(speed);
		}
	}

	/**
	 * turn right at speed of 0.1
	 */

	public void turnRight() {
		if (!disable) {
			mDrive.tankDrive(0, 0.1);
		}
	}

	/**
	 * turn left at speed of 0.1
	 */

	public void turnLeft() {
		if (!disable) {
			mDrive.tankDrive(0.1, 0);
		}
	}

	/**
	 * control the drive train with a arcade drive
	 * 
	 * @param stick
	 *            the one joystick
	 */

	public void arcadeJoystickDrive(Joystick stick) {
		if (!disable) {
			// KEEEEEEPPPPPPP THIIIISSSS
			mDrive.arcadeDrive(-stick.getY(), stick.getRawAxis(4));
		}
	}

	/**
	 * control the robot with tank drive joystick configuration
	 * 
	 * @param s1
	 *            the left stick
	 * @param s2
	 *            the right stick
	 */

	public void tankJoystickDrive(Joystick s1, Joystick s2) {
		if (!disable) {
			mDrive.tankDrive(s1, s2);
		}
	}

	@Override
	public Collection<Pair<String, Boolean>> getBooleanValue() {
		LinkedList<Pair<String, Boolean>> booleanValues = new LinkedList<Pair<String, Boolean>>();
		booleanValues.add(new Pair<String, Boolean>("Encoder Right Direction",
				right.getDirection()));
		booleanValues.add(new Pair<String, Boolean>("Encoder Left Direction",
				left.getDirection()));
		return booleanValues;
	}

	@Override
	public Collection<Pair<String, Double>> getDecimalValues() {
		Double get = new Double(right.get());
		LinkedList<Pair<String, Double>> encoder = new LinkedList<Pair<String, Double>>();
		encoder.add(new Pair<String, Double>("Encoder Right Get:", get));
		encoder.add(new Pair<String, Double>("Encoder Right Distance:", right
				.getDistance()));
		get = new Double(left.get());
		encoder.add(new Pair<String, Double>("Encoder Left Get", get));
		encoder.add(new Pair<String, Double>("Encoder Left Distance", left
				.getDistance()));
		return encoder;
	}

}
