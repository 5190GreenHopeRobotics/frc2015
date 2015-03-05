package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.ScaledControl;

public class SetPowerCurvesOI implements OI {

	private OI sourceOI;

	public SetPowerCurvesOI(OI sourceOI) {
		this.sourceOI = sourceOI;
	}

	@Override
	public double getForwardReverseAxis() {
		return ScaledControl.squared(sourceOI.getForwardReverseAxis());
	}

	@Override
	public double getLeftRightAxis() {
		return ScaledControl.squared(sourceOI.getLeftRightAxis());
	}

	@Override
	public double getArmAxis() {
		return ScaledControl.squared(sourceOI.getArmAxis());
	}

	@Override
	public double getCherryPickerAxis() {
		return ScaledControl.squared(sourceOI.getCherryPickerAxis());
	}

	@Override
	public double getPawlAxis() {
		return ScaledControl.squared(sourceOI.getPawlAxis());
	}

	@Override
	public boolean zeroPawlButton() {
		return false;
	}

}
