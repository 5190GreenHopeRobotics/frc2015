package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.PawlJoystickCommand;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class PawlSubsystem extends Subsystem implements Displayable {
	private static PawlSubsystem instance;

	private Jaguar jaguar;
	private Potentiometer pawlPotentiometer;
	private Potentiometer motorPotentiometer;
	private DigitalInput clutchEngagedSwitch;

	private PawlSubsystem() {
		jaguar = new Jaguar(RobotMap.PAWL_JAGUAR_PORT);
		pawlPotentiometer = new AnalogPotentiometer(
				RobotMap.PAWL_POTENTIMETER_PORT, 265.3888684113527, -174.15);
		motorPotentiometer = new AnalogPotentiometer(
				RobotMap.PAWL_MOTOR_POTENTIMETER_PORT, -258.0645161290323,
				218.65);
		clutchEngagedSwitch = new DigitalInput(
				RobotMap.PAWL_CLUTCH_ENAGED_SWITCH_PORT);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new PawlJoystickCommand());
	}

	public static PawlSubsystem getInstance() {
		if (instance == null) {
			try {
				instance = new PawlSubsystem();
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
		return instance;
	}

	public double getAngle() {
		return pawlPotentiometer.get();
	}

	public void movePawl(double speed) {
		jaguar.set(speed);
	}

	public void stopPawl() {
		jaguar.set(0);
	}

	@Override
	public void displayValues(Display display) {
		display.putNumber("Pawl Angle", pawlPotentiometer.get());
		display.putNumber("Pawl Motor Angle", motorPotentiometer.get());
		display.putBoolean("Pawl Clutch Engaged", clutchEngagedSwitch.get());
	}

}
