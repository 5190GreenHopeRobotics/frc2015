package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

public class ArmDrive implements PIDOutput {
	private SpeedController armJaguar1;
	private SpeedController armJaguar2;
	private DigitalInput armMaxLimitSwitch;
	private DigitalInput armMinLimitSwitch;

	public ArmDrive(SpeedController armJaguar1, SpeedController armJaguar2,
			DigitalInput armMaxLimitSwitch, DigitalInput armMinLimitSwitch) {
		this.armJaguar1 = armJaguar1;
		this.armJaguar2 = armJaguar2;
		this.armMaxLimitSwitch = armMaxLimitSwitch;
		this.armMinLimitSwitch = armMinLimitSwitch;
	}

	@Override
	public void pidWrite(double power) {
		set(power);
	}

	public void set(double power) {
		if (power < 0 && !armMinLimitSwitch.get()) {
			stopArm();
		} else if (power > 0 && !armMaxLimitSwitch.get()) {
			stopArm();
		} else {
			armJaguar1.set(power);
			armJaguar2.set(-power);
		}
	}

	public void stopArm() {
		armJaguar1.set(0);
		armJaguar2.set(0);
	}

}
