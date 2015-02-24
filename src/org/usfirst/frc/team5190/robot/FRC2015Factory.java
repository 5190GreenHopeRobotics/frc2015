package org.usfirst.frc.team5190.robot;

import java.util.List;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.CherryPickerSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;

public class FRC2015Factory {

	private static FRC2015Factory instance;
	static {
		instance = new FRC2015Factory();
	}

	protected DriveTrainSubsystem driveTrain;
	protected List<Throwable> error;
	protected NavigationSubsystem sensors;
	protected ArmSubsystem arm;
	protected CherryPickerSubsystem cherry;

	private FRC2015Factory() {
		init();
	}

	protected void init() {
		try {
			driveTrain = new DriveTrainSubsystem();
			sensors = new NavigationSubsystem();
			arm = new ArmSubsystem();
			cherry = new CherryPickerSubsystem();
		} catch (Throwable w) {
			System.err.println(w.getMessage() + "src:" + w.getStackTrace());
		}
	}

	public DriveTrainSubsystem getDriveTrain() {
		return driveTrain;
	}

	public void setDriveTrain(DriveTrainSubsystem driveTrain) {
		this.driveTrain = driveTrain;
	}

	public NavigationSubsystem getSensors() {
		return sensors;
	}

	public static FRC2015Factory getInstance() {
		return instance;
	}

	public List<Throwable> getError() {
		return error;
	}

	public ArmSubsystem getArm() {
		return arm;
	}

	public CherryPickerSubsystem getCherry() {
		return cherry;
	}

}
