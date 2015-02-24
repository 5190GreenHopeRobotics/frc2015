package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

public interface SubsystemFactory {

	public Throwable[] getError();

	public DriveTrainSubsystem getDriveTrain();

	public NavigationSubsystem getSensors();

	public Subsystem getSubsystem(String name);

	public <T extends Subsystem> void create(Class<T> type);
}
