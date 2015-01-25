package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.Servo;





public class cameraMovement {
	
	
	//Setup the Servo
	public Servo cameraServo = new Servo(0);
	
	public void moveServo(){
		
		
		cameraServo.set(0.2);
		cameraServo.setAngle(0.45);
		
	}
	
	
	

}
