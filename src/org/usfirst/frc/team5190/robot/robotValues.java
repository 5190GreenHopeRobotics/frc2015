package org.usfirst.frc.team5190.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class robotValues {
	
	Robot robot = new Robot();
	
	
	public void Value(){
		
		SmartDashboard.getBoolean("Robot State", robot.RobotIsEnabled);
		SmartDashboard.putBoolean("Robot State", robot.RobotIsEnabled);
	}
	
	
}
