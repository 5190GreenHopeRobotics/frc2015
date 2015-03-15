package org.usfirst.frc.team5190.robot.oi;

import edu.wpi.first.wpilibj.Preferences;

public class ScaleInputsOI implements OI {

	private Preferences pref;
	private double forwardReverseScalingValue;
	private double leftRightScalingValue;
	private double armScalingValue;
	private double cherryPickerScalingValue;
	private double pawlScalingValue;
	private OI sourceOI;

	public ScaleInputsOI(double defaultScalingValue, OI sourceOI) {
		forwardReverseScalingValue = defaultScalingValue;
		forwardReverseScalingValue = pref.getDouble(
				"control.axis.scaling.forwardreverse", 0.7);

		leftRightScalingValue = defaultScalingValue;
		leftRightScalingValue = pref.getDouble(
				"control.axis.scaling.leftright", 0.7);

		armScalingValue = defaultScalingValue;
		armScalingValue = pref.getDouble("control.axis.scaling.arm", 0.5);

		cherryPickerScalingValue = defaultScalingValue;
		cherryPickerScalingValue = pref.getDouble(
				"control.axis.scaling.cherrypicker", 0.3);

		pawlScalingValue = defaultScalingValue;
		pawlScalingValue = pref.getDouble("control.axis.scaling.pawl", 0.3);

		this.sourceOI = sourceOI;
	}

	public void setForwardReverseScalingValue(double forwardReverseScalingValue) {
		this.forwardReverseScalingValue = forwardReverseScalingValue;
	}

	public void setLeftRightScalingValue(double leftRightScalingValue) {
		this.leftRightScalingValue = leftRightScalingValue;
	}

	public void setArmScalingValue(double armScalingValue) {
		this.armScalingValue = armScalingValue;
	}

	public void setCherryPickerScalingValue(double cherryPickerScalingValue) {
		this.cherryPickerScalingValue = cherryPickerScalingValue;
	}

	public void setPawlScalingValue(double pawlScalingValue) {
		this.pawlScalingValue = pawlScalingValue;
	}

	@Override
	public double getForwardReverseAxis() {
		return sourceOI.getForwardReverseAxis() * forwardReverseScalingValue;
	}

	@Override
	public double getLeftRightAxis() {
		return sourceOI.getLeftRightAxis() * leftRightScalingValue;
	}

	@Override
	public double getArmAxis() {
		return sourceOI.getArmAxis() * armScalingValue;
	}

	@Override
	public double getCherryPickerAxis() {
		return sourceOI.getCherryPickerAxis() * cherryPickerScalingValue;
	}

	@Override
	public double getPawlAxis() {
		return sourceOI.getPawlAxis() * pawlScalingValue;
	}

}
