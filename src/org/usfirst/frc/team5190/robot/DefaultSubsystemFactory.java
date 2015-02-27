package org.usfirst.frc.team5190.robot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DefaultSubsystemFactory implements SubsystemFactory {

	protected static DefaultSubsystemFactory factory;
	protected DriveTrainSubsystem driveTrain;
	protected List<Throwable> error;
	protected NavigationSubsystem sensors;
	protected Map<Class<?>, Subsystem> nonGenericSystem;
	static {
		factory = new DefaultSubsystemFactory();
	}

	public DefaultSubsystemFactory getInstance() {
		return factory;
	}

	private DefaultSubsystemFactory() {
		error = new LinkedList<Throwable>();
		nonGenericSystem = new HashMap<Class<?>, Subsystem>();
		init();
	}

	protected void init() {
		try {
			driveTrain = new DriveTrainSubsystem();

		} catch (Throwable e) {
			System.out.println("DRIVE TRAIN NOT FUNCTIONAL:" + e.getMessage()
					+ ":" + e.getStackTrace());
			error.add(e);
		}
		try {
			sensors = new NavigationSubsystem();
		} catch (Throwable e) {
			System.out.println("SENSORS NOT FUNCTIONAL:" + e.getMessage() + ":"
					+ e.getStackTrace());
			error.add(e);
		}
	}

	@Override
	public DriveTrainSubsystem getDriveTrain() {
		return driveTrain;
	}

	@Override
	public NavigationSubsystem getSensors() {
		// TODO Auto-generated method stub
		return sensors;
	}

	@Override
	public Subsystem getSubsystem(String name) {
		try {
			return nonGenericSystem
					.get(Class
							.forName("org.usfirst.frc.team5190.robot.subsystem."
									+ name));
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	@Override
	public Throwable[] getError() {
		// TODO Auto-generated method stub
		return (Throwable[]) error.toArray();
	}

	@Override
	public <T extends Subsystem> void create(Class<T> type) {
		if (nonGenericSystem.containsKey(type)) {
			return;
		}
		try {
			Subsystem toAdd = type.newInstance();
			nonGenericSystem.put(type, toAdd);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			error.add(e);
			return;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			error.add(e);
			return;
		}
	}
}
