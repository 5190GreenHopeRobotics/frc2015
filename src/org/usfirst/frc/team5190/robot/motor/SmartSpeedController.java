package org.usfirst.frc.team5190.robot.motor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * 
 * @author dan, sdai a speed controller wrapper that is suppose to turn every
 *         speed controller into CANTalon speed controller with pid and follower
 */
public class SmartSpeedController implements SpeedController {

	public enum ControlMode {
		PercentVBus, Angle, Speed, Distance, Follower
	}

	private SpeedController speedController;
	private DigitalInput forwardLimitSwitch;
	private DigitalInput reverseLimitSwitch;
	private Potentiometer potentiometer;
	private Encoder encoder;

	private ControlMode controlMode = ControlMode.PercentVBus;
	private boolean forwardLimitSwitchEnabled = false;
	private boolean reverseLimitSwitchEnabled = false;
	private boolean forwardLimitSwitchNormallyOpen = true;
	private boolean reverseLimitSwitchNormallyOpen = true;
	private double forwardSoftLimit;
	private double reverseSoftLimit;
	private boolean forwardSoftLimitEnabled = false;
	private boolean reverseSoftLimitEnabled = false;
	private boolean disabled;
	private SmartSpeedController masterController;
	private double currentValue;
	private PIDController pidController;
	private double p;
	private double i;
	private double d;
	 

	/**
	 * init the smart controller with a regular controller
	 * 
	 * @param speedController
	 *            any speed controller
	 */
	public SmartSpeedController(SpeedController speedController) {
		this.speedController = speedController;
	}

	/**
	 * init the smart speed controller with regular controller as well as the
	 * limit switch for brake
	 * 
	 * @param speedController
	 *            the regular controller
	 * @param forwardLimitSwitch
	 *            the limit switch for the max
	 * @param reverseLimitSwitch
	 *            the limit switch for the min
	 */
	public SmartSpeedController(SpeedController speedController,
			DigitalInput forwardLimitSwitch, DigitalInput reverseLimitSwitch) {
		this(speedController);
		this.forwardLimitSwitch = forwardLimitSwitch;
		this.reverseLimitSwitch = reverseLimitSwitch;
		if (forwardLimitSwitch != null) {
			forwardLimitSwitchEnabled = true;
		}
		if (reverseLimitSwitch != null) {
			reverseLimitSwitchEnabled = true;
		}
	}

	/**
	 * get the current mode for this controller
	 * 
	 * @return the mode
	 */
	public ControlMode getControlMode() {
		return controlMode;
	}

	/**
	 * set the control mode for the speed controller
	 * 
	 * @param controlMode
	 *            the mode of the controller
	 */
	public void setControlMode(ControlMode controlMode) {
		this.controlMode = controlMode;
		if (encoder != null) {
			if (controlMode == ControlMode.Distance) {
				encoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
			} else if (controlMode == ControlMode.Speed) {
				encoder.setPIDSourceParameter(PIDSourceParameter.kRate);
			}
		}
	}

	/**
	 * get the potentiometer for this controller
	 * 
	 * @return the pot
	 */
	public Potentiometer getPotentiometer() {
		return potentiometer;
	}

	/**
	 * set the potentiometer for this controller
	 * 
	 * @param potentiometer
	 *            the potentiometer to set
	 */
	public void setPotentiometer(Potentiometer potentiometer) {
		this.potentiometer = potentiometer;
	}

	/**
	 * get the encoder for this speed controller
	 * 
	 * @return the encoder
	 */
	public Encoder getEncoder() {
		return encoder;
	}

