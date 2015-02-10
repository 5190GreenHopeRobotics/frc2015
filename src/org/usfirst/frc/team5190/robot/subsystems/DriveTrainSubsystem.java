package org.usfirst.frc.team5190.robot.subsystems;

import java.util.Collection;
import java.util.LinkedList;

import org.usfirst.frc.team5190.robot.IndependentSensors;
import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * the drive train subsystem
 */
public class DriveTrainSubsystem extends Subsystem implements Displayable {
	public static final double kP = 0.03;
	private DigitalInput mLimitSwitch;
	private PIDRobotDrive mDrive;
	private boolean disable = false;
	private Encoder right, left;
	private PIDController pid;
	private PIDEncoderDriveTrain enc;
	private Gyro gyro;
	private Jaguar frontleft, backleft, frontright, backright;

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
		mDrive = new PIDRobotDrive(frontleft, backleft, frontright, backright);
		mDrive.setSafetyEnabled(false);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		// init the limit switch
		mLimitSwitch = new DigitalInput(RobotMap.DRIVE_TRAIN_LIMIT_SWITCH);

		// init the encoder
		enc = new PIDEncoderDriveTrain();

		// get the encoders
		right = enc.getRight();
		left = enc.getLeft();
		// get lidar
		// init pid
		pid = new PIDController(0.5, 0, 0.4, enc, mDrive);
		// pid.disable();
		// get gyro
		gyro = IndependentSensors.getGyro();
		mDrive.setGyro(gyro);
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
	public void initDefaultCommand() {
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
			mDrive.arcadeDrive(speed, -gyro.getAngle() * kP);

		}
	}

	/**
	 * stop the robot with PID
	 */

//	public void halt() {
//		this.PIDEnable(true);
//		this.driveToPoint(0);
//		this.setDisable(true);
//	}

	/**
	 * resume the robot with PID
	 */
	public void resume() {
		this.PIDEnable(false);
		this.setDisable(false);
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
	 * turn at rate of amount
	 * 
	 * @param amount
	 *            the rate of turn, from -1 - 1
	 */

	public void turn(double amount) {
		if (!disable) {
			mDrive.arcadeDrive(0, amount, false);
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
			mDrive.setMaxOutput(0.4);
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

	/**
	 * drive a specific distance
	 * 
	 * @param distance
	 *            in inches to drive to
	 */
	public void driveToPoint(double point) {
		pid.setSetpoint(point);
	}

	/**
	 * enable or disable the pid
	 * 
	 * @param e
	 *            true for enable, false for disable
	 */
	public void PIDEnable(boolean e) {
		if (e) {
			pid.enable();
		} else {
			pid.disable();
		}
	}

	@Override
	public Collection<Pair<String, Boolean>> getBooleanValue() {
		LinkedList<Pair<String, Boolean>> booleanValues = new LinkedList<Pair<String, Boolean>>();
		booleanValues.add(new Pair<String, Boolean>("Limit Switch",
				mLimitSwitch.get()));
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
