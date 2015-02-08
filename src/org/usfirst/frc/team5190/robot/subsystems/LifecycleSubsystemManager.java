package org.usfirst.frc.team5190.robot.subsystems;

import java.util.ArrayList;

public class LifecycleSubsystemManager {
	private static LifecycleSubsystemManager instance;
	private ArrayList<LifecycleSubsystem> subsystems;

	/** Construct an Observable with zero Observers. */

	private LifecycleSubsystemManager() {
		subsystems = new ArrayList<LifecycleSubsystem>();
	}

	public static LifecycleSubsystemManager getInstance() {
		if (instance == null) {
			instance = new LifecycleSubsystemManager();
		}
		return instance;
	}

	public void addSubsystem(LifecycleSubsystem subsystem) {
		if (subsystem == null)
			throw new NullPointerException();
		if (!subsystems.contains(subsystem)) {
			subsystems.add(subsystem);
		}
	}

	private void init() {
		for (LifecycleSubsystem subsystem : subsystems) {
			subsystem.init();
		}
	}

	public void autonomousInit() {
		init();
		for (LifecycleSubsystem subsystem : subsystems) {
			subsystem.autonomousInit();
		}
	}

	public void teleopInit() {
		init();
		for (LifecycleSubsystem subsystem : subsystems) {
			subsystem.teleopInit();
		}
	}

	public void disable() {
		for (LifecycleSubsystem subsystem : subsystems) {
			subsystem.disable();
		}
	}
}