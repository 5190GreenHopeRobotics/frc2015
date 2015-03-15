package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.PIDSource;

public class LidarDistancePIDSource implements PIDSource {
	private NavigationSubsystem navigationSubsystem = NavigationSubsystem
			.getInstance();

	LidarDistancePIDSource() {
	}

	public double pidGet() {
		return navigationSubsystem.getLidarDistanceFromObject();
	}

}
