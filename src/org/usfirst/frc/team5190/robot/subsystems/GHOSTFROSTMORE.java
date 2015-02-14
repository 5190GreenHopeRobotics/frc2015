package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GHOSTFROSTMORE extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Jaguar armJaguar = new Jaguar(8);
	private DigitalInput limitswitch = new DigitalInput(1);
	private DigitalInput topLimitSwitch = new DigitalInput(2);
	private final double motorspeed = 0.5;
	public boolean maximized = false;
	public boolean minimized = false;
	public boolean started = true;

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public boolean getlimitswitch() {
		return limitswitch.get();
	}

	public void raiseArm() {
		armJaguar.set(0.5);
	}

	public void lowerArm() {
		armJaguar.set(-motorspeed);
	}

	public void joystickControl(Joystick stick) {

		if (stick.getY() < 0 && !limitswitch.get()) {
			stopArm();
		} else if (stick.getY() > 0 && !topLimitSwitch.get()) {
			stopArm();
		} else {
			armJaguar.set(stick.getY());
		}
	}

	public void stopArm() {
		armJaguar.set(0);
	}
}
