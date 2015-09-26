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
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Preferences.IncompatibleTypeException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private static final double DRIVE_VELOCITY_RANGE = 1000;

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

	// data variables
	private double p = 0;
	private double i = 0;
	private double d = 0;
	private int izone = 0;
	private int profile = 0;
	private double rampRate = 0;
	private double leftMotorSpeed = 0.0;
	private double rightMotorSpeed = 0.0;
	private double t_left = 0.0;
	private double t_right = 0.0;
	private double tempThrottle = 1.0;
	private double loadFactor = 1.0;

	private void getPIDFromConfiguration() {
		p = prefs.getDouble("dt.distance.p", DRIVE_SET_DISTANCE_P);
		i = prefs.getDouble("dt.distance.i", DRIVE_SET_DISTANCE_I);
		d = prefs.getDouble("dt.distance.d", DRIVE_SET_DISTANCE_D);
	}

	private void getPIDAttrFromConfiguration() {
		rampRate = prefs.getDouble("dt.velocity.ramp.rate",
				DRIVE_VELOCITY_RAMP_RATE);
		izone = prefs.getInt("dt.velocity.izone", DRIVE_VELOCITY_IZONE);
		profile = prefs.getInt("dt.velocity.profile", DRIVE_VELOCITY_PROFILE);
	}

	/**
	 * Scale the throttle input to be 50-100%
	 */
	private void scaleThrottle() {
		tempThrottle = Robot.oi.getFlightStickSpeed();
		tempThrottle = tempThrottle / 2 + 0.5;
	}

	private void calculateMotorSpeed(double moveValue, double rotateValue) {
		t_left = moveValue - rotateValue;
		t_right = moveValue + rotateValue;

		leftMotorSpeed = t_left + skim(t_right);
		rightMotorSpeed = t_right + skim(t_left);

		leftMotorSpeed *= velocityRange;
		rightMotorSpeed *= velocityRange;
	}

	/**
	 * using the current/voltage to calculate hold power of lift motors. This is
	 * directly proportional to the load on the arm (geometry has to be taken
	 * into account). Load factor is a bit slow to change, so we are just adding
	 * 15% when above a threshold
	 * 
	 * @param rotateValue
	 *            the rotateValue
	 * @return scaled rotate value
	 */
	private double scaleRotateValue(double rotateValue) {
		rotateValue *= armSubsystem.getArmLoadFactor();
		return rotateValue;
	}

	private PIDController createBasicPIDController(PIDSource src, PIDOutput out) {
		getPIDFromConfiguration();
		PIDController result = new PIDController(p, i, d, src, out, 0.01);
		return result;
	}

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

		private void configurePIDController() {
			double tolerance = inchesToTicks(prefs.getDouble(
					"dt.distance.tolerance", DRIVE_SET_DISTANCE_TOLERANCE));
			pidController.setAbsoluteTolerance(tolerance);
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
			AveragedEncoderTicksPIDSource averagedEncoder = new AveragedEncoderTicksPIDSource();
			pidController = createBasicPIDController(averagedEncoder,
					new DriveStraightPIDOutput());
			configurePIDController();
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
			getPIDFromConfiguration();
			getPIDAttrFromConfiguration();
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

	@Override
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

	/**
	 * the new arcade drive, for 2015 version, see github history
	 * 
	 * @param moveValue
	 * @param rotateValue
	 */
	public void arcadeDrive(double moveValue, double rotateValue) {
		if (!disable) {

			// This is one way to adjust drive power, with the trigger button
			// and the throttle slider on the joystick....gotta have moarPowah!

			scaleThrottle();

			// Here is another add....
			rotateValue = scaleRotateValue(rotateValue);

			if (!moarPowah) { // back 'er down unless you pull the trigger
				moveValue *= tempThrottle;
				rotateValue *= tempThrottle;
			}

			calculateMotorSpeed(moveValue, rotateValue);

			frontLeft.set(leftMotorSpeed);
			frontRight.set(rightMotorSpeed);
		}
	}

	double skim(double v) {
		// gain determines how much to skim off the top
		if (v > 1.0)
			return -((v - 1.0) * 0.5);
		else if (v < -1.0)
			return -((v + 1.0) * 0.5);
		return 0;
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

	@Override
	public void displayValues(Display display) {
		display.putNumber("Velocity Range", velocityRange);
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
