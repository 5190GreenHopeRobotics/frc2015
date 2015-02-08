package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Adds hooks to a subsystem that needs to do initialization or cleanup when
 * autonomous or teleop is enabled or disabled.
 * 
 */
public class LifecycleSubsystem extends Subsystem {

	public LifecycleSubsystem(String name) {
		super(name);
		LifecycleSubsystemManager.getInstance().addSubsystem(this);
	}

	/**
	 * Provided default implementation for this method from parent
	 * {@link Subsystem}. Does nothing.
	 */
	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * Called once when either the autonomous or teleop modes are enabled.
	 * Called before the {@link #autonomousInit()} or {@link #teleopInit()}
	 * methods are called.
	 */
	protected void init() {
	}

	/**
	 * Called once when the autonomous mode is enabled
	 */
	protected void autonomousInit() {
	}

	/**
	 * Called once when the teleop mode is enabled
	 */
	protected void teleopInit() {
	}

	/**
	 * Called once when either the teleop or autonomous modes
	 */
	protected void disable() {
	}
}
