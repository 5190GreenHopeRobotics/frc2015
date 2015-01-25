package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CameraServoSubsystem extends Subsystem {
    
    public Servo cameraServo = new Servo(9);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void scanField(){
    	
    	System.out.println("Called");
    	
    	cameraServo.set(0.2);
    	cameraServo.setAngle(180);
    	
    }
    
    
}

