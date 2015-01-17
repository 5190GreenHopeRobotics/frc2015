package org.usfirst.frc.team5190.robot;

import edu.wpi.first.wpilibj.CameraServer;

public class Camera {
	
	CameraServer serv;
	
	
	{
		
    serv = CameraServer.getInstance();
    serv.setQuality(50);
    serv.startAutomaticCapture("cam0");
		
	}
	



}
