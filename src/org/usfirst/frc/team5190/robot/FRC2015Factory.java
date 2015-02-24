package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.CherryPickerSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;

public class FRC2015Factory {

	private static DriveTrainSubsystem driveTrain;
	private static NavigationSubsystem sensors;
	private static ArmSubsystem arm;
	private static CherryPickerSubsystem cherry;

	private FRC2015Factory() {
	}

	static {
		try {
			driveTrain = new DriveTrainSubsystem();
			sensors = new NavigationSubsystem();
			arm = new ArmSubsystem();
			cherry = new CherryPickerSubsystem();
		} catch (Throwable t) {
			System.err.println(t.getMessage() + "src:" + t.getStackTrace());
			throw t;
		}
	}

	public static DriveTrainSubsystem getDriveTrain() {
		return driveTrain;
	}

	public static NavigationSubsystem getSensors() {
		return sensors;
	}

	public static ArmSubsystem getArm() {
		return arm;
	}

	public static CherryPickerSubsystem getCherry() {
		return cherry;
	}
}
