package org.usfirst.frc.team5190.robot.motor;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;

public class CanTalonSRXSpeedController implements SpeedController {

	private CanTalonSRX canTalonSRX;

	public CanTalonSRXSpeedController(CanTalonSRX canTalonSRX) {
		this.canTalonSRX = canTalonSRX;
	}

	@Override
	public void pidWrite(double output) {
		canTalonSRX.Set(output);
	}

	@Override
	public double get() {
		return 0;
	}

	@Override
	public void set(double speed, byte syncGroup) {
		canTalonSRX.Set(speed);
	}

	@Override
	public void set(double speed) {
		canTalonSRX.Set(speed);
	}

	@Override
	public void disable() {
		canTalonSRX.Set(0);
	}

}
