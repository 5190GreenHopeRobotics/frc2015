package org.usfirst.frc.team5190.robot.motor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

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

	private PIDController pidController;
	private double p;
	private double i;
	private double d;

	public SmartSpeedController(SpeedController speedController) {
		this.speedController = speedController;
	}

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

	public ControlMode getControlMode() {
		return controlMode;
	}

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

	public Potentiometer getPotentiometer() {
		return potentiometer;
	}

	public void setPotentiometer(Potentiometer potentiometer) {
		this.potentiometer = potentiometer;
	}

	public Encoder getEncoder() {
		return encoder;
	}

	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
		if (controlMode == ControlMode.Distance) {
			encoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
		} else if (controlMode == ControlMode.Speed) {
			encoder.setPIDSourceParameter(PIDSourceParameter.kRate);
		}
	}

	public boolean isForwardLimitSwitchEnabled() {
		return forwardLimitSwitchEnabled;
	}

	public void setForwardLimitSwitchEnabled(boolean forwardLimitSwitchEnabled) {
		this.forwardLimitSwitchEnabled = forwardLimitSwitchEnabled;
	}

	public boolean isReverseLimitSwitchEnabled() {
		return reverseLimitSwitchEnabled;
	}

	public void setReverseLimitSwitchEnabled(boolean reverseLimitSwitchEnabled) {
		this.reverseLimitSwitchEnabled = reverseLimitSwitchEnabled;
	}

	public boolean isForwardLimitSwitchNormallyOpen() {
		return forwardLimitSwitchNormallyOpen;
	}

	public void setForwardLimitSwitchNormallyOpen(
			boolean forwardLimitSwitchNormallyOpen) {
		this.forwardLimitSwitchNormallyOpen = forwardLimitSwitchNormallyOpen;
	}

	public boolean isReverseLimitSwitchNormallyOpen() {
		return reverseLimitSwitchNormallyOpen;
	}

	public void setReverseLimitSwitchNormallyOpen(
			boolean reverseLimitSwitchNormallyOpen) {
		this.reverseLimitSwitchNormallyOpen = reverseLimitSwitchNormallyOpen;
	}

	public double getForwardSoftLimit() {
		return forwardSoftLimit;
	}

	public void setForwardSoftLimit(double forwardSoftLimit) {
		this.forwardSoftLimit = forwardSoftLimit;
	}

	public double getReverseSoftLimit() {
		return reverseSoftLimit;
	}

	public void setReverseSoftLimit(double reverseSoftLimit) {
		this.reverseSoftLimit = reverseSoftLimit;
	}

	public boolean isForwardSoftLimitEnabled() {
		return forwardSoftLimitEnabled;
	}

	public void setForwardSoftLimitEnabled(boolean forwardSoftLimitEnabled) {
		this.forwardSoftLimitEnabled = forwardSoftLimitEnabled;
	}

	public boolean isReverseSoftLimitEnabled() {
		return reverseSoftLimitEnabled;
	}

	public void setReverseSoftLimitEnabled(boolean reverseSoftLimitEnabled) {
		this.reverseSoftLimitEnabled = reverseSoftLimitEnabled;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setPID(double p, double i, double d) {
		this.p = p;
		this.i = i;
		this.d = d;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getI() {
		return i;
	}

	public void setI(double i) {
		this.i = i;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return 0;
	}

	@Override
	public void set(double speed, byte syncGroup) {
		set(speed);
	}

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