	/**
	 * set the encoder for this controller
	 * 
	 * @param encoder
	 *            the encoder to set
	 */
	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
		if (controlMode == ControlMode.Distance) {
			encoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
		} else if (controlMode == ControlMode.Speed) {
			encoder.setPIDSourceParameter(PIDSourceParameter.kRate);
		}
	}

	/**
	 * get whether the forward limit switch will have a effect on the controller
	 * 
	 * @return is it enabled
	 */
	public boolean isForwardLimitSwitchEnabled() {
		return forwardLimitSwitchEnabled;
	}

	/**
	 * enable or disable the forward limit switch
	 * 
	 * @param forwardLimitSwitchEnabled
	 *            the forward limit switch enable or disable
	 */
	public void setForwardLimitSwitchEnabled(boolean forwardLimitSwitchEnabled) {
		this.forwardLimitSwitchEnabled = forwardLimitSwitchEnabled;
	}

	/**
	 * get whether the reverse limit switch is enabled
	 * 
	 * @return is it enabled
	 */
	public boolean isReverseLimitSwitchEnabled() {
		return reverseLimitSwitchEnabled;
	}

	/**
	 * enable or disable the reverse limit switch
	 * 
	 * @param reverseLimitSwitchEnabled
	 *            enable or disable it
	 */
	public void setReverseLimitSwitchEnabled(boolean reverseLimitSwitchEnabled) {
		this.reverseLimitSwitchEnabled = reverseLimitSwitchEnabled;
	}

	/**
	 * check the normal status for the forward limitswitch, whether it is
	 * normally open
	 * 
	 * @return if it is normally open
	 */
	public boolean isForwardLimitSwitchNormallyOpen() {
		return forwardLimitSwitchNormallyOpen;
	}

	/**
	 * set if the forward limit switch is normally open
	 * 
	 * @param forwardLimitSwitchNormallyOpen
	 *            is it normally open
	 */
	public void setForwardLimitSwitchNormallyOpen(
			boolean forwardLimitSwitchNormallyOpen) {
		this.forwardLimitSwitchNormallyOpen = forwardLimitSwitchNormallyOpen;
	}

	/**
	 * get if the reverse limit switch is normally open
	 * 
	 * @return is it normally open
	 */
	public boolean isReverseLimitSwitchNormallyOpen() {
		return reverseLimitSwitchNormallyOpen;
	}

	/**
	 * set if the reverse limit switch is normally open
	 * 
	 * @param reverseLimitSwitchNormallyOpen
	 *            is it normally open
	 */
	public void setReverseLimitSwitchNormallyOpen(
			boolean reverseLimitSwitchNormallyOpen) {
		this.reverseLimitSwitchNormallyOpen = reverseLimitSwitchNormallyOpen;
	}

	/**
	 * get the soft limit for this controller
	 * 
	 * @return the limit, depends on the mode
	 */
	public double getForwardSoftLimit() {
		return forwardSoftLimit;
	}

	/**
	 * set the forward limit
	 * 
	 * @param forwardSoftLimit
	 *            depends on the mode
	 */
	public void setForwardSoftLimit(double forwardSoftLimit) {
		this.forwardSoftLimit = forwardSoftLimit;
	}

	/**
	 * get the reverse limit
	 * 
	 * @return depends on the mode
	 */
	public double getReverseSoftLimit() {
		return reverseSoftLimit;
	}

	/**
	 * set the reverse limit
	 * 
	 * @param reverseSoftLimit
	 *            depends on the mode
	 */
	public void setReverseSoftLimit(double reverseSoftLimit) {
		this.reverseSoftLimit = reverseSoftLimit;
	}

	/**
	 * is forward limit enabled
	 * 
	 * @return the enable status
	 */
	public boolean isForwardSoftLimitEnabled() {
		return forwardSoftLimitEnabled;
	}

	/**
	 * enable or disable forward soft limit
	 * 
	 * @param forwardSoftLimitEnabled
	 *            enable or disable
	 */
	public void setForwardSoftLimitEnabled(boolean forwardSoftLimitEnabled) {
		this.forwardSoftLimitEnabled = forwardSoftLimitEnabled;
	}

	/**
	 * is reverse soft limit enabled
	 * 
	 * @return the status of the reverse soft limit
	 */
	public boolean isReverseSoftLimitEnabled() {
		return reverseSoftLimitEnabled;
	}

	/**
	 * set the reverse soft limit status, enable or disable
	 * 
	 * @param reverseSoftLimitEnabled
	 *            enable/disable
	 */
	public void setReverseSoftLimitEnabled(boolean reverseSoftLimitEnabled) {
		this.reverseSoftLimitEnabled = reverseSoftLimitEnabled;
	}

	/**
	 * is the controller disabled
	 * 
	 * @return is it disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * set the p, i, and d for the pid controller
	 * 
	 * @param p
	 *            p
	 * @param i
	 *            i
	 * @param d
	 *            d
	 */
	public void setPID(double p, double i, double d) {
		this.p = p;
		this.i = i;
		this.d = d;
	}

	/**
	 * get the P value
	 * 
	 * @return p
	 */
	public double getP() {
		return p;
	}

	/**
	 * set the P value
	 * 
	 * @param p
	 *            p
	 */
	public void setP(double p) {
		this.p = p;
	}

	/**
	 * get the I value
	 * 
	 * @return the i value
	 */
	public double getI() {
		return i;
	}

	/**
	 * set the I value
	 * 
	 * @param i
	 *            i
	 */
	public void setI(double i) {
		this.i = i;
	}

	/**
	 * get the D value
	 * 
	 * @return the d
	 */
	public double getD() {
		return d;
	}

	/**
	 * set the d value
	 * 
	 * @param d
	 *            d
	 */
	public void setD(double d) {
		this.d = d;
	}

	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return currentValue;
	}

	@Override
	public void set(double speed, byte syncGroup) {
		set(speed);
	}

	/**
	 * get the current position depends on the mode
	 * 
	 * @return distance if it is in DIstance, pot reading if int Angle
	 */
	public double getPosition() {
		if (controlMode == ControlMode.Distance) {
			return encoder.getDistance();
		} else if (controlMode == ControlMode.Angle) {
			return potentiometer.get();
		} else {
			return 0;
		}
	}

	@Override
	public void set(double value) {
		this.currentValue = value;
		if (disabled || controlMode == ControlMode.Follower) {
			return;
		}
		if (softLimitReachedForDirection(value)) {
			speedController.set(0);
		} else if (limitSwitchActivatedForDirection(value)) {
			speedController.set(0);
		} else {
			if (controlMode == ControlMode.PercentVBus) {
				speedController.set(value);
			} else if (controlMode == ControlMode.Angle) {
				createDistancePid();
				pidController.setSetpoint(value);
			} else if (controlMode == ControlMode.Distance) {
				createAnglePid();
				pidController.setSetpoint(value);
			} else if (controlMode == ControlMode.Speed) {
				speedController.set(value);
			}

		}
	}

	/**
	 * is the pid on target
	 * 
	 * @return is it on target
	 */
	public boolean isOnTarget() {
		return pidController.onTarget();
	}

	private boolean softLimitReachedForDirection(double speed) {
		if (forwardSoftLimitEnabled && speed > 0) {
			return getPosition() >= forwardSoftLimit;
		}
		if (reverseSoftLimitEnabled && speed < 0) {
			return getPosition() <= reverseSoftLimit;
		}
		return false;
	}

	private boolean limitSwitchActivatedForDirection(double speed) {
		if (forwardLimitSwitchEnabled && speed > 0) {
			return forwardLimitSwitchNormallyOpen ^ forwardLimitSwitch.get();
		}
		if (reverseLimitSwitchEnabled && speed < 0) {
			return reverseLimitSwitchNormallyOpen ^ reverseLimitSwitch.get();
		}
		return false;
	}

	protected void createDistancePid() {
		pidController = new PIDController(p, i, d, encoder, speedController);
	}

	protected void createAnglePid() {
		pidController = new PIDController(p, i, d, potentiometer,
				speedController);
	}

	@Override
	public void disable() {
		disabled = true;
		speedController.disable();
	}

}
