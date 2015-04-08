package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.dashboard.SmartDashBoardDisplayer;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.ArmJoystickCommand;
import org.usfirst.frc.team5190.robot.config.Configurable;
import org.usfirst.frc.team5190.robot.config.ConfigurationManager;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Preferences;

/**
 * the arm subsystem
 */
public class ArmSubsystem extends LifecycleSubsystem implements Displayable,
		Configurable {
	private static ArmSubsystem instance;

	private static final int ARM_RANGE = 361;
	private static final int ARM_BOTTOM_OFFSET = 206;

	private static final double ARM_SET_ANGLE_P = 12.5;
	private static final double ARM_SET_ANGLE_I = 0.05;
	private static final double ARM_SET_ANGLE_D = 9000;
	private static final int ARM_SET_ANGLE_IZONE = 10;
	private static final double ARM_SET_ANGLE_RAMP_RATE = 48;
	private static final int ARM_SET_ANGLE_PROFILE = 1;

	private static final double ARM_HOLD_P = 1.8;
	private static final double ARM_HOLD_I = 0.003;
	private static final double ARM_HOLD_D = 0;
	private static final int ARM_HOLD_IZONE = 100;
	private static final double ARM_HOLD_RAMP_RATE = 0;
	private static final int ARM_HOLD_PROFILE = 0;

	// Levels for arm corresponding with totes, current values are
	// placeholders,
	// need to acquire more math to find real values
	private CANTalon armCANTalonLeft;
	private CANTalon armCANTalonRight;
	private ControlMode controlMode;
	private int armBottomOffset;
	private int armRange;
	
	private double armLiftLoad;
	private double armLiftPower;
	private double armLiftLoadFactor;

	private Preferences prefs = Preferences.getInstance();

	public class SetArmAngle {

		private double angle;

		private SetArmAngle(double angle) {
			this.angle = armBottomOffset + angle;

			System.out.println("The Angle Given To Be Set" + this.angle);
		}

		public void start() {
			if (controlMode != ControlMode.Position) {
				armCANTalonLeft.changeControlMode(ControlMode.Position);
				controlMode = ControlMode.Position;
			}
			configureSetAnglePID();
			armCANTalonLeft.set(angle);
		}

		public void execute() {
			armCANTalonLeft.set(angle);
		}

		/**
		 * stop the pid
		 */
		public void end() {
		}
	}

	private ArmSubsystem() {
		super("ArmSubsystem");
		SmartDashBoardDisplayer.getInstance().addDisplayable(this);
		ConfigurationManager.getInstance().addConfigurable(this);

		armBottomOffset = prefs.getInt("arm.bottom.offset", ARM_BOTTOM_OFFSET);
		armRange = prefs.getInt("arm.range", ARM_RANGE);
		armBottomOffset = ARM_BOTTOM_OFFSET;
		armRange = ARM_RANGE;
		armCANTalonLeft = new CANTalon(RobotMap.ARM_TALONSRX_LEFT_CAN_ID);
		controlMode = ControlMode.PercentVbus;
		armCANTalonLeft.changeControlMode(controlMode);
		armCANTalonLeft.set(0);
		armCANTalonLeft.setFeedbackDevice(FeedbackDevice.AnalogPot);
		armCANTalonLeft.enableBrakeMode(true);
		// armCANTalonLeft.enableForwardSoftLimit(true);
		// armCANTalonLeft.enableReverseSoftLimit(true);
		armCANTalonLeft.setSafetyEnabled(false);

		armCANTalonRight = new CANTalon(RobotMap.ARM_TALONSRX_RIGHT_CAN_ID);
		armCANTalonRight.changeControlMode(ControlMode.Follower);
		armCANTalonRight.set(RobotMap.ARM_TALONSRX_LEFT_CAN_ID);
		armCANTalonRight.reverseOutput(true);
		armCANTalonRight.enableBrakeMode(true);
		armCANTalonRight.setSafetyEnabled(false);
		
		armLiftLoad = 5.0;	//default arm load
		armLiftPower = 1.0;
	}

	public static ArmSubsystem getInstance() {
		if (instance == null) {
			try {
				instance = new ArmSubsystem();
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
		return instance;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new ArmJoystickCommand());
	}

	public void moveArm(double power) {
		if (power > 0.05 || power < -0.05) {
			if (controlMode != ControlMode.PercentVbus) {
				armCANTalonLeft.changeControlMode(ControlMode.PercentVbus);
				controlMode = ControlMode.PercentVbus;
			}
			armCANTalonLeft.set(power);
		} else {
			if (controlMode != ControlMode.Position) {
				configureHoldPID();
				armCANTalonLeft.ClearIaccum();
				armCANTalonLeft.changeControlMode(ControlMode.Position);
				armCANTalonLeft.set(armCANTalonLeft.getPosition());
				controlMode = ControlMode.Position;
			}
			else{	
				//this only calculates when the arm is in hold mode	
				//but, I noticed this doesn't always run when I think it will....must
				//press on the arm and move it a bit before this runs....huh?
				
			}
			computeArmLoad();
		}
	}
	
	private void computeArmLoad() {
		double loadMinimum = 0.0;	//in lbs
		double loadMaximum = 40.0;	
		double armLiftLoadTemp;
		double armLiftLoadFilterFactor = 3.0;
		double armLiftLoadPowerFactor = .125;
		
		//Arm Load is a function of lift power and arm angle 				
		armLiftPower = 2.0 * (armCANTalonLeft.getBusVoltage() * armCANTalonLeft.getOutputCurrent());
		
		//something to get the value close to the right power adjustment at the arcadeDrive
		armLiftPower *= armLiftLoadPowerFactor;
		
		armLiftLoadTemp = armLiftPower * Math.cos(Math.toRadians(getAngleDegrees())); 
		
//		//ratio of arm length (lever arm) to front wheel lever arm
		//use this if we want actual units and not just some factor
//		armLiftLoadTemp *= 2.1;
			
		//limit it
		armLiftLoadTemp = Math.min(loadMaximum, armLiftLoadTemp);
		armLiftLoadTemp = Math.max(loadMinimum, armLiftLoadTemp);
		
		//Filter it
		armLiftLoad += (armLiftLoadTemp - armLiftLoad) / armLiftLoadFilterFactor;
		if(armLiftLoad > 1.25){
			armLiftLoadFactor = 1.15;
		}
		else{
			armLiftLoadFactor = 1.0;

		}
		
	}
	
	public double getArmLoadFactor(){	
		return armLiftLoadFactor;
	}

	/**
	 * Arm Potentiometer position value shifted by the Arm bottom offset. Will
	 * be in the
	 */
	public double getAngleDegrees() {
		double degreesFromPosition = 0.0;
		//VERY hard coded.....
		double degreeFactor = 0.33;
		double degreeOffset = -45.0;
				
		degreesFromPosition = (getAngle() * degreeFactor) + degreeOffset;
		
		//limit it
		degreesFromPosition = Math.min(60, degreesFromPosition);
		degreesFromPosition = Math.max(-60, degreesFromPosition);
				
		return degreesFromPosition;
	}
	
	/**
	 * Arm Potentiometer position value shifted by the Arm bottom offset. Will
	 * be in the
	 */
	public double getAngle() {
		return armCANTalonLeft.getPosition() - armBottomOffset;
	}

	public SetArmAngle setArmAngle(double positionAngle) {
		return new SetArmAngle(positionAngle);
	}

	private void configureSetAnglePID() {
		double p = prefs.getDouble("arm.set.angle.p", ARM_SET_ANGLE_P);
		double i = prefs.getDouble("arm.set.angle.i", ARM_SET_ANGLE_I);
		double d = prefs.getDouble("arm.set.angle.d", ARM_SET_ANGLE_D);
		double rampRate = prefs.getDouble("arm.set.angle.ramp.rate",
				ARM_SET_ANGLE_RAMP_RATE);
		int izone = prefs.getInt("arm.set.angle.izone", ARM_SET_ANGLE_IZONE);
		int profile = prefs.getInt("arm.set.angle.profile",
				ARM_SET_ANGLE_PROFILE);
		System.out.println("Set Angle P: " + p + " I: " + i + " D: " + d
				+ " rampRate: " + rampRate + " izone: " + izone + " profile: "
				+ profile);
		armCANTalonLeft.setPID(p, i, d, 0, izone, rampRate, profile);
	}

	private void configureHoldPID() {
		double p = prefs.getDouble("arm.hold.p", ARM_HOLD_P);
		double i = prefs.getDouble("arm.hold.i", ARM_HOLD_I);
		double d = prefs.getDouble("arm.hold.d", ARM_HOLD_D);
		double rampRate = prefs.getDouble("arm.hold.ramp.rate",
				ARM_HOLD_RAMP_RATE);
		int izone = prefs.getInt("arm.hold.izone", ARM_HOLD_IZONE);
		int profile = prefs.getInt("arm.hold.profile", ARM_HOLD_PROFILE);
		System.out.println("Hold P: " + p + " I: " + i + " D: " + d
				+ " rampRate: " + rampRate + " izone: " + izone + " profile: "
				+ profile);
		armCANTalonLeft.setPID(p, i, d, 0, izone, rampRate, profile);
	}

	@Override
	// Display values
	public void displayValues(Display display) {
		display.putNumber("Arm Angle", getAngle());
		display.putNumber("Arm Power", armCANTalonLeft.getSpeed());
		display.putBoolean("Arm High Hard Limit",
				armCANTalonLeft.isFwdLimitSwitchClosed());
		display.putBoolean("Arm Low Hard Limit",
				armCANTalonLeft.isRevLimitSwitchClosed());
		display.putBoolean("Arm High Soft Limit", getAngle() >= armRange);
		display.putBoolean("Arm Low Soft Limit", getAngle() <= 0);
		display.putNumber("Arm Angle Degrees", getAngleDegrees());
		display.putNumber("Arm Load Factor", getArmLoadFactor());
		display.putNumber("Arm Lift Power", armLiftPower);
		
		
	}

	@Override
	protected void init() {
	}

	@Override
	protected void disable() {
		armCANTalonLeft.changeControlMode(ControlMode.PercentVbus);
		controlMode = ControlMode.PercentVbus;
		armCANTalonLeft.set(0);
	}

	@Override
	public void updateConfiguration() {
		// int armBottomOffset = prefs.getInt("arm.bottom.offset",
		// ARM_BOTTOM_OFFSET);
		// int armRange = prefs.getInt("arm.range", ARM_RANGE);
		// if (armBottomOffset != this.armBottomOffset
		// || armRange != this.armRange) {
		// this.armBottomOffset = armBottomOffset;
		// this.armRange = armRange;
		// armCANTalonLeft.setReverseSoftLimit(armBottomOffset);
		// armCANTalonLeft.setForwardSoftLimit(armRange);
		// }
	}
}
