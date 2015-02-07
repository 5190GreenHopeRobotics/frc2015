package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Prototypearm extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Victor armvictor = new Victor(8);
	private DigitalInput limitswitch = new DigitalInput(1);
	private final double motorspeed = 0.5;
	public boolean maximized = false;
	public boolean minimized = false;
	public boolean started = true;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public boolean getlimitswitch() {
		return limitswitch.get();
	}

	public void raisearm() {
		armvictor.set(motorspeed);
	}

	public void lowerarm() {
		armvictor.set(-motorspeed);
	}

	public void stoparm() {
		armvictor.set(0);
	}
}
