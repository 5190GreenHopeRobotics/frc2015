package org.usfirst.frc.team5190.robot.subsystems;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * the drive train subsystem
 */
public class DriveTrainSubsystem extends PIDSubsystem implements Displayable {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private DigitalInput mLimitSwitch;
	private RobotDrive mDrive;
	boolean disable = false;
	private Encoder right, left;
	// private Gyro gyro;
	Jaguar frontleft, backleft, frontright, backright;

	/**
	 * Init the drive train at default port, in RobotMap
	 */
	public DriveTrainSubsystem() {
		super("DriveTran", 1, 0, 0);
		frontleft = new Jaguar(RobotMap.FRONTLEFT);
		backleft = new Jaguar(RobotMap.BACKLEFT);
		frontright = new Jaguar(RobotMap.FRONTRIGHT);
		backright = new Jaguar(RobotMap.BACKRIGHT);
		mDrive = new RobotDrive(frontleft, backleft, frontright, backright);
		mLimitSwitch = new DigitalInput(RobotMap.DRIVE_TRAIN_LIMIT_SWITCH);
		mDrive.setSafetyEnabled(true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		mDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

		right = new Encoder(RobotMap.ENCODER_RIGHT_CHANNEL_A,
				RobotMap.ENCODER_RIGHT_CHANNEL_B);
		left = new Encoder(RobotMap.ENCODER_LEFT_CHANNEL_A,
				RobotMap.ENCODER_LEFT_CHANNEL_B);
		// gyro = new Gyro(RobotMap.GYRO_PORT);
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
	 * disable or enable the drive train
	 * 
	 * @param flag
	 *            true for disable, false for enable
	 */
	public void setDisable(boolean flag) {
		disable = flag;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
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
			mDrive.arcadeDrive(speed, 0);

		}
	}

	/**
	 * drive the robot at speed for time second
	 * 
	 * @param speed
	 *            the speed(-1 -1), which the robot will go at
	 * @param time
	 *            the number of second it will run
	 */
	@Deprecated
	public void drive(double speed, long time) {
		mDrive.tankDrive(speed, speed);
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {

		} finally {
			this.stopAll();
		}
	}

	/**
	 * stop the robot
	 */

	public void stopAll() {
		mDrive.tankDrive(0, 0);
	}

	/**
	 * turn right at speed of 0.1
	 */

	public void turnRight() {
		if (!disable) {
			mDrive.tankDrive(0, 0.1);
		}
		// gyro.reset();
	}

	/**
	 * turn left at speed of 0.1
	 */

	public void turnLeft() {
		if (!disable) {
			mDrive.tankDrive(0.1, 0);
		}
		// gyro.reset();
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
		// gyro.reset();
	}

	/**
	 * control the drive train with a arcade drive
	 * 
	 * @param stick
	 *            the one joystick
	 */

	public void arcadeJoystickDrive(Joystick stick) {
		if (!disable) {
			if (!mLimitSwitch.get()) {
				stopAll();
				return;
			}
			setPower(Robot.oi.getSpeed());
			mDrive.arcadeDrive(stick);
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
		// TODO Auto-generated method stub
		Double get = new Double(right.get());
		LinkedList<Pair<String, Double>> encoder = new LinkedList<Pair<String, Double>>();
		encoder.add(new Pair<String, Double>("Encoder Right Get:", get));
		encoder.add(new Pair<String, Double>("Encoder Right Get Distance:",
				right.getDistance()));
		get = new Double(left.get());
		encoder.add(new Pair<String, Double>("Encoder Left Get", get));
		encoder.add(new Pair<String, Double>("Encoder Left Distance", left
				.getDistance()));
		return encoder;
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return (left.get() + right.get()) / 2;
	}

	@Override
	protected void usePIDOutput(double output) {
		frontleft.set(output);
		frontright.set(output);
		backleft.set(output);
		backright.set(output);

	}
}
