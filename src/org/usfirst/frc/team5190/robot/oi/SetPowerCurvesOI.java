package org.usfirst.frc.team5190.robot.oi;

public class SetPowerCurvesOI implements OI {

	private OI sourceOI;

	public SetPowerCurvesOI(OI sourceOI) {
		this.sourceOI = sourceOI;
	}

	@Override
	public double getForwardReverseAxis() {
		return OIUtils.scaledCubic(0.8, sourceOI.getForwardReverseAxis());
	}

	@Override
	public double getLeftRightAxis() {
		return OIUtils.scaledCubic(0.8, sourceOI.getLeftRightAxis());
	}

	@Override
	public double getArmAxis() {
		return OIUtils.scaledCubic(0.8, sourceOI.getArmAxis());
	}

	@Override
	public double getCherryPickerAxis() {
		return OIUtils.scaledCubic(0.8, sourceOI.getCherryPickerAxis());
	}

	@Override
	public double getPawlAxis() {
		return OIUtils.scaledCubic(0.8, sourceOI.getPawlAxis());
	}

}
