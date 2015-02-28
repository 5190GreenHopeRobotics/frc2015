package org.usfirst.frc.team5190.robot.subsystems;

import java.util.Collection;
import java.util.LinkedList;

import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.motor.SmartSpeedController;
import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class PawlSubsystem extends Subsystem implements Displayable {
	private static PawlSubsystem instance;
	private SmartSpeedController smartController;
	private Jaguar jaguar;
	private Potentiometer potentiometer;

	private PawlSubsystem() {
		smartController = new SmartSpeedController(new Jaguar(RobotMap.PAWL_JAGUAR_PORT));
		potentiometer = new AnalogPotentiometer(
				RobotMap.PAWL_POTENTIMETER_PORT, 40, 0);
	}

	@Override
	protected void initDefaultCommand() {
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
		return potentiometer.get();
	}

	public void movePawl(double speed) {
		smartController.set(speed);
	}

	public void stopPawl() {
		smartController.set(0);
	}

	@Override
	public Collection<Pair<String, Boolean>> getBooleanValue() {
		return null;
	}

	@Override
	public Collection<Pair<String, Double>> getDecimalValues() {
		LinkedList<Pair<String, Double>> values = new LinkedList<Pair<String, Double>>();
		values.add(new Pair<String, Double>("Pawl Angle", potentiometer.get()));
		return values;
	}

}
