package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

public class ArmPIDOutput implements PIDOutput {
	private SpeedController armJaguar1;
	private SpeedController armJaguar2;

	public ArmPIDOutput(SpeedController armJaguar1, SpeedController armJaguar2) {
		this.armJaguar1 = armJaguar1;
		this.armJaguar2 = armJaguar2;
	}

	@Override
	public void pidWrite(double power) {
		armJaguar1.set(power);
		armJaguar2.set(power);
	}

}
