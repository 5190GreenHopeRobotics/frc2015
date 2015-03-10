package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.PIDSource;

public class RobotHeadingPIDSource implements PIDSource {

	private NavigationSubsystem navigationSubsystem = NavigationSubsystem
			.getInstance();

	RobotHeadingPIDSource() {
	}

	@Override
	public double pidGet() {
		return navigationSubsystem.getYaw();
	}

}
